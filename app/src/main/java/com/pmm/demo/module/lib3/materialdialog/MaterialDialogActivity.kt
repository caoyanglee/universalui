package com.pmm.demo.module.lib3.materialdialog

import android.os.Bundle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.pmm.demo.R
import com.pmm.demo.base.BaseViewActivityV2
import com.pmm.demo.databinding.ActivityMaterialDialogBinding
import com.pmm.ui.core.dialog.ProgressDialog
import com.pmm.ui.ktx.*

class MaterialDialogActivity : BaseViewActivityV2(R.layout.activity_material_dialog) {
    private val mVB by viewBinding(ActivityMaterialDialogBinding::bind, R.id.container)

    override fun afterViewAttach(savedInstanceState: Bundle?) {

        //进度条
        mVB.mBtn1.click {
            ProgressDialog.show(this, cancelable = true)
        }
        //日期选择呢器
        mVB.mBtn2.click {
//            showDatePicker { year, month, dayOfMonth, datetime ->
//                toast("$year-$month-$dayOfMonth")
//            }
        }
        //时间选择器
        mVB.mBtn3.click {
//            showTimePicker { hourOfDay, minute, datetime ->
//                toast("$hourOfDay:$minute")
//            }
        }
        //确认选择器
        mVB.mBtn4.click {
            showConfirmDialog {
                toast("确定成功")
            }
        }

        //列表选择器
        mVB.mBtn5.click {
            showListPicker(
                    title = "操作",
                    items = listOf("删除", "还原"),
                    callBack = { dialog, which, text ->
                        toast("位置=$which 文本=$text")
                    }
            )
        }
        //输入选择器
        mVB.mBtn6.click {
//            showInputPicker(
//                    title = "昵称",
//                    hint = "请输入昵称",
//                    prefill = "永动机",
//                    callBack = { _, charSequence ->
//                        toast("输入=${charSequence}")
//                    }
//            )
        }
        //单选选择器
        mVB.mBtn7.click {
            showSingleChoicePicker(
                    title = "性别",
                    items = listOf("男", "女"),
                    callBack = { dialog, which, text ->
                        toast("位置=$which 文本=$text")
                    }
            )
        }

    }

}
