package com.pmm.ui.widget

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.RippleDrawable
import android.graphics.drawable.StateListDrawable
import android.os.Build
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import androidx.annotation.ColorInt
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatTextView
import com.pmm.ui.R

/**
 * Author:你需要一台永动机
 * Date:2018/9/17 10:04
 * Description:可变化的TextView,为了减少drawable的使用
 */
class SimpleView : AppCompatTextView {

    //获取对应的属性值 Android框架自带的属性 attr
    private val pressed = android.R.attr.state_pressed
    private val window_focused = android.R.attr.state_window_focused
    private val focused = android.R.attr.state_focused
    private val selected = android.R.attr.state_selected
    private val activited = android.R.attr.state_activated
    private val enabled = android.R.attr.state_enabled

    //bg
    private val bg: GradientDrawable by lazy { GradientDrawable() }
    private val selectBg: GradientDrawable by lazy { GradientDrawable() }
    private val unEnableBg: GradientDrawable by lazy { GradientDrawable() }

    //stroke
    private var strokeWidth = 0f
    private var strokeColor = Color.TRANSPARENT
    private var strokePressColor = strokeColor
    private var strokeUnEnableColor = strokeColor

    //corner
    private var cornerRadius = 0f
    private var cornerRadius_TL = 0f
    private var cornerRadius_TR = 0f
    private var cornerRadius_BL = 0f
    private var cornerRadius_BR = 0f

    //text
    private var textDefaultColor = Color.BLACK
    private var textPressColor = textDefaultColor
    private var textUnEnableColor = textDefaultColor

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        val attr = context.obtainStyledAttributes(attrs, R.styleable.SimpleView, defStyle, 0)
        //background
        val backgroundColor = attr.getColor(R.styleable.SimpleView_wm_backgroundColor, Color.TRANSPARENT)
        val backgroundPressColor = attr.getColor(R.styleable.SimpleView_wm_backgroundPressColor, backgroundColor)
        val backgroundUnEnableColor = attr.getColor(R.styleable.SimpleView_wm_backgroundUnEnableColor, backgroundColor)
        //stroke
        strokeWidth = attr.getDimension(R.styleable.SimpleView_wm_strokeWidth, 0f)
        strokeColor = attr.getColor(R.styleable.SimpleView_wm_strokeColor, Color.TRANSPARENT)
        strokePressColor = attr.getColor(R.styleable.SimpleView_wm_strokePressColor, strokeColor)
        strokeUnEnableColor = attr.getColor(R.styleable.SimpleView_wm_strokeUnEnableColor, strokeColor)
        //corner
        cornerRadius = attr.getDimension(R.styleable.SimpleView_wm_cornerRadius, -1f)
        cornerRadius_TL = attr.getDimension(R.styleable.SimpleView_wm_cornerRadius_TL, 0f)
        cornerRadius_TR = attr.getDimension(R.styleable.SimpleView_wm_cornerRadius_TR, 0f)
        cornerRadius_BL = attr.getDimension(R.styleable.SimpleView_wm_cornerRadius_BL, 0f)
        cornerRadius_BR = attr.getDimension(R.styleable.SimpleView_wm_cornerRadius_BR, 0f)

        //textPressColor
        textDefaultColor = attr.getColor(R.styleable.SimpleView_wm_textColor, Color.BLACK)
        textPressColor = attr.getColor(R.styleable.SimpleView_wm_textPressColor, textDefaultColor)
        textUnEnableColor = attr.getColor(R.styleable.SimpleView_wm_textUnEnableColor, textDefaultColor)

        attr.recycle()//必须释放

        //默认背景
        bg.apply {
            bg.setColor(backgroundColor)
            bg.setStroke(strokeWidth.toInt(), strokeColor)
            bg.cornerRadii = floatArrayOf(
                    cornerRadius_TL, cornerRadius_TL,
                    cornerRadius_TR, cornerRadius_TR,
                    cornerRadius_BR, cornerRadius_BR,
                    cornerRadius_BL, cornerRadius_BL)

            if (this@SimpleView.cornerRadius != -1f) bg.cornerRadius = this@SimpleView.cornerRadius
        }

        //选中背景
        selectBg.apply {
            selectBg.setColor(backgroundPressColor)
            selectBg.setStroke(strokeWidth.toInt(), strokePressColor)
            selectBg.cornerRadii = floatArrayOf(
                    cornerRadius_TL, cornerRadius_TL,
                    cornerRadius_TR, cornerRadius_TR,
                    cornerRadius_BR, cornerRadius_BR,
                    cornerRadius_BL, cornerRadius_BL)

            if (this@SimpleView.cornerRadius != -1f) selectBg.cornerRadius = this@SimpleView.cornerRadius
        }

        //不可用背景
        unEnableBg.apply {
            unEnableBg.setColor(backgroundUnEnableColor)
            unEnableBg.setStroke(strokeWidth.toInt(), strokeUnEnableColor)
            unEnableBg.cornerRadii = floatArrayOf(
                    cornerRadius_TL, cornerRadius_TL,
                    cornerRadius_TR, cornerRadius_TR,
                    cornerRadius_BR, cornerRadius_BR,
                    cornerRadius_BL, cornerRadius_BL
            )
            if (this@SimpleView.cornerRadius != -1f) unEnableBg.cornerRadius = this@SimpleView.cornerRadius
        }

        //复制给背景
        background = createBgSelector(bg, selectBg, bg, unEnableBg, selectBg)

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            foreground = getRippleDrawable()
//        }
        //text
        setTextColor(createTextStateList(textDefaultColor, textPressColor, textPressColor, textUnEnableColor, textPressColor))

        //gravity
        gravity = Gravity.CENTER
    }


    //获取系统的水波纹效果
    @RequiresApi(Build.VERSION_CODES.M)
    private fun getRippleDrawable(): RippleDrawable? {
        val typedValue = TypedValue()
        val attribute = intArrayOf(android.R.attr.selectableItemBackground)
        val typedArray = context.theme.obtainStyledAttributes(typedValue.resourceId, attribute)
        val drawable = typedArray.getDrawable(0) as RippleDrawable
        drawable.radius = cornerRadius.toInt()
        typedArray.recycle()
        return drawable
    }


    /**
     * 设置文本Selector
     */
    private fun createTextStateList(normal: Int, pressed: Int, focused: Int, unable: Int, activated: Int): ColorStateList {
//        val colors = intArrayOf(pressed, focused, normal, focused, unable, activated, normal)
        val colors = intArrayOf(pressed, unable, activated, normal)
        val states = arrayOfNulls<IntArray>(colors.size)
        states[0] = intArrayOf(this.enabled, this.pressed)
        states[1] = intArrayOf(-this.enabled)
//        states[1] = intArrayOf(enabled, this.focused)
//        states[2] = intArrayOf(enabled)
//        states[3] = intArrayOf(this.focused)
        states[2] = intArrayOf(this.activited)
        states[3] = intArrayOf()
        return ColorStateList(states, colors)
    }

    /**
     * 设置背景的Selector
     */
    private fun createBgSelector(normal: Drawable, pressed: Drawable, focused: Drawable, unable: Drawable, activated: Drawable): StateListDrawable {
        val bg = StateListDrawable()
        // View.PRESSED_ENABLED_STATE_SET
        bg.addState(intArrayOf(this.enabled, this.pressed), pressed)
        //activated
        bg.addState(intArrayOf(this.enabled, this.activited), activated)
//        // View.ENABLED_FOCUSED_STATE_SET
//        bg.addState(intArrayOf(this.enabled, this.focused), focused)
//        // View.ENABLED_STATE_SET
//        bg.addState(intArrayOf(this.enabled), normal)
//        // View.FOCUSED_STATE_SET
//        bg.addState(intArrayOf(this.focused), focused)
//        // View.WINDOW_FOCUSED_STATE_SET
//        bg.addState(intArrayOf(this.window_focused), unable)
        //unable
        bg.addState(intArrayOf(-this.enabled, -this.activited), unable)
        // default
        bg.addState(intArrayOf(), normal)
        return bg
    }

    /**
     * 设置背景颜色
     */
    fun setBgColor(@ColorInt default: Int? = null, @ColorInt press: Int? = null, @ColorInt unEnable: Int? = null) {
        default?.let { bg.setColor(it) }
        press?.let { selectBg.setColor(it) }
        unEnable?.let { unEnableBg.setColor(it) }
        background = createBgSelector(bg, selectBg, bg, unEnableBg, selectBg)
    }

    /**
     * 设置边框宽度
     */
    fun setBorderWidth(strokeWidth: Float) {
        this.strokeWidth = strokeWidth
        this.strokeWidth.let {
            bg.setStroke(it.toInt(), strokeColor)
            selectBg.setStroke(it.toInt(), strokePressColor)
            unEnableBg.setStroke(it.toInt(), strokeUnEnableColor)
        }

        background = createBgSelector(bg, selectBg, bg, unEnableBg, selectBg)
    }

    /**
     * 设置边框颜色
     */
    fun setBorderColor(@ColorInt default: Int? = null, @ColorInt press: Int? = null, @ColorInt unEnable: Int? = null) {
        default?.let { this.strokeColor = it }
        press?.let { this.strokePressColor = it }
        unEnable?.let { this.strokeUnEnableColor = it }
        bg.setStroke(strokeWidth.toInt(), strokeColor)
        selectBg.setStroke(strokeWidth.toInt(), strokePressColor)
        unEnableBg.setStroke(strokeWidth.toInt(), strokeUnEnableColor)

        background = createBgSelector(bg, selectBg, bg, unEnableBg, selectBg)
    }

    /**
     * 设置圆角 1
     */
    fun setCorner(cornerRadius: Float) {
        bg.cornerRadius = cornerRadius
        selectBg.cornerRadius = cornerRadius
        unEnableBg.cornerRadius = cornerRadius


        background = createBgSelector(bg, selectBg, bg, unEnableBg, selectBg)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            foreground = getRippleDrawable()
        }
    }

    /**
     * 设置圆角 2
     */
    fun setCorner(
            cornerRadius_TL: Float? = null,
            cornerRadius_TR: Float? = null,
            cornerRadius_BR: Float? = null,
            cornerRadius_BL: Float? = null) {
        cornerRadius_TL?.let { this.cornerRadius_TL = it }
        cornerRadius_TR?.let { this.cornerRadius_TR = it }
        cornerRadius_BR?.let { this.cornerRadius_BR = it }
        cornerRadius_BL?.let { this.cornerRadius_BL = it }

        bg.cornerRadii = floatArrayOf(
                this@SimpleView.cornerRadius_TL, this@SimpleView.cornerRadius_TL,
                this@SimpleView.cornerRadius_TR, this@SimpleView.cornerRadius_TR,
                this@SimpleView.cornerRadius_BR, this@SimpleView.cornerRadius_BR,
                this@SimpleView.cornerRadius_BL, this@SimpleView.cornerRadius_BL)

        selectBg.cornerRadii = floatArrayOf(
                this@SimpleView.cornerRadius_TL, this@SimpleView.cornerRadius_TL,
                this@SimpleView.cornerRadius_TR, this@SimpleView.cornerRadius_TR,
                this@SimpleView.cornerRadius_BR, this@SimpleView.cornerRadius_BR,
                this@SimpleView.cornerRadius_BL, this@SimpleView.cornerRadius_BL)

        unEnableBg.cornerRadii = floatArrayOf(
                this@SimpleView.cornerRadius_TL, this@SimpleView.cornerRadius_TL,
                this@SimpleView.cornerRadius_TR, this@SimpleView.cornerRadius_TR,
                this@SimpleView.cornerRadius_BR, this@SimpleView.cornerRadius_BR,
                this@SimpleView.cornerRadius_BL, this@SimpleView.cornerRadius_BL)

        var max = this@SimpleView.cornerRadius_TL
        if (this@SimpleView.cornerRadius_TR > max) max = this@SimpleView.cornerRadius_TR
        if (this@SimpleView.cornerRadius_BR > max) max = this@SimpleView.cornerRadius_BR
        if (this@SimpleView.cornerRadius_BL > max) max = this@SimpleView.cornerRadius_BL

        this@SimpleView.cornerRadius = max

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            foreground = getRippleDrawable()
//        }

        background = createBgSelector(bg, selectBg, bg, unEnableBg, selectBg)
    }


}