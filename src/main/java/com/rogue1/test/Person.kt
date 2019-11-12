package com.rogue1.test

/**
 * sample data class
 */
data class Person(val firstName: String, val lastName: String, val age: Int)






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



