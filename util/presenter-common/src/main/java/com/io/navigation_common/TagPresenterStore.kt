package com.io.navigation_common

internal class TagPresenterStore<Key: Any>() {
    private val tags = HashMap<Key, String>()
    private val tagPresenterStore = HashMap<String, PresenterBody>()

    fun restore(cacheTags: List<Pair<Key, String>>){
        if (tags.isEmpty()){
            tags + cacheTags
        }
    }

    fun save(): List<Pair<Key, String>>{
        return tags.map { it.toPair() }
    }

    fun <P: UIPresenter> createOrGet(
        tag: String,
        cacheKey: Key,
        clazz: Class<out UIPresenter>,
        factory: PresenterFactory
    ): P {
        return if (tagPresenterStore.contains(tag)){
            getPresenter(tag, clazz)
        } else {
            createPresenter(tag, cacheKey, clazz, factory)
        }
    }

    private fun <P: UIPresenter> createPresenter(
        tag: String,
        cacheKey: Key,
        clazz: Class<out UIPresenter>,
        factory: PresenterFactory
    ):P{
        val presenter = factory.create<P>(clazz)
        tags[cacheKey] = tag
        tagPresenterStore[tag] = PresenterBody(presenter, factory::class.java)
        return presenter
    }

    private fun  <P: UIPresenter> getPresenter(
        tag: String,
        clazz: Class<out UIPresenter>
    ):P {
        val presenterBody = tagPresenterStore[tag]!!
        val presenterClazz = presenterBody.presenter::class.java

        check(presenterClazz == clazz) {
            "Was initialize $presenterClazz by tag = $tag, but you try" +
                    " get presenter $clazz by this tag" +
                    " One tag can have only one presenter."
        }

        @Suppress("UNCHECKED_CAST")
        return presenterBody.presenter as P
    }

    fun clearByCacheKey(cacheKey: Key){
        tags.remove(cacheKey)?.also { tag ->
            tagPresenterStore.remove(tag)?.presenter?.clear()
        }
    }
}