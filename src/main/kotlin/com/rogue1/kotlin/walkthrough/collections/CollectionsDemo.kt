package com.rogue1.kotlin.walkthrough.collections


/**
 *
 * Kotlin provides a pair of interfaces for each Collection implementations.
 * A read-only interface and a mutable interface.
 *
 * Kotlin Lists and Sets extends collections interface.
 *
 *
 */
class CollectionsDemo {

    data class Person(val name: String, val age: Int)

    /**
     * immutable list
     */
    private object Immutable {

        val list: Collection<Person> = listOf(Person("a", 10), Person("b", 20))

        val map: Map<Int, String> = mapOf(1 to "one", 2 to "two", 3 to "three")

        val set: Collection<Int> = setOf(1,2,3,4)
    }

    /**
     * Mutable collections. This type of collections has mutable methods like add/remove.
     */
    private object Mutable {
        val list: MutableCollection<Person> = mutableListOf(Person("a", 10), Person("b", 20))
        init {
            list.add(Person("z", -1))
        }
    }

    private fun <T> printAll(input: Collection<T>) {
        for(x in input) {
            println(x)
        }
    }

    init {
        printAll(Immutable.list)
        printAll(Immutable.set)
        // map is not a collection. only lists and sets are  printAll(Immutable.map)
        //Map is not a collection but you can iterate over it ...
        for(entry in Immutable.map) {
            println("key = ${entry.key}, value = ${entry.value}")
        }
    }

    /**
     * There are no separate Mutable and immutable implementations for collections/map.
     * The immutable version just don't expose the set/mutable methods.
     * You could still cast the immutable collections to its mutable collection and perform necessary
     * mutable operations.
     */
    fun breakImmutability() {
        if (Immutable.list is MutableList) {
            Immutable.list[0] = Immutable.list[1]
        }
        if (Immutable.set is MutableSet) {
            Immutable.set.add(10)
        }
        if (Immutable.map is MutableMap) {
            Immutable.map[0] = "zero"
        }
    }



}