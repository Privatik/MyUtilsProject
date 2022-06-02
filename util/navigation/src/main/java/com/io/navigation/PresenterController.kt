package com.io.navigation

import java.util.*
import kotlin.properties.Delegates

internal class PresenterController<Key: Any>() {
    private val sharedPresenters = hashMapOf<Class<out UIPresenter>, SharedPresenterBody>()
    private val sharedScreenWithSharedPresenter = hashMapOf<Key, HashSet<Class<out UIPresenter>>>()

    private val screenWithPresenterMap = hashMapOf<Key, MutableList<PresenterBody>>()

    private val screensKeys = Stack<ScreenInfo<Key>>()

    private var currentPresenterFactory: PresenterFactory by Delegates.notNull()


    fun updateFactory(
        key: Key,
        factory: () -> PresenterFactory
    ){
        val factoryInstance = factory.invoke()

        val screenInfo = ScreenInfo(
            screen = key,
            presenterFactory = factoryInstance
        )

        screensKeys.push(screenInfo)
        updateFactoryForPresenter(factoryInstance)
    }

    private fun updateFactoryForPresenter(factory: PresenterFactory){
        if (currentPresenterFactory == factory) return
        currentPresenterFactory = factory
    }

    fun popBackStack(key: Key, pop:() -> List<Key>) {
        stopWorkPresenter(key)
        val backstack = pop()
        deleteUnnecessaryFactory(backstack)
    }

    private fun stopWorkPresenter(key: Key){
        screenWithPresenterMap.remove(key)?.forEach { presenterBody ->
            presenterBody.presenter.clear()
        }

        sharedScreenWithSharedPresenter.remove(key)?.forEach {
            val sharedBody = sharedPresenters[it]!!
            if (sharedBody.count == 1){
                val presenter = sharedPresenters.remove(it)!!
                presenter.presenter.clear()
            } else {
                sharedPresenters[it] = sharedBody.copy(count = sharedBody.count - 1)
            }
        }
    }


    private fun deleteUnnecessaryFactory(backStack: List<Key>){
        var isFind = false
        while (!isFind){
            if (screensKeys.size == 1){
                val screenInfo = screensKeys.peek()
                updateFactoryForPresenter(screenInfo.presenterFactory)
                break
            }

            val screenInfo = screensKeys.pop()
            val currentScreen = backStack.findLast { it == screenInfo.screen }
            stopWorkPresenter(screenInfo.screen)

            if (currentScreen != null){
                updateFactoryForPresenter(screenInfo.presenterFactory)
                isFind = true
            }
        }
    }

    fun <P: UIPresenter> getSharedPresenter(
        key: Key,
        clazz: Class<out UIPresenter>
    ): P {
        if (sharedPresenters.containsKey(clazz)){
            val sharedPresenter = sharedPresenters[clazz]!!
            val screenWithSharedPresenter = sharedScreenWithSharedPresenter.initializeOrGet(
                key,
                hashSetOf()
            )
            if (!screenWithSharedPresenter.contains(clazz)){
                sharedPresenters[clazz] = sharedPresenter.copy(count = sharedPresenter.count + 1)
            }

            @Suppress("UNCHECKED_CAST")
            return sharedPresenter.presenter as P
        } else {
            val presenter = currentPresenterFactory.create<P>(clazz)
            sharedPresenters[clazz] = SharedPresenterBody(presenter = presenter)
            val clazzSet = hashSetOf(clazz)
            sharedScreenWithSharedPresenter[key] = clazzSet
            presenter.build()
            return presenter
        }
    }

    fun <P: UIPresenter> getPresenter(
        key: Key,
        clazz: Class<out UIPresenter>
    ): P {
        val currentListPresenters = screenWithPresenterMap.initializeOrGet(
            key,
            mutableListOf()
        )

        val currentPresenter = currentListPresenters.findLast { it.clazz == clazz }
        if (currentPresenter != null){
            @Suppress("UNCHECKED_CAST")
            return currentPresenter.presenter as P
        } else {
            val presenter = currentPresenterFactory.create<P>(clazz)
            val presenterBody = PresenterBody(
                clazz = clazz,
                presenter = presenter
            )
            screenWithPresenterMap[key]!!.apply { add(presenterBody) }
            presenter.build()
            return presenter
        }
    }
}