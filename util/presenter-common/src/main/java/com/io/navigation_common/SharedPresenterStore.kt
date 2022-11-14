package com.io.navigation_common


import kotlin.collections.HashMap

internal class SharedPresenterStore<Key: Any>() {
    private val retainSharedPresenters = HashMap<Key, HashSet<String>>()
    private val sharedPresenters = HashMap<Class<out UIPresenter>, PresenterBody>()
    private val sharedScreenWithSharedPresenter = HashMap<Key, HashSet<Class<out UIPresenter>>>()

    fun save(): List<Pair<Key, Set<String>>> {
        retainSharedPresenters.clear()
        return sharedScreenWithSharedPresenter
            .map { entry ->
                entry.key to entry.value.map { it.name }.toSet()
            }
    }

    fun restore(keys: List<Pair<Key, Set<String>>> ){
        if (retainSharedPresenters.isEmpty()){
            retainSharedPresenters + keys
        }
    }

    internal fun <P: UIPresenter> createOrGetSharedPresenter(
        cacheKey: Key,
        clazz: Class<out UIPresenter>,
        factory: PresenterFactory
    ): P {
        return if (sharedPresenters.containsKey(clazz)){
            getPresenter<P>(cacheKey, clazz)
        } else {
            createPresenter<P>(cacheKey, clazz, factory)
        }
    }

    private fun <P: UIPresenter> createPresenter(
        cacheKey: Key,
        clazz: Class<out UIPresenter>,
        factory: PresenterFactory
    ): P{
        val presenter = factory.create<P>(clazz)
        val clazzFactory = factory::class.java

        sharedPresenters[clazz] = PresenterBody(presenter = presenter, clazzFactory = clazzFactory)
        val clazzSet = hashSetOf(clazz)

        sharedScreenWithSharedPresenter[cacheKey] = clazzSet
        return presenter
    }

    private fun  <P: UIPresenter> getPresenter(
        cacheKey: Key,
        clazz: Class<out UIPresenter>
    ): P{
        val sharedPresenter = sharedPresenters[clazz]!!
        @Suppress("UNCHECKED_CAST")
        return sharedPresenter.presenter as P
    }

//    private fun restorePresenter(sharedPresenterBody: SharedPresenterBody): SharedPresenterBody{
//        val clazzName = sharedPresenterBody.presenter::class.java.name
//        val set = retainSharedPresenters[clazzName] ?: return sharedPresenterBody
//        var count = 0
//        set.forEach {
//            val screenWithSharedPresenter = sharedScreenWithSharedPresenter.initializeOrGet(
//                it,
//                hashSetOf()
//            )
//            screenWithSharedPresenter.add(sharedPresenterBody.presenter::class.java)
//            sharedScreenWithSharedPresenter[it] = screenWithSharedPresenter
//            count++
//        }
//
//        retainSharedPresenters.remove(clazzName)
//        return sharedPresenterBody.copy(count = count)
//    }

    fun clearByCacheKey(cacheKey: Key){
        sharedScreenWithSharedPresenter.remove(cacheKey)?.forEach {
            sharedPresenters.remove(it)!!.apply { presenter.clear() }
        }
    }
}