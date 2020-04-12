package com.rogue1.test

class Lambdas {

    /**
     * lambda function
     *   * the entire function must be enclosed in a curly brace.
     *   * input parameters must NOT be enclosed in parameters
     */
    fun plus(): (Int, Int) -> Int {
        return {x: Int, y: Int -> println("calculating $x + $y"); x+y }
    }

    /**
     * a sample higher order function.
     * <code>
     *
     * </code>
     */
    private fun <T> biOperator(func: (T,T) -> T, arg1: T, arg2: T): T {
        val res1 = func(arg1, arg2)
        val res2 = func.invoke(arg1, arg2) // invoke is scala's apply
        return func(res1, res2)
    }

    /**
     * anonymous function is an alternative to lambda expressions.
     * the literal syntax is same as a normal function definition except function name is skipped.
     */
    fun anonymousFun() {
        biOperator(fun(x: Int,y: Int) = x + y, 10, 20)
    }


    interface Processor {
        fun process(): String
    }

    class ProcessorImpl1 : Processor {
        override fun process(): String = "foo"
    }

    class ProcessorImpl2: Processor {
        override fun process(): String = "bar"
    }

}