package com.io.navigation_common

import java.lang.ref.WeakReference

internal class ReferenceOnPresenter<Guide: Any>(
    val cacheKey: String,
    guide: Guide
) {
    private val referent = WeakReference(guide)

    fun getGuide(): Guide? = referent.get()

    fun checkOnExitsReferent(): Boolean {
        return referent.get() != null
    }

    override fun hashCode(): Int {
        return cacheKey.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ReferenceOnPresenter<*>

        if (cacheKey != other.cacheKey) return false
        return true
    }
}