package com.weimu.universalview.core.pager

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.PagerAdapter

import java.util.ArrayList

/**
 * 适合多个fragment，内存占用少
 */
class BaseFragmentStatePagerAdapter(var fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

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
    fun setFragments(mFragment: List<Fragment>) {
        val transaction = fm.beginTransaction()
        for (item in this.mFragment) {
            transaction.remove(item)
        }
        transaction.commit()
        fm.executePendingTransactions()

        this.mFragment.clear()
        this.mFragment.addAll(mFragment)
        notifyDataSetChanged()
    }

}
