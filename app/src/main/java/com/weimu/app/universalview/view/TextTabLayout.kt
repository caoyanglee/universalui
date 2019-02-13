package com.weimu.app.universalview.view

import android.content.Context
import android.support.v4.view.ViewPager
import android.support.v7.widget.LinearLayoutCompat
import android.util.AttributeSet
import android.widget.TextView
import com.weimu.app.universalview.R
import com.weimu.universalib.interfaces.MyViewPagerChangeListener
import com.weimu.universalib.ktx.dip2px
import com.weimu.universalib.ktx.getColorPro
import com.weimu.universalview.ktx.setMargins
import com.weimu.universalview.ktx.setOnClickListenerPro

/**
 * Author:你需要一台永动机
 * Date:2018/9/27 14:44
 * Description:首页 推荐唱片 & 推荐艺术家
 */
class TextTabLayout : LinearLayoutCompat {

    var textColorDefault = context.getColorPro(R.color.white_alpha70)//文本默认颜色
    var textColorPress = context.getColorPro(R.color.white)//文本选中颜色

    var textSizeDefault = 14f
    var textSizePress = 18f


    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        setWillNotDraw(false)//重写onDraw方法,需要调用这个方法来清除flag
        clipChildren = false
        clipToPadding = false
        init(context, attrs, defStyle)
    }


    private val viewList = arrayListOf<TextView>()

    var onTabClickListener: ((index: Int, title: String) -> Unit)? = null

    //设置数据源
    var dataList = arrayListOf<String>()
        set(value) {
            field = value
            viewList.clear()
            removeAllViews()
            for ((index, item) in value.withIndex()) {
                val textView = getTextView(index, item)
                addView(textView)
                viewList.add(textView)
                textView.setTextColor(if (index == currentIndex) textColorPress else textColorDefault)
                textView.textSize = if (index == currentIndex) textSizePress else textSizeDefault
            }
        }

    //设置当前的Index
    var currentIndex = 0
        //当前的Index
        set(value) {
            field = value
            for ((index, item) in viewList.withIndex()) {
                item.setTextColor(if (index == value) textColorPress else textColorDefault)
                item.textSize = if (index == currentIndex) textSizePress else textSizeDefault
            }
        }

    var viewPager: ViewPager? = null
        set(value) {
            field = value
            value?.addOnPageChangeListener(object : MyViewPagerChangeListener() {
                override fun onPageSelected(position: Int) {
                    currentIndex = position
                }

            })
        }


    private fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        //attrs
//        val a = getContext().obtainStyledAttributes(attrs, R.styleable.LineIndicator, defStyleAttr, 0)
//        indicatorBgColor = a.getColor(R.styleable.LineIndicator_li_backgroundColor, Color.argb(26, 237, 203, 164))
//        indicatorColor = a.getColor(R.styleable.LineIndicator_li_backgroundColor, Color.argb(255, 237, 203, 164))
//        a.recycle()

        //设置方向
        //orientation = LinearLayoutCompat.VERTICAL
        //设置背景
        //setBackgroundColor(indicatorBgColor)
        //设置布局动画
        //layoutAnimation = AnimationUtils.loadLayoutAnimation(context, R.anim.layout_animation_fadein)
    }


    private fun getTextView(position: Int, title: String): TextView = TextView(context).apply {
        setTextColor(textColorDefault)
        textSize = textSizeDefault
        text = title
        layoutParams = LinearLayoutCompat.LayoutParams(LinearLayoutCompat.LayoutParams.MATCH_PARENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT)
        if (orientation == LinearLayoutCompat.VERTICAL) {
            setMargins(0, if (position == 0) 0 else context.dip2px(20f), 0, 0)
        } else {
            setMargins(if (position == 0) 0 else context.dip2px(20f), 0, 0, 0)
        }

        setOnClickListenerPro {
            currentIndex = position
            viewPager?.currentItem = currentIndex
            onTabClickListener?.invoke(position, title)
        }
    }

}
