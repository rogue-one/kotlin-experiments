package com.rogue1.test

/**
 * extension function for Person data class.
 * extension function declared directly in the package level (equivalent to package object in scala)
 */
fun Person.greet(): String {
    return if (this.age > 10)  // if condition is a expression just like in scala
    "Good Morning " + this.firstName + " " + this.lastName
    else
        ""
}


object Main {
    @JvmStatic fun main(args: Array<String>): Unit {
        val myList = MyList<String>()
        myList.setHead("stalin")
        myList.setTail("zhukov")
        println(process(myList))
    }
}


