package com.pmm.ui.core.recyclerview

import android.content.Context
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pmm.ui.ktx.click

/**
 * @project KotLinProject
 * @author 艹羊
 * @date 2017/6/15 下午5:38
 * @description 适配器的基类
 */
//todo 设置空视图或者错误视图为0时，会出现问题
abstract class BaseRecyclerAdapter<H, T>(protected var mContext: Context) : RecyclerView.Adapter<BaseRecyclerViewHolder>() {
    private val TYPE_HEADER = 0
    private val TYPE_ITEM = 1
    private val TYPE_FOOT = 2
    private val TYPE_EMPTY = 3
    private val TYPE_ERROR = 4


    var dataList = arrayListOf<T>()
    var headerData: H? = null

    var onHeaderClick: ((headerData: H?) -> Unit)? = null//头部点击事件
    var onItemClick: ((item: T, position: Int) -> Unit)? = null//item点击事件
    var onItemLongClick: ((item: T, position: Int) -> Unit)? = null//Item长按事件
    var onFooterClick: (() -> Unit)? = null//底部点击事件

    //不私有
    var headViewSize = 0
    var emptyViewSize = 0
    var errorViewSize = 0
    var footViewSize = 0


    var emptyHeight = -1
    var errorHeight = -1

    //---Layout---

    //获取Header的Layout
    protected open fun getHeaderLayoutRes() = -1

    protected open fun getHeaderUI(): View? = null

    //获取EmptyView的Layout
    protected open fun getEmptyLayoutRes() = -1

    protected open fun getEmptyUI(): View? = null

    //获取ErrorView的Layout
    protected open fun getErrorLayoutRes() = -1

    protected open fun getErrorUI(): View? = null

    //获取Body的Layout
    protected open fun getItemLayoutRes(): Int = -1

    protected open fun getItemUI(): View? = null

    //获取Footer的Layout
    protected open fun getFooterLayoutRes() = -1

    protected open fun getFooterUI(): View? = null


    init {
        headViewSize = if (getHeaderUI() != null || getHeaderLayoutRes() != -1) 1 else 0
        emptyViewSize = if (getEmptyUI() != null || getEmptyLayoutRes() != -1) 1 else 0
        errorViewSize = if (getErrorUI() != null || getErrorLayoutRes() != -1) 1 else 0
        footViewSize = if (getFooterUI() != null || getFooterLayoutRes() != -1) 1 else 0
    }


    //---Holder---

    //获取Header的Holder
    protected open fun getHeaderHolder(itemView: View?): BaseRecyclerViewHolder = BaseRecyclerViewHolder(itemView)

    //获取Empty的Holder
    protected open fun getEmptyHolder(itemView: View?): BaseRecyclerViewHolder = BaseRecyclerViewHolder(itemView)

    //获取Error的Holder
    protected open fun getErrorHolder(itemView: View?): BaseRecyclerViewHolder = BaseRecyclerViewHolder(itemView)

    //获取Body的Holder
    protected open fun getViewHolder(itemView: View?): BaseRecyclerViewHolder = BaseRecyclerViewHolder(itemView)

    //获取Footer的Holder
    protected open fun getFooterHolder(itemView: View?): BaseRecyclerViewHolder = BaseRecyclerViewHolder(itemView)


    //---View---

    //显示Header的视图变化
    protected open fun headerViewChange(holder: BaseRecyclerViewHolder) {}

    //显示Header的视图变化 with playLoads
    protected open fun headerViewChangeWithPlayLoads(holder: BaseRecyclerViewHolder) {}

    //显示Body的视图变化
    abstract fun itemViewChange(holder: BaseRecyclerViewHolder, position: Int)

    //显示body的视图变化 with playLoads
    open fun itemViewChangeWithPlayLoads(holder: BaseRecyclerViewHolder, position: Int, payloads: MutableList<Any>) {}

    //显示Footer的视图变化
    protected open fun footerViewChange(holder: BaseRecyclerViewHolder) {}

    //显示空视图的变化
    protected open fun emptyViewChange(holder: BaseRecyclerViewHolder) {
        holder.itemView.apply {
            val layoutParam = this.layoutParams

            layoutParam.height = if (emptyHeight < 0) 0 else emptyHeight
            this.layoutParams = layoutParams
        }
    }

    //显示错误视图的变化
    protected open fun errorViewChange(holder: BaseRecyclerViewHolder) {
        holder.itemView.apply {
            val layoutParam = this.layoutParams
            layoutParam.height = if (errorHeight < 0) 0 else errorHeight
            this.layoutParams = layoutParams
        }
    }


    override fun getItemViewType(position: Int): Int {
        var type = TYPE_ITEM
        if (headViewSize == 1 && position == 0) {
            type = TYPE_HEADER
        } else if (emptyViewSize == 1 && position == 0 + headViewSize) {
            type = TYPE_EMPTY
        } else if (errorViewSize == 1 && position == 0 + headViewSize + errorViewSize) {
            type = TYPE_ERROR
        } else if (footViewSize == 1 && position == itemCount - 1) {
            //最后一个位置
            type = TYPE_FOOT
        }
        return type
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRecyclerViewHolder {
        var targetView: View? = null
        val mInflater = LayoutInflater.from(parent.context)
        when (viewType) {
            TYPE_HEADER -> {
                targetView = getHeaderUI() ?: mInflater.inflate(getHeaderLayoutRes(), parent, false)
                return getHeaderHolder(targetView)
            }
            TYPE_EMPTY -> {
                targetView = getEmptyUI() ?: mInflater.inflate(getEmptyLayoutRes(), parent, false)
                return getEmptyHolder(targetView)
            }
            TYPE_ERROR -> {
                targetView = getErrorUI() ?: mInflater.inflate(getErrorLayoutRes(), parent, false)
                return getErrorHolder(targetView)
            }
            TYPE_FOOT -> {
                targetView = getFooterUI() ?: mInflater.inflate(getFooterLayoutRes(), parent, false)
                return getFooterHolder(targetView)
            }
            TYPE_ITEM -> {
                targetView = getItemUI() ?: mInflater.inflate(getItemLayoutRes(), parent, false)
                return getViewHolder(targetView)
            }
        }
        return getViewHolder(targetView)
    }


    override fun onBindViewHolder(holder: BaseRecyclerViewHolder, position: Int) {}

    //with PlayLoads
    override fun onBindViewHolder(holder: BaseRecyclerViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(holder, position, payloads)
        when (getItemViewType(position)) {
            TYPE_HEADER -> {
                holder.itemView.setOnClickListener {
                    onHeaderClick?.invoke(headerData)
                }
                if (payloads.size > 0) {
                    headerViewChangeWithPlayLoads(holder)
                } else {
                    headerViewChange(holder)
                }

            }
            TYPE_EMPTY -> {
                emptyViewChange(holder)
            }
            TYPE_ERROR -> {
                errorViewChange(holder)
            }
            TYPE_ITEM -> {
                val index = position - (headViewSize + emptyViewSize + errorViewSize)

                //单点
                holder.itemView.click {
                    val item = getItem(index) ?: return@click
                    onItemClick?.invoke(item, index)
                }
                //长按
                holder.itemView.setOnLongClickListener {
                    val item = getItem(index) ?: return@setOnLongClickListener true
                    onItemLongClick?.invoke(item, index)
                    true
                }
                //刷新  必须先设置点击事件，因为有可能itemViewChange会覆盖
                if (payloads.size > 0) {
                    itemViewChangeWithPlayLoads(holder, index, payloads)
                } else {
                    itemViewChange(holder, index)
                }
            }
            TYPE_FOOT -> {
                holder.itemView.click {
                    onFooterClick?.invoke()
                }
                footerViewChange(holder)
            }


        }
    }


    final override fun getItemCount(): Int = headViewSize + emptyViewSize + errorViewSize + dataList.size + footViewSize

    //header
    open fun setHeaderDataToAdapter(data: H) {
        this.headerData = data
        if (headViewSize == 1) notifyItemChanged(0)
    }


    open fun setHeaderDataToAdapterWithPlayLoads(data: H) {
        this.headerData = data
        if (headViewSize == 1) notifyItemChanged(0, headerData)
    }


    open fun showHeader() {
        if (headViewSize == 1) return
        headViewSize = 1
        notifyItemChanged(0)
    }

    open fun hideHeader() {
        if (headViewSize == 0) return
        headViewSize = 0
        notifyItemChanged(0)
    }


    //body
    open fun refreshList() {
        notifyItemRangeChanged(headViewSize + errorViewSize + emptyViewSize, dataList.size)
    }


    //比较不耗性能 有动画
    open fun setDataToAdapterWithAnim(data: List<T>?, delayMills: Long = 300) {
        when {
            data.isNullOrEmpty() -> clearList()
            dataList.size == 0 -> addData(data)
            dataList.size != 0 -> {
                val originDataSize = dataList.size
                dataList.clear()
                notifyItemRangeRemoved(headViewSize + errorViewSize + emptyViewSize, originDataSize)
                //notifyDataSetChanged()
                Handler().postDelayed({ addData(data) }, delayMills)
            }
        }
    }

    //此方法 性能不好 请避免使用此方法
    open fun setDataToAdapter(data: List<T>?) {
        when {
            data.isNullOrEmpty() -> clearList()
            dataList.size == 0 -> addData(data)
            dataList.size != 0 -> {
                clearList()
                addData(data)
            }
        }
    }

    open fun addData(data: List<T>?) {
        if (data.isNullOrEmpty()) return
        val currentSize = itemCount
        dataList.addAll(data)
        val start = currentSize - footViewSize
        val count = data.size + footViewSize
        notifyItemRangeChanged(start, count)
    }

    open fun addData(item: T) {
        dataList.add(item)
        val start = itemCount - footViewSize
        val count = 1 + footViewSize
        notifyItemRangeChanged(start, count)
    }

    open fun addData(position: Int, item: T) {
        dataList.add(position, item)
        val start = headViewSize + emptyViewSize + errorViewSize + position
        notifyItemInserted(start)
        notifyItemRangeChanged(headViewSize + emptyViewSize + errorViewSize, itemCount - footViewSize)
    }

    open fun removeItem(position: Int) {
        if (position == -1 || dataList.isEmpty() || position >= dataList.size) return
        dataList.removeAt(position)
        val start = headViewSize + emptyViewSize + errorViewSize + position
        notifyItemRemoved(start)
        notifyItemRangeChanged(headViewSize + emptyViewSize + errorViewSize, itemCount - footViewSize)
    }

    //清除数据
    open fun clearList() {
        dataList.clear()
        notifyDataSetChanged()
    }

    //Empty
    open fun showEmpty(height: Int): Boolean {
        if (emptyViewSize != 1) return false
        emptyHeight = height
        notifyItemChanged(0 + headViewSize)
        return true
    }

    open fun hideEmpty(): Boolean {
        if (emptyViewSize != 1) return false
        if (emptyHeight == -1) return true
        emptyHeight = -1
        notifyItemChanged(0 + headViewSize)
        return true
    }

    //Error
    open fun showError(height: Int): Boolean {
        if (errorViewSize != 1) return false
        errorHeight = height
        notifyItemChanged(0 + headViewSize + emptyViewSize)
        return true
    }

    open fun hideError(): Boolean {
        if (errorViewSize != 1) return false
        if (errorHeight == -1) return true
        errorHeight = -1
        notifyItemChanged(0 + headViewSize + emptyViewSize)
        return true
    }

    //Footer
    open fun showFooter() {
        if (footViewSize == 1) return
        footViewSize = 1
        notifyItemChanged(itemCount - footViewSize)
    }

    open fun hideFooter() {
        if (footViewSize == 0) return
        footViewSize = 0
        notifyItemChanged(itemCount - footViewSize)
    }

    //java.lang.IndexOutOfBoundsException: Index: 0, Size: 0  下拉请求的过程中导致的问题
    open fun getItem(position: Int): T? = try {
        dataList[position]
    } catch (e: Exception) {
        null
    }

    //刷新当前Item
    open fun refreshItem(position: Int, item: T? = null) {
        if (item != null) dataList[position] = item
        notifyItemChanged((headViewSize + emptyViewSize + errorViewSize) + position)
    }

    //刷新当前Item 携带payload 局部刷新
    open fun refreshItemWithPlayLoad(position: Int, payload: Any? = null) {
        notifyItemChanged((headViewSize + emptyViewSize + errorViewSize) + position, payload)
    }

    //指定某个item
    open fun topItem(position: Int) {
        val item = dataList.get(position)
        val newDataList = ArrayList<T>()
        newDataList.add(item)
        dataList.removeAt(position)
        newDataList.addAll(dataList)
        dataList = newDataList
        notifyItemRangeChanged(headViewSize + emptyViewSize + errorViewSize, itemCount - footViewSize)
    }


    /**
     * 刷新所有Item notifyDataSetChanged
     */
    open fun refreshAllItem() {
        notifyItemRangeChanged(headViewSize + emptyViewSize + errorViewSize, itemCount - footViewSize)
    }


}