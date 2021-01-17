package com.rogue1.kotlin.walkthrough.coroutines

import kotlinx.coroutines.*


/**
 * all Coroutines extend from CoroutineScope so there is a one to one mapping between CoroutineScopes and Coroutines.
 * i.e. for each Coroutine you create with launch/async command a new CoroutineScope is created.
 */
object CRScopes : Helper {


    /**
     * GlobalScope is a scope that builds coroutines whose lifetime is tied to the entire application's lifetime.
     * This coroutine will get killed only when either all the operations in the coroutine completes
     * or when the entire application is killed (whichever happens first)
     */
    fun globalCoroutines() {
        GlobalScope.launch { delay(1000L);println("") }
    }


    /**
     * The launch and async extensions functions in a scope creates a new CoroutineScope.
     * This new scope will be a child scope to the original scope. So there is a one-to-one relationship
     * between a Coroutine and a CoroutineScope.
     */
    fun launchAsyncCreatesNewScopes() {
        runBlocking {
            scope.launch {
                val outerScope: CoroutineScope = this
                launch {
                    assert(outerScope != this, { "the outer scope shouldn't match the inner scope" })
                }
            }.join()
        }
    }



    /**
     * a coroutine scope will wait for all its children coroutines to run complete.
     * so if you have job or deferred value inside your coroutine you don't have to wait for it to complete by calling
     * join or await functions respectively. the coroutine scope will end only when all the underlying coroutines complete.
     */
    fun scopeWillWaitForAllJobs() {
        val scope = CoroutineScope(Dispatchers.Default)
        var timeMs = timeCoroutines { scope.launch { launch { delay(300) } }.join() }
        log("coroutine ran for $timeMs duration")
        assert(timeMs > 300, { "the scope ran only for $timeMs duration. expected > 300" })
        timeMs = timeCoroutines { scope.launch { async { delay(300) } }.join() }
        assert(timeMs > 300, { "the scope ran only for $timeMs duration. expected > 300" })
        timeMs = timeCoroutines { scope.launch { coroutineScope { launch { delay(300) } } }.join() }
        assert(timeMs > 300, { "the scope ran only for $timeMs duration. expected > 300" })
    }

    /**
     * a scope/CR hierarchy is only created when use launch/async to launch new CR. not just because a scope
     * is used under another scope. in the below example
     */
    fun scopeCRHierarchy() {
        val scope = CoroutineScope(Dispatchers.Default)
        runBlocking {
            val parentJob = coroutineContext[Job]!!
            scope.launch {
                delay(100)
                val flag = parentJob.children.contains(coroutineContext[Job]!!)
                assert(!flag, {"this scope shouldn't be chained to runBlocking scope"})
            }.join()
            launch {
                delay(100)
                val flag = parentJob.children.contains(coroutineContext[Job]!!)
                assert(flag, {"this scope shouldn be chained to runBlocking scope"})
            }
        }
    }


}