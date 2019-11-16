package com.rogue1.test

interface DataMaker {
    fun makeData(): String
    fun makeAnother(): Int
}

class DataMakerImpl : DataMaker {
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
