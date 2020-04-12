package com.rogue1.test


/**
 * like Scala it is a syntactic sugar
 */
typealias Number = Int


/**
 * Any class to support destructuring must implement componentX (where X is 1 to N numbers).
 */
class UnpackPerson(val firstName: String, val lastName: String, val age: Int) {
    operator fun component1(): String = firstName
    operator fun component2() = lastName
    operator fun component3() = age

    /**
     * Functions with block body must always specify return types explicitly, unless it's intended for them to return Unit,
     * in which case it is optional. Kotlin does not infer return types for functions with block bodies because
     * such functions may have complex control flow in the body, and the return type will be non-obvious
     * to the reader (and sometimes even for the compiler).
     */
    fun test1(i: Int): String {
        return when (i) {
            1,10 -> "one or ten"
            else -> "rest"
        }
    }


    fun lambdaExample1() {
        listOf<Int>(1,2,3,4,5,6,7,8,9).fold(0, {acc: Int, x: Int -> acc + x})
    }



    /**
     * a sample de-structuring of UnpackPerson class with 3 components implemented
     */
    fun clone() {
        val (name1, name2, years) = this
        UnpackPerson(name1, name2, years)
    }


    companion object {

        init {
            UnpackPerson echo "stalin"
        }

        /**
         * function marked with infix modifier can be called by omitting the dot and the parentheses for the call.
         * below function can be called as
         * <code>
         *     UnpackPerson echo "I am Legend"
         * </code>
         * An infix function must be either a member function or extension function. It must be associated with an object.
         * It cannot be a standalone toplevel function. This is because the below code is not a valid syntax
         * <code>
         *     echo "I am Legend"
         * </code>
         */
        private infix fun echo(msg: String) { println(msg) }


         /**
          * kotlin allows the function type signature itself to have the input parameter name.
          * ```(Int) -> Int``` is how normally the type would have denoted. but this time you can use
          * the type notation as shown below ```(in1: Int) -> Int```
          */
         val test1: (in1: Int) -> Int = { b ->  b + 1 }

        /**
         * it is the default argument name for a single parameter lambda function.
         */
        fun <T> myTest(): Int {
             val list = listOf(1,2,3,4,5)
             list.forEach {  if (it == 3) return it + 100 }
             return 10
         }


    }

}

/**
 *  use parantheses when the input parameter must be destructured. otherwise don't use parantheses around the input
 *  parameter.
 */
fun pairUnpack(): Int {
    val tuple = Pair(10, 20)
    val func1: (Pair<Int, Int>) -> Int = { (a, b) -> a + b }
    val func2: (Pair<Int, Int>) -> Int = { a -> a.first + a.second }
    return func1(tuple)
}

fun personUnPack() {
    val (firstName, lastName, age) = UnpackPerson("test1", "test2", 100)
}






object ReifiedInlineFuncs {

    interface Data
    class IntData(val data: Int) : Data
    class StringData(val data: String) : Data

    inline fun <reified T : Data> makeData(): T? {
        return when(T::class.simpleName) {
            "IntData" -> IntData(10) as T
            "StringData" -> StringData("sample") as T
            else -> null
        }
    }
}






