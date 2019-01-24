package com.weimu.universalview.ktx

import android.app.Activity
import android.support.v4.content.ContextCompat
import android.view.ContextThemeWrapper
import com.afollestad.materialdialogs.MaterialDialog
import com.tbruyelle.rxpermissions2.RxPermissions
import com.weimu.universalib.ktx.openAppInfoPage
import com.weimu.universalview.R

/**
 * Author:你需要一台永动机
 * Date:2019/1/24 15:19
 * Description:
 */
//缺失权限的提示
fun Activity.requestPermission(vararg permissions: String,
                               dialogMessage: String = "没有此权限将无法正常使用软件!\n是否要获取此权限？",
                               grantListener: (() -> Unit)? = null,
                               dialogPositiveListener: (() -> Boolean)? = null,
                               dialogNegativeListener: (() -> Unit)? = null
) {

    val d = RxPermissions(this).request(*permissions)
            .subscribe {
                if (it) {
                    grantListener?.invoke()
                } else {
                    MaterialDialog.Builder(this)
                            .cancelable(false)
                            .backgroundColor(ContextCompat.getColor(this, R.color.white))
                            .title("提示")
                            .content("" + dialogMessage)
                            .positiveText("去开启")
                            .negativeText("知道了")
                            .onPositive { dialog, which ->
                                if (dialogPositiveListener?.invoke() == true) return@onPositive
                                this.requestPermission(
                                        permissions = *permissions,
                                        dialogMessage = dialogMessage,
                                        grantListener = grantListener
                                )

                            }
                            .onNegative { dialog, which -> dialogNegativeListener?.invoke() }
                            .show()
                }
            }
}