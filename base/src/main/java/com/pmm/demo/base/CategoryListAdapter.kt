package com.pmm.demo.base

import android.content.Context
import com.pmm.ui.core.BaseB
import com.pmm.ui.core.recyclerview.BaseRecyclerAdapter
import com.pmm.ui.core.recyclerview.BaseRecyclerViewHolder
import kotlinx.android.synthetic.main.base_list_item_main_category.view.*

/**
 * Author:你需要一台永动机
 * Date:2018/9/29 16:39
 * Description:
 */
class CategoryListAdapter(mContext: Context) : BaseRecyclerAdapter<BaseB, CategoryB>(mContext) {

    override fun getItemLayoutRes() = R.layout.base_list_item_main_category

    override fun itemViewChange(holder: BaseRecyclerViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.apply {
            tv_primary.text = item.primaryTitle
            tv_secondary.text = item.subTitle
        }
    }
}