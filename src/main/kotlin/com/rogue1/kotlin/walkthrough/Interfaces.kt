package com.rogue1.kotlin.walkthrough


/**
 * Interfaces can have functions and properties. The property must be abstract or it must have a getter/setter accessors
 * with out generating a backing field (when you generate a backing field you break the rule #1 all properties must be
 * abstract)
 */
interface TestInterface {

    var field1: String

    var data: String
    get() {
        return field1
    } set(value) {
        field1 = value
    }

    fun bar()

    /**
     * interfaces cannot be sealed in kotlin. This is because kotlin interfaces are designed to inter-operate with
     * Java and nothing prevents a Java code to extend a sealed interface.
     */
    /*sealed*/ interface Test { fun hello(): String }

}

