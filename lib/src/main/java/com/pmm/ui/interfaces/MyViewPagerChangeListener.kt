package com.pmm.ui.interfaces

import androidx.viewpager.widget.ViewPager


/**
 * Author:你需要一台永动机
 * Date:2018/6/28 16:31
 * Description:
 */
abstract class MyViewPagerChangeListener : ViewPager.OnPageChangeListener {

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

    override fun onPageScrollStateChanged(state: Int) {}
}