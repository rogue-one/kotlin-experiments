package com.rogue1.kotlin.walkthrough.coroutines

import kotlinx.coroutines.*
import kotlin.coroutines.EmptyCoroutineContext

class CRImplementations: Helper {

    /**
     * prefer composition over inheritance while using [[CoroutineScope]]
     */
    class CRScopeInterfaceImpl1  {

        private val scope = CoroutineScope(Dispatchers.IO)

        /**
         * the convention is that public method that triggers a coroutine-scope must be a non-suspended regular function.
         */
        fun run() {
           scope.launch {
                delay(1000)
            }
        }

        fun isActive(): Boolean = scope.isActive

        fun cancel(message: String) {
            scope.cancel(message)
        }

    }

    /**
     * using coroutineScope is alternative to creating a class like [[CRScopeInterfaceImpl1]]
     * * when you use this function you create a new scope and execute the suspended function block passed to it.
     * * this coroutineScope function will end only when all the child sub-routines inside the suspended function block
     * complete. no explicit join or await is needed.
     * * if any of the child coroutines in coroutineScope function fails then the entire coroutineScope is cancelled
     *   including all other children coroutines
     * * if bgjob fails job suspendedRes and asyncRes is cancelled. similarly if suspendedRes fails other
     *   two are cancelled and so on.
     *
     */
    fun usingCoroutineScopeFunc() {
        runBlocking {
            coroutineScope {
                val bgjob = launch { delay(200); println("launched job1 in background") }
                val suspendedRes =  suspend  { delay(100); println("task1"); 10  }()
                val asyncRes = async { delay(100); println("async function"); 10 }
            }
        }
    }

    /**
     * coroutineScope will exit only after all the child coroutines complete.
     */
     fun testCRScopeExitsOnlyOnAllChildCompletion() {
         runBlocking {
            val time = timeCoroutines {
                coroutineScope {
                    launch { delay(100) }
                    launch { delay(1000) }
                    launch { delay(100) }
                }
            }
             assert(time >= 1000) { "coroutineScope must have ran atleast 1 second" }
         }
     }


    /**
     * use withContext function if you want to launch a new coroutine but suspend the current co-routine until the
     * new one completes. both async and launch will immediately return after starting the coroutine but withContext
     * like coroutineScope
     */
    fun withContextFunc() {
        runBlocking {
            withContext(EmptyCoroutineContext) {

            }
        }

    }

}