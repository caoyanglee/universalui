package com.weimu.universalview.helper

import android.util.Base64

/**
 * Author:你需要一台永动机
 * Date:2017/12/27 10:07
 * Description:
 */
object Base64Helper {

    //字符串 ->加密-> 字符串
    fun encode64(strData: String) = String(Base64.encode(strData.toByteArray(), Base64.DEFAULT))

    //字节数组 ->加密-> 字符串
    fun encode64(binaryData: ByteArray) = String(Base64.encode(binaryData, Base64.DEFAULT))

    //字节数组 ->解密-> 字节数组
    fun deCode64(binaryData: ByteArray) = Base64.decode(binaryData, Base64.DEFAULT)

    //字符串 ->解密-> 字节数组
    fun deCode64(strData: String) = Base64.decode(strData, Base64.DEFAULT)
}