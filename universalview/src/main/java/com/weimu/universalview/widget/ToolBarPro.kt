package com.weimu.universalview.widget

import android.animation.LayoutTransition
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.weimu.universalib.ktx.dip2px
import com.weimu.universalib.ktx.getStatusBarHeight
import com.weimu.universalview.ktx.setOnClickListenerPro
import java.lang.ref.WeakReference
import kotlin.properties.Delegates

/**
 * Author:你需要一台永动机
 * Date:2019/3/21 17:53
 * Description:
 */

class ToolBarPro : ViewGroup {

    private val TAG = "ToolBarPro"

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    private var activityReference: WeakReference<Activity?>? = null

    var showStatusView by Delegates.observable(GlobalConfig.showStatusView) { property, oldValue, newValue -> requestLayout() }
    var toolbarHeight by Delegates.observable(GlobalConfig.toolbarHeight) { property, oldValue, newValue -> requestLayout() }
    var toolBarPaddingLeft by Delegates.observable(GlobalConfig.toolbarPaddingLeft) { property, oldValue, newValue -> requestLayout() }
    var toolBarPaddingRight by Delegates.observable(GlobalConfig.toolBarPaddingRight) { property, oldValue, newValue -> requestLayout() }

    //全局配置
    object GlobalConfig {
        var showStatusView = false//是否显示状态栏视图
        var toolbarHeight = 44f//dp
        var toolbarPaddingLeft = 15f//dp
        var toolBarPaddingRight = 15f//dp
        var centerTitleSize = 17f//sp 标题文字大小
    }

    private val ivNavigation: ImageView by lazy {
        ImageView(context).apply {
            this.layoutParams = MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT).apply {
                this.leftMargin = context.dip2px(toolBarPaddingLeft)
                this.topMargin = context.dip2px(6f)
                this.bottomMargin = context.dip2px(6f)
            }
            this.scaleType = ImageView.ScaleType.CENTER_INSIDE
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                this.foreground = getRippleDrawable()
            }
            this.setOnClickListenerPro {
                activityReference?.get()?.onBackPressed()
            }
        }
    }

    private val tvNavigation: TextView by lazy {
        getActionTextView().apply {
            this.layoutParams = MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT).apply {
                this.leftMargin = context.dip2px(toolBarPaddingLeft)
                this.topMargin = context.dip2px(6f)
                this.bottomMargin = context.dip2px(6f)
            }
        }
    }

    private val tvTitle by lazy {
        TextView(context).apply {
            this.layoutParams = MarginLayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT).apply {
                this.leftMargin = context.dip2px(104f)
                this.rightMargin = context.dip2px(104f)
            }
            this.text = ""
            this.gravity = Gravity.CENTER
            this.textSize = GlobalConfig.centerTitleSize
            this.setTextColor(Color.WHITE)
            this.setSingleLine()//必须设置此属性 否则无法正常居中
            this.ellipsize = TextUtils.TruncateAt.END
        }
    }

    private val ivMenuView1 by lazy {
        getActionImageView().apply {
            this.layoutParams = MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT).apply {
                this.rightMargin = context.dip2px(toolBarPaddingRight)
                this.topMargin = context.dip2px(6f)
                this.bottomMargin = context.dip2px(6f)
            }
        }
    }

    private val ivMenuView2 by lazy {
        getActionImageView().apply {
            this.layoutParams = MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT).apply {
                this.rightMargin = context.dip2px(8f)
                this.topMargin = context.dip2px(6f)
                this.bottomMargin = context.dip2px(6f)
            }
        }
    }

    private val tvMenuView1 by lazy {
        getActionTextView().apply {
            this.layoutParams = MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT).apply {
                this.rightMargin = context.dip2px(toolBarPaddingRight)
                this.topMargin = context.dip2px(6f)
                this.bottomMargin = context.dip2px(6f)
            }
        }
    }

    private val tvMenuView2 by lazy {
        getActionTextView().apply {
            this.layoutParams = MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT).apply {
                this.rightMargin = context.dip2px(8f)
                this.topMargin = context.dip2px(6f)
                this.bottomMargin = context.dip2px(6f)
            }
        }
    }


    init {
        this.layoutTransition = LayoutTransition()
        //本身视图的基础配置
        this.apply {
            clipChildren = false
            setPadding(context.dip2px(0f), context.dip2px(0f), context.dip2px(0f), context.dip2px(0f))
        }
    }

    //默认配置
    fun with(activity: Activity? = null) = this.apply {
        if (activity != null) this.activityReference = WeakReference(activity)
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        //分别获取宽高的测量模式
        //val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        //val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val statusHeight = if (showStatusView) context.getStatusBarHeight() else 0
        val toolbarHeight = context.dip2px(toolbarHeight)
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
    }


    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val startY = if (showStatusView) context.getStatusBarHeight() else 0//是否有状态栏视图

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
            it.layout(left, top, right, startY + bottom)
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
            it.layout(left, top, right, startY + bottom)
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

    }

    //屏蔽上下padding
    @Deprecated("不建议使用")
    override fun setPadding(left: Int, top: Int, right: Int, bottom: Int) {
        if (top > 0 || bottom > 0) return
        super.setPadding(left, top, right, bottom)
    }

    //获取系统的水波纹效果
    private fun getRippleDrawable(): Drawable? {
        val typedValue = TypedValue()
        val attribute = intArrayOf(android.R.attr.selectableItemBackgroundBorderless)
        val typedArray = context.theme.obtainStyledAttributes(typedValue.resourceId, attribute)
        val drawable = typedArray.getDrawable(0)
        typedArray.recycle()
        return drawable
    }

    //获取ActionView ImageView
    private fun getActionImageView(): ImageView {
        return ImageView(context).apply {
            this.scaleType = ImageView.ScaleType.CENTER_INSIDE
            //this.setPadding(context.dip2px(15f), 0, context.dip2px(15f), 0)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) this.foreground = getRippleDrawable()

        }
    }

    //获取ActionView TextView
    private fun getActionTextView(): TextView {
        return TextView(context).apply {
            this.gravity = Gravity.CENTER
            this.textSize = 16f
            this.setTextColor(Color.WHITE)
            this.text = ""
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) this.foreground = getRippleDrawable()
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

    //是否是ToolBarPro的子视图
    private fun View.isChild() = this.parent == this@ToolBarPro

    //是否需要measure,layout
    private fun View?.shouldRender(action: ((v: View) -> Unit)) {
        if (this != null && this.parent == this@ToolBarPro) action.invoke(this)
    }

}