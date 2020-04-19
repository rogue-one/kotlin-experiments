package com.rogue1.kotlin.walkthrough.collections

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