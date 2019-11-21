package com.pmm.ui.core.pager

import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.PagerAdapter


/**
 * Author:你需要一台永动机
 * Date:2019-11-21 13:49
 * Description: 适合少Fragment
 */
open class BaseFragmentPagerAdapter(
        private var fm: FragmentManager,
        private val mFragment: ArrayList<Fragment> = arrayListOf(),
        behavior: Int = BEHAVIOR_SET_USER_VISIBLE_HINT
) : FragmentPagerAdapter(fm, behavior) {

    override fun getItem(position: Int): Fragment = mFragment[position]

    override fun getCount(): Int = mFragment.size

    override fun isViewFromObject(view: View, obj: Any): Boolean = view === (obj as Fragment).view

    override fun getItemPosition(`object`: Any): Int = PagerAdapter.POSITION_NONE

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val instantiateItem: Fragment = super.instantiateItem(container, position) as Fragment
        val item = mFragment[position]
        //fm对应的Fragment与当前集合的Fragment相同时，直接返回
        if (instantiateItem === item) {
            return instantiateItem
        }
        //不同时，移除fm的Fragment,返回集合内最新的Fragment
        fm.beginTransaction().apply {
            this.remove(instantiateItem).commitAllowingStateLoss()
            this.add(container.id, item, makeFragmentName(container.id, getItemId(position))).commitNowAllowingStateLoss()
        }
        return item
    }

    //FragmentPagerAdapter的私有方法，使内部fm能通过findByTag获取正确的Fragment
    private fun makeFragmentName(viewId: Int, id: Long): String = "android:switcher:$viewId:$id"

    /**
     * 优化后的方法
     */
    fun setFragments(mFragment: List<Fragment>) {
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

    /**
     * 获取Fragment
     */
    fun getFragments() = mFragment

}
