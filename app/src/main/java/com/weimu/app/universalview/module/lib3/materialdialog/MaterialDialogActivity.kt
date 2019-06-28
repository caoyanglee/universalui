package com.weimu.app.universalview.module.lib3.materialdialog

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.weimu.app.universalview.R
import com.weimu.app.universalview.base.BaseViewActivity
import com.weimu.universalview.core.dialog.ProgressDialog
import com.weimu.universalview.ktx.*
import kotlinx.android.synthetic.main.activity_material_dialog.*

class MaterialDialogActivity : BaseViewActivity() {
    override fun getLayoutResID() = R.layout.activity_material_dialog

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, MaterialDialogActivity::class.java)
        }
    }

    override fun afterViewAttach(savedInstanceState: Bundle?) {

        //进度条
        mBtn1.setOnClickListenerPro {
            ProgressDialog.show(this, cancelable = true)
        }
        //日期选择呢器
        mBtn2.setOnClickListenerPro {
            showDatePicker { year, month, dayOfMonth ->
                toastSuccess("$year-$month-$dayOfMonth")
            }
        }
        //时间选择器
        mBtn3.setOnClickListenerPro {
            showTimePicker { hourOfDay, minute ->
                toastSuccess("$hourOfDay:$minute")
            }
        }
        //确认选择器
        mBtn4.setOnClickListenerPro {
            showConfirmDialog {
                toastSuccess("确定成功")
            }
        }

        //列表选择器
        mBtn5.setOnClickListenerPro {
            showListPicker(
                    title = "性别",
                    items = *arrayOf("男", "女"),
                    callBack = { dialog, which, text ->
                        toastSuccess("位置=$which 文本=$text")
                    }
            )
        }

    }

}
