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

/* ================================================================================================================== */
    // this is a property defined with a user defined getter and setter. thi
    var head: T? = null
    get () {
        println("fetching head value")
        return field
    }
    set(value) { // the setter function could optionally have a different access modifier than the field
        println("setting head value to $value")
        field = value
    }
/* ================================================================================================================== */
    var tail: T? = null
    get() {
        println("returning tail")
        return field
    } set(value) {
        println("setting tail value to $value")
        field = value
    }

    /**
     * companion object. This is one thing better than Scala. because in Scala you must give the companion object the
     * same name as the Main class. this redundant naming is not needed for Kotlin.
     */
    companion object {
        fun <T> create(a1: T, a2: T): MyList<T> {
            val list = MyList<T>()
            list.head = a1
            list.tail = a2
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
    return input.head
}


/**
 * internal access modifier makes sure this class/property is only visible within this JVM compilation unit.
 * this means anything with a internal access modifier is visible only to the classes within the target jar files.
 */
internal class Test(val value: Int) {

}



