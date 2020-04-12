package com.rogue1.kotlin.walkthrough

interface DataContainer {

    fun getData(): String

    companion object {
       @JvmStatic fun process(container: DataContainer) {
            println(container.getData())
        }
    }

}

interface DataProcessor: DataContainer  {

    fun process(str: String): String

   companion object {
       @JvmStatic fun process(processor: DataProcessor) {
           processor.process(processor.getData())
       }
   }


}

fun testDataContainer() {
    /**
     * this is how we create an anonymous class that implements the interface DataContainer
     */
    DataContainer.process(object : DataContainer { override fun getData() = "dummy_data" })

}

open class AnonObjFun {

    // Private function, so the return type is the anonymous object type
    private fun foo() = object {
        val x: String = "x"
    }

    /**
     * Note that anonymous objects can be used as types only in local and private declarations.
     * If you use an anonymous object as a return type of a public function or the type of a public property,
     * the actual type of that function or property will be the declared supertype of the anonymous object,
     * or Any if you didn't declare any supertype. Members added in the anonymous object will not be accessible.
     *
     */
    fun publicFoo() = object {
        val x: String = "x"
    }

    fun bar() {
        val x1 = foo()       // Works
       // val x2 = publicFoo().x  // ERROR: Unresolved reference 'x'
    }

    /**
     *  here the assignment on the left hand side is not the class but the companion object of the class
     */
    val factory = AnonObjFun


    /**
     * the companion can also have name given to it. when the name is not given the default name of
     * Companion will be used. also companion objects can implement classes and interfaces
     */
    companion object Factory : AnonObjFun(), DataProcessor {

        override fun process(str: String): String = str.toLowerCase()

        override fun getData(): String = "Dummy-Data"

    }



}



