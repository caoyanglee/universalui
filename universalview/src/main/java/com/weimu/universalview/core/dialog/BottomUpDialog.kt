package com.weimu.universalview.core.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v7.app.AppCompatActivity
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import com.weimu.universalview.R

/**
 * Author:你需要一台永动机
 * Date:2019/4/9 23:40
 * Description:
 */
abstract class BottomUpDialog : BaseDialog() {

    override fun getGravity(): Int = Gravity.BOTTOM

    override fun getWindowAnimation(): Int = R.style.BottomToUpDialog


}