package com.rogue1.kotlin.walkthrough.coroutines

import kotlinx.coroutines.*
import kotlinx.coroutines.CoroutineScope
import kotlin.system.measureTimeMillis

object Coroutine : Helper {

    /**
     * a coroutine runs its suspended function in sequence.
     * asynchronous  functions are mostly run in sequential mode using map/flatMap monads in Futures/Promises.
     * so coroutine makes it natural by executing all of its suspended functions in sequence.
     * But each suspended functions in the coroutine could still be run in a different thread.
     */
    fun sequentialByDefault() {
        val job = scope.launch {
            delay(100)
            delay(100)
        }
        val timeMs = measureTimeMillis { runBlocking { job.join() } }
        assert(timeMs > 200, {"all suspended functions didn't execute in sequence in coroutine"})
        val list = mutableListOf<String>()
        scope.launch {
            for (i in 1..10) {
                delay(200)
                list.add(Thread.currentThread().name)
            }
        }
        if (list.size > 1) {
            log("more than one thread was used for executing the work")
        } else {
            warn("all work was executed in a single thread")
        }
    }

    /**
     * the launch function creates a job and return immediately. In this below example we create two jobs
     * that delays for 1000Ms each. since the two launches do things in parallel the overall scope execution
     * should be just slightly above 1000Ms.
     */
    fun parallelCrLaunch() {
        val timeMs= timeCoroutines {
            scope.launch {
                launch {
                    delay(1000)
                }
                launch {
                    delay(1000)
                }
            }
        }
        assert(timeMs in 1001..1999, {"the outer scope must run longer than 1000 ms"})
    }


}