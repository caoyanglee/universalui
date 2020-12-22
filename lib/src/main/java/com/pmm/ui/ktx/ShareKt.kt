package com.pmm.ui.ktx

import android.content.Context
import android.content.Intent
import androidx.annotation.StringDef
import com.pmm.ui.R
import java.io.File

/**
 * Author:你需要一台永动机
 * Date:2020-02-26 17:34
 * Description:专门处理分享API
 */

@StringDef(value = [ShareContentType.TEXT, ShareContentType.IMAGE, ShareContentType.AUDIO, ShareContentType.VIDEO, ShareContentType.FILE])
@Retention(AnnotationRetention.SOURCE)
@MustBeDocumented
annotation class ShareContentType {

    companion object {
        /**
         * Share Text
         */
        const val TEXT: String = "text/plain"

        /**
         * Share Image
         */
        const val IMAGE: String = "image/*"

        /**
         * Share Audio
         */
        const val AUDIO: String = "audio/*"

        /**
         * Share Video
         */
        const val VIDEO: String = "video/*"

        /**
         * Share File
         */
        const val FILE: String = "*/*"
    }
}


/**
 * Original Share 原生分享
 * @param shareFile 分享文件
 * @param shareText 分享文本
 * @param shareContentType 分享内容类型
 */
fun Context.share(
        shareFile: File? = null,
        shareText: String = "",
        @ShareContentType shareContentType: String = ShareContentType.TEXT
) {
    val shareIntent = Intent(Intent.ACTION_SEND)
    shareIntent.type = shareContentType
    if (shareContentType == ShareContentType.TEXT) {
        if (shareText.isBlank()) return
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText)
    } else {
        if (shareFile == null) return
        shareIntent.putExtra(Intent.EXTRA_STREAM, getUri4File(shareFile))
    }
    startActivity(Intent.createChooser(shareIntent, this.getString(R.string.share)))
}