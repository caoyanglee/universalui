package com.weimu.app.universalview

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
        //assertEquals(4, 2 + 2)
//        val a = Color.WHITE.isLightColor()
//        Log.d("weimu","${a}")
//        assertEquals(true, )

//        val a = "2019-05-09T13:52:56.41+08:00"
        val a = "2019-05-09T05:52:56.000Z"
        print(a)
        print("\n")
        val b = a.utc2Local()
        print(b)
        print("\n")
        val c = b.local2UTC()
        print(c)
        print("\n")
        val d = c.utc2Local()
        print(d)
    }


    fun copy(from: Array<out Any>, to: Array<Any>) {
        assert(from.size == to.size)
        for (i in from.indices)
            to[i] = from[i]

    }


    private fun test() {
        var parent:Consumer<Food> = Everybody()
        var child:Consumer<FastFood> = ModernPeople()
        ///child = parent
        parent = child
    }

    open class Food
    open class FastFood : Food()
    class Burger : FastFood()

    interface Consumer<out T>

    class Everybody : Consumer<Food>

    class ModernPeople : Consumer<FastFood>

    class American : Consumer<Burger>
}
