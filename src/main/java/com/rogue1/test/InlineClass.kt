package com.rogue1.test

/**
 * inline classes must have only one property that is either a primitive type like Int, Double etc or a String.
 * these classes only exists at the compile time but at run time will be replaced by the value it is wrapping around.
 * This will provide you type safety without the overhead of allocating an extra object.
 * Inline classes in Kotlin is again a replica of Value classes in Scala.
 */
inline class Data(val data: String) {
    /**
     * inline classes can have methods and implement/extend classes and interfaces. but it cannot have a backing
     * field just like interfaces in Kotlin.
     */
    fun anotherData() = 100
}


object InlineClassFuncs {

//=====================================================================================================================

/*
both the functions will be compiled to have the same signature and hence will fail at compile time. to avoid this the
function with Data parameter will be managled with a new function name like process-<some-hash-id>
 */
    fun process(data: Data) {
        TODO()
    }

    fun process(data: String) {
        TODO()
    }
//=====================================================================================================================
}