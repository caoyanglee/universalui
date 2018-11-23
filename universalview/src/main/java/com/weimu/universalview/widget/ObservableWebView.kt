package com.weimu.universalview.widget

import android.content.Context
import android.util.AttributeSet
import android.webkit.WebView

/**
 * 可监听的webVIew
 */
class ObservableWebView : WebView {
    private var mOnScrollChangedCallback: OnScrollChangedCallback? = null

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {}

    override fun onOverScrolled(scrollX: Int, scrollY: Int, clampedX: Boolean, clampedY: Boolean) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY)
    }


    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        if (mOnScrollChangedCallback != null) mOnScrollChangedCallback!!.onScroll(l, t, oldl, oldt)

        //        Logger.i("contentHeight=" + this.getContentHeight() + "  scale=" + this.getScale() + "  height=" + this.getHeight() + "  scrollY=" + this.getScrollY());

        if (this.contentHeight * this.scale - (this.height + this.scrollY) < 3) {
            //已经处于底端
            if (mOnScrollChangedCallback != null) mOnScrollChangedCallback!!.onTouchBottom()
        } else if (this.scrollY == 0) {
            //已经处于顶部了
            if (mOnScrollChangedCallback != null) mOnScrollChangedCallback!!.onTouchTop()
        }
    }


    fun setOnScrollChangedCallback(onScrollChangedCallback: OnScrollChangedCallback) {
        mOnScrollChangedCallback = onScrollChangedCallback
    }




    /**
     * Impliment in the activity/fragment/view that you want to listen to the webview
     */
    interface OnScrollChangedCallback {
        fun onScroll(l: Int, t: Int, oldl: Int, oldt: Int)

        fun onTouchBottom()

        fun onTouchTop()
    }
}