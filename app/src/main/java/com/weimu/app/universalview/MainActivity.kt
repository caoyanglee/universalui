package com.weimu.app.universalview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.weimu.universalib.ktx.toast
import com.weimu.universalview.ktx.setOnClickListenerPro
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sv_test.isActivated = false
        sv_test.setOnClickListenerPro {
            toast("呵呵哒")
        }
    }
}
