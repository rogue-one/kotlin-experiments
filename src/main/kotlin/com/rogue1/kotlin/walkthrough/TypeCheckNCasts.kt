package com.rogue1.kotlin.walkthrough

class TypeCheckNCasts {

    /**
     * casting in Kotlin
     */
    val cast = 100 as Any

    /**
     * this is an conditional cast. cast the data if not convert it to null
     */
    val castOrNull: String? = 10 as? String



    fun typeCheck(data: Any) {
        /**
         * data is automatically cast to String by the compiler inside the if block.
         * This feature is called Smart cast.
         */
        if (data is String &&  data.length > 0) {
           println(data.length)
        }
    }

}