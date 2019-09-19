package com.pmm.ui.core.pager


import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter

/**
 * 适合多个fragment，内存占用少
 */
open class BaseFragmentStatePagerAdapter(var fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    private val mFragment = arrayListOf<Fragment>()

    override fun getItem(arg0: Int): Fragment {
        return mFragment[arg0]
    }

    override fun getCount(): Int {
        return mFragment.size
    }

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }

    /**
     * 优化后的方法
     */
    open fun setFragments(mFragment: List<Fragment>) {
        val transaction = fm.beginTransaction()
        for (item in this.mFragment) {
            transaction.remove(item)
        }
        transaction.commitAllowingStateLoss()
        fm.executePendingTransactions()

        this.mFragment.clear()
        this.mFragment.addAll(mFragment)
        notifyDataSetChanged()
    }

}
