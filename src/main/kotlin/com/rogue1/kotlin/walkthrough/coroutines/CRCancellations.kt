package com.rogue1.kotlin.walkthrough.coroutines

import kotlinx.coroutines.*
import kotlin.coroutines.*


object CRCancellations: Helper {

    /**
     * if you cancel a scope all the jobs/CRs inside the scope will be automatically cancelled
     */
    fun cancelScopeCancelsAllCRs() {
        var counter = 0
        val scope = CoroutineScope(EmptyCoroutineContext)
        val job1 = scope.launch { try { delay(Long.MAX_VALUE) } finally { counter += 1 } }
        val job2 = scope.launch { try { delay(Long.MAX_VALUE) } finally { counter += 1 } }
        scope.cancel()
        assert(!job1.isActive && !job2.isActive && counter == 2) {
            "all jobs in a cancelled scope will also be cancelled"
        }
    }


    /**
     * when you cancel a co-routine. the cancellation don't take effect immediately but happens when the next suspension
     * point is hit.
     */
    fun cancellationsTakeEffectAtSuspensionBoundaries() {
        var count = 0
        suspend fun work() {
            delay(100)
            count += 1
            work()
        }
        val scope = CoroutineScope(Dispatchers.Default)
        scope.launch {
            work()
        }
        Thread.sleep(1000)
        scope.cancel()
        assert(count >= 9)
    }

    /**
     * if you carrying out a very expensive computation that will likely take longer to execute without
     * any suspension point. you must explicitly check the isActive flag on the coroutine to check to end the computation
     * on cancellation.
     */
    @Suppress("BlockingMethodInNonBlockingContext")
    fun makeCRCancellableOnLongComputation() {
        var counter = 0
        suspend fun work() {
            while(currentCoroutineContext().isActive) {
                Thread.sleep(100)
                counter += 1
            }
        }
        val scope = CoroutineScope(Dispatchers.Default)
        scope.launch { work() }
        Thread.sleep(1000)
        scope.cancel()
        assert(counter >= 9)
    }


}