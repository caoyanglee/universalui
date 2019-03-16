package com.weimu.app.universalview.module.lib3.eventbus

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.weimu.app.universalview.R
import com.weimu.universalview.core.toolbar.ToolBarManager
import kotlinx.android.synthetic.main.activity_event_bus.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class EventBusActivity : AppCompatActivity() {


    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, EventBusActivity::class.java)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_bus)

        ToolBarManager.with(this, window.decorView.findViewById(android.R.id.content))
                .setTitle("EventBusActivity")
                .setNavigationIcon(R.drawable.universal_arrow_back_white)


        btn_next.setOnClickListener {
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
        tv_message.text = "这里是~EventBusSec发送的消息：${event.message}"
    }


}