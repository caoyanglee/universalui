package com.pmm.ui.helper

import android.content.Context
import android.media.MediaScannerConnection
import android.net.Uri

/**
 * Author:你需要一台永动机
 * Date:2018/4/21 01:37
 * Description:保存bitmap图片到图库时，需要通知系统图库，不然系统图库检测不到
 */
class MediaScanner(context: Context) : MediaScannerConnection.MediaScannerConnectionClient {

    /**
     * 扫描对象
     */
    private var mediaScanConn: MediaScannerConnection? = null

    /**
     * 文件路径集合
     */
    private var filePaths: Array<String>? = null
    /**
     * 文件MimeType集合
     */
    private var mimeTypes: Array<String>? = null

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
    fun scanFiles(filePaths: Array<String>, mimeTypes: Array<String>) {
        this.filePaths = filePaths
        this.mimeTypes = mimeTypes
        mediaScanConn!!.connect()//连接扫描服务
    }

    /**
     * @author YOLANDA
     */
    override fun onMediaScannerConnected() {
        if (filePaths == null) return
        for (i in filePaths!!.indices) {
            mediaScanConn!!.scanFile(filePaths!![i], mimeTypes!![i])//服务回调执行扫描
        }
        filePaths = null
        mimeTypes = null
    }

    /**
     * 扫描一个文件完成
     *
     * @param path
     * @param uri
     * @author YOLANDA
     */
    override fun onScanCompleted(path: String, uri: Uri) {
        scanTimes++
        if (scanTimes == filePaths!!.size) {//如果扫描完了全部文件
            mediaScanConn!!.disconnect()//断开扫描服务
            scanTimes = 0//复位计数
        }
    }
}
