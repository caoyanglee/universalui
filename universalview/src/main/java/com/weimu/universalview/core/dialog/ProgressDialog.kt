package com.weimu.universalview.core.dialog

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Color
import android.support.v7.widget.LinearLayoutCompat
import android.view.Gravity
import android.widget.ProgressBar
import android.widget.TextView
import com.afollestad.materialdialogs.MaterialDialog
import com.weimu.universalview.R
import com.weimu.universalview.helper.RxSchedulers
import com.weimu.universalview.ktx.dip2px
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.util.concurrent.TimeUnit


@SuppressLint("StaticFieldLeak")
object ProgressDialog {

    private var materialDialog: AlertDialog? = null
    private var animDisposable: Disposable? = null
    private var textView: TextView? = null

    fun show(
            context: Context,
            title: String = "提示",
            message: String = "请求中",
            cancelable: Boolean = false
    ) {
        try {
            if ((context as Activity).isFinishing) return
        } catch (e: Exception) {
            return
        }
        try {
            //处理dialog
            when {
                materialDialog == null -> {
                    materialDialog = AlertDialog.Builder(context).apply {
                        this.setTitle(title)
                        this.setView(LinearLayoutCompat(context).apply {
                            val padding = context.dip2px(24f)
                            this.setPadding(padding, padding, padding, padding)
                            this.gravity = Gravity.CENTER_VERTICAL
                            this.addView(ProgressBar(context).apply {
                                layoutParams = LinearLayoutCompat.LayoutParams(
                                        LinearLayoutCompat.LayoutParams.WRAP_CONTENT,
                                        LinearLayoutCompat.LayoutParams.WRAP_CONTENT)
                            })
                            textView = TextView(context).apply {
                                this.setTextColor(Color.GRAY)
                                this.text = message

                                this.textSize = 16f
                                this.setPadding(context.dip2px(16f), 0, 0, 0)
                            }

                            this.addView(textView)
                        })
                        this.setCancelable(cancelable)
                        this.setOnCancelListener {
                            animDisposable?.dispose()
                            animDisposable = null
                        }
                    }.show()
                }
                materialDialog != null && !(materialDialog!!.isShowing) -> {
                    materialDialog?.show()
                }
                materialDialog != null && materialDialog!!.isShowing -> {
                    materialDialog?.setTitle(title)
                    materialDialog?.setMessage(message)
                }
            }
            //anim
            Observable.interval(300, TimeUnit.MILLISECONDS)
                    .compose(RxSchedulers.toMain())
                    .subscribe(object : Observer<Long> {
                        override fun onComplete() {
                            textView?.text = message
                        }

                        override fun onSubscribe(d: Disposable) {
                            animDisposable = d
                        }

                        override fun onNext(t: Long) {
                            if (textView?.text!!.contains("......")) {
                                textView?.text = message
                            } else {
                                textView?.text = "${textView?.text}."
                            }
                        }

                        override fun onError(e: Throwable) {
                            //error
                        }

                    })
        } catch (e: Exception) {
            //nothing
        }
    }

    fun hide() {
        try {
            materialDialog?.dismiss()
            animDisposable?.dispose()
        } catch (e: Exception) {
            return
        } finally {
            materialDialog = null
            animDisposable = null
        }
    }


}