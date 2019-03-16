package com.weimu.universalview.helper

import android.app.Activity
import android.graphics.Rect
import android.view.View
import android.view.ViewTreeObserver
import android.widget.FrameLayout

//防止键盘挡住编辑内容
class AndroidBug5497Workaround private constructor(activity: Activity, isFullScreen: Boolean) {

    private val mChildOfContent: View
    private var usableHeightPrevious: Int = 0
    private val frameLayoutParams: FrameLayout.LayoutParams

    init {
        val content = activity.findViewById<View>(android.R.id.content) as FrameLayout
        mChildOfContent = content.getChildAt(0)
        mChildOfContent.viewTreeObserver.addOnGlobalLayoutListener { possiblyResizeChildOfContent(isFullScreen) }
        frameLayoutParams = mChildOfContent.layoutParams as FrameLayout.LayoutParams
    }

    private fun possiblyResizeChildOfContent(isFullScreen: Boolean) {
        val usableHeightNow = computeUsableHeight(isFullScreen)
        if (usableHeightNow != usableHeightPrevious) {
            val usableHeightSansKeyboard = mChildOfContent.rootView.height
            val heightDifference = usableHeightNow - usableHeightNow

            //Logger.e("usableHeightSansKeyboard=" + usableHeightSansKeyboard + " usableHeightNow=" + usableHeightNow + " heightDifference=" + heightDifference);

            if (heightDifference > usableHeightSansKeyboard / 4) {
                // keyboard probably just became visible
                frameLayoutParams.height = usableHeightNow - heightDifference
            } else {
                // keyboard probably just became hidden
                frameLayoutParams.height = usableHeightNow
            }
            mChildOfContent.requestLayout()
            usableHeightPrevious = usableHeightNow
        }
    }

    private fun computeUsableHeight(isFullScreen: Boolean): Int {
        val r = Rect()
        mChildOfContent.getWindowVisibleDisplayFrame(r)
        return if (isFullScreen) {
            r.bottom// 全屏模式下： return r.bottom
        } else r.bottom - r.top
//非全屏模式
    }

    companion object {
        // For more information, see https://code.google.com/p/android/issues/detail?id=5497
        // To use this class, simply invoke assistActivity() on an Activity that already has its content view set.

        fun assistActivity(activity: Activity, isFullScreen: Boolean) {
            AndroidBug5497Workaround(activity, isFullScreen)
        }

        fun assistActivity(activity: Activity) {
            AndroidBug5497Workaround(activity, false)
        }
    }


}

