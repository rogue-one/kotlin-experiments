package com.rogue1.test

import java.util.*
import kotlin.properties.Delegates

/**
 * in keyword in the type parameter means the type parameter is Contra-variant
 * out keyword in the type parameter means the type parameter is Co-variant.
 * Since MyList has the type parameter in both co-variance {function return type} and
 * contra-variance positions (function argument position)
 */
class MyList<T>() {

    /* ================================================================================================================== */
    /***
     * this is one cool feature of kotlin that I liked. ability to declare custom getters and setters. Something similar
     * exists in Scala but kotlin's impl is definitely seems nicer. In the below case Kotlin will generate the following'
     * methods getHead, setHead(T t) and similarly for tail too it would generate these methods. each accessor methods
     * (getHead, setHead) will have its own backing private field created which will be called `head` and `tail`.
     */
    var head: T? = null
        get () {
            println("fetching head value")
            return field // our property head has a backing field which can be referenced using the keyword field
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
     * a backing field is not always created. these are the scenarios when it is created.
     *  1. a property uses default implementation for either a getter or setter
     *  2. the customer accessor (getter/setter) uses the field identifier.
     *
     * if none of the above two cases is statisfied then a backing field is not created.
     */
    val isEmpty: Boolean
        get() = this.head != null

    /**
     * companion object. This is one thing better than Scala. because in Scala you must give the companion object the
     * same name as the Main class. this redundant naming is not needed for Kotlin.
     */
    companion object {

        /**
         * there are annotation processors that run at compile and not at run time doing code generation (scala macros).
         * These annotations can use read only values (val) but only if the val is initialized and available at compile time.
         * to guarantee that a property is initialized and available at compile time it can have a const modifier as
         * shown below.
         */
        const val COMPILE_TIME_CONSTANT = 100

        @JvmStatic fun <T> create(a1: T, a2: T): MyList<T> {
            val list = MyList<T>()
            list.head = a1
            list.tail = a2
            return list
        }
    }


//===================================================================================================================

    /**
     * sometimes you don't want kotlin to create and manage the backing field and you want to manage it yourself.
     * This is also possible as shown below.
     */
    private var _data: String? = null
    var data: Optional<String>
     get() {
         return Optional.of(_data.orEmpty())
     } set(value) {
       _data =  value.get()
    }
//===================================================================================================================


    /**
     *
     */
    val lazyVal: String by lazy {
        println("initializing lazyVal property")
        "hello world"
    }


    val observeVal: String by Delegates.observable("no-val") {
        _, old, new -> println("old value $old, new value $new")
    }

    /**
     * map delegation where the property is intialized by values in map.
     * In the below class the input map can be ```mapOf("name" to "Jane Doe", "age" to 25)```
     */
    class MapDelagate(map: Map<String, Any>) {
        val name: String by map
        val age: Int by map
    }

}