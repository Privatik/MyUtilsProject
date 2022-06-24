package com.io.navigation_common

import java.util.HashMap

internal class SharedPresenterStore<Key: Any>(
    private val factoryObjectCounter: FactoryObjectCounter
) {
    private val sharedPresenters = HashMap<Class<out UIPresenter>, SharedPresenterBody>()
    private val sharedScreenWithSharedPresenter = HashMap<Key, HashSet<Class<out UIPresenter>>>()

    internal fun <P: UIPresenter> createOrGetSharedPresenter(
        itemKey: String?,
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
                sharedPresenters[clazz] = sharedPresenter.copy(count = sharedPresenter.count + 1)
                sharedScreenWithSharedPresenter[currentKey]?.apply { add(clazz) }
                writeMessage("Add SharedPresenter in New screen $clazz count ${sharedPresenter.count + 1}")
            }
            writeMessage("Get SharedPresenter $clazz")
            @Suppress("UNCHECKED_CAST")
            return sharedPresenter.presenter as P
        } else {
            val presenter = factory.create<P>(clazz)
            val clazzFactory = factory::class.java

            sharedPresenters[clazz] = SharedPresenterBody(presenter = presenter, clazzFactory = clazzFactory)
            val clazzSet = hashSetOf(clazz)
            writeMessage("Add SharedPresenter $clazz")

            factoryObjectCounter.addObject(itemKey, clazzFactory)

            sharedScreenWithSharedPresenter[currentKey] = clazzSet
            presenter.build()
            return presenter
        }
    }

    fun clearByKey(screen: Key){
        sharedScreenWithSharedPresenter[screen]?.forEach {
            val sharedPresenter = sharedPresenters[it]!!

            val count = sharedPresenter.count
            writeMessage("delete sharedPresenter $count")

            if (count <= 1){
                sharedPresenters.remove(it)!!.apply {
                    presenter.clear()
                    factoryObjectCounter.removeObject(clazzFactory)
                }
            } else {
                sharedPresenters[it] = sharedPresenter.copy(count = count - 1)
            }
        }
    }
}