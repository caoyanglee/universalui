package com.weimu.universalview.ktx

import android.content.ContentResolver
import android.provider.Settings

/**
 * Author:你需要一台永动机
 * Date:2018/12/21 13:51
 * Description:
 */
//获取手机型号
fun Any.getSystemModel() = android.os.Build.MODEL

// 获取手机厂商
fun Any.getDeviceBrand() = android.os.Build.BRAND


//是否开启 不保留活动
fun ContentResolver.isAlwaysFinishActivities(): Boolean {
    val isAlways = Settings.Global.getInt(this, Settings.Global.ALWAYS_FINISH_ACTIVITIES, 0)//开发者选项 不保留活动
    return isAlways == 1
}