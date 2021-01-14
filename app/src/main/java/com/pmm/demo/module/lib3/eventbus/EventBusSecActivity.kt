package com.pmm.demo.module.lib3.eventbus

import android.os.Bundle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.pmm.demo.R
import com.pmm.demo.base.BaseViewActivityV2
import com.pmm.demo.base.initToolBarWithBack
import com.pmm.demo.databinding.ActivityEventBusSecBinding
import com.pmm.ui.ktx.getContent
import com.pmm.ui.ktx.toast
import org.greenrobot.eventbus.EventBus

class EventBusSecActivity : BaseViewActivityV2(R.layout.activity_event_bus_sec) {
    private val mVB by viewBinding(ActivityEventBusSecBinding::bind, R.id.container)


    override fun afterViewAttach(savedInstanceState: Bundle?) {
        initToolBarWithBack("EventBusSecActivity")


        mVB.btnSend.setOnClickListener {
            val message = mVB.etSend.getContent()
            EventBus.getDefault().post(TextEvent(message))
            toast("发送成功 返回查看")
        }
    }


}
