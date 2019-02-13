package com.weimu.app.universalview.view

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.weimu.app.universalview.R
import com.weimu.universalib.ktx.dip2px
import com.weimu.universalview.ktx.setMargins
import com.weimu.universalview.ktx.setOnClickListenerPro
import kotlin.properties.Delegates

/**
 * Author:你需要一台永动机
 * Date:2018/10/27 16:22
 * Description:星星评分
 */
//todo 有空看那个封一个库
class StarRateView : LinearLayout {

    private val starLlist = arrayListOf<ImageView>()
    private var rateTv: TextView? = null


    private var isShowText = true
    private var isCanClick = false
    private var starFull: Drawable? = null
    private var starHalf: Drawable? = null
    var onStarCLickListener: ((rateValue: Int) -> Unit)? = null//星星点击事件

    var rateValue by Delegates.observable(5) { property, oldValue, newValue ->
        //callBack
        onStarCLickListener?.invoke(newValue)
        //text
        rateTv?.text = "${newValue}分"
        //star
        starLlist.forEachIndexed { index, imageView ->
            imageView.setImageDrawable(if (newValue >= index + 1) starFull else starHalf)
        }
    }//默认分数

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        setWillNotDraw(false)//重写onDraw方法,需要调用这个方法来清除flag
        gravity = Gravity.CENTER_VERTICAL
        orientation=LinearLayout.HORIZONTAL

        val attr = context.obtainStyledAttributes(attrs, R.styleable.StarRateView, defStyleAttr, 0)
        starFull = attr.getDrawable(R.styleable.StarRateView_srv_starFull)
        starHalf = attr.getDrawable(R.styleable.StarRateView_srv_starHalf)

        //点击
        isCanClick = attr.getBoolean(R.styleable.StarRateView_srv_canClick, false)
        for (item in 1..5) {
            if (item == 1) {
                starLlist.add(ImageView(context).apply { initStar(item) })
            } else {
                starLlist.add(ImageView(context).apply { initStar(item, context.dip2px(4f)) })
            }
        }

        //显示文本
        isShowText = attr.getBoolean(R.styleable.StarRateView_srv_showText, true)
        if (isShowText) rateTv = TextView(context).apply { init() }

        //分数
        rateValue = attr.getInteger(R.styleable.StarRateView_srv_rateValue, 0)
        attr.recycle()
    }


    private fun ImageView.initStar(index: Int = 0, leftMargin: Int = 0) {
        addView(this)
        this.setImageDrawable(starHalf)
        this.setMargins(leftMargin, 0, 0, 0)
        if (isCanClick)
            this.setOnClickListenerPro {
                rateValue = index
            }
    }

    private fun TextView.init() {
        addView(this)
        this.textSize = 12f
        this.setTextColor(Color.argb(255, 246, 206, 155))
        this.setMargins(context.dip2px(4f), 0, 0, 0)
    }

}