package com.rogue1.kotlin.walkthrough.coroutines

import kotlinx.coroutines.*

object Exceptions : Helper {

    /**
     * handling exceptions within the suspended functions is very similar to handling exceptions
     * in a normal function. we veer away from normal exception handling when the exception reaches a coroutine
     */
    private fun suspendedFunctions() {
        suspend fun test1() {
            throw RuntimeException("")
        }

        suspend fun test2() {
            try {
                test1()
            } catch (e: RuntimeException) {
            }
        }
        runBlocking { test2() }
    }

    /**
     * if one of the child coroutines fails due to an exception than it will bubble up all the way to the
     * top most coroutine. wrapping the nested CRs with try catch will not prevent it from bubbling up to the
     * topmost scope/CR.
     */
    private fun childCoroutines() {
        var flag = false
        try {
            runBlocking {
                launch {
                    launch {
                        try { launch { throw RuntimeException("fail") } } catch (_: RuntimeException) { }
                    }
                }
            }
        } catch (_: RuntimeException) { flag = true }
        assert(flag, {"the exception thrown at the inner CR must bubble up to the top most CR"})
    }

    /**
     * you can use CoroutineExceptionHandler to handle exceptions bubbling up from nested CRs.
     * This must be set either at the top most CR or at the top most scope.
     * It will not stop the exception propagating upwards if CoroutineExceptionHandler set at lower scopes/CRs
     * instead of the top most ones.
     */
    private fun unhandledExceptionHandler() {
        var data: String? = null
        val uhe = CoroutineExceptionHandler { _, e: Throwable -> data = e.message }
        val scope = CoroutineScope(Dispatchers.Default + uhe)
        runBlocking {
            scope.launch {
                launch { launch { throw RuntimeException("fail") } }
            }.join()
        }
        assert(data!! == "fail", {""})
    }

    fun childCoroutinesWithDisjointScope() {
        var data: String? = null
        val uhe = CoroutineExceptionHandler { _, e: Throwable -> data = e.message }
        val scope = CoroutineScope(Dispatchers.Default)
        runBlocking {
            val parent = coroutineContext[Job]!!
            try {
                val job: Job  = scope.launch { launch { throw RuntimeException("fail") } }
                job.join()
            } catch (e: Throwable) { println("=".repeat(100)) }
        }
        //assert(data == "fail", {"the exception should not reach runBlocking scope"})
    }

    /**
     * if a coroutine created by a launch/async within a suspended function fails then the exception is rethrown
     *
     */
    private fun suspendedFuncWithFailedCR() {
        /**
         * the fail function fails even though we are not calling await() on the data.
         * this is because the coroutine launched by the async fails and that causes the suspended function to fail.
         */
        @Suppress("DeferredResultUnused")
        suspend fun failAsync() {
            coroutineScope { async { throw RuntimeException("fail") } }
        }
        runBlocking {
            try {
                failAsync()
            } catch (e: RuntimeException) {
                log("caught it")
            }
        }
        val thread = Thread()
        thread.setUncaughtExceptionHandler { t, e -> }
    }

    /**
     * It seems exceptions occurring in global scopes are being swallowed
     */
    private fun globalScope() {
        runBlocking { GlobalScope.launch { throw RuntimeException("fail") } }
    }

    fun run() {
        suspendedFunctions()
        suspendedFuncWithFailedCR()
        childCoroutines()
        unhandledExceptionHandler()
        childCoroutinesWithDisjointScope()
    }


}