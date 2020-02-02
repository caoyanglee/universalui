package com.pmm.ui.widget

import android.animation.LayoutTransition
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.pmm.ui.ktx.*
import com.pmm.ui.R
import java.lang.ref.WeakReference
import kotlin.properties.Delegates

/**
 * Author:你需要一台永动机
 * Date:2019/3/21 17:53
 * Description:
 */

class ToolBarPro : ViewGroup {

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    private val colorPrimary: Int by lazy { context.getColorPro(R.color.colorPrimary) }
    private val colorAccent: Int by lazy { context.getColorPro(R.color.colorAccent) }

    private var activityReference: WeakReference<Activity?>? = null

    var showStatusView by Delegates.observable(GlobalConfig.showStatusView) { property, oldValue, newValue -> requestLayout() }
    var toolbarHeight by Delegates.observable(if (GlobalConfig.toolbarHeight == -1) context.dip2px(44f) else GlobalConfig.toolbarHeight) { property, oldValue, newValue -> requestLayout() }
    var toolBarPaddingLeft by Delegates.observable(if (GlobalConfig.toolbarPaddingLeft == -1) context.dip2px(15f) else GlobalConfig.toolbarPaddingLeft) { property, oldValue, newValue -> requestLayout() }
    var toolBarPaddingRight by Delegates.observable(if (GlobalConfig.toolbarPaddingRight == -1) context.dip2px(15f) else GlobalConfig.toolbarPaddingRight) { property, oldValue, newValue -> requestLayout() }

    //全局配置
    object GlobalConfig {
        var showStatusView = false//是否显示状态栏视图

        //ToolBar
        var toolbarHeight = -1//父视图的高度
        var toolbarPaddingLeft = -1//父视图的左右padding
        var toolbarPaddingRight = -1//父视图的左右padding
        var toolbarBgColor: Int? = null
        //CenterTitle
        var centerTitleSize: Float? = null//sp 标题文字大小
        var centerTitleColor: Int? = null
        //Navigation
        var navigationDrawable: Drawable? = null
        var navigationColor: Int? = null
        //menu1
        var menu1Drawable: Drawable? = null
        var menu1TextColor: Int? = null
        var menu1TextSize: Float? = null
        //menu2
        var menu2Drawable: Drawable? = null
        var menu2TextColor: Int? = null
        var menu2TextSize: Float? = null
        //menu3
        var menu3Drawable: Drawable? = null
        var menu3TextColor: Int? = null
        var menu3TextSize: Float? = null

        //menu4
        var menu4Drawable: Drawable? = null
        var menu4TextColor: Int? = null
        var menu4TextSize: Float? = null

        //divider
        var dividerShow: Boolean = true
        var dividerSize: Int = 1//1px
        var dividerColor: Int = Color.rgb(221, 221, 221)
    }

    private val ivNavigation: ImageView by lazy {
        ImageView(context).apply {
            this.layoutParams = MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT).apply {
                this.leftMargin = toolBarPaddingLeft
                this.topMargin = context.dip2px(6f)
                this.bottomMargin = context.dip2px(6f)
            }
            this.scaleType = ImageView.ScaleType.CENTER_INSIDE
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                this.foreground = context.getRippleBorderLess()
            }
            setImageDrawable(GlobalConfig.navigationDrawable)
            this.click {
                activityReference?.get()?.onBackPressed()
            }
        }
    }

    private val tvNavigation: TextView by lazy {
        getActionTextView().apply {
            this.layoutParams = MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT).apply {
                this.leftMargin = toolBarPaddingLeft
                this.topMargin = context.dip2px(6f)
                this.bottomMargin = context.dip2px(6f)
            }
            this.setTextColor(GlobalConfig.navigationColor ?: colorAccent)
        }
    }

    private val tvTitle by lazy {
        TextView(context).apply {
            this.layoutParams = MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT).apply {
                this.leftMargin = context.dip2px(56f)
                this.rightMargin = context.dip2px(56f)
            }
            this.text = ""
            this.gravity = Gravity.CENTER
            this.setSingleLine()//必须设置此属性 否则无法正常居中
            this.ellipsize = TextUtils.TruncateAt.END
            GlobalConfig.centerTitleSize?.let { this.textSize = (it) } //设置大小
            GlobalConfig.centerTitleColor?.let { this.setTextColor(it) } //设置颜色
        }
    }

    private val ivMenuView1 by lazy {
        getActionImageView().apply {
            this.layoutParams = MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT).apply {
                this.rightMargin = toolBarPaddingRight
                this.topMargin = context.dip2px(6f)
                this.bottomMargin = context.dip2px(6f)
            }
            setImageDrawable(GlobalConfig.menu1Drawable)
        }
    }

    private val ivMenuView2 by lazy {
        getActionImageView().apply {
            this.layoutParams = MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT).apply {
                this.rightMargin = context.dip2px(16f)
                this.topMargin = context.dip2px(6f)
                this.bottomMargin = context.dip2px(6f)
            }
            setImageDrawable(GlobalConfig.menu2Drawable)
        }
    }

    private val ivMenuView3 by lazy {
        getActionImageView().apply {
            this.layoutParams = MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT).apply {
                this.rightMargin = context.dip2px(16f)
                this.topMargin = context.dip2px(6f)
                this.bottomMargin = context.dip2px(6f)
            }
            setImageDrawable(GlobalConfig.menu3Drawable)
        }
    }

    private val ivMenuView4 by lazy {
        getActionImageView().apply {
            this.layoutParams = MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT).apply {
                this.rightMargin = context.dip2px(16f)
                this.topMargin = context.dip2px(6f)
                this.bottomMargin = context.dip2px(6f)
            }
            setImageDrawable(GlobalConfig.menu4Drawable)
        }
    }

    private val tvMenuView1 by lazy {
        getActionTextView().apply {
            this.layoutParams = MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT).apply {
                this.rightMargin = toolBarPaddingRight
                this.topMargin = context.dip2px(6f)
                this.bottomMargin = context.dip2px(6f)
            }
            this.setTextColor(GlobalConfig.menu1TextColor ?: colorAccent)

            GlobalConfig.menu1TextSize?.let { this.textSize = (it) } //设置大小
        }
    }

    private val tvMenuView2 by lazy {
        getActionTextView().apply {
            this.layoutParams = MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT).apply {
                this.rightMargin = context.dip2px(16f)
                this.topMargin = context.dip2px(6f)
                this.bottomMargin = context.dip2px(6f)
            }
            this.setTextColor(GlobalConfig.menu2TextColor ?: colorAccent)

            GlobalConfig.menu2TextSize?.let { this.textSize = (it) } //设置大小
        }
    }

    private val tvMenuView3 by lazy {
        getActionTextView().apply {
            this.layoutParams = MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT).apply {
                this.rightMargin = context.dip2px(16f)
                this.topMargin = context.dip2px(6f)
                this.bottomMargin = context.dip2px(6f)
            }
            this.setTextColor(GlobalConfig.menu3TextColor ?: colorAccent)

            GlobalConfig.menu3TextSize?.let { this.textSize = (it) } //设置大小
        }
    }

    private val tvMenuView4 by lazy {
        getActionTextView().apply {
            this.layoutParams = MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT).apply {
                this.rightMargin = context.dip2px(16f)
                this.topMargin = context.dip2px(6f)
                this.bottomMargin = context.dip2px(6f)
            }
            this.setTextColor(GlobalConfig.menu4TextColor ?: colorAccent)

            GlobalConfig.menu4TextSize?.let { this.textSize = (it) } //设置大小
        }
    }

    private val divider by lazy {
        View(context).apply {
            this.layoutParams = MarginLayoutParams(LayoutParams.MATCH_PARENT, GlobalConfig.dividerSize)
            this.setBackgroundColor(GlobalConfig.dividerColor)
        }
    }


    init {
        this.setBackgroundColor(GlobalConfig.toolbarBgColor ?: colorPrimary)
        this.layoutTransition = LayoutTransition()
        //本身视图的基础配置
        this.apply {
            clipChildren = false
            setPadding(context.dip2px(0f), context.dip2px(0f), context.dip2px(0f), context.dip2px(0f))
            if (GlobalConfig.dividerShow) addView(divider)//增加divider
        }
    }

    //默认配置
    fun with(activity: Activity? = null) = this.apply {
        if (activity != null) this.activityReference = WeakReference(activity)
    }

    //获取当前ToolBar的背景颜色
    fun getToolBarBgColor(): Int = (this.background as ColorDrawable).color


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //分别获取宽高的测量模式
        //val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        //val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val statusHeight = if (showStatusView) context.getStatusBarHeight() else 0
        val heightSize = toolbarHeight + statusHeight

        setMeasuredDimension(widthSize, heightSize)

        val newWidthMeasureSpec = MeasureSpec.makeMeasureSpec(widthSize, MeasureSpec.AT_MOST)
        val newHeightMeasureSpec = MeasureSpec.makeMeasureSpec(heightSize - statusHeight, MeasureSpec.EXACTLY)

        fun measureChild(v: View) {
            v.shouldRender {
                measureChildWithMargins(it, newWidthMeasureSpec, 0, newHeightMeasureSpec, 0)
            }
        }

        measureChild(ivNavigation)
        measureChild(tvNavigation)
        measureChild(tvTitle)
        measureChild(ivMenuView1)
        measureChild(tvMenuView1)
        measureChild(ivMenuView2)
        measureChild(tvMenuView2)
        measureChild(ivMenuView3)
        measureChild(tvMenuView3)
        measureChild(ivMenuView4)
        measureChild(tvMenuView4)
        measureChild(divider)
    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val startY = if (showStatusView) context.getStatusBarHeight() else 0//是否有状态栏视图
        val endY = startY + toolbarHeight

        //navigation iv
        ivNavigation.shouldRender {
            val param = (it.layoutParams as MarginLayoutParams)
            val left = paddingLeft + param.leftMargin
            val right = paddingLeft + (it.layoutParams as MarginLayoutParams).leftMargin + it.measuredWidth
            val top = startY + param.topMargin
            val bottom = top + it.measuredHeight
            it.layout(left, top, right, bottom)
        }

        //navigation tv
        tvNavigation.shouldRender {
            val param = (it.layoutParams as MarginLayoutParams)
            val left = paddingLeft + param.leftMargin
            val right = paddingLeft + (it.layoutParams as MarginLayoutParams).leftMargin + it.measuredWidth
            val top = startY + param.topMargin
            val bottom = top + it.measuredHeight
            it.layout(left, top, right, bottom)
        }

        //title
        tvTitle.shouldRender {
            val param = (it.layoutParams as MarginLayoutParams)
            val left = (tvTitle.layoutParams as MarginLayoutParams).leftMargin
            val right = measuredWidth - (tvTitle.layoutParams as MarginLayoutParams).rightMargin
            val top = startY + param.topMargin
            val bottom = top + it.measuredHeight
            tvTitle.layout(left, top, right, bottom)
        }

        //actionView1 iv
        ivMenuView1.shouldRender {
            val param = (it.layoutParams as MarginLayoutParams)
            val left = measuredWidth - paddingRight - it.measuredWidth - param.rightMargin
            val right = measuredWidth - paddingRight - param.rightMargin
            val top = startY + param.topMargin
            val bottom = top + it.measuredHeight
            it.layout(left, top, right, bottom)
        }

        //actionView1 tv
        tvMenuView1.shouldRender {
            val param = (it.layoutParams as MarginLayoutParams)
            val left = measuredWidth - paddingRight - it.measuredWidth - param.rightMargin
            val right = measuredWidth - paddingRight - param.rightMargin
            val top = startY + param.topMargin
            val bottom = top + it.measuredHeight
            it.layout(left, top, right, bottom)
        }

        //actionView2 iv
        ivMenuView2.shouldRender {
            val param = (it.layoutParams as MarginLayoutParams)
            val left = ivMenuView1.left - it.measuredWidth - param.rightMargin
            val right = ivMenuView1.left - param.rightMargin
            val top = startY + param.topMargin
            val bottom = top + it.measuredHeight
            it.layout(left, top, right, bottom)
        }

        //actionView2 tv
        tvMenuView2.shouldRender {
            val param = (it.layoutParams as MarginLayoutParams)
            val left = tvMenuView1.left - it.measuredWidth - param.rightMargin
            val right = tvMenuView1.left - param.rightMargin
            val top = startY + param.topMargin
            val bottom = top + it.measuredHeight
            it.layout(left, top, right, bottom)
        }

        //actionView3 iv
        ivMenuView3.shouldRender {
            val param = (it.layoutParams as MarginLayoutParams)
            val left = ivMenuView2.left - it.measuredWidth - param.rightMargin
            val right = ivMenuView2.left - param.rightMargin
            val top = startY + param.topMargin
            val bottom = top + it.measuredHeight
            it.layout(left, top, right, bottom)
        }

        //actionView3 tv
        tvMenuView3.shouldRender {
            val param = (it.layoutParams as MarginLayoutParams)
            val left = tvMenuView2.left - it.measuredWidth - param.rightMargin
            val right = tvMenuView2.left - param.rightMargin
            val top = startY + param.topMargin
            val bottom = top + it.measuredHeight
            it.layout(left, top, right, bottom)
        }

        //actionView3 iv
        ivMenuView4.shouldRender {
            val param = (it.layoutParams as MarginLayoutParams)
            val left = ivMenuView3.left - it.measuredWidth - param.rightMargin
            val right = ivMenuView3.left - param.rightMargin
            val top = startY + param.topMargin
            val bottom = top + it.measuredHeight
            it.layout(left, top, right, bottom)
        }

        //actionView3 tv
        tvMenuView4.shouldRender {
            val param = (it.layoutParams as MarginLayoutParams)
            val left = tvMenuView3.left - it.measuredWidth - param.rightMargin
            val right = tvMenuView3.left - param.rightMargin
            val top = startY + param.topMargin
            val bottom = top + it.measuredHeight
            it.layout(left, top, right, bottom)
        }

        //divider
        divider.shouldRender {
            val param = (it.layoutParams as MarginLayoutParams)
            val left = 0
            val right = left + it.measuredWidth
            val top = endY - GlobalConfig.dividerSize
            val bottom = endY
            it.layout(left, top, right, bottom)
        }

    }

    //屏蔽上下padding
    @Deprecated("不建议使用")
    override fun setPadding(left: Int, top: Int, right: Int, bottom: Int) {
        if (top > 0 || bottom > 0) return
        super.setPadding(left, top, right, bottom)
    }


    //获取ActionView ImageView
    private fun getActionImageView(): ImageView {
        return ImageView(context).apply {
            this.scaleType = ImageView.ScaleType.CENTER_INSIDE
            //this.setPadding(context.dip2px(15f), 0, context.dip2px(15f), 0)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) this.foreground = context.getRippleBorderLess()

        }
    }

    //获取ActionView TextView
    private fun getActionTextView(): TextView {
        return TextView(context).apply {
            this.gravity = Gravity.CENTER
            this.textSize = 16f
            this.setTextColor(Color.WHITE)
            this.text = ""
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) this.foreground = context.getRippleBorderLess()
        }
    }


    //导航按钮 ImageView
    fun navigationIcon(config: (ImageView.() -> Unit)): ToolBarPro = apply {
        if (tvNavigation.isChild()) removeView(tvNavigation)
        ivNavigation.config()
        if (!ivNavigation.isChild()) addView(ivNavigation)
    }

    //导航按钮 TextView
    fun navigationText(config: (TextView.() -> Unit)): ToolBarPro = apply {
        if (ivNavigation.isChild()) removeView(ivNavigation)
        tvNavigation.config()
        if (!tvNavigation.isChild()) addView(tvNavigation)
    }

    //中间标题 TextView
    fun centerTitle(config: (TextView.() -> Unit)): ToolBarPro = apply {
        tvTitle.config()
        if (!tvTitle.isChild()) addView(tvTitle)
    }

    //右侧菜单1 ImageView
    fun menuIcon1(config: (ImageView.() -> Unit)): ToolBarPro = apply {
        if (tvMenuView1.isChild()) removeView(tvMenuView1)
        ivMenuView1.config()
        if (!ivMenuView1.isChild()) addView(ivMenuView1)
    }


    //右侧菜单2 ImageView
    fun menuIcon2(config: (ImageView.() -> Unit)): ToolBarPro = apply {
        if (tvMenuView2.isChild()) removeView(tvMenuView2)
        ivMenuView2.config()
        if (!ivMenuView2.isChild()) addView(ivMenuView2)
    }

    //右侧菜单3 ImageView
    fun menuIcon3(config: (ImageView.() -> Unit)): ToolBarPro = apply {
        if (tvMenuView3.isChild()) removeView(tvMenuView3)
        ivMenuView3.config()
        if (!ivMenuView3.isChild()) addView(ivMenuView3)
    }

    //右侧菜单4 ImageView
    fun menuIcon4(config: (ImageView.() -> Unit)): ToolBarPro = apply {
        if (tvMenuView4.isChild()) removeView(tvMenuView4)
        ivMenuView4.config()
        if (!ivMenuView4.isChild()) addView(ivMenuView4)
    }

    //右侧菜单1 TextView
    fun menuText1(config: (TextView.() -> Unit)): ToolBarPro = apply {
        if (ivMenuView1.isChild()) removeView(ivMenuView1)
        tvMenuView1.config()
        if (!tvMenuView1.isChild()) addView(tvMenuView1)
    }


    //右侧菜单2 TextView
    fun menuText2(config: (TextView.() -> Unit)): ToolBarPro = apply {
        if (ivMenuView2.isChild()) removeView(ivMenuView2)
        tvMenuView2.config()
        if (!tvMenuView2.isChild()) addView(tvMenuView2)
    }

    //右侧菜单3 TextView
    fun menuText3(config: (TextView.() -> Unit)): ToolBarPro = apply {
        if (ivMenuView3.isChild()) removeView(ivMenuView3)
        tvMenuView3.config()
        if (!tvMenuView3.isChild()) addView(tvMenuView3)
    }

    //右侧菜单4 TextView
    fun menuText4(config: (TextView.() -> Unit)): ToolBarPro = apply {
        if (ivMenuView4.isChild()) removeView(ivMenuView4)
        tvMenuView4.config()
        if (!tvMenuView4.isChild()) addView(tvMenuView4)
    }

    //divider
    fun divider(config: (View.() -> Unit)): ToolBarPro = apply {
        if (divider.isChild()) removeView(divider)
        divider.config()
        if (!divider.isChild()) addView(divider)
    }

    //是否是ToolBarPro的子视图
    private fun View.isChild() = this.parent == this@ToolBarPro

    //是否需要measure,layout
    private fun View?.shouldRender(action: ((v: View) -> Unit)) {
        if (this != null && this.parent == this@ToolBarPro) action.invoke(this)
    }

}