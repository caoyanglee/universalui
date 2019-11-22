package com.pmm.demo.module.lib3.materialdialog

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.pmm.demo.R
import com.pmm.demo.base.BaseViewActivity
import com.pmm.ui.core.dialog.ProgressDialog
import com.pmm.ui.ktx.*
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
        mBtn1.click {
            ProgressDialog.show(this, cancelable = true)
        }
        //日期选择呢器
        mBtn2.click {
            showDatePicker { year, month, dayOfMonth ->
                toast("$year-$month-$dayOfMonth")
            }
        }
        //时间选择器
        mBtn3.click {
            showTimePicker { hourOfDay, minute ->
                toast("$hourOfDay:$minute")
            }
        }
        //确认选择器
        mBtn4.click {
            showConfirmDialog {
                toast("确定成功")
            }
        }

        //列表选择器
        mBtn5.click {
            showListPicker(
                    title = "性别",
                    items = listOf("男", "女"),
                    callBack = { dialog, which, text ->
                        toast("位置=$which 文本=$text")
                    }
            )
        }
        //输入选择器
        mBtn6.click {
            showInputPicker(
                    title = "昵称",
                    hint = "请输入昵称",
                    prefill = "永动机",
                    callBack = { _, charSequence ->
                        toast("输入=${charSequence}")
                    }
            )
        }

    }

}
