package com.pmm.ui.ktx

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.*
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import com.pmm.ui.interfaces.MyTextWatcher


//TextView 扩展函数&扩展属性

//******    SpannableString     ******

/**
 * 暂时所知的Span类型 characterStyle
 * 文字大小：AbsoluteSizeSpan(18, true)
 * 文字颜色：ForegroundColorSpan(color)
 * 文字背景：BackgroundColorSpan(color)
 * 文字加粗：StyleSpan(Typeface.BOLD)
 * 文字斜体：StyleSpan(Typeface.ITALIC)
 * 文字点击：MyClickSpan(dyeColor,isUnderLine,clickListener)
 * 文字字体：MyTypefaceSpan(typeface)
 * 文字图片：CenterAlignImageSpan(drawable)
 */
class SpannableParam(var content: String) {
    var characterStyles = arrayListOf<CharacterStyle>()
        private set

    //通用增加Span
    fun addSpan(characterStyle: CharacterStyle): SpannableParam {
        characterStyles.add(characterStyle)
        return this
    }

    //文字大小
    fun setTextSize(size: Int, dip: Boolean): SpannableParam {
        characterStyles.add(AbsoluteSizeSpan(size, dip))
        return this
    }

    //文字颜色
    fun setTextColor(@ColorInt color: Int): SpannableParam {
        characterStyles.add(ForegroundColorSpan(color))
        return this
    }

    //文字背景
    fun setTextBackground(@ColorInt color: Int): SpannableParam {
        characterStyles.add(BackgroundColorSpan(color))
        return this
    }

    //文字样式
    fun setTextStyle(style: Int = Typeface.NORMAL): SpannableParam {
        characterStyles.add(StyleSpan(style))
        return this
    }

    //文字点击
    fun setClick(@ColorInt dyeColor: Int? = null, isUnderLine: Boolean = false, clickListener: ((widget: View?) -> Unit)? = null): SpannableParam {
        characterStyles.add(MyClickSpan(dyeColor, isUnderLine, clickListener))
        return this
    }

    //文字字体
    fun setTypeFace(typeface: Typeface): SpannableParam {
        characterStyles.add(MyTypefaceSpan(typeface))
        return this
    }

    //文字图片
    fun setImage(drawable: Drawable): SpannableParam {
        characterStyles.add(CenterAlignImageSpan(drawable))
        return this
    }
}

/**
 * 设置文本 通过分段来设置 文本样式 多个span
 */
fun TextView.setSpannableString(vararg items: SpannableParam) {
    val ssb = SpannableStringBuilder()
    for (item in items) {
        val targetStyle = item.characterStyles
        if (targetStyle.isEmpty()) {
            ssb.append(SpannableStringBuilder(item.content))
            continue
        }
        ssb.append(SpannableStringBuilder(item.content).apply {
            for (style in targetStyle) {
                if (style is ImageSpan) {
                    item.content = item.content.isBlank().toString() ?: "图片"
                    setSpan(style, 0, this.length, ImageSpan.ALIGN_BOTTOM)//这个与CenterAlignImageSpan配合使用
                    continue
                }
                if (style is ClickableSpan) {
                    movementMethod = LinkMovementMethod.getInstance()//必须设置才能点击
                    highlightColor = ContextCompat.getColor(context, android.R.color.transparent)//设置透明的高亮背景
                }
                setSpan(style, 0, this.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        })
    }
    this.text = ssb
}

/**
 * 设置文本 通过分段来设置 文本样式 多个span
 * @tips 建议使用TextView的扩展
 */
fun SpannableStringBuilder.addSpans(vararg items: SpannableParam): SpannableStringBuilder {
    val ssb = SpannableStringBuilder()
    for (item in items) {
        val targetStyle = item.characterStyles
        if (targetStyle.isEmpty()) {
            ssb.append(SpannableStringBuilder(item.content))
            continue
        }
        ssb.append(SpannableStringBuilder(item.content).apply {
            for (style in targetStyle) {
                if (style is ImageSpan) {
                    item.content = item.content.isBlank().toString() ?: "图片"
                    setSpan(style, 0, this.length, ImageSpan.ALIGN_BOTTOM)//这个与CenterAlignImageSpan配合使用
                    continue
                }
                setSpan(style, 0, this.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        })
    }
    return ssb
}


//点击的Span
class MyClickSpan(
        @ColorInt var textColor: Int? = null,
        var isUnderLine: Boolean = false,
        var clickCallback: ((widget: View?) -> Unit)? = null) : ClickableSpan() {

    override fun updateDrawState(ds: TextPaint) {
        super.updateDrawState(ds)
        with(ds) {
            this.isUnderlineText = isUnderLine//是否有下划线
            if (textColor != null) this.color = textColor as Int//设置点击颜色
        }
    }

    override fun onClick(widget: View) {
        clickCallback?.invoke(widget)//设置点击事件
    }
}

//使链接不在有下划线
class LinkUrlSpan(
        url: String,
        @ColorInt var textColor: Int? = null,
        var isUnderLine: Boolean = false,
        var urlClickCallback: ((url: String) -> Unit)? = null,
) : URLSpan(url) {

    override fun updateDrawState(ds: TextPaint) {
        super.updateDrawState(ds)
        with(ds) {
            this.isUnderlineText = isUnderLine//没有下划线
            if (textColor != null) this.color = textColor as Int//设置点击颜色
        }
    }

    override fun onClick(widget: View) {
        urlClickCallback?.invoke(url)
    }
}


//自定义获取TypeFace的Span
class MyTypefaceSpan(private val typeface: Typeface) : MetricAffectingSpan() {

    override fun updateDrawState(ds: TextPaint) {
        apply(ds)
    }

    override fun updateMeasureState(paint: TextPaint) {
        apply(paint)
    }

    private fun apply(paint: Paint) {
        val oldStyle: Int

        val old = paint.typeface
        if (old == null) {
            oldStyle = 0
        } else {
            oldStyle = old.style
        }
        val tf = typeface
        val fake = oldStyle and tf.style.inv()

        if (fake and Typeface.BOLD != 0) {
            paint.isFakeBoldText = true
        }

        if (fake and Typeface.ITALIC != 0) {
            paint.textSkewX = -0.25f
        }

        paint.typeface = tf
    }
}

//图片居中显示Span
class CenterAlignImageSpan(drawable: Drawable) : ImageSpan(drawable) {

    override fun draw(canvas: Canvas, text: CharSequence, start: Int, end: Int, x: Float, top: Int, y: Int, bottom: Int,
                      paint: Paint) {

        val b = drawable
        val fm = paint.fontMetricsInt
        val transY = (y + fm.descent + y + fm.ascent) / 2 - b.bounds.bottom / 2//计算y方向的位移
        canvas.save()
        canvas.translate(x, transY.toFloat())//绘制图片位移一段距离
        b.draw(canvas)
        canvas.restore()
    }
}


//根据关键词染色，懒得再数了
fun TextView.dyeByKeyword(keyWord: String, @ColorInt color: Int) {
    if (TextUtils.isEmpty(keyWord)) return
    if (!text.contains(keyWord)) return
    val str = SpannableStringBuilder(text)
    var start = 0
    while (true) {
        start = text.indexOf(keyWord, start)
        if (start < 0 || start >= text.length) break
        str.setSpan(ForegroundColorSpan(color), start, start + keyWord.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)//染色
        start += keyWord.length
    }
    text = str
}


//******    Others     ******

var TextView.leftMargin: Int
    get():Int {
        return (layoutParams as ViewGroup.MarginLayoutParams).leftMargin
    }
    set(value) {
        (layoutParams as ViewGroup.MarginLayoutParams).leftMargin = value
    }

//针对TextView显示中文中出现的排版错乱问题，通过调用此方法得以解决
fun TextView.fullText() {
    var content = text
    if (content is SpannableString)
        content = text.toString()
    val c = (content as String).toCharArray()
    for (i in c.indices) {
        if (c[i].toInt() == 12288) {
            c[i] = 32.toChar()
            continue
        }
        if (c[i].toInt() in 65281..65374) {
            c[i] = (c[i].toInt() - 65248).toChar()
        }

    }
    text = String(c)
}

//设置TextView的drawable
fun TextView.setDrawables(
        @DrawableRes leftImage: Int? = null,
        @DrawableRes topImage: Int? = null,
        @DrawableRes rightImage: Int? = null,
        @DrawableRes bottomImage: Int? = null,
        drawablePadding: Int = 0) {
    val leftDrawable = if (leftImage != null) context.getDrawablePro(leftImage) else null
    val topDrawable = if (topImage != null) context.getDrawablePro(topImage) else null
    val rightDrawable = if (rightImage != null) context.getDrawablePro(rightImage) else null
    val bottomDrawable = if (bottomImage != null) context.getDrawablePro(bottomImage) else null
    if (leftDrawable == null && topDrawable == null && rightDrawable == null && bottomDrawable == null) return
    this.setCompoundDrawables(leftDrawable, topDrawable, rightDrawable, bottomDrawable)
    this.compoundDrawablePadding = drawablePadding
}

//清除TextView的drawable
fun TextView.clearAllDrawables() {
    this.setCompoundDrawables(null, null, null, null)
}

//检查通行证 -> 检查对其有意义的编辑框
fun TextView.addGuard(vararg editText: EditText) {
    val that = this
    that.isEnabled = editText.isEmpty()
    for (item in editText) {
        item.addTextChangedListener(object : MyTextWatcher {

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                that.isEnabled = true
                for (innerItem in editText) {
                    if (innerItem.isEmpty()) that.isEnabled = false
                }
            }

        })
    }
}

//显示中划线
fun TextView.addMiddleLine() {
    this.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG
}

//显示中划线并加清晰
fun TextView.addMiddleLineAndEmphasize() {
    this.paint.flags = Paint.STRIKE_THRU_TEXT_FLAG or Paint.ANTI_ALIAS_FLAG

}

//取消中划线
fun TextView.clearMiddleLine() {
    this.paint.flags = 0
}

//显示下划线
fun TextView.addUnderLine() {
    this.paint.flags = Paint.UNDERLINE_TEXT_FLAG
}

//加粗
fun TextView.setBold(isBold: Boolean) {
    //todo model_name.typeface = Typeface.defaultFromStyle(Typeface.NORMAL)
    this.paint.isFakeBoldText = isBold
}

//获取文本的内容
fun TextView.getContent(): String = this.text.toString().trim()


//增加缩进
fun TextView.addIndent(marginFirstLine: Int, marginNextLines: Int) {
    val originText = this.getContent()
    val result = SpannableString(originText)
    result.setSpan(LeadingMarginSpan.Standard(marginFirstLine, marginNextLines), 0, originText.length, SpannableString.SPAN_INCLUSIVE_INCLUSIVE)
    this.text = result
}

//展示密码 InputTypes
fun TextView.showPassword() {
    inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
}

//隐藏密码  InputType
fun TextView.hidePassword() {
    inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
}