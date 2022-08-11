package com.io.navigation_common

import java.util.HashMap

internal class SharedPresenterStore<Key: Any>() {
    private val sharedPresenters = HashMap<Class<out UIPresenter>, SharedPresenterBody>()
    private val sharedScreenWithSharedPresenter = HashMap<Key, HashSet<Class<out UIPresenter>>>()

    internal fun <P: UIPresenter> createOrGetSharedPresenter(
        currentKey: Key,
        clazz: Class<out UIPresenter>,
        factory: PresenterFactory
    ): P {
        if (sharedPresenters.containsKey(clazz)){
            val sharedPresenter = sharedPresenters[clazz]!!
            val screenWithSharedPresenter = sharedScreenWithSharedPresenter.initializeOrGet(
                currentKey,
                hashSetOf()
            )
            if (!screenWithSharedPresenter.contains(clazz)){
                println("Presenter-shared add in shared $sharedPresenter")
                sharedPresenters[clazz] = sharedPresenter.copy(count = sharedPresenter.count + 1)
                sharedScreenWithSharedPresenter[currentKey]?.apply { add(clazz) }
            }
            println("Presenter-shared get ${sharedPresenters[clazz]}")
            @Suppress("UNCHECKED_CAST")
            return sharedPresenter.presenter as P
        } else {
            val presenter = factory.create<P>(clazz)
            val clazzFactory = factory::class.java

            sharedPresenters[clazz] = SharedPresenterBody(presenter = presenter, clazzFactory = clazzFactory)
            val clazzSet = hashSetOf(clazz)

            println("Presenter-shared create ${sharedPresenters[clazz]}")
            sharedScreenWithSharedPresenter[currentKey] = clazzSet
            return presenter
        }
    }

    fun clearByKey(screen: Key){
        sharedScreenWithSharedPresenter.remove(screen)?.forEach {
                val sharedPresenter = sharedPresenters[it]!!

                val count = sharedPresenter.count

                if (count <= 1){
                    println("Presenter-shared remove all $sharedPresenter")
                    sharedPresenters.remove(it)!!.apply {
                        presenter.clear()
                    }
                } else {
                    println("Presenter-shared remove one item $sharedPresenter")
                    sharedPresenters[it] = sharedPresenter.copy(count = count - 1)
                }
            }
    }
}