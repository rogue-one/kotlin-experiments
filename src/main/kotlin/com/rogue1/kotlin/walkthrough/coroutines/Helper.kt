package com.rogue1.kotlin.walkthrough.coroutines

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import java.text.SimpleDateFormat
import java.util.*
import kotlin.concurrent.getOrSet
import kotlin.system.measureTimeMillis

interface Helper {

    val scope: CoroutineScope
    get() { return CoroutineScope(Dispatchers.Default) }

    /**
     * measure execution time of suspended code blocks.
     */
    fun timeCoroutines(code: suspend () -> Unit): Long {
        return measureTimeMillis {
            runBlocking {
                code()
            }
        }
    }


    fun log(message: String): Unit {
        println("thread = ${Thread.currentThread().name}, time = ${timestamp()}, message = $message")
    }

    fun warn(message: String) {
        System.err.println("thread = ${Thread.currentThread().name}, time = ${timestamp()}, message = $message")
    }

    private fun timestamp(): String {
        val local = ThreadLocal<SimpleDateFormat>()
        val sdf = local.getOrSet { SimpleDateFormat("HH:mm:ss")  }
        return sdf.format(Date())
    }


}