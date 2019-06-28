package com.weimu.app.universalview

import com.weimu.universalview.ktx.leftDecimal
import com.weimu.universalview.ktx.local2UTC
import com.weimu.universalview.ktx.utc2Local
import org.junit.Test

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    @Test
    fun addition_isCorrect() {
        val a = 2.345
        val b = a.leftDecimal(2)
        print(b)//2.34
    }


}
