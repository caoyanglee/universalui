package com.pmm.ui.ktx

import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.text.TextUtils
import androidx.core.content.FileProvider
import java.io.BufferedReader
import java.io.File
import java.io.IOException
import java.io.InputStreamReader
import java.math.BigDecimal

/**
 * Author:你需要一台永动机
 * Date:2018/12/10 18:34
 * Description:
 */

//获取某文件的存储大小
@Throws(Exception::class)
fun File.getFileSize(): String {
    if (this.isDirectory) throw IllegalArgumentException("argument must a file")
    return getFormatSize(this.length().toDouble())
}

// 获取文件夹大小
//Context.getExternalFilesDir() --> SDCard/Android/data/你的应用的包名/files/ 目录，一般放一些长时间保存的数据
//Context.getExternalCacheDir() --> SDCard/Android/data/你的应用包名/cache/目录，一般存放临时缓存数据
@Throws(Exception::class)
fun File.getFolderSize(): Long {
    if (!this.isDirectory) throw IllegalArgumentException("argument must a folder")
    var size: Long = 0
    try {
        val fileList = this.listFiles()
        for (i in fileList.indices) {
            // 如果下面还有文件
            if (fileList[i].isDirectory) {
                size += fileList[i].getFolderSize()
            } else {
                size += fileList[i].length()
            }
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }

    return size
}


//获取总缓存缓存的大小
@Throws(Exception::class)
fun Context.getTotalCacheSize(): String {
    var cacheSize = this.cacheDir.getFolderSize()
    if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
        cacheSize += this.externalCacheDir?.getFolderSize()?:0
    }
    return getFormatSize(cacheSize.toDouble())
}


//格式化单位 k kb mb gb tb
private fun getFormatSize(size: Double): String {
    val kiloByte = size / 1024
    if (kiloByte < 1) {
        //            return size + "Byte";
        return "0K"
    }

    val megaByte = kiloByte / 1024
    if (megaByte < 1) {
        val result1 = BigDecimal(java.lang.Double.toString(kiloByte))
        return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                .toPlainString() + "KB"
    }

    val gigaByte = megaByte / 1024
    if (gigaByte < 1) {
        val result2 = BigDecimal(java.lang.Double.toString(megaByte))
        return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                .toPlainString() + "MB"
    }

    val teraBytes = gigaByte / 1024
    if (teraBytes < 1) {
        val result3 = BigDecimal(java.lang.Double.toString(gigaByte))
        return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                .toPlainString() + "GB"
    }
    val result4 = BigDecimal(teraBytes)
    return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB"
}


// 删除所有缓存
fun Context.clearAllCache() {
    val that = this
    //内置存储 缓存
    this.cacheDir.deleteDir()
    //位置存储 缓存
    if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
        this.externalCacheDir?.deleteDir()
    }
}

//删除某文件夹
fun File.deleteDir(): Boolean {
    val dir = this
    if (dir.isDirectory) {
        val children = dir.list()
        for (i in children.indices) {
            val success = File(dir, children[i]).deleteDir()
            if (!success) {
                return false
            }
        }
    }
    return dir.delete()
}


/**
 * 从asset路径下读取对应文件转String输出
 */
fun Context.getAssetsString(fileName: String): String {
    val sb = StringBuilder()
    val am = this.assets
    try {
        val br = BufferedReader(InputStreamReader(
                am.open(fileName)))
        var next = ""

        while (true) {
            next = br.readLine() ?: ""
            if (next.isBlank()) break
            sb.append(next)
        }
    } catch (e: IOException) {
        e.printStackTrace()
        sb.delete(0, sb.length)
    }

    return sb.toString().trim { it <= ' ' }
}

/**
 * 获取文件的Uri
 * 兼容7.0
 */
fun Context.getUri4File(file: File?): Uri {
    //获取当前app的包名
    val FPAuth = "$packageName.fileprovider"

    if (file == null) throw NullPointerException()

    val uri: Uri
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        uri = FileProvider.getUriForFile(this.applicationContext, FPAuth, file)
    } else {
        uri = Uri.fromFile(file)
    }
    return uri
}

/**
 * 是否存在文件
 */
fun File.isFileExist(): Boolean {
    if (TextUtils.isEmpty(this.path)) return false
    return this.exists() && this.isFile
}

/**
 * 是否存在文件夹
 */
fun File.isDirectoryExist(): Boolean {
    if (TextUtils.isEmpty(this.path)) return false
    return this.exists() && this.isDirectory
}
