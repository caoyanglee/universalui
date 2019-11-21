package com.pmm.ui.core.pager


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter

/**
 * 适合多个fragment，内存占用少
 */
abstract class BaseFragmentStatePagerAdapter(
        fm: FragmentManager,
        behavior: Int = BEHAVIOR_SET_USER_VISIBLE_HINT) : FragmentStatePagerAdapter(fm, behavior) {


    override fun getItemPosition(`object`: Any): Int = PagerAdapter.POSITION_NONE

    /**
     * 获取对应Position的Fragment
     */
    fun getFragment(position: Int): Fragment {
        var superClass: Class<*>? = null
        while (true) {
            if (superClass == null) {
                superClass = this.javaClass.superclass
            } else {
                superClass = superClass.superclass
            }
            if (superClass === FragmentStatePagerAdapter::class.java) {
                break
            }
        }
        val field = superClass?.getDeclaredField("mFragments")
        field?.isAccessible = true
        val list = field?.get(this) as ArrayList<Fragment>
        return list[position]
    }

}
