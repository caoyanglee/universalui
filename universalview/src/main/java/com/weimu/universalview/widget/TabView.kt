package com.weimu.universalview.widget

import android.animation.LayoutTransition
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.StateListDrawable
import android.os.Build
import android.support.annotation.DrawableRes
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutCompat
import android.util.AttributeSet
import android.util.SparseArray
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import com.weimu.universalview.R
import com.weimu.universalview.ktx.*
import kotlin.properties.Delegates


/**
 * Author:你需要一台永动机
 * Date:2019-06-14 11:12
 * Description:导航栏视图
 */
class TabView : LinearLayoutCompat {

    private lateinit var hLinearLayout: LinearLayoutCompat

    private var tabList: List<TabData> by Delegates.observable(arrayListOf()) { _, _, newValue ->
        with(hLinearLayout) {
            this.removeAllViews()
            for ((index, item) in newValue.withIndex()) {
                this.addView(getTabCellView(index, item))
            }
            this@TabView.position = 0
        }
    }

    private var mTvMap: SparseArray<TextView> = SparseArray()
    private var mDotMap: SparseArray<SimpleView> = SparseArray()
    private var mNumMap: SparseArray<SimpleView> = SparseArray()


    private var position: Int by Delegates.observable(0) { _, _, newValue ->
        for (i in 0 until mTvMap.size()) {
            mTvMap[i].isActivated = newValue == i
        }

    }//tab位置


    private var textColor = Color.TRANSPARENT
    private var textColorActive = Color.TRANSPARENT
    private var textSize = 10f

    var onTabClick: ((position: Int) -> Unit)? = null

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
            context,
            attrs,
            defStyle
    ) {
        init(context, attrs, defStyle)
    }

    private fun init(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        //init attrs
        val a = context.obtainStyledAttributes(attrs, R.styleable.TabView, defStyleAttr, 0)
        val isShowDivider = a.getBoolean(R.styleable.TabView_tab_dividerShow, true)
        val dividerColor =
                a.getColor(R.styleable.TabView_tab_dividerColor, Color.rgb(221, 221, 221))
        val dividerSize = a.getDimension(R.styleable.TabView_tab_dividerSize, 1f)
        textColor = a.getColor(R.styleable.TabView_tab_textColor, Color.BLACK)
        textColorActive = a.getColor(
                R.styleable.TabView_tab_textColorActive,
                context.getColorPro(R.color.colorAccent)
        )

        textSize = a.getDimension(R.styleable.TabView_tab_textSize, 10f)
        a.recycle()

        this.apply {
            this.orientation = VERTICAL
        }

        val divider = View(getContext()).apply {
            this.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, dividerSize.toInt())
            this.setBackgroundColor(dividerColor)
            if (isShowDivider) this.visible() else this.gone()
            this@TabView.addView(this)
        }

        hLinearLayout = LinearLayoutCompat(context).apply {
            this.orientation = HORIZONTAL
            this.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
            this@TabView.addView(this)
        }

        setData(
                TabData("Tab1", R.color.colorPrimary, R.color.colorPrimaryDark),
                TabData("Tab2", R.color.colorPrimary, R.color.colorPrimaryDark),
                TabData("Tab3", R.color.colorPrimary, R.color.colorPrimaryDark),
                TabData("Tab4", R.color.colorPrimary, R.color.colorPrimaryDark)
        )
    }

    private fun getTabCellView(position: Int, data: TabData): FrameLayout {
        return FrameLayout(context).apply {
            this.layoutTransition = LayoutTransition()//布局动画
            this.layoutParams = LayoutParams(0, LayoutParams.MATCH_PARENT, 1f)
            this.setOnClickListenerPro {
                this@TabView.position = position
                onTabClick?.invoke(position)
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                this.foreground = context.getRippleBorderLess()
            }

            //文本
            val mTvCenter = TextView(context).apply {
                this.layoutParams = FrameLayout.LayoutParams(
                        LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT,
                        Gravity.CENTER
                )
                this.text = "${data.name}"
                this.gravity = Gravity.CENTER
                this.textSize = textSize
                this.setTextColor(createTextSelector())

                var topDrawable: Drawable? = null

                if (data.imgNormal != null && data.imgActive == null) {
                    try {
                        topDrawable = context.getDrawablePro(data.imgNormal!!)
                    } catch (e: Exception) {
                        //nothing
                    }
                }

                if (data.imgNormal != null && data.imgActive != null) {
                    try {
                        topDrawable = createImgSelector(
                                ContextCompat.getDrawable(context, data.imgNormal!!)!!,
                                ContextCompat.getDrawable(context, data.imgActive!!)!!
                        )
                    } catch (e: Exception) {
                        //nothing
                    }
                }


                this.setCompoundDrawables(
                        null,
                        topDrawable,
                        null,
                        null
                )
                this.compoundDrawablePadding = context.dip2px(2f)

            }

            this.addView(mTvCenter)
            mTvMap.put(position, mTvCenter)

            //小红点
            val mDot = SimpleView(context).apply {
                this.layoutParams =
                        FrameLayout.LayoutParams(context.dip2px(8f), context.dip2px(8f), Gravity.CENTER)
                                .apply {
                                    this.setMargins(context.dip2px(8f), 0, 0, context.dip2px(16f))
                                }
                this.setBgColor(Color.RED, Color.RED, Color.RED)
                this.isEnabled = false
                this.setCorner(90f)
                this.gone()

            }

            this.addView(mDot)
            mDotMap.put(position, mDot)

            //数量红圈
            val mNum = SimpleView(context).apply {
                this.layoutParams =
                        FrameLayout.LayoutParams(
                                context.dip2px(16f),
                                context.dip2px(16f),
                                Gravity.CENTER
                        )
                                .apply {
                                    this.setMargins(context.dip2px(8f), 0, 0, context.dip2px(16f))
                                }
                this.setBgColor(Color.RED, Color.RED, Color.RED)
                this.isEnabled = false
                this.setCorner(90f)
                this.textSize = 10f
                this.setTextColor(Color.WHITE)
                this.text = "0"
                this.gone()
            }
            this.addView(mNum)
            mNumMap.put(position, mNum)


        }
    }

    class TabData(
            var name: String,
            @DrawableRes var imgNormal: Int? = null,
            @DrawableRes var imgActive: Int? = null
    )

    fun setData(vararg tabs: TabData) {
        this.tabList = tabs.asList()
    }

    fun showRedBot(position: Int) {
        if (!checkPassport()) return
        mDotMap[position].visible()
    }

    fun hideRedBot(position: Int) {
        if (!checkPassport()) return
        mDotMap[position].gone()
    }

    fun showNum(position: Int, num: Int) {
        if (!checkPassport()) return
        mNumMap[position].text = "${
        when {
            num < 0 -> 0
            num > 99 -> 99
            else -> num
        }
        }"
        mNumMap[position].visible()
    }

    fun hideNum(position: Int) {
        if (!checkPassport()) return
        mNumMap[position].text = "0"
        mNumMap[position].gone()
    }

    private fun checkPassport(): Boolean {
        if (tabList.isEmpty()) return false
        if (position < 0 || position >= tabList.size) return false
        return true
    }

    /**
     * 设置图片的Selector
     */
    private fun createImgSelector(
            normal: Drawable, activated: Drawable
    ): StateListDrawable {
        return StateListDrawable().apply {
            //activated
            this.addState(intArrayOf(-android.R.attr.state_activated), normal)
            this.addState(intArrayOf(android.R.attr.state_activated), activated)
            // default
            this.addState(intArrayOf(), normal)
            this.setBounds(0, 0, this.minimumWidth, this.minimumHeight) //设置边界

            this.setEnterFadeDuration(300)
            this.setExitFadeDuration(300)
        }
    }

    /**
     * 设置文本的Selector
     */
    private fun createTextSelector(): ColorStateList {
        val colors = intArrayOf(textColorActive, textColor, textColor)
        val states = arrayOfNulls<IntArray>(colors.size)
        states[0] = intArrayOf(android.R.attr.state_activated)
        states[1] = intArrayOf(-android.R.attr.state_activated)
        states[2] = intArrayOf()
        return ColorStateList(states, colors)
    }


}