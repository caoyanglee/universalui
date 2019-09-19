package com.weimu.universalview.core.snackerbar

import android.graphics.Color
import android.os.Handler
import android.support.design.widget.Snackbar
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.weimu.universalview.R

/**
 * Created by 艹羊 on 2017/2/23.
 * 底部弹出的视图
 */
object SnackBarCenter {


    fun custom(snackbar: Snackbar) {
        val snackbarView = snackbar.view as LinearLayout//获取SnackBar布局View实例
        val textView = snackbarView.findViewById<View>(R.id.snackbar_text) as TextView//获取文本View实例
        val button = snackbarView.findViewById<View>(R.id.snackbar_action) as Button//获取按钮View实例

        snackbarView.setBackgroundColor(Color.parseColor("#eceff1"))//更改背景颜色
        textView.setTextColor(Color.parseColor("#448AFF"))//更改文本颜色
    }


    fun show(view: View, text: CharSequence) {
        Handler().postDelayed({ Snackbar.make(view, text, Snackbar.LENGTH_SHORT).show() }, 300)
        count()
    }

    fun showLong(view: View, text: CharSequence) {
        Snackbar.make(view, text, Snackbar.LENGTH_LONG).show()
        count()
    }

    fun showByDelay(view: View, text: CharSequence, delay: Int) {
        Handler().postDelayed({ Snackbar.make(view, text, Snackbar.LENGTH_SHORT).show() }, delay.toLong())
        count()
    }


    fun showLoneByDelay(view: View, text: CharSequence, delay: Int) {
        Handler().postDelayed({ Snackbar.make(view, text, Snackbar.LENGTH_LONG).show() }, delay.toLong())
        count()
    }


    //优化
    private var messageShowCount = 0
    private val gcCount = 5

    private fun count() {
        messageShowCount++
        if (messageShowCount >= gcCount) {
            System.gc()
            messageShowCount = 0
        }
    }
}
