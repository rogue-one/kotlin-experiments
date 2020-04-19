package com.rogue1.kotlin.walkthrough

interface Logger {

    fun log(message: String) {
        println("thread = ${Thread.currentThread().name} , message = $message")
    }


    fun warn(message: String) {
        System.err.println("thread = ${Thread.currentThread().name} , message = $message")
    }
}