package com.rogue1.test

import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.lang.Exception

object WhenClause {


    fun boolToString(bool: Boolean): String {
        return when(bool) { // return is needed for when a function a curly brace around function
            true -> "true"
            false -> "false" // no else clause is required since all possible values (true/false) has been taken care of
        }
    }

    /**
     *
     * @param input
     */
    fun genericWhen(input: Int): String {
        return when(input) {
            0 -> "value too low"
            1,2 -> "value still too low" // any two possible values
            in 3..99 -> "value is in a good range" // range of match
            !in 1..1000 -> "yet another case" // negative match
            else -> "everything else"
        }
    }

    /**
     * use method to show case automatic closure of resources
     */
    fun autoRscClose(file: File): Unit {
        BufferedReader(InputStreamReader(FileInputStream(file))).use {
            for (line in it.lines()) { // it the default parameter name for the single parameter lambda function
                println(line)
            }
        }
    }

    /**
     *
     */
    fun processEachLine(input: File): Unit {
        input.forEachLine { println(it) }
        input.useLines { it.forEach { println(it) } }
    }

    /**
     *
     */
    fun smartAutoCast(input: Any): String  {
        return if (input is String) input // input is automatically cast to String
        else if (input is Int) (input + 100).toString() // input is automatically cast to Int
        else ""
    }

    /**
     *
     */
    fun typeChecking(input: Any): Int {
        return when(input) {
            is String -> input.length // input has been automatically cast to String
            is Int -> input + 100
            else -> -1
        }
    }

    /**
     * when pattern matching without input arg. an alternative to if else block.
     */
    fun withoutInput(input: Int): String {
        return when {
            input < 0 -> "its a negative number"
            input in 1..10 -> "between 1 and 10"
            else -> "everything else"
        }
    }

    /**
     * the try catch in itself can be an expression.
     * you could optionally have finally clause which will not return anything
     */
    fun tryCatch(): Int {
        return try {  100 }
        catch (e: Exception) { -1 }
        finally { println("running finally") }
    }


    fun equality(obj1: Any, obj2: Any): Boolean {
        obj1 === obj2 // object identity. this is similar to eq in Scala
        return obj1 == obj2 // object equality (). this is similar to == in Scala
    }


    fun multiLineStrings(person: DataClass): String {
        return """
            firstName: ${person.firstName}
            lastName: ${person.lastName}
            age in 10 years: ${person.age + 10}
        """.trimIndent()
    }



}