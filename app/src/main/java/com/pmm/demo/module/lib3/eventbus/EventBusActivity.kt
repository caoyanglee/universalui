package com.pmm.demo.module.lib3.eventbus

import android.content.Intent
import android.os.Bundle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.pmm.demo.R
import com.pmm.demo.base.BaseViewActivityV2
import com.pmm.demo.base.initToolBarWithBack
import com.pmm.demo.databinding.ActivityEventBusBinding
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class EventBusActivity : BaseViewActivityV2(R.layout.activity_event_bus) {
    private val mVB by viewBinding(ActivityEventBusBinding::bind, R.id.container)

    override fun afterViewAttach(savedInstanceState: Bundle?) {
        initRender()
    }

    override fun initRender() {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initToolBarWithBack("EventBusActivity")

        mVB.btnNext.setOnClickListener {
            startActivity(Intent(this, EventBusSecActivity::class.java))
        }

        EventBus.getDefault().register(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onTextReceive(event: TextEvent) {
        mVB.tvMessage.text = "这里是~EventBusSec发送的消息：${event.message}"
    }


}
