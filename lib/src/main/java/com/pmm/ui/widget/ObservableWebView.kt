package com.pmm.ui.widget

import android.content.Context
import android.util.AttributeSet
import android.webkit.WebView

/**
 * 可监听的webVIew
 */
class ObservableWebView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.webViewStyle//构造函数必须带有样式，否则键盘无法弹起
) : WebView(context, attrs, defStyleAttr) {

    var mOnScrollChangedCallback: OnScrollChangedCallback? = null

    override fun onScrollChanged(l: Int, t: Int, oldl: Int, oldt: Int) {
        super.onScrollChanged(l, t, oldl, oldt)
        mOnScrollChangedCallback?.onScroll(l, t, oldl, oldt)

        //Log.i("contentHeight=" + this.getContentHeight() + "  scale=" + this.getScale() + "  height=" + this.getHeight() + "  scrollY=" + this.getScrollY());

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