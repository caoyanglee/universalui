package com.weimu.app.universalview.adapter

import android.content.Context
import com.weimu.app.universalview.R
import com.weimu.universalib.origin.BaseB
import com.weimu.universalview.core.recyclerview.BaseRecyclerAdapter
import com.weimu.universalview.core.recyclerview.BaseRecyclerViewHolder

/**
 * Author:你需要一台永动机
 * Date:2019/2/22 14:40
 * Description:
 */
class StringAdapter(mContext: Context) : BaseRecyclerAdapter<BaseB, String>(mContext) {
    override fun getItemLayoutRes(): Int = R.layout.recy_item_string

    override fun itemViewChange(holder: BaseRecyclerViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView?.apply {

        }
    }
}