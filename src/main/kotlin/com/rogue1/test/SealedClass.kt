package com.rogue1.test

/**
 * sealed modifier is available just as in Scala.
 * one important distinction is that Sealed classes are also abstract classes and cannot be instantiated
 */
sealed class MySealedParent(val data: Int)

class SealedChild(data: Int, val extra: Map<Int, String>) : MySealedParent(data)