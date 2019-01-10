package com.weimu.universalview.core.pager

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * 加载View视图
 */
abstract class BasePagerAdapter<T>(var mContext: Context) : PagerAdapter() {

    private var dataList = arrayListOf<T>()


    //获取Body的Layout
    protected abstract fun getItemLayoutRes(): Int

    //显示Body的视图变化
    abstract fun itemViewChange(view: View, position: Int)


    override fun getCount(): Int {
        return dataList.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)

    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view === obj
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(mContext).inflate(getItemLayoutRes(), container, false)
        container.addView(view)//添加页卡
        itemViewChange(view, position)
        return view
    }

    fun getItem(position: Int): T = dataList[position]


    fun setDataToAdapter(dataList: List<T>) {
        this.dataList.clear()
        this.dataList.addAll(dataList)
        notifyDataSetChanged()
    }
}


