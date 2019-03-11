package com.weimu.app.universalview.module.lib3.materialdialog

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.weimu.app.universalview.R
import com.weimu.app.universalview.base.BaseViewActivity

class MaterialDialogActivity : BaseViewActivity() {
    override fun getLayoutResID() = R.layout.activity_material_dialog

    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, MaterialDialogActivity::class.java)
        }
    }

    override fun afterViewAttach(savedInstanceState: Bundle?) {
    }


    fun onClick(v: View) {
        when (v.id) {
            R.id.btn_dialog_progress -> {
//                ProgressDialog.show(getContext())
//                Observable.timer(1,TimeUnit.SECONDS)
//                        .compose(RxSchedulers.toMain())
//                        .subscribe{
//                            ProgressDialog.hide()
//                        }
            }
        }
    }
}
