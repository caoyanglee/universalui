package com.pmm.ui.core.recyclerview

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding


open class BaseRecyclerViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!)

open class BaseRecyclerViewHolderV2<T : ViewBinding>(val mVB: T) : RecyclerView.ViewHolder(mVB.root)