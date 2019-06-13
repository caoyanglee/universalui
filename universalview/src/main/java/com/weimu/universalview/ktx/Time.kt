package com.weimu.universalview.ktx

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


/**
 * String转Date
 * @param format 模板 yyyy-MM-dd HH:mm:ss
 */
fun String.string2Date(
        format: String = "yyyy-MM-dd HH:mm:ss",
        local: Locale = Locale.getDefault()
): Date = SimpleDateFormat(format, local).parse(this)

/**
 * Date转String
 *  @param format 模板 yyyy-MM-dd HH:mm:ss
 */
fun Date.date2String(
        format: String = "yyyy-MM-dd HH:mm:ss",
        local: Locale = Locale.getDefault(),
        isUtc: Boolean = false
): String = SimpleDateFormat(format, local).apply {
    if (isUtc) this.timeZone = TimeZone.getTimeZone("UTC")
}.format(this)


/**
 * 格式化日期 终极版
 * 单位：毫秒
 * @param format 模板 yyyy-MM-dd HH:mm:ss
 */
fun Long.formatDate(
        format: String = "yyyy-MM-dd HH:mm:ss",
        local: Locale = Locale.getDefault(),
        isUtc: Boolean = false
): String = SimpleDateFormat(format, local).apply {
    if (isUtc) this.timeZone = TimeZone.getTimeZone("UTC")
}.format(Date(this))

/**
 *
 * 格式化日期
 * 单位：毫秒
 * 刚刚
 * 分钟
 * 今天
 * 昨天
 * 前天
 */
fun Long.formatDate2now(): String {
    val currentMilSec = Date().time
    //是否为0
    if (this == 0L) return this.formatDate("yyyy/MM/dd HH:mm")

    //差 多少秒
    val diffSec = currentMilSec / 1000 - this / 1000//时间差 秒
    if (diffSec < 0) this.formatDate("yyyy/MM/dd HH:mm")

    if (diffSec < 60) return "刚刚"
    if (diffSec < 60 * 60) return "${diffSec / 60}分钟前"
    //差 多少天
    val diffDay = (diffSec / 60 / 60 / 24).toInt()
    when {
        diffDay == 0 -> return "今天 ${this.formatDate("HH:mm")}"
        diffDay == 1 -> return "昨天 ${this.formatDate("HH:mm")}"
        diffDay == 2 -> return "前天 ${this.formatDate("HH:mm")}"
        diffDay > 7 -> return "一周前"
        diffDay > 30 -> return "一个月前"
        diffDay > 30 * 6 -> return "半年前"
        diffDay > 30 * 12 -> return "一年前"
    }
    return this.formatDate("yyyy/MM/dd HH:mm")
}


/**
 * 比较时间  是否超过一周的时间
 * 单位：毫秒
 */
fun Long.moreThanAWeek(): Boolean {
    val currentTime = Calendar.getInstance().time.time
    val targetTime = this
    if (targetTime == 0L) return true
    val differ = currentTime - targetTime
    if (differ > 1000 * 60 * 60 * 24 * 7) {
        return true
    }
    return false
}

/**
 * 比较时间 是否超过几天
 */
fun Long.moreThanDays(day: Int = 7): Boolean {
    val currentTime = Calendar.getInstance().time.time
    val recordTime = this
    if (recordTime == 0L) return true
    val differ = currentTime - recordTime
    if (differ > 1000 * 60 * 60 * 24 * day) {
        return true
    }
    return false
}


/**
 *  当地时间 ---> UTC时间
 *  @param localFormatStr 模板 yyyy-MM-dd HH:mm:ss                    2016-09-19T07:13:56
 *  @param utcFormatStr   模板 yyyy-MM-dd'T'HH:mm:ss.SSS'Z'            2019-05-09T05:52:56.000Z
 */
fun String.local2UTC(
        localFormatStr: String = "yyyy-MM-dd HH:mm:ss",
        utcFormatStr: String = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
): String {
    val locaFormat = SimpleDateFormat(localFormatStr, Locale.getDefault()).apply {
        this.timeZone = TimeZone.getDefault()
    }

    val utcFormat = SimpleDateFormat(utcFormatStr, Locale.getDefault()).apply {
        this.timeZone = TimeZone.getTimeZone("UTC")
    }

    try {
        return utcFormat.format(locaFormat.parse(this))
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return ""
}


/**
 *  UTC时间 ---> 当地时间
 *  @param localFormatStr 模板 yyyy-MM-dd HH:mm:ss                    2016-09-19T07:13:56
 *  @param utcFormatStr   模板 yyyy-MM-dd'T'HH:mm:ss.SSS'Z'            2019-05-09T05:52:56.000Z
 */
fun String.utc2Local(
        localFormatStr: String = "yyyy-MM-dd HH:mm:ss",
        utcFormatStr: String = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
): String {
    if (this.isBlank()) return ""
    val utcFormat = SimpleDateFormat(utcFormatStr, Locale.getDefault()).apply {
        this.timeZone = TimeZone.getTimeZone("UTC")
    }
    val localFormat = SimpleDateFormat(localFormatStr, Locale.getDefault()).apply {
        this.timeZone = TimeZone.getDefault()
    }
    try {
        return localFormat.format(utcFormat.parse(this))
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return ""
}

/**
 * 获取当前时间戳
 */
fun Any.getCurrentTimeStamp() = System.currentTimeMillis()





