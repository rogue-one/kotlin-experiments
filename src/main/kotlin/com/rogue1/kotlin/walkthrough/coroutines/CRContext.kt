package com.rogue1.kotlin.walkthrough.coroutines

import kotlinx.coroutines.*
import kotlin.coroutines.AbstractCoroutineContextElement
import kotlin.coroutines.CoroutineContext

/**
 * suspendable functions are not usually tied to a specific thread.
 * They can be run by any number of random thread. And many frameworks (mainly 1 thread per request web frameworks) use
 * ThreadLocal to pass data between them. But this scheme will not work in coroutines since there is no strong association
 * between suspendable functions and thread. CoroutineContext satisfies this need in the coroutine world.
 * Every single Co-routine will have CoroutineContext associated with it.
 */
object CRContext {

    /**
     * CoroutineContext can be imagined as an IndexedSet data structure of nested type Element defined in CoRoutineContext.
     * Some of the standard key items that can be found in the context are
     *  1. CoroutineName
     *  2. CoRoutineId
     *  3. Dispatcher
     *  The main reason why this is designed as an IndexSet and not normal class with attributes for its content
     *  such as Dispatcher/CoRoutineId etc is that the context will be used by users to pass on their own
     *  application data in addition to predefined data in the context.
     *
     *  The unique thing about CoroutineContext is that all the Elements that makes up the IndexedSet of a CoroutineContext
     *  is by itself an child type of CoroutineContext
     */


    /**
     * When one Coroutine launches a new Coroutine using async, runBlocking, launch builders it by default
     * inherits all the properties of the parent coroutine.
     *
     */
    @ExperimentalStdlibApi
    fun contextInheritance(): Unit {
       runBlocking {
           launch(Dispatchers.Default) {
               log("top level coroutine")
               launch {
                   assert(coroutineContext[CoroutineDispatcher]!!.key == Dispatchers.IO.key)
                   log("nested coroutine")
               }
           }
       }
    }

    /**
     * You can easily override specific components in the IndexedSet.
     * Just sum (plus operator) all the components of the CoroutineContext that you like to override.
     *
     */
    fun contextOverridden(): Unit {
        runBlocking {
            launch {
                log("top level coroutine with blocking dispatcher")
                launch(Dispatchers.Default) {
                    log("nested coroutine with default dispatcher")
                    delay(100)
                }
                delay(100)
            }
        }
    }

    /**
     * Job is one of the standard element of a CoroutineContext. unlike most other elements it is not inherited directly
     * from the parent context but a new Job is created for each Coroutine. A parent Coroutine and child Coroutine will
     * each have its own Job object with former being the parent of the latter.
     */
    fun jobInheritance() {
       var childJob: Job? = null
       runBlocking {
           val parentJob: Job = launch {
               sample1(1000)
               childJob = launch {
                   sample1(1000)
               }
           }
           assert(parentJob.children.contains(childJob))
           println("childJob is indeed a child of parentJob")
       }
    }

    /**
     * when the parent job is cancelled all its children jobs are cancelled
     */
    fun cancelChildOnParentCancel() {
        var childJob: Job? = null
        runBlocking {
            val parentJob = launch {
                childJob = launch { while (isActive) {  sample1(500) } }
            }
            delay(2000)
            parentJob.cancelAndJoin()
        }
        assert(childJob!!.isCancelled)
    }

    /**
     * this function shows the usage of CustomData in the coroutine context
     */
    fun custDataInCoroutineCtx() {
        runBlocking {
            launch(CustomData(100)) {
                val topData = coroutineContext[CustomData]!!
                launch {
                    val nestedData = coroutineContext[CustomData]!!
                    assert(topData == nestedData)
                    log("top data and nested data match each other")
                }
            }
        }
    }

    /**
     * any custom data that can be attached to the CoroutineContext must
     *   1. extend AbstractCoroutineContextElement class
     *   2. it must have assign the Key object by which it will be referred in the contextObject
     *      i.e. this Key object will be the Key to the Map and the instance of this CustomClass will be its value.
     *      The standard is to have the Companion object of as its Key.
     *   3. The Key object must extend CoroutineContext.Key<CustomData>
     */
    class CustomData(val num: Int) : AbstractCoroutineContextElement(CustomData) {
        companion object Key: CoroutineContext.Key<CustomData>
    }


    private fun log(msg: String) {
        println("thread = ${Thread.currentThread().name}, message = $msg")
    }

    private suspend fun sample1(time: Long) {
        delay(time)
        println("I am done!!")
    }

}
