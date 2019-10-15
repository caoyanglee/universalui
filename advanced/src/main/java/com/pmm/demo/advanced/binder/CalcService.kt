package com.pmm.demo.advanced.binder

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.RemoteException
import android.util.Log
import com.pmm.demo.advanced.ICalcAIDL

/**
 * Author:你需要一台永动机
 * Date:2019-10-15 11:19
 * Description:
 */
class CalcService : Service() {

    private val TAG = "server"

    override fun onCreate() {
        Log.e(TAG, "onCreate")
    }

    override fun onBind(t: Intent): IBinder? {
        Log.e(TAG, "onBind")
        return mBinder
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

    private val mBinder = object : ICalcAIDL.Stub() {

        @Throws(RemoteException::class)
        override fun add(x: Int, y: Int): Int {
            return x + y
        }

        @Throws(RemoteException::class)
        override fun min(x: Int, y: Int): Int {
            return x - y
        }

    }

}