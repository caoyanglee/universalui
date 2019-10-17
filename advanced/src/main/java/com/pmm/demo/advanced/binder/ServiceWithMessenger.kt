package com.pmm.demo.advanced.binder

import android.app.Service
import android.content.Intent
import android.os.*
import android.util.Log
import android.widget.Toast

/**
 * Author:你需要一台永动机
 * Date:2019-10-15 11:19
 * Description: 使用Messenger的Service
 */
class ServiceWithMessenger : Service() {

    private val TAG = "ServiceWithMessenger"

    private val serviceHandler = Handler {
        val obtain = Message.obtain(it)
        try {
            obtain.replyTo.send(obtain)
        } catch (e: RemoteException) {
            e.printStackTrace()
        }
        Toast.makeText(applicationContext, "接收消息：" + it.what, Toast.LENGTH_LONG).show()
        false
    }

    private val messager = Messenger(serviceHandler)//生成Binder

    override fun onBind(t: Intent): IBinder? {
        Log.e(TAG, "onBind")
        return messager.getBinder();
    }

    override fun onCreate() {
        Log.e(TAG, "onCreate")
    }


    override fun onDestroy() {
        Log.e(TAG, "onDestroy")
        super.onDestroy()
    }

    override fun onUnbind(intent: Intent): Boolean {
        Log.e(TAG, "onUnbind")
        return super.onUnbind(intent)
    }

    override fun onRebind(intent: Intent) {
        Log.e(TAG, "onRebind")
        super.onRebind(intent)
    }
}