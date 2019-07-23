package com.weimu.universalview.widget

import android.content.Context
import android.util.AttributeSet
import android.webkit.WebView

/**
 * 可监听的webVIew
 */
class ObservableWebView : WebView {

    var mOnScrollChangedCallback: OnScrollChangedCallback? = null

    constructor(context: Context) : this(context, null)

    //构造函数必须带有样式，否则键盘无法弹起
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, android.R.attr.webViewStyle)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)


    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        mOnScrollChangedCallback?.onScroll(l, t, oldl, oldt)

        //Logger.i("contentHeight=" + this.getContentHeight() + "  scale=" + this.getScale() + "  height=" + this.getHeight() + "  scrollY=" + this.getScrollY());

        if (this.contentHeight * this.scale - (this.height + this.scrollY) < 3) {
            //已经处于底端
            mOnScrollChangedCallback?.onTouchBottom()
        } else if (this.scrollY == 0) {
            //已经处于顶部了
            mOnScrollChangedCallback?.onTouchTop()
        }
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