package com.rogue1.test

/**
 * sample data class
 */
data class Person(val firstName: String, val lastName: String, val age: Int)

/**
 * in keyword in the type parameter means the type parameter is Contra-variant
 * out keyword in the type parameter means the type parameter is Co-variant.
 * Since MyList has the type parameter in both co-variance {function return type} and
 * contra-variance positions (function argument position)
 */
class MyList<T>() {

    var data1: T? = null
    var data2: T? = null

    fun setHead(a: T): Unit {
        data1 = a
    }

    fun setTail(a: T): Unit {
        data2 = a
    }

    fun head(): T? {
        return data1
    }

    fun tail(): T? {
        return data2
    }

    /**
     * companion object. This is one thing better than Scala. because in Scala you must give the companion object the
     * same name as the Main class. this redundant naming is not needed for Kotlin.
     */
    companion object {
        fun <T> create(a1: T, a2: T): MyList<T> {
            val list = MyList<T>()
            list.setHead(a1)
            list.setHead(a2)
            return list
        }
    }
}

/**
 * an example of usage side variance declaration. MyList cannot be either co-variance or contra-variance.
 * but the process function declares the MyList parameter as co-variance since within the function the type paramater
 * is only used in co-variance position.
 */
fun <T> process(input: MyList<out T>): T? {
    return input.head()
}



