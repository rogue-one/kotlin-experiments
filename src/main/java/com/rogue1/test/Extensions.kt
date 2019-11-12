package com.rogue1.test

object Extensions {

    /**
     * like extension functions kotlin allows us to define extension properties using custom getters.
     * since extensions doesn't allow to modify the original class the accessors cannot have a backing field.
     */
    val <T> List<T>.listSize: Int
    get() { return this.size - 1 }

    /**
     * an extension function defined on MutableList<T> type
     */
    fun <T> MutableList<T>.swap(idx1: Int, idx2: Int): Unit {
        val tmp = this[idx1]
        this[idx1] = this[idx2]
        this[idx2] = tmp
    }

}


class Host(val hostName: String) {
    fun printHostname() { print(hostName) }
    val data: String = hostName
}

class Connection(private val host: Host, private val port: Int) {

    private fun printPort() { print(port) }

    val data: String = "$host:$port"

    /**
     * this is an extension function defined inside the class Connection.
     * this extension function will be available only with this class Connection.
     */
    private fun Host.printConnectionString(p: Int) {
        printHostname()   // calls Host.printHostname()
        print(":")
        printPort()   // calls Connection.printPort()
        this.data // refer data field in Host class <here this refers to the host object>
        this@Connection.data // special way to refer to Connection object instead of this object.
    }

    fun connect() {
        /*...*/
        host.printConnectionString(port)   // calls the extension function
    }
}

