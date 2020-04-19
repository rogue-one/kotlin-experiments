package com.rogue1.kotlin.walkthrough.collections

object Dictionary {

    /**
     *
     * this map init is expensive because it takes an vararg as input and uses Tuples as intermediate constructor argument.
     */
    val expensiveMap: Map<String, Int> = mapOf("one" to 1, "two" to 2)

    /**
     * this is a cheap way to define a map
     */
    val cheapMap: HashMap<String, Int> = HashMap<String, Int>().apply { this["one"] = 1; this["two"] = 2 }

    val emptyMap1 = mapOf<Int, String>()

    val emptyMap2 = emptyMap<Int, String>()

}