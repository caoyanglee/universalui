package com.pmm.demo.base

import android.content.Context
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.pmm.demo.base.databinding.BaseListItemMainCategoryBinding
import com.pmm.ui.core.recyclerview.BaseRecyclerAdapter
import com.pmm.ui.core.recyclerview.BaseRecyclerViewHolder

/**
 * Author:你需要一台永动机
 * Date:2018/9/29 16:39
 * Description:
 */
class CategoryListAdapter(mContext: Context) : BaseRecyclerAdapter<Any, CategoryB>(mContext) {

    override fun getItemLayoutRes() = R.layout.base_list_item_main_category

    override fun getViewHolder(itemView: View?): BaseRecyclerViewHolder = MyViewHolder(itemView)

    override fun itemViewChange(holder: BaseRecyclerViewHolder, position: Int) {
        val item = getItem(position) ?: return
        (holder as MyViewHolder).mVB.apply {
            tvPrimary.text = item.primaryTitle
            tvSecondary.text = item.subTitle
        }
    }

    inner class MyViewHolder(itemView: View?) : BaseRecyclerViewHolder(itemView) {
        val mVB by viewBinding(BaseListItemMainCategoryBinding::bind, R.id.container)
    }
}