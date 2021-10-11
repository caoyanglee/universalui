package com.pmm.ui.helper

import android.content.Context
import android.media.MediaScannerConnection
import android.net.Uri
import android.util.Log
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

/**
 * Author:你需要一台永动机
 * Date:2018/4/21 01:37
 * Description:保存bitmap图片到图库时，需要通知系统图库，不然系统图库检测不到
 * val picturePath = getExternalFilesDir(Environment.DIRECTORY_PICTURES) 检测不到图片
 * val picturePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) 检测得到图片
 */
class MediaScanner(context: Context) : MediaScannerConnection.MediaScannerConnectionClient {

    private val TAG = "MediaScanner"

    /**
     * 扫描对象
     */
    private var mediaScanConn: MediaScannerConnection? = null

    var scanCompletedListener: (() -> Unit)? = null//扫描完成回调

    /**
     * 文件路径集合
     */
    private var filePaths: Array<String> = arrayOf()

    /**
     * 文件MimeType集合
     */
    private var mimeTypes: Array<String> = arrayOf()

    private var scanTimes = 0

    init {
        //实例化
        mediaScanConn = MediaScannerConnection(context, this)
    }

    /**
     * 扫描文件
     *
     * @param filePaths
     * @param mimeTypes  arrayOf(MimeTypeMap.getSingleton().getMimeTypeFromExtension("png"))
     * @author YOLANDA
     */
    fun scanFiles(filePaths: Array<String>, mimeTypes: Array<String>, scanCompletedListener: (() -> Unit)? = null): MediaScanner {
        this.filePaths = filePaths
        this.mimeTypes = mimeTypes
        Log.d(TAG, "连接扫描服务 文件数 = ${this.filePaths.size}")
        mediaScanConn?.connect()//连接扫描服务
        this.scanCompletedListener = scanCompletedListener
        return this
    }

    /**
     * @author YOLANDA
     */
    override fun onMediaScannerConnected() {
        Log.d(TAG, "是否连接 = ${mediaScanConn?.isConnected}")
        for (i in filePaths.indices) {
            Log.d(TAG, "path = ${filePaths[i]}")
            Log.d(TAG, "type = ${mimeTypes[i]}")
            mediaScanConn?.scanFile(filePaths[i], mimeTypes[i])//服务回调执行扫描
        }
    }

    /**
     * 扫描一个文件完成
     *
     * @param path
     * @param uri
     * @author YOLANDA
     */
    override fun onScanCompleted(path: String, uri: Uri?) {
        scanTimes++
        Log.d(
            TAG, """
            扫描成功
            path = $path
            uri = $uri
            scanTimes = $scanTimes,
            filePaths.size = ${filePaths.size}
        """.trimIndent()
        )
        if (scanTimes == filePaths.size) {
            MainScope().launch {
                scanCompletedListener?.invoke()
            }
            mediaScanConn?.disconnect()//断开扫描服务
            scanTimes = 0//复位计数
        }
    }
}
