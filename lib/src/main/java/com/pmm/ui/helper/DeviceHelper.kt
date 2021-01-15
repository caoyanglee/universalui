package com.pmm.ui.helper

import com.pmm.ui.ktx.toJsonStr


/**
 * Author:你需要一台永动机
 * Date:1/15/21 10:45 AM
 * Description:
 */
object DeviceHelper {

    //获取手机型号
    fun getDeviceModel() = android.os.Build.MODEL

    // 获取手机厂商
    fun getDeviceBrand() = android.os.Build.BRAND
}