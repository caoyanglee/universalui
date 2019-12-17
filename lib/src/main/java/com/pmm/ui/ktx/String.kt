package com.pmm.ui.ktx

import java.util.*

/**
 * Author:你需要一台永动机
 * Date:2019/1/27 16:24
 * Description:补充http的开头
 */
fun String?.addHttpStart(): String {
    if (this == null) return ""
    if (!this.startsWith("https://") && !this.startsWith("http://")) return "http://$this" else return this
}


/**
 * 转半角
 */
fun String.toDBC(content: String): String {
    val c = content.toCharArray()
    for (i in c.indices) {
        //全角空格为12288，半角空格为32
        if (c[i].toInt() == 12288) {
            c[i] = 32.toChar()
            continue
        }
        if (c[i].toInt() in 65281..65374) {
            //其他字符半角(33-126)与全角(65281-65374)的对应关系是：均相差65248
            c[i] = (c[i].toInt() - 65248).toChar()
        }
    }
    return String(c)
}

/**
 * 转全角
 */
fun String.toSBC(content: String): String {
    //半角转全角：
    val c = content.toCharArray()
    for (i in c.indices) {
        if (c[i].toInt() == 32) {
            c[i] = 12288.toChar()
            continue
        }
        if (c[i].toInt() < 127)
            c[i] = (c[i].toInt() + 65248).toChar()
    }
    return String(c)
}

//uuid字符串
fun UUID.stringfy() = this.toString().replace("-", "")
