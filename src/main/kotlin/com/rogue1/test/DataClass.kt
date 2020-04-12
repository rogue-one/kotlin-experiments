package com.rogue1.test

/**
 * sample data class
 */
data class DataClass(val firstName: String, val lastName: String, val age: Int)


fun test(data: DataClass) {
    val (firstName, lastName, age) = data // data class destructuring
    println("$firstName has a age of $age")

}
