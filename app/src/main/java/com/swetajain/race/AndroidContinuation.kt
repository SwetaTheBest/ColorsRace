package com.swetajain.race

import android.os.Handler
import android.os.Looper
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.Continuation
import kotlin.coroutines.ContinuationInterceptor

class AndroidContinuation<in T>(val cont: Continuation<T>) : Continuation<T> by cont {
    override fun resumeWith(result: Result<T>) {
        when (Looper.myLooper() == Looper.getMainLooper()) {
            true -> cont.resumeWith(result)
            false -> {
                Handler(Looper.getMainLooper()).post {
                    cont.resumeWith(result)
                }
            }
        }
    }

    object Android : AbstractCoroutineContextElement
        (ContinuationInterceptor), ContinuationInterceptor {
        override fun <T> interceptContinuation(continuation: Continuation<T>): Continuation<T> =
            AndroidContinuation(continuation)

    }
}
