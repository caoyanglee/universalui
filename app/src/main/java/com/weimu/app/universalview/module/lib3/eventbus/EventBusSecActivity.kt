package com.weimu.app.universalview.module.lib3.eventbus

import android.os.Bundle
import com.weimu.app.universalview.R
import com.weimu.app.universalview.base.BaseViewActivity
import com.weimu.universalview.core.toolbar.ToolBarManager
import com.weimu.universalview.ktx.getContent
import kotlinx.android.synthetic.main.activity_event_bus_sec.*
import org.greenrobot.eventbus.EventBus

class EventBusSecActivity : BaseViewActivity() {
    override fun getLayoutResID(): Int = R.layout.activity_event_bus_sec


    override fun afterViewAttach(savedInstanceState: Bundle?) {
        ToolBarManager.with(this, window.decorView.findViewById(android.R.id.content))
                .setTitle("EventBusSecActivity")
                .setNavigationIcon(R.drawable.universal_arrow_back_white)

        btn_send.setOnClickListener {
            val message = et_send.getContent()
            EventBus.getDefault().post(TextEvent(message))
            toastSuccess("发送成功 返回查看")
        }
    }


}
