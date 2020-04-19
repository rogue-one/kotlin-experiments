package com.rogue1.kotlin.walkthrough.coroutines

import kotlinx.coroutines.*
import java.lang.RuntimeException
import java.util.*
import kotlin.system.measureTimeMillis


/**
 * GlobalScope
 */
object CRScopes {


    /**
     * GlobalScope is a scope that builds coroutines whose lifetime is tied to the entire application's lifetime.
     * This coroutine will get killed only when either all the operations in the coroutine completes
     * or when the entire application is killed (whichever happens first)
     */
    fun globalCoroutines() {
        GlobalScope.launch { delay(1000L) ;println("") }
    }

    /**
     * an outer scope will wait for all the inner jobs/scopes to complete.
     * In the below example the job waits for 1000 ms and then launches a another Coroutine/CoroutineScope
     * which again waits for 1000. So the job that makes the total outer-scope will run for a time greater than 2000.
     */
    fun scopeWaitForAllChildren() {
        val scope = CoroutineScope(Dispatchers.Default)
        val job = scope.launch {
            delay(1000)
            launch {
                log("starting nested job 1")
                delay(1000)
                log("ending nested job 1")
            }
        }
        val timeMs = measureTimeMillis {
            runBlocking {
                job.join()
            }
        }
        assert(timeMs > 2000, {"the job ran only for $timeMs. expected > 3000"})
    }

    /**
     * the launch function creates a job and return immediately. In this below example we create two jobs
     * that delays for 1000Ms each. since two launches do things in parallel the overall scope execution
     * should be just slightly above 1000Ms.
     */
    fun parallelCrLaunch() {
        val scope = CoroutineScope(Dispatchers.Default)
        val job = scope.launch {
            launch {
                delay(1000)
            }
            launch {
                delay(1000)
            }
        }
        val timeMs = measureTimeMillis {
            runBlocking {
                job.join()
            }
        }
        assert(timeMs in 1001..1999, {"the outer scope must run longer than 1000 ms"})
    }

    /**
     * The launch and async extensions functions in a scope creates a new CoroutineScope.
     * This new scope will be a child scope to the original scope. So there is a one-to-one relationship
     * between a Coroutine and a CoroutineScope.
     */
    fun launchAsyncAreScopes() {
        runBlocking {
            val job = launch(CoroutineName("inner")) {
                delay(1000)
                log("inner scope name ${coroutineContext[CoroutineName]}")
            }
            assert(coroutineContext[Job]!!.children.contains(job), {"child parent relationship is missing"})
        }
    }

    /**
     * any failures in the
     */
    @Suppress("UNREACHABLE_CODE")
    fun failPropagation() {
        val timeMs = measureTimeMillis {
           val res = runBlocking {
               val time = 500L
                val job1 = launch { delay(Long.MAX_VALUE); -1 }
                val job2 = launch { delay(time); throw RuntimeException("I am a failing coroutine") }
                delay(time + 200)
                assert(job1.isCancelled, {"job 1 should have been cancelled by now"})
            }
        }
    }

    fun parentShouldWaitForAllChild() {

    }

    /**
     * A Coroutine Scope can initiate multiple coroutines that runs in the background.
     * but it will wait
     *
     */
    fun mustWaitForAllCRComplete() {
        val scope = CoroutineScope(Dispatchers.Default)
        scope.launch {
            for (i in 1..2) {
               val data =  async(Dispatchers.Default) {
                    expensiveCall(1000)
                }
            }
        }
        scope.ensureActive()
    }


    fun customScopeEg() {
        val scope = CoroutineScope(Dispatchers.Default)
        scope.launch {

        }
    }

    suspend fun expensiveCall(work: Long) {
        delay(work)
        log("my work is done")
    }

    private fun log(msg: String) {
        println("time = ${Date()} thread = ${Thread.currentThread().name}, message = $msg")
    }

}