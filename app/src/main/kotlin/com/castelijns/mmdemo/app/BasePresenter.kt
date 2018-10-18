package com.castelijns.mmdemo.app

import androidx.annotation.CallSuper
import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.Dispatchers
import kotlinx.coroutines.experimental.Job
import kotlin.coroutines.experimental.CoroutineContext

abstract class BasePresenter : CoroutineScope {

    protected val job = Job()

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    abstract fun start()

    @CallSuper
    fun stop() {
        job.cancel()
    }

}
