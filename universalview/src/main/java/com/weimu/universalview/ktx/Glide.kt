package com.weimu.universalview.ktx

import android.content.Context
import android.os.Environment
import com.bumptech.glide.Glide
import com.weimu.universalib.ktx.deleteDir
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Author:你需要一台永动机
 * Date:2019/1/24 16:49
 * Description:
 */

/**
 * 删除Glide缓存
 */
fun Context.clearGlideCache() {
    val that = this
    //glide
    Glide.get(this).clearMemory()//必须要主线程内执行
    GlobalScope.launch(Dispatchers.Default) {
        Glide.get(that).clearDiskCache()//必须在子线程内执行
    }
}