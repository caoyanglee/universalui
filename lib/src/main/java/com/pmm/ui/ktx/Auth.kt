package com.pmm.ui.ktx

import android.text.TextUtils
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * Author:你需要一台永动机
 * Date:2018/12/21 14:07
 * Description:
 */

/**
 * 验证身份证

 * @param IDStr
 * *
 * @return "YES" 代表合法的身份证 ,其他值代表错误信息
 * *
 * @throws ParseException
 */
fun String.verifyID(): String {
    val IDStr = this
    var tipInfo = ""// 记录错误信息
    var Ai = ""

    if (null == IDStr || IDStr.trim { it <= ' ' }.isEmpty())
        return "身份证号码长度应该为15位或18位。"

    // 判断号码的长度 15位或18位
    if (IDStr.length != 15 && IDStr.length != 18) {
        tipInfo = "身份证号码长度应该为15位或18位。"
        return tipInfo
    }
    // 18位身份证前17位位数字，如果是15位的身份证则所有号码都为数字
    if (IDStr.length == 18) {
        Ai = IDStr.substring(0, 17)
    } else if (IDStr.length == 15) {
        Ai = IDStr.substring(0, 6) + "19" + IDStr.substring(6, 15)
    }
    if (!Ai.isNumeric()) {
        tipInfo = "身份证15位号码都应为数字 ; 18位号码除最后一位外，都应为数字。"
        return tipInfo
    }
    // 判断出生年月是否有效
    val strYear = Ai.substring(6, 10)// 年份
    val strMonth = Ai.substring(10, 12)// 月份
    val strDay = Ai.substring(12, 14)// 日期
    if (!"$strYear-$strMonth-$strDay".isDate()) {
        tipInfo = "身份证出生日期无效。"
        return tipInfo
    }
    val gc = GregorianCalendar()
    val s = SimpleDateFormat("yyyy-MM-dd")
    try {
        if (gc.get(Calendar.YEAR) - Integer.parseInt(strYear) > 150 || gc.time.time - s.parse("$strYear-$strMonth-$strDay").time < 0) {
            tipInfo = "身份证生日不在有效范围。"
            return tipInfo
        }
    } catch (e: NumberFormatException) {
        e.printStackTrace()
    } catch (e: ParseException) {
        e.printStackTrace()
    }

    if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
        tipInfo = "身份证月份无效"
        return tipInfo
    }
    if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
        tipInfo = "身份证日期无效"
        return tipInfo
    }
    // 判断地区码是否有效
    val areacode = GetAreaCode()
    // 如果身份证前两位的地区码不在Hashtable，则地区码有误
    if (areacode[Ai.substring(0, 2)] == null) {
        tipInfo = "身份证地区编码错误。"
        return tipInfo
    }
    if (!isVerifyCode(Ai, IDStr)) {
        tipInfo = "身份证校验码无效，不是合法的身份证号码"
        return tipInfo
    }
    return tipInfo
}

/*
* 判断第18位校验码是否正确 第18位校验码的计算方式： 1. 对前17位数字本体码加权求和 公式为：S = Sum(Ai * Wi), i =
* 0, ... , 16 其中Ai表示第i个位置上的身份证号码数字值，Wi表示第i位置上的加权因子，其各位对应的值依次为： 7 9 10 5 8 4
* 2 1 6 3 7 9 10 5 8 4 2 2. 用11对计算结果取模 Y = mod(S, 11) 3. 根据模的值得到对应的校验码
* 对应关系为： Y值： 0 1 2 3 4 5 6 7 8 9 10 校验码： 1 0 X 9 8 7 6 5 4 3 2
*/
private fun isVerifyCode(Ai: String, IDStr: String): Boolean {
    var Ai = Ai
    val VarifyCode = arrayOf("1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2")
    val Wi = arrayOf("7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7", "9", "10", "5", "8", "4", "2")
    var sum = 0
    for (i in 0..16) {
        sum += Integer.parseInt(Ai[i].toString()) * Integer.parseInt(Wi[i])
    }
    val modValue = sum % 11
    val strVerifyCode = VarifyCode[modValue]
    Ai += strVerifyCode
    if (IDStr.length == 18) {
        if (!TextUtils.equals(Ai, IDStr))
            return false
    }
    return true
}

/**
 * 将所有地址编码保存在一个Hashtable中

 * @return Hashtable 对象
 */
private fun GetAreaCode(): Hashtable<String, String> {
    val hashtable = Hashtable<String, String>()
    hashtable.put("11", "北京")
    hashtable.put("12", "天津")
    hashtable.put("13", "河北")
    hashtable.put("14", "山西")
    hashtable.put("15", "内蒙古")
    hashtable.put("21", "辽宁")
    hashtable.put("22", "吉林")
    hashtable.put("23", "黑龙江")
    hashtable.put("31", "上海")
    hashtable.put("32", "江苏")
    hashtable.put("33", "浙江")
    hashtable.put("34", "安徽")
    hashtable.put("35", "福建")
    hashtable.put("36", "江西")
    hashtable.put("37", "山东")
    hashtable.put("41", "河南")
    hashtable.put("42", "湖北")
    hashtable.put("43", "湖南")
    hashtable.put("44", "广东")
    hashtable.put("45", "广西")
    hashtable.put("46", "海南")
    hashtable.put("50", "重庆")
    hashtable.put("51", "四川")
    hashtable.put("52", "贵州")
    hashtable.put("53", "云南")
    hashtable.put("54", "西藏")
    hashtable.put("61", "陕西")
    hashtable.put("62", "甘肃")
    hashtable.put("63", "青海")
    hashtable.put("64", "宁夏")
    hashtable.put("65", "新疆")
    hashtable.put("71", "台湾")
    hashtable.put("81", "香港")
    hashtable.put("82", "澳门")
    hashtable.put("91", "国外")
    return hashtable
}


//判断字符串是否为数字,0-9重复0次或者多次
fun String.isNumeric(): Boolean {
    val pattern = Pattern.compile("[0-9]*")
    val isNum = pattern.matcher(this)
    return isNum.matches();
}

//判断字符串出生日期是否符合正则表达式：包括年月日，闰年、平年和每月31天、30天和闰月的28天或者29天
fun String.isDate(): Boolean {
    val pattern = Pattern.compile("^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))?$")
    val m = pattern.matcher(this)
    return m.matches();
}

//手机号格式验证
fun String.isMobileNo(): Boolean {
    var isMobileNo = false
    try {
        val p = Pattern.compile("^((13[0-9])|(15[0-9])|(17[0-9])|(18[0-9]))\\d{8}$")
        val m = p.matcher(this)
        isMobileNo = m.matches()
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return isMobileNo
}

//隐藏手机号中间四位
fun String.hideMobileNo(): String {
    if (!this.isMobileNo()) return this
    var mobileFormat = ""
    try {
        mobileFormat = this.substring(0, 3) + "****" + this.substring(7)
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return mobileFormat
}

//是否是邮件地址
fun String.isEmail(): Boolean {
    if (this.isBlank()) return false
    val regEx1 =
            "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$"
    val p: Pattern
    val m: Matcher
    p = Pattern.compile(regEx1)
    m = p.matcher(this)
    return m.matches()
}