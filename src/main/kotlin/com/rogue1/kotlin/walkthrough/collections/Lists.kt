package com.rogue1.kotlin.walkthrough.collections

object Lists {

    private val list1 = List<Int>(10, {idx -> idx})

    /**
     * collections have mapTo, filterTo, flatMapTo transformations methods
     * that will take target mutable collection in addition. the transformations are applied on this
     * mutable collections instead of creating a brand new collection like map, filter, flatMap does.
     *
     */
    val list2 = list1.mapTo(mutableSetOf()){ it * 100 }


    /**
     * the same overloaded + operator can be used to add one single element or a list of elements to the list.
     */
    private val list3 = (list1 + 100) + listOf(100,200)


    private val list4 = list1 - 0 - listOf(6,7,8)


}