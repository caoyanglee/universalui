package com.weimu.universalview.core.dialog

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import com.afollestad.materialdialogs.MaterialDialog
import com.weimu.universalview.R


object ProgressDialog {

    @SuppressLint("StaticFieldLeak")
    private var materialDialog: MaterialDialog? = null

    fun show(
            context: Context,
            title: String = context.getString(R.string.progressbar_title),
            content: String = context.getString(R.string.progressbar_content),
            cancelable: Boolean = false
    ) {
        try {
            if ((context as Activity).isFinishing) return
        } catch (e: Exception) {
            return
        }
        try {
            if (materialDialog == null) {
                materialDialog = MaterialDialog.Builder(context)
                        .title(title)
                        .content(content)
                        .progress(true, 0)
                        .cancelable(cancelable)
                        .show()

            } else if (!(materialDialog!!.isShowing)) {
                materialDialog?.show()
            } else if (materialDialog!!.isShowing) {
                materialDialog?.setTitle(title)
                materialDialog?.setContent(content)
            }
        } catch (e: Exception) {
            return
        }
    }

    fun hide() {
        try {
            materialDialog?.dismiss()
        } catch (e: Exception) {
            return
        } finally {
            materialDialog = null
        }
    }


}