package com.io.navigation_common


import kotlin.collections.HashMap

internal class SharedPresenterStore<CacheKey: Any> {
    private val retainSharedPresenters = HashMap<String, CacheKey>()
    private val sharedPresenters = HashMap<Class<out UIPresenter>, UIPresenter>()
    private val sharedScreenWithSharedPresenter = HashMap<CacheKey, HashSet<Class<out UIPresenter>>>()

    fun save(): Map<String, CacheKey> {
        retainSharedPresenters.clear()

        val retain = HashMap<String, CacheKey>()
        sharedScreenWithSharedPresenter.forEach { (key, classes) ->
            classes.forEach {
                retain[it.name] = key
            }
        }
        return retain
    }

    fun restore(retain: Map<String, CacheKey>){
        if (retainSharedPresenters.isEmpty()){
            retainSharedPresenters + retain
        }
    }

    internal fun <P: UIPresenter> getSharedPresenter(
        cacheKey: CacheKey,
        clazz: Class<out UIPresenter>,
        factory: PresenterFactory
    ): P {
        return if (sharedPresenters.containsKey(clazz)){
            getPresenter<P>(clazz)
        } else {
            createPresenter<P>(cacheKey, clazz, factory)
        }
    }

    private fun <P: UIPresenter> createPresenter(
        cacheKey: CacheKey,
        clazz: Class<out UIPresenter>,
        factory: PresenterFactory
    ): P{
        val presenter = factory.create<P>(clazz)
        val clazzFactory = factory::class.java
        val currentCacheKey = retainSharedPresenters.remove(presenter::class.java.name) ?: cacheKey

        sharedPresenters[clazz] = presenter
        val clazzSet = hashSetOf(clazz)

        sharedScreenWithSharedPresenter[currentCacheKey] = clazzSet
        return presenter
    }

    private fun  <P: UIPresenter> getPresenter(
        clazz: Class<out UIPresenter>
    ): P{
        val sharedPresenter = sharedPresenters[clazz]!!
        @Suppress("UNCHECKED_CAST")
        return sharedPresenter as P
    }

    fun clearByCacheKey(cacheKey: CacheKey){
        sharedScreenWithSharedPresenter.remove(cacheKey)?.forEach {
            sharedPresenters.remove(it)!!.clear()
        }
    }
}