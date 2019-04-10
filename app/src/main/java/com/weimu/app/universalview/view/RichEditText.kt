package com.weimu.app.universalview.view

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.OnLifecycleEvent
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import org.jetbrains.annotations.NotNull
import java.lang.reflect.Field

/**
 * Author:你需要一台永动机
 * Date:2019/4/10 10:41
 * Description:
 */
class RichEditText : WebView, LifecycleObserver {

    private val SETUP_HTML = """
        <!DOCTYPE html>
        <html>
            <head>
                <meta name="viewport" content="user-scalable=no">
                <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
            </head>
            <body>
                <div id="editor" contenteditable="true"></div>
            </body>
        </html>
    """.trimIndent()

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    init {

        this.apply {
            isVerticalScrollBarEnabled = false
            isHorizontalScrollBarEnabled = false
            webViewClient = object : WebViewClient(){}
            webChromeClient = object : WebChromeClient(){}
        }

        this.settings.apply {
            this.javaScriptEnabled = true//支持javaScript
            this.setAppCacheEnabled(true)
            this.blockNetworkImage = false
            this.domStorageEnabled = true//开启DOM缓存，关闭的话H5自身的一些操作是无效的
            this.cacheMode = WebSettings.LOAD_DEFAULT
            this.loadsImagesAutomatically = true//在页面装载完成之后再去加载图片。
            this.allowUniversalAccessFromFileURLs = true
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                this.mixedContentMode = WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE
            }
            this.loadWithOverviewMode = true
            this.useWideViewPort = true
        }



        loadUrl("http://www.baidu.com");

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume(@NotNull owner: LifecycleOwner) {
        //防止后台无法释放js导致耗电
        this.settings.javaScriptEnabled = true
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onDestroy(@NotNull owner: LifecycleOwner) {
        this.webViewClient = null
        this.settings.javaScriptEnabled = false
        this.stopLoading()
        releaseAllWebViewCallback()
    }

    //释放访内存
    private fun releaseAllWebViewCallback() {
        try {
            val sConfigCallback: Field? = Class.forName("android.webkit.BrowserFrame").getDeclaredField("sConfigCallback")
            sConfigCallback?.isAccessible = true
            sConfigCallback?.set(null, null)
        } catch (e: NoSuchFieldException) {
            //doNothing
        } catch (e: ClassNotFoundException) {
            //doNothing
        } catch (e: IllegalAccessException) {
            //doNothing
        }

    }
}