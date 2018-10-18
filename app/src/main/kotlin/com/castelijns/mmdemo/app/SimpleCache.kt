package com.castelijns.mmdemo.app

open class SimpleCache<T> {

    protected var cache: List<T>? = null

    fun cacheData(data: List<T>) {
        cache = data
    }
}
