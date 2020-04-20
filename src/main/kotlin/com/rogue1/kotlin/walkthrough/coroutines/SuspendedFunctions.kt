package com.rogue1.kotlin.walkthrough.coroutines

import kotlinx.coroutines.runBlocking

object SuspendedFunctions: Helper {

    /**
     *
     */
    fun testExceptionHandling() {
        suspend fun test() {
            throw RuntimeException("I am done")
        }
        runBlocking {
            try { test() } catch (e: RuntimeException) {  log("exception caught") }
        }
    }

}