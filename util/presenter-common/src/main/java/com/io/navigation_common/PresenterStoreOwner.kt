package com.io.navigation_common

open class PresenterStoreOwner<Guide: Any>(
    private val adapter: PresenterStoreOwner.Adapter<Guide>
){
    private val referenceOnPresenters = hashSetOf<ReferenceOnPresenter<Guide>>()

    private val sharedPresenterStore = SharedPresenterStore<String>()
    private val simpleStores = HashMap<String, SimplePresenterStore>()

    protected val restorePresenterStore: RestorePresenterStore<String> = DefaultRestorePresenterStore(
        sharedPresenterStore = sharedPresenterStore,
    )

    @Synchronized
    fun <P: UIPresenter> cleanGarbageIntoStoreAndCreatePresenter(
        clazz: Class<out UIPresenter>,
        factory: PresenterFactory,
        isShared: Boolean = false
    ): P {
        println("Navigation cleanGarbageIntoStoreAndCreatePresenter")
        removeUnnecessaryPresenters()
        val referenceOnPresenter = getAndSaveCurrentReferenceOnPresenterItem()
        return createPresenter(
            cacheKey = referenceOnPresenter.cacheKey,
            clazz = clazz,
            factory = factory,
            isShared = isShared
        )
    }

    private fun <P: UIPresenter> createPresenter(
        cacheKey: String,
        clazz: Class<out UIPresenter>,
        factory: PresenterFactory,
        isShared: Boolean = false
    ): P {
        println("Navigation createPresenter")
        return if (isShared){
            sharedPresenterStore.getSharedPresenter<P>(cacheKey, clazz, factory)
        } else {
            val store = createOrGetPresenterStore(cacheKey)
            store.getPresenter<P>(clazz, factory)
        }
    }

    fun forcedCleanGarbage() = removeUnnecessaryPresenters()

    @Synchronized
    private fun removeUnnecessaryPresenters(){
        referenceOnPresenters
            .filter {
                !it.checkOnExitsReferent() || adapter.isOptionallyVerifyValidGuide(it.getGuide())
            }
            .onEach { removeUnnecessaryPresentersByKey(it.cacheKey) }
            .forEach(referenceOnPresenters::remove)
    }

    private fun removeUnnecessaryPresentersByKey(cacheKey: String){
        simpleStores.remove(cacheKey)?.clear()
        sharedPresenterStore.clearByCacheKey(cacheKey)
    }

    private fun createOrGetPresenterStore(cacheKey: String): SimplePresenterStore {
        simpleStores[cacheKey]?.let {
            return it
        }
        val store = SimplePresenterStore()
        simpleStores[cacheKey] = store

        return store
    }

    private fun getAndSaveCurrentReferenceOnPresenterItem(): ReferenceOnPresenter<Guide>{
        val reference = getCurrentReferenceOnPresenterItem()
        referenceOnPresenters.add(reference)
        return reference
    }

    private fun getCurrentReferenceOnPresenterItem(): ReferenceOnPresenter<Guide> {
        val guide = adapter.getGuide()
        return ReferenceOnPresenter(
            cacheKey = adapter.getCacheKey(guide),
            guide = guide
        )
    }

    abstract class Adapter<Guide: Any> {
        abstract fun getGuide(): Guide
        abstract fun getCacheKey(currentGuide: Guide): String
        open fun isOptionallyVerifyValidGuide(guide: Guide?): Boolean = false
    }

}