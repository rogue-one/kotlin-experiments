package com.rogue1.kotlin.walkthrough.coroutines

import com.rogue1.kotlin.walkthrough.Logger
import kotlinx.coroutines.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.NonCancellable.isActive
import kotlin.random.Random
import kotlin.system.measureTimeMillis

object Coroutine : Logger {

    fun exceptionHandler() {
        val scope = CoroutineScope(Dispatchers.Default)
        val handler = CoroutineExceptionHandler { _, exception ->
            println("Caught $exception")
        }
        scope.launch {

        }
    }

    /**
     * a coroutine runs its suspended function in sequence.
     * asynchronous  functions are mostly run in sequential mode using map/flatMap monads in Futures/Promises.
     * so coroutine makes it natural by executing all of its suspended functions in sequence.
     * But each suspended functions in the coroutine could still be run in a different thread.
     */
    fun sequentialByDefault() {
        val scope = CoroutineScope(Dispatchers.Default)
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


}