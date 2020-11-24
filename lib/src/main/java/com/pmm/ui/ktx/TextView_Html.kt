package com.pmm.ui.ktx

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ImageSpan
import android.text.style.URLSpan
import android.text.util.Linkify
import android.widget.TextView
import androidx.annotation.ColorInt
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import org.xml.sax.XMLReader
import java.util.*
import java.util.regex.Pattern

/**
 * Author:你需要一台永动机
 * Date:2019-04-26 17:47
 * Description:
 */
//显示html文本
fun TextView.setHtml(htmlStr: String, tagHandler: Html.TagHandler? = null) {
    if (tagHandler != null) {
        this.isClickable = true
        this.movementMethod = LinkMovementMethod.getInstance()//设置后才能点击
    }
    val builder: SpannableStringBuilder
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        //FROM_HTML_MODE_COMPACT：html块元素之间使用一个换行符分隔
        //FROM_HTML_MODE_LEGACY：html块元素之间使用两个换行符分隔
        builder = SpannableStringBuilder(Html.fromHtml(htmlStr, Html.FROM_HTML_MODE_COMPACT, URLImageGator(this), tagHandler))
    } else {
        builder = SpannableStringBuilder(Html.fromHtml(htmlStr, URLImageGator(this), tagHandler))
    }
    this.text = builder
}


internal class URLImageGator(var textView: TextView) : Html.ImageGetter {

    override fun getDrawable(source: String?): Drawable? {
        val drawableWrapper = URLDrawableWrapper()


        Glide.with(textView.context).asBitmap().load(source).into(object : SimpleTarget<Bitmap>() {

            //因为ImageGetter接口返回的Drawable是不可变的，但是在加载网络图片完成后需要更新图片，因此在Drawable外加一个Wrapper
            override fun onResourceReady(sourceDrawable: Bitmap, transition: Transition<in Bitmap>?) {
                val targetBitmapDrawable = BitmapDrawable(textView.context.resources, sourceDrawable)
                //获取原图大小
                val width = targetBitmapDrawable.intrinsicWidth
                val height = targetBitmapDrawable.intrinsicHeight
                val aspectRatio = width * 1f / height * 1f
                //自定义drawable的高宽, 缩放图片大小最好用matrix变化，可以保证图片不失真

                val realWidth = textView.width
                val realHeight = (textView.width / aspectRatio).toInt()

                targetBitmapDrawable.setBounds(0, 0, realWidth, realHeight)
                drawableWrapper.setBounds(0, 0, realWidth, realHeight)
                drawableWrapper.drawable = targetBitmapDrawable
                textView.text = textView.text
                textView.invalidate()
            }
        })
        return drawableWrapper
    }

}

//实现DrawableWrapper
internal class URLDrawableWrapper : BitmapDrawable() {
    var drawable: Drawable? = null

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        if (drawable != null)
            drawable!!.draw(canvas)
    }
}

//实现图片点击 提供农默认的TagHandler供使用者  使用
class URLTagHandler(var imageClickListener: ((picUrl: String) -> Unit)? = null) : Html.TagHandler {

    override fun handleTag(opening: Boolean, tag: String?, output: Editable?, xmlReader: XMLReader?) {
        if (tag.isNullOrEmpty()) return
        if (output.isNullOrEmpty()) return
        //Log.e("tag=" + tag.toLowerCase())
        // 处理标签<img>
        if (tag.toLowerCase(Locale.getDefault()) == "img") {
            // 获取长度
            val len = output.length
            // 获取图片地址
            val images = output.getSpans(len - 1, len, ImageSpan::class.java)
            val imgURL = images[0].source
            // 使图片可点击并监听点击事件
            output.setSpan(MyClickSpan {
                imageClickListener?.invoke(imgURL ?: "")
            }, len - 1, len, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
    }

}

//文本的URL正则
const val ContentPattern = "((www|wap)\\.)([\\w-&&[^\\u0391-\\uFFE5]]+\\.)([\\w-?%&=&&[^\\u0391-\\uFFE5]])+"
const val ContentPatternV1 = "((http|ftp|https)://)(([a-zA-Z0-9\\._-]+\\.[a-zA-Z]{2,6})|([0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}))(:[0-9]{1,4})*(/[a-zA-Z0-9\\&=%_\\./-~-]*)?"


fun TextView.linkByUrl(@ColorInt textColor: Int = Color.rgb(33, 168, 240),
                       isUnderLine: Boolean = false,
                       urlClickCallback: ((url: String) -> Unit)? = null) {
    Linkify.addLinks(this, Pattern.compile(ContentPattern, Pattern.CASE_INSENSITIVE), "http://")
    Linkify.addLinks(this, Pattern.compile(ContentPatternV1, Pattern.CASE_INSENSITIVE), "")

    movementMethod = LinkMovementMethod.getInstance();

    val sp = SpannableString(this.text)
    val urls = sp.getSpans(0, text.length, URLSpan::class.java)

    val style = SpannableStringBuilder(text)
    style.clearSpans(); // should clear old spans
    for (url in urls) {
        val myURLSpan = LinkUrlSpan(url.url, textColor, isUnderLine, urlClickCallback)
        style.setSpan(myURLSpan, sp.getSpanStart(url), sp.getSpanEnd(url), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    }
    text = style
}