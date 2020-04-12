package com.rogue1.test

import kotlin.properties.Delegates
import kotlin.reflect.KProperty

interface DataMaker {
    fun makeData(): String
    fun makeAnother(): Int
}

class DataMakerImpl : DataMaker {
    /**
     * properties can have custom delegate. The delegate class must have the getValue and setValue functions defined with
     * proper signature.
     */
    val data: String by PropertyDelegate()

    override fun makeAnother(): Int = 10

    override fun makeData() = "your-data"
}

/**
 * here the DelegateClass extends the DataMaker interface what but implementation of DataMaker it will use is decided
 * at runtime. The compiler will make sure that DelegateClass will forward all DataMaker interface function calls to
 * maker delegate object
 */
class DelegateClass(maker: DataMaker) : DataMaker by maker {
    /**
     * The DelegateClass doesn't always have to rely on the Delegate impl class (maker: DataMakerImpl) in this case.
     */
    override fun makeAnother(): Int = -1
}


fun delegate() {
    val maker = DelegateClass(DataMakerImpl())
    maker.makeData() // delegated to DataMakerImpl
    maker.makeAnother() // right from DelegateClass
}

/**
 * a custom property delegator
 */
class PropertyDelegate {

    /**
     * thisRef parameter is a reference to the actual object that holds the property.
     * In this case it is an instance of [[DataMakerImpl]].
     *
     * property parameter has all the information about the property. information such as name, type information,
     * access modifiers etc.
     */
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
        return "$thisRef, thank you for delegating '${property.name}' to me!"
    }

    /**
     * this function in addition to what is available in getValue has the new value for the property.
     * This property is not needed for a read only variable.
     */
    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        println("$value has been assigned to '${property.name}' in $thisRef.")
    }
}

object StandardDelegates {

    /**
     * the lazy delegate initializes the property lazily and only once.
     * lazy delegate is synchronized and thread safe by default
     */
    val lazyValue: String by lazy {
        println("initializing value")
        "foo"
    }

    /**
     * unsynchronized lazy value. use this if you don't want the synchronized overhead.
     */
    val unsafeLazyValue: String by lazy(LazyThreadSafetyMode.NONE) {
        println("initializing value")
        ""
    }

    /**
     * creates a observable delegate
     */
    val observableProp: Int by Delegates.observable(0) {
        property, oldValue, newValue ->  println("updating property ${property.name} $oldValue to $newValue")
    }




}
