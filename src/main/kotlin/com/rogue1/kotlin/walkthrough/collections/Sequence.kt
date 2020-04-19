package com.rogue1.kotlin.walkthrough.collections

/**
 * Sequence is lazy version of collections. They are similar to Streams in Java.
 *
 * each map, filter and other operations on say List creates a intermediate List.
 * These short lived objects are not needed and will shortly GCed and creates unnecessary overhead.
 *
 * for a list.map.filter like action. first the map operation is run for all member of the lists and a new intermediate
 * list is created. then the filter operation is run for all the elements of the intermediate list.
 *
 * for a seq.map.filter like action. both the map and filter functions are executed for each element of the sequence.
 * This doesn't create any intermediate sequences/lists etc.
 *
 */
object Sequence {

    /**
     * a sequence processing is only triggred by an action (just like dataframes/rdd in spark).
     * one type of actions is to create a regular collections (List, Set, Map)
     */
    private val listSeq by lazy {
        (1..100)
            .asSequence()
            .filter { it % 2 == 0 }
            .map { it to it * 100 }
            .toMap()
    }

    /**
     * another type of action is to do a reduce operation.
     * specialized methods like sum/avg also exists
     */
    private val reduceSeq =
        (1..100)
            .asSequence()
            .filter { it % 2 == 0 }
            .map { it * 100 }
            .reduce({acc,x -> acc + x})

    /**
     *
     * this is an yet another way to generate sequence. using coroutines
     */
    val randomNumbers = sequence<Int> {
        yield(10)
        yieldAll(listOf(100,20,300))
        yield(1)
    }

}