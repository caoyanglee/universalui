package com.weimu.universalview.core.pager

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.weimu.universalview.ktx.setOnClickListenerPro

/**
 * 加载View视图
 */
abstract class BasePagerAdapter<T>(var mContext: Context) : PagerAdapter() {

    private var dataList = arrayListOf<T>()

    var onItemClickListener: ((item: T, position: Int) -> Unit)? = null

    //获取Body的Layout
    protected abstract fun getItemLayoutRes(): Int

    //显示Body的视图变化
    abstract fun itemViewChange(view: View, position: Int)


    override fun getCount(): Int = dataList.size

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean = (view === obj)

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        return LayoutInflater.from(mContext).inflate(getItemLayoutRes(), container, false).apply {
            container.addView(this)//添加页卡
            this?.setOnClickListenerPro { onItemClickListener?.invoke(getItem(position), position) }
            itemViewChange(this, position)
        }
    }

    fun getItem(position: Int): T = dataList[position]

    override fun getItemPosition(`object`: Any): Int = PagerAdapter.POSITION_NONE

    fun setDataToAdapter(dataList: List<T>) {
        this.dataList.clear()
        this.dataList.addAll(dataList)
        notifyDataSetChanged()
    }


}


