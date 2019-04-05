package com.weimu.universalview.ktx

import android.app.Activity
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat
import com.afollestad.materialdialogs.MaterialDialog
import com.tbruyelle.rxpermissions2.RxPermissions
import com.weimu.universalview.R

/**
 *
 * 请求权限
 * Manifest.permission.WRITE_EXTERNAL_STORAGE
 * Manifest.permission.READ_EXTERNAL_STORAGE
 *
 * 消息例子：没有此权限将无法正常使用软件!\n是否要获取此权限？
 *
 */
fun FragmentActivity.requestPermission(vararg permissions: String,
                                       granted: (() -> Unit)? = null,
                                       dialogMessage: String,
                                       dialogPositive: (() -> Boolean)? = null,
                                       dialogNegative: (() -> Unit)? = null
) {
    val d = RxPermissions(this).request(*permissions)
            .subscribe {
                if (it) {
                    granted?.invoke()
                } else {
                    if (dialogMessage.isBlank()) return@subscribe
                    MaterialDialog.Builder(this)
                            .cancelable(false)
                            .backgroundColor(ContextCompat.getColor(this, R.color.white))
                            .title("提示")
                            .content("" + dialogMessage)
                            .positiveText("去开启")
                            .negativeText("知道了")
                            .onPositive { dialog, which ->
                                if (dialogPositive?.invoke() == true) return@onPositive
                                this.requestPermission(
                                        permissions = *permissions,
                                        granted = granted,
                                        dialogMessage = dialogMessage
                                )

                            }
                            .onNegative { dialog, which -> dialogNegative?.invoke() }
                            .show()
                }
            }
}