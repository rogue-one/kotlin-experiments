package com.rogue1.test

/**
 * a simple Enum class with four possible values
 */
enum class Direction {
    NORTH, SOUTH, EAST, WEST
}

/**
 * enum class can have parameters or abstract functions just like normal classes.
 * A enum class is allowed to implement a interface but is not allowed to extend a class
 */
enum class Colour (val red: Short, val green: Short, val blue: Short) {
    RED(255, 0,0) { override fun color() = "RED"  },
    GREEN(0, 255, 0) { override fun color() = "GREEN" },
    BLUE(0, 0, 255) { override fun color() = "BLUE" }; // this semicolon is needed.
    abstract fun color(): String
}

object EnumDemo {

    fun processColor(color: Colour): String {
        println(color.name) // name of the enum RED, GREEN, BLUE etc
        println(color.ordinal) // position of the enum in Int
        println(color.red)
        return color.color()
    }

    fun demo() {
        val data = processColor(Colour.BLUE)
    }

}