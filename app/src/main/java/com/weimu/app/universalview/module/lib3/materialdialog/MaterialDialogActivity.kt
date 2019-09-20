package com.weimu.app.universalview.module.lib3.materialdialog

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.weimu.app.universalview.R
import com.weimu.app.universalview.base.BaseViewActivity
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
                    items = *arrayOf("男", "女"),
                    callBack = { dialog, which, text ->
                        toast("位置=$which 文本=$text")
                    }
            )
        }

    }

}
