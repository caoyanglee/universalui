package com.weimu.universalview.ktx

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


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
        local: Locale = Locale.getDefault()
): String = SimpleDateFormat(format, local).format(this)


/**
 * 格式化日期 终极版
 * 单位：毫秒
 * @param format 模板 yyyy-MM-dd HH:mm:ss
 */
fun Long.formatDate(
        format: String = "yyyy-MM-dd HH:mm:ss",
        local: Locale = Locale.getDefault()
): String = SimpleDateFormat(format, local).format(Date(this))

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
    TimeUnit.MILLISECONDS
    val currentMilSec = Date().time
    //是否为0
    if (this == 0L) return currentMilSec.formatDate("yyyy/MM/dd HH:mm")


    //差 多少秒
    val diffSec = currentMilSec / 1000 - this / 1000//时间差 秒
    if (diffSec < 60) return "刚刚"
    if (diffSec < 60 * 60) return "${diffSec / 60}分钟前"

    //差 多少天
    val currentDay = currentMilSec.formatDate("dd")
    val targetDay = this.formatDate("dd")
    val diffDay = currentDay.toInt() - targetDay.toInt()
    when (diffDay) {
        0 -> return "今天 ${this.formatDate("HH:mm")}"
        1 -> return "昨天 ${this.formatDate("HH:mm")}"
        2 -> return "前天 ${this.formatDate("HH:mm")}"
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
internal fun Long.moreThanDays(day: Int = 7): Boolean {
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
 * 当地时间 ---> UTC时间
 */
fun Any.local2UTC(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).apply {
        this.timeZone = TimeZone.getTimeZone("gmt")
    }
    return sdf.format(Date())
}


/**
 * 进入时间 UTC时间 2016-09-19T07:13:56
 * 实现的方式不好，以后继续修正
 *  @param format 模板 yyyy-MM-dd HH:mm:ss
 */
fun String.utc2Local(format: String = "yyyy-MM-dd HH:mm:ss"): String {
    if (this.isBlank()) return ""
    val utcFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.getDefault()).apply {
        this.timeZone = TimeZone.getTimeZone("UTC")
    }
    val wantFormat = SimpleDateFormat(format, Locale.getDefault()).apply {
        this.timeZone = TimeZone.getDefault()
    }
    try {
        val timeBefore = utcFormat.parse(this)
        return wantFormat.format(timeBefore)
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return ""
}

/**
 * 获取当前时间戳
 */
fun Any.getCurrentTimeStamp() = System.currentTimeMillis()





