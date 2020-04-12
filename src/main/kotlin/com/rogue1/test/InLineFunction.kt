package com.rogue1.test

/**
 * inline function in kotlin is quite different. It must only be used with higher order functions specifically functions
 * that take other functions as input. this passed function will be baked into the inline function by the compiler.
 * if you inline a function without input function then it will be shown as a warning. the differences between Kotlin and
 * Scala are
 *
 *
 * +----------------------------------------------------+------------------------------------------------------+
 * |                             Scala                  |                                    Kotlin            |
 * +----------------------------------------------------+------------------------------------------------------+
 * | It is a hint and compiler takes the final          | this is an instruction and the compiler would do it  |
 * | decision                                           |                                                      |
 * |----------------------------------------------------+------------------------------------------------------+
 * | It inlines any function that doesn't take a lambda | this only inlines higher order functions with lambda |
 * | function as parameter                              | expressions as parameter                             |
 * +----------------------------------------------------+------------------------------------------------------+
 * sometimes you don't want to inline a specific lambda function parameter. these parameter must be annotated with
 * noinline modifier.
 *
 */
inline fun process(func1: () -> Unit, noinline func2: () -> Unit) {

}


/**
 * Kotlin in JVM has the same type erasure problem. so lets consider you have a Map<String, Any>. your function
 * will take key as the input parameter and a type parameter T. your function  will fetch the map value using
 * the key and cast the Any value to type T. But the problem is your function will have type erasure at run-time
 * and type cast within the function never happens.
 *
 * Reified functions must be a inline function and this handles the issue described above. being an inlined function
 * it will replace the function call with the actual function body and replaces the type parameter and the input parameter
 * with actual values passed to the inline function.
 *
 */
object Reified {

    val map: Map<String, Any> = mapOf("string" to "data", "int" to -1, "bool" to true)

    /**
     * this won't work. even when I run the below it will not return the expected null because of Type erasure.
     * {{{
     *    println(nonReifiedFun<Int>("string")) // prints data
     * }}}
     */
    fun <T> nonReifiedFun(key: String): T? {
        return map[key] as? T
    }

    /**
     *
     *
     * {{{
     *   println(reifiedFun<Int>("string")) // prints null
     * }}}
     * the above code will be re-written as
     * {{{
     * println(map[key] as? Int) // prints null
     * }}}
     */
    inline fun <reified T> reifiedFun(key: String): T? {
        return map[key] as? T
    }
}

