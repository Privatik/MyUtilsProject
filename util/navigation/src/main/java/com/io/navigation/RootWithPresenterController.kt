package com.io.navigation

import androidx.compose.ui.graphics.Color
import ru.alexgladkov.odyssey.compose.RootController
import ru.alexgladkov.odyssey.compose.RootControllerType
import java.util.*
import kotlin.collections.HashSet

class RootWithPresenterController(
    startPresenterFactory: () -> PresenterFactory = ::emptyPresenter,
    rootControllerType: RootControllerType = RootControllerType.Root,
    backgroundColor: Color = Color.White,
): RootController(rootControllerType, backgroundColor) {

    private val currentScreenKey: String
         get() = currentScreen.value.screen.key

    private val sharedPresenters = hashMapOf<Class<out UIPresenter>, SharedPresenterBody>()
    private val sharedScreenWithSharedPresenter = hashMapOf<String, HashSet<Class<out UIPresenter>>>()

    private val screenWithPresenterMap = hashMapOf<String, MutableList<PresenterBody>>()

    private val screensKeys = Stack<ScreenInfo>()

    private var currentPresenterFactory: PresenterFactory = startPresenterFactory.invoke()

    init {
        val screenInfo = ScreenInfo(
            screen = "",
            presenterFactory = startPresenterFactory
        )

        screensKeys.push(screenInfo)
        screenWithPresenterMap[screenInfo.screen] = mutableListOf()
    }

    internal fun updateFactory(factory: () -> PresenterFactory){
        val screenInfo = ScreenInfo(
            screen = currentScreenKey,
            presenterFactory = factory
        )

        screensKeys.push(screenInfo)
        updateFactoryForPresenter(factory.invoke())
    }

    fun backToScreenWithPresenter(screen: String){
        stopWorkPresenter(currentScreenKey)
        backToScreen(screen)
        deleteUnnecessaryFactory()
    }

    override fun popBackStack() {
        stopWorkPresenter(currentScreenKey)
        super.popBackStack()
        removeLastScreenWithPresenters()
    }

    private fun deleteUnnecessaryFactory(){
        val backstack = getBackStack()

        var isFind = false
        while (!isFind){
            if (screensKeys.size == 1){
                val screenInfo = screensKeys.peek()
                updateFactoryForPresenter(screenInfo.presenterFactory())
                break
            }

            val screenInfo = screensKeys.pop()
            val currentScreen = backstack.findLast { it.key == screenInfo.screen }
            stopWorkPresenter(screenInfo.screen)

            if (currentScreen != null){
                updateFactoryForPresenter(screenInfo.presenterFactory())
                isFind = true
            }
        }
    }

    private fun updateFactoryForPresenter(factory: PresenterFactory?){
        if (factory == null) return
        if (currentPresenterFactory == factory) return
        currentPresenterFactory = factory
    }

    private fun stopWorkPresenter(screen: String){
        screenWithPresenterMap.remove(screen)?.forEach { presenterBody ->
            presenterBody.presenter.clear()
        }

        sharedScreenWithSharedPresenter.remove(screen)?.forEach {
            val sharedBody = sharedPresenters[it]!!
            if (sharedBody.count == 1){
                val presenter = sharedPresenters.remove(it)!!
                presenter.presenter.clear()
            } else {
                sharedPresenters[it] = sharedBody.copy(count = sharedBody.count - 1)
            }
        }
    }

    private fun removeLastScreenWithPresenters(){
        val screen = screensKeys.peek()
        if (screen.screen == currentScreenKey || screensKeys.size == 1){
            updateFactoryForPresenter(screen.presenterFactory.invoke())
            if (screensKeys.size != 1){
                screensKeys.pop()
            }
        }
    }

    private fun checkOnNullPresenterMap(){
        if (screenWithPresenterMap[currentScreenKey] == null){
            screenWithPresenterMap[currentScreenKey] = mutableListOf()
        }
    }

    internal fun <P: UIPresenter> createPresenter(
        clazz: Class<out UIPresenter>,
        isShared: Boolean
    ): P {
        return if (isShared) getSharedPresenter(clazz) else getPresenter(clazz)
    }

    private fun <P: UIPresenter> getSharedPresenter(
        clazz: Class<out UIPresenter>
    ): P {
        if (sharedPresenters.containsKey(clazz)){
            val sharedPresenter = sharedPresenters[clazz]!!
            val screenWithSharedPresenter = sharedScreenWithSharedPresenter[currentScreenKey]!!
            if (!screenWithSharedPresenter.contains(clazz)){
                sharedPresenters[clazz] = sharedPresenter.copy(count = sharedPresenter.count + 1)
            }

            @Suppress("UNCHECKED_CAST")
            return sharedPresenter.presenter as P
        } else {
            val presenter = currentPresenterFactory.create<P>(clazz)
            sharedPresenters[clazz] = SharedPresenterBody(presenter = presenter)

            return presenter
        }
    }

    private fun <P: UIPresenter> getPresenter(
        clazz: Class<out UIPresenter>
    ): P {
        checkOnNullPresenterMap()
        val currentListPresenters = screenWithPresenterMap[currentScreenKey]!!

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
            screenWithPresenterMap[currentScreenKey]!!.apply { add(presenterBody) }
            return presenter
        }
    }
}