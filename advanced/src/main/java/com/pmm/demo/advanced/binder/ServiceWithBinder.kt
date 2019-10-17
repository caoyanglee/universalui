package com.pmm.demo.advanced.binder

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.os.RemoteException
import android.util.Log
import com.pmm.demo.advanced.ICalcAIDL

/**
 * Author:你需要一台永动机
 * Date:2019-10-17 11:30
 * Description: 使用Binder的Service
 */
class ServiceWithBinder : Service() {

    private val TAG = "ServiceWithBinder"

    class InnerBinder : Binder() {

        @Throws(RemoteException::class)
        fun multiply(x: Int, y: Int): Int {
            return x * y
        }

        @Throws(RemoteException::class)
        fun divide(x: Int, y: Int): Int {
            return x / y
        }

    }

    override fun onBind(t: Intent): IBinder? {
        Log.e(TAG, "onBind")
        return InnerBinder()
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