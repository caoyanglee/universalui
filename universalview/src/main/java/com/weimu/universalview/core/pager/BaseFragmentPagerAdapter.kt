package com.weimu.universalview.core.pager

import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.PagerAdapter


/**
 * 适合少Fragment
 */
open class BaseFragmentPagerAdapter(var fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val mFragment = arrayListOf<Fragment>()

    override fun getItem(arg0: Int): Fragment {
        return mFragment[arg0]
    }

    override fun getCount(): Int {
        return mFragment.size
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view === (obj as Fragment).view
    }

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }

    /**
     * 优化后的方法
     */
    open fun setFragments(mFragment: List<Fragment>) {
        if (this.mFragment.isNotEmpty()) {
            val transaction = fm.beginTransaction()
            this.mFragment.forEach { transaction.remove(it) }
            transaction.commitAllowingStateLoss()
        }
        fm.executePendingTransactions()
        this.mFragment.clear()
        this.mFragment.addAll(mFragment)
        notifyDataSetChanged()
    }


}
