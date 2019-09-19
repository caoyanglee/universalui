package com.weimu.universalview.core.dialog

import android.view.Gravity
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