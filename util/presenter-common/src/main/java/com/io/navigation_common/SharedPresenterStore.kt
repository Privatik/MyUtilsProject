package com.io.navigation_common


import kotlin.collections.HashMap

internal class SharedPresenterStore<Key: Any>() {
    private val retainSharedPresenters = HashMap<String, HashSet<Key>>()
    private val sharedPresenters = HashMap<Class<out UIPresenter>, SharedPresenterBody>()
    private val sharedScreenWithSharedPresenter = HashMap<Key, HashSet<Class<out UIPresenter>>>()

    fun save(): Map<String, HashSet<Key>> {
        val keys = HashMap<Class<out UIPresenter>, HashSet<Key>>()

        sharedScreenWithSharedPresenter.forEach { (key, hashSet) ->
            hashSet.forEach { clazz ->
                if (keys.containsKey(clazz)){
                    keys[clazz]?.add(key)
                } else {
                    val set = hashSetOf<Key>()
                    set.add(key)
                    keys[clazz] = set
                }
            }
        }

        return keys.mapKeys { it.key.name } + retainSharedPresenters
    }

    fun restore(keys: Map<String, HashSet<Key>>){
        if (sharedScreenWithSharedPresenter.isEmpty()){
            retainSharedPresenters.putAll(keys)
        }
    }

    internal fun <P: UIPresenter> createOrGetSharedPresenter(
        currentKey: Key,
        clazz: Class<out UIPresenter>,
        factory: PresenterFactory
    ): P {
        return if (sharedPresenters.containsKey(clazz)){
            getPresenter<P>(currentKey, clazz)
        } else {
            createPresenter<P>(currentKey, clazz, factory)
        }
    }

    private fun <P: UIPresenter> createPresenter(
        currentKey: Key,
        clazz: Class<out UIPresenter>,
        factory: PresenterFactory
    ): P{
        val presenter = factory.create<P>(clazz)
        val clazzFactory = factory::class.java

        sharedPresenters[clazz] = restorePresenter(SharedPresenterBody(presenter = presenter, clazzFactory = clazzFactory))
        val clazzSet = hashSetOf(clazz)

        sharedScreenWithSharedPresenter[currentKey] = clazzSet
        return presenter
    }

    private fun  <P: UIPresenter> getPresenter(
        currentKey: Key,
        clazz: Class<out UIPresenter>
    ): P{
        val sharedPresenter = sharedPresenters[clazz]!!
        val screenWithSharedPresenter = sharedScreenWithSharedPresenter.initializeOrGet(
            currentKey,
            hashSetOf()
        )
        if (!screenWithSharedPresenter.contains(clazz)){
            sharedPresenters[clazz] = sharedPresenter.copy(count = sharedPresenter.count + 1)
            sharedScreenWithSharedPresenter[currentKey]?.apply { add(clazz) }
        }

        @Suppress("UNCHECKED_CAST")
        return sharedPresenter.presenter as P
    }

    private fun restorePresenter(sharedPresenterBody: SharedPresenterBody): SharedPresenterBody{
        val clazzName = sharedPresenterBody.presenter::class.java.name
        val set = retainSharedPresenters[clazzName] ?: return sharedPresenterBody
        var count = 0
        set.forEach {
            val screenWithSharedPresenter = sharedScreenWithSharedPresenter.initializeOrGet(
                it,
                hashSetOf()
            )
            screenWithSharedPresenter.add(sharedPresenterBody.presenter::class.java)
            sharedScreenWithSharedPresenter[it] = screenWithSharedPresenter
            count++
        }

        retainSharedPresenters.remove(clazzName)
        return sharedPresenterBody.copy(count = count)
    }

    fun clearByKey(screen: Key){
        sharedScreenWithSharedPresenter.remove(screen)?.forEach {
                val sharedPresenter = sharedPresenters[it]!!

                val count = sharedPresenter.count

                if (count <= 1){
                    sharedPresenters.remove(it)!!.apply {
                        presenter.clear()
                    }
                } else {
                    sharedPresenters[it] = sharedPresenter.copy(count = count - 1)
                }
            }
    }
}