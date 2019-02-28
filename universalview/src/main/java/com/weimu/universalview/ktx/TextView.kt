package com.weimu.universalview.ktx

import android.graphics.Paint
import android.graphics.Typeface
import android.os.Build
import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import android.support.v4.content.ContextCompat
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.*
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.weimu.universalview.interfaces.MyTextWatcher


//TextView 扩展函数&扩展属性

//******    SpannableString     ******


/**
 * 暂时所知的Span类型
 * 文字大小： AbsoluteSizeSpan(18, true)
 * 文字颜色：ForegroundColorSpan(color)
 * 文字加粗：StyleSpan(Typeface.BOLD)
 * 文字斜体：StyleSpan(Typeface.ITALIC)
 */
data class SpannableParam(var content: String, var characterStyle: CharacterStyle? = null)

/**
 * 设置文本 通过分段来设置 文本样式
 */
fun TextView.setSpannableString(vararg items: SpannableParam) {
    val ssb = SpannableStringBuilder()
    for (item in items) {
        if (item.characterStyle == null) {
            ssb.append(SpannableStringBuilder(item.content))
        } else {
            ssb.append(SpannableStringBuilder(item.content).apply {
                setSpan(item.characterStyle, 0, this.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            })
            if (item.characterStyle is ClickableSpan){
                movementMethod = LinkMovementMethod.getInstance()//必须设置才能点击
                highlightColor = ContextCompat.getColor(context, android.R.color.transparent)//设置透明的高亮背景
            }
        }
    }
    this.text = ssb
}

//根据关键词染色，懒得再数了
fun TextView.dyeByKeyword(keyWord: String, @ColorRes color: Int) {
    if (TextUtils.isEmpty(keyWord)) return
    if (!text.contains(keyWord)) return
    val str = SpannableStringBuilder(text)
    var start = 0
    while (true) {
        start = text.indexOf(keyWord, start)
        if (start < 0 || start >= text.length) break
        str.setSpan(ForegroundColorSpan(ContextCompat.getColor(context, color)), start, start + keyWord.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)//染色
        start += keyWord.length
    }
    text = str
}


//染色+点击事件
fun TextView.addClickArea(start: Int, end: Int,
                          @ColorInt color: Int = -1,
                          isUnderLine: Boolean = false,
                          click: () -> Unit) {

    val origin = text.toString().trim { it <= ' ' }
    val str = SpannableString(origin)
    str.setSpan(object : BaseClickSpan(if (color == -1) currentTextColor else color, isUnderLine) {
        override fun onClick(widget: View?) {
            //点击事件
            click()
        }

    }, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    text = str
    movementMethod = LinkMovementMethod.getInstance()//必须设置才能点击
    highlightColor = ContextCompat.getColor(context, android.R.color.transparent)//设置透明的高亮背景
}


//染色+自动寻找+点击事件
fun TextView.addClickAreaByKeyWord(keyWord: String,
                                   @ColorInt color: Int = -1,
                                   isUnderLine: Boolean = true,
                                   click: () -> Unit) {

    if (TextUtils.isEmpty(keyWord)) return
    val str = SpannableStringBuilder(text)
    var start = 0
    while (true) {
        start = text.indexOf(keyWord, start)
        if (start < 0 || start >= text.length) break
        str.setSpan(object : BaseClickSpan(if (color == -1) currentTextColor else color, isUnderLine) {
            override fun onClick(widget: View?) {
                //点击事件
                click()
            }


        }, start, start + keyWord.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        start += keyWord.length
    }
    text = str
    movementMethod = LinkMovementMethod.getInstance()//必须设置才能点击
    highlightColor = ContextCompat.getColor(context, android.R.color.transparent)//设置透明的高亮背景
}

//clickSpan
abstract class BaseClickSpan : ClickableSpan {
    private var colorDye = -1//颜色
    private var isUnderLine = false//是否有下划线

    constructor(@ColorInt colorDye: Int) : super() {
        this.colorDye = colorDye
    }

    constructor(isUnderLine: Boolean) : super() {
        this.isUnderLine = isUnderLine
    }


    constructor(colorDye: Int, isUnderLine: Boolean) : super() {
        this.colorDye = colorDye
        this.isUnderLine = isUnderLine
    }


    override fun updateDrawState(ds: TextPaint?) {
        super.updateDrawState(ds)
        ds?.apply {
            color = colorDye
            isUnderlineText = isUnderLine
        }
    }
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


//顶部画drawable
fun TextView.setTopDrawable(resource: Int = -1) {
    if (resource == -1) {
        this.setCompoundDrawables(null, null, null, null)//画在顶部
        return
    }
    val drawable = context.resources.getDrawable(resource)
    drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight) //设置边界
    this.setCompoundDrawables(null, drawable, null, null)//画在顶部
}


//右侧画drawable
fun TextView.setRightDrawable(resource: Int = -1) {
    if (resource == -1) {
        this.setCompoundDrawables(null, null, null, null)//画在顶部
        return
    }
    val drawable = ContextCompat.getDrawable(context, resource)!!
    drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight) //设置边界
    this.setCompoundDrawables(null, null, drawable, null)
}

//左侧画drawable
fun TextView.setLeftDrawable(resource: Int = -1) {
    if (resource == -1) {
        this.setCompoundDrawables(null, null, null, null)//画在顶部
        return
    }
    val drawable = context.resources.getDrawable(resource)
    drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight) //设置边界
    this.setCompoundDrawables(drawable, null, null, null)
}

//清除所有的drawable
fun TextView.clearDrawables() {
    this.setCompoundDrawables(null, null, null, null)
}


//检查通行证 -> 检查对其有意义的编辑框
fun TextView.addGuard(vararg editText: EditText) {
    val that = this
    that.isEnabled = editText.isEmpty()
    for (item in editText) {
        item.addTextChangedListener(object : MyTextWatcher() {

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

//显示html文本
fun TextView.setHtml(htmlStr: String) {
    var newHtmlStr = htmlStr.replace("\r\n", "<br/>");
    newHtmlStr = newHtmlStr.replace("\n\n", "<br/><br/>");
    newHtmlStr = newHtmlStr.replace(" ", "&#x0009;");

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        this.text = Html.fromHtml(newHtmlStr, Html.FROM_HTML_MODE_LEGACY)
    } else {
        this.text = Html.fromHtml(newHtmlStr)
    }
}


//获取文本的内容
fun TextView.getContent(): String = this.text.toString().trimMargin()


//设置文本的颜色
fun TextView.setTextColorV2(@ColorRes color: Int) {
    this.setTextColor(ContextCompat.getColor(context, color))
}


//增加缩进
fun TextView.addIndent(marginFirstLine: Int, marginNextLines: Int) {
    val originText = this.getContent()
    val result = SpannableString(originText)
    result.setSpan(LeadingMarginSpan.Standard(marginFirstLine, marginNextLines), 0, originText.length, SpannableString.SPAN_INCLUSIVE_INCLUSIVE)
    this.text = result
}