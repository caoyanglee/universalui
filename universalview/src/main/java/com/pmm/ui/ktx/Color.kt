package com.pmm.ui.ktx

import android.graphics.Color

//是否是亮色
fun Int.isLightColor(): Boolean {
    val darkness = 1 - (0.299 * Color.red(this) + 0.587 * Color.green(this) + 0.114 * Color.blue(this)) / 255
    return darkness < 0.5
}