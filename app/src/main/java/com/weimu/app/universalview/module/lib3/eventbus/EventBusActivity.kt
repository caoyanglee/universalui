package com.weimu.app.universalview.module.lib3.eventbus

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.weimu.app.universalview.R
import com.pmm.ui.ktx.setOnClickListenerPro
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

        mToolbar.with(this)
                .centerTitle { this.text = "EventBusActivity" }
                .navigationIcon { this.setOnClickListenerPro { onBackPressed() } }


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
