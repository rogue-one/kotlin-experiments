package com.rogue1.test

import java.lang.FunctionalInterface


@FunctionalInterface
interface MyFunctionalInterface {
    fun process(input: Int): String
}


class Outer {

    private val bar: Int = 1

    /**
     *  a nested class in Kotlin is surprisingly equal to a nested static class in Java.
     *  one would think that a class inside a companion object would be the equivalent to nested static class in Java.
     */
    class Nested1 {
        fun foo() = 2
    }

    /**
     * to make a non static nested inner class that has access to the outer class's instance variables
     * the inner class must have the `inner` keyword in it.
     */
    inner class Nested2 {
        /**
         * use the qualified this to reference the outer class's instance variables.
         */
        fun foo() = this@Outer.bar + 100
    }

    companion object {
        /**
         * as stated earlier just nested a class definition inside the outer class makes it a nested static inner class.
         * we don't need to define a class inside the companion object
         */
        class Nested2 {
            fun foo() = 1
        }
    }
}

object FunctionalInterface {

    const val data = 10


    private fun handle(process: MyFunctionalInterface) {
        process.process(data)
    }

    fun objectExpression() {
        handle(object: MyFunctionalInterface{
            override fun process(input: Int): String = input.toString()
        })
        /**
         * MyFunctionalInterface does qualify to be a SAM (single abstract method) in Java since it is a interface
         * with only one single method. So this SAM could be technically replaced with a lambda function (Int) -> String.
         * But interfaces defined in Kotlin cannot be replaced with a lambda function yet. so Kotlin interfaces that
         * qualify for SAM is not replaceable with lambda expressions.
         */
        //handle { it.toString }

        /**
         *
         */
        handle(customProcess { it.toString() })
    }

    private fun customProcess(func: (Int) -> String): MyFunctionalInterface {
        return (object: MyFunctionalInterface {
            override fun process(input: Int): String = func.invoke(input)
        })
    }



}