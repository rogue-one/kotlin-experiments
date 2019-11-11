package com.rogue1.test


/**
 * sealed is open copy of scala.
 */
sealed class ParentPerson(protected open val name: String) {

    /**
     * both classes and fields are by default final and must be explicitly declared open to be overridable
     */
    open fun greet() = "hello world"
}

/**
 * when you override a property/method from parent class you don't need to repeat the access modifier.
 * access modifier is needed only when it has to be different from the base class
 */
class ChildPerson1(override protected val name: String,
                  val age: Int) : ParentPerson(name) {


    /**
     * unlike scala you cannot have this println statement inside the class body outside of any methods.
     * class body can only have either property declaration/initialization or method declaration. It cannot have
     * arbitrary statements and these statements must be inside one or many init blocks as shown below.
     */
    init {
        println("first initializer block")
    }

    init {
        println("second initializer block")
    }

    /**
     * secondary constructor must delegate to primary constructor just like in scala
     */
    constructor(): this("default", 10)

    /**
     *like in scala an overriden method/property must have a override keyword to avoid n-advertantly over-ridding functions.
     * overriden methods are open by default and require explicit final modifier to seal it.
     */
    final override fun greet() = "bye world"

}

class ChildClass2 : ParentPerson {

    /**
     * some times the child class is allowed not to have a primary constructor.
     *
     */
    @Suppress("ConvertSecondaryConstructorToPrimary")
    constructor(name: String): super(name)

}

