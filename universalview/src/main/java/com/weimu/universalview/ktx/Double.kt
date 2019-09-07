package com.weimu.universalview.ktx

import java.text.DecimalFormat

/**
 * Author:你需要一台永动机
 * Date:2019-06-20 15:12
 * Description: 对浮点数进行格式化　　
 *
 * 占位符格式为： %[index$][标识]*[最小宽度][.精度]转换符
 *
 * 可用标识：
 *
 * -，在最小宽度内左对齐,不可以与0标识一起使用。
 * 0，若内容长度不足最小宽度，则在左边用0来填充。
 * #，对8进制和16进制，8进制前添加一个0,16进制前添加0x。
 * +，结果总包含一个+或-号。
 * 空格，正数前加空格，负数前加-号。
 * ,，只用与十进制，每3位数字间用,分隔。
 * (，若结果为负数，则用括号括住，且不显示符号。
 *
 * 可用转换符：
 *
 * b，布尔类型，只要实参为非false的布尔类型，均格式化为字符串true，否则为字符串false。
 * n，平台独立的换行符, 也可通过System.getProperty("line.separator")获取。
 * f，浮点数型（十进制）。显示9位有效数字，且会进行四舍五入。如99.99。
 * a，浮点数型（十六进制）。
 * e，指数类型。如9.38e+5。
 * g，浮点数型（比%f，%a长度短些，显示6位有效数字，且会进行四舍五入）
 */

fun Double?.toString(decimalsNum: Int): String {
    if (decimalsNum < 0) return "$this"
    return String.format("%.${decimalsNum}f", this)
}

/**
 * 增加逗号
 */
fun Double?.toCommaString(decimalsNum: Int = 0): String {
    val format = StringBuilder("#,###")
    if (decimalsNum > 0) {
        format.append(".")
        repeat(decimalsNum) {
            format.append("0")
        }
    }
    val dec = DecimalFormat(format.toString())
    return dec.format(this)
}


