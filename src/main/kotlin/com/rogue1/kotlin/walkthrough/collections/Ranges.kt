package com.rogue1.kotlin.walkthrough.collections


/**
 * It is possible to create Ranges for your custom types too..
 */
object Ranges {

    fun intRange() {
        for(i in 1..10 step 1) {
            println(i)
        }
        // rangeTo function can be called by its operator form ..
        for (i in 1.rangeTo(10)) {
            println(i)
        }
    }

    fun intReverseRange() {
        for (i in 10 downTo 1) {
            println(i)
        }
    }

    /**
     * until doesn't include 'z'
     */
    fun charRange() {
        for (i in 'a' until 'z' step 1) {
            println(i)
        }
    }



}