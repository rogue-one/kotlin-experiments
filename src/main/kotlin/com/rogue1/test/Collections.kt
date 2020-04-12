package com.rogue1.test


/**
 *
 * Kotlin provides a pair of interfaces for each Collection implementations.
 * A read-only interface and a mutable interface.
 */
class TestCollections {

    data class Person(val name: String, val age: Int)

    val list = listOf(Person("a", 10), Person("b", 20))

    /**
     *
     * this map init is expensive because it takes an vararg as input and uses Tuples as intermediate constructor argument.
     */
    val expensiveMap = mapOf("one" to 1, "two" to 2)

    /**
     * this is a cheap way to define a map
     */
    val cheapMap = HashMap<String, Int>().apply { this["one"] = 1; this["two"] = 2 }


    init {
        /**
         * parantheses around input parameters must be used only when we are employing de-structuring.
         */
        list.fold(0) { acc, (_, x) -> acc + x }
    }

    object Variance {

        /**
         *
         * this is legal because read only lists are co-variant.
         */
        val shapes: List<Shape> = listOf(Rectangle())

        open class Shape {
             open fun name(): String = "shape"
        }

        class Rectangle: Shape() {
            override fun name(): String = "rectangle"
        }
    }

}