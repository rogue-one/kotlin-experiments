package com.rogue1.kotlin.walkthrough.coroutines

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext


object CRDispatchers {


    /**
     * Unconfined dispatcher is not confined to single thread pool.
     *  1. Its execution is started in the thread which invoked it.
     *  2. if the scope launches another scope within it but with different dispatcher (Default) then the new CR is ran
     *     in the new dispatcher
     *  3. But once the launched CR completes and then transfers the control back to the parent CR the parent CR will
     *     continue to run.
     */
    fun unConfinedDispatchers() {
        val list =  mutableListOf<String>()
        runBlocking {
            launch(Dispatchers.Unconfined) {
                list.add(Thread.currentThread().name)
                withContext(Dispatchers.Default) {
                    list.add(Thread.currentThread().name)
                }
                list.add(Thread.currentThread().name)
            }
        }
        assert(
            list
                    .map { it.split("\\s".toRegex())[0] }
                    .map { it.split("-")[0] } == listOf("main", "DefaultDispatcher", "DefaultDispatcher")
        )
    }

}