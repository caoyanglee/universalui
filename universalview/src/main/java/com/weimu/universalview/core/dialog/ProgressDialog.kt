package com.weimu.universalview.core.dialog

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import com.afollestad.materialdialogs.MaterialDialog
import com.weimu.universalview.R


object ProgressDialog {

    @SuppressLint("StaticFieldLeak")
    var materialDialog: MaterialDialog? = null


    fun show(context: Context, title: String = context.getString(R.string.progressbar_title), content: String = context.getString(R.string.progressbar_content)) {

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
                        .cancelable(false)
                        .show()

            } else if (!(materialDialog!!.isShowing)) {
                materialDialog?.show()
            }
        } catch (e: Exception) {
            return
        }
    }

    fun hide() {
        try {
            materialDialog?.dismiss()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            materialDialog = null
        }
    }


}