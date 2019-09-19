package com.weimu.app.universalview.module.lib3.eventbus

import android.os.Bundle
import com.weimu.app.universalview.R
import com.weimu.app.universalview.base.BaseViewActivity
import com.weimu.universalview.ktx.getContent
import com.weimu.universalview.ktx.setOnClickListenerPro
import com.weimu.universalview.ktx.toast
import kotlinx.android.synthetic.main.activity_event_bus_sec.*
import org.greenrobot.eventbus.EventBus

class EventBusSecActivity : BaseViewActivity() {
    override fun getLayoutResID(): Int = R.layout.activity_event_bus_sec


    override fun afterViewAttach(savedInstanceState: Bundle?) {
        mToolbar.with(this)
                .centerTitle { this.text = "EventBusSecActivity" }
                .navigationIcon { this.setOnClickListenerPro { onBackPressed() } }


        btn_send.setOnClickListener {
            val message = et_send.getContent()
            EventBus.getDefault().post(TextEvent(message))
            toast("发送成功 返回查看")
        }
    }


}
