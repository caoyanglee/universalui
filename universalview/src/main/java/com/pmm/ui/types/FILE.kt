package com.pmm.ui.types

import android.graphics.Color

/**
 * Author:你需要一台永动机
 * Date:2018/6/28 09:22
 * Description:文件分类（长名称，短名称，颜色）
 */
enum class FILE(var longName: String, var shortName: String, var color: Int, var uriType: String) {
    APK("apk", "APK", Color.rgb(60, 64, 69), "application/vnd.android.package-archive"),
    RTF("rtf", "T", Color.rgb(60, 64, 69), "application/msword"),
    CSV("csv", "X", Color.rgb(46, 112, 76), "application/vnd.ms-excel"),
    XLS("xls", "X", Color.rgb(46, 112, 76), "application/vnd.ms-excel"),
    XLSX("xlsx", "X", Color.rgb(46, 112, 76), "application/vnd.ms-excel"),
    PPT("ppt", "PPT", Color.rgb(182, 71, 53), "application/vnd.ms-powerpoint"),
    PPTX("pptx", "PPT", Color.rgb(182, 71, 53), "application/vnd.ms-powerpoint"),
    TXT("txt", "T", Color.rgb(60, 64, 69), "text/plain"),
    DOC("doc", "W", Color.rgb(49, 90, 149), "application/msword"),
    DOCX("docx", "W", Color.rgb(49, 90, 149), "application/msword"),
    PDF("pdf", "PDF", Color.rgb(211, 81, 74), "application/pdf"),
    PNG("png", "PNG", Color.rgb(22, 130, 251), "image/png"),
    JPG("jpg", "JPG", Color.rgb(22, 130, 251), "image/jpg"),
    JPEG("jpeg", "JPEG", Color.rgb(22, 130, 251), "image/*jpeg"),
    UNKNOW("?", "?", Color.rgb(153, 153, 153), "*/*")
}