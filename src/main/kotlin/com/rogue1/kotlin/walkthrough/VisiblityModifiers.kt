package com.rogue1.kotlin.walkthrough

/**
 * private class Dummy1 is only visible within this file
 */
private class Dummy1 (val data: Int)

/**
 * the constructor keyword is required
 */
class Dummy2 private constructor (private val hello: Int) {

    private val data: Dummy1 = Dummy1(hello)

    /**
     * internal field only visible within this project (maven/gradle module)
     */
    @Suppress("MemberVisibilityCanBePrivate")
    internal val list = listOf<Int>(1,2,3,4,6,7)

    fun test(): Int {
        val x = list[1]
        return x + 200
    }

}