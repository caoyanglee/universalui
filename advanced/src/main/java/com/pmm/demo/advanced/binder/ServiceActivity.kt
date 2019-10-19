package com.pmm.demo.advanced.binder

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import android.widget.Toast
import com.pmm.demo.advanced.ICalcAIDL
import com.pmm.demo.advanced.R
import com.pmm.demo.base.BaseViewActivity
import com.pmm.demo.base.initToolBar
import com.pmm.metro.Station

@Station("/android/advanced/binder")
class ServiceActivity : BaseViewActivity() {

    private val TAG = "client"

    override fun getLayoutResID(): Int = R.layout.activity_service

    override fun afterViewAttach(savedInstanceState: Bundle?) {
        initToolBar()
    }

    private var binderInterface: ServiceWithBinder.InnerBinder? = null

    private val mServiceConnBinder = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName) {
            Log.e(TAG, "onServiceDisconnected Binder")
            binderInterface = null
        }

        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            Log.e(TAG, "onServiceConnected Binder")
            binderInterface = service as ServiceWithBinder.InnerBinder
            //调用服务的方法
            binderInterface?.multiply(3, 2)
        }
    }

    private var mCalcAidl: ICalcAIDL? = null

    private val mServiceConnAIDL = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName) {
            Log.e(TAG, "onServiceDisconnected AIDL")
            mCalcAidl = null
        }

        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            Log.e(TAG, "onServiceConnected AIDL")
            mCalcAidl = ICalcAIDL.Stub.asInterface(service)
            //调用服务的方法
            mCalcAidl?.add(3, 2)
        }
    }

    /**
     * 点击BindService按钮时调用
     * @param view
     */
    fun bindService(view: View) {
        val intentBinder = Intent(this, ServiceWithBinder::class.java)
        bindService(intentBinder, mServiceConnBinder, Context.BIND_AUTO_CREATE)

        val intentAIDL = Intent(this, ServiceWithAIDL::class.java)
        bindService(intentAIDL, mServiceConnAIDL, Context.BIND_AUTO_CREATE)
    }

    /**
     * 点击unBindService按钮时调用
     * @param view
     */
    fun unbindService(view: View) {
        unbindService(mServiceConnAIDL)

        unbindService(mServiceConnBinder)
    }

    /**
     * 点击12+12按钮时调用
     * @param view
     */
    @Throws(Exception::class)
    fun addInvoked(view: View) {

        if (mCalcAidl != null) {
            val addRes = mCalcAidl?.add(12, 12)
            Toast.makeText(this, addRes.toString() + "", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "服务器被异常杀死，请重新绑定服务端", Toast.LENGTH_SHORT)
                    .show()

        }

    }

    /**
     * 点击50-12按钮时调用
     * @param view
     */
    @Throws(Exception::class)
    fun minInvoked(view: View) {

        if (mCalcAidl != null) {
            val addRes = mCalcAidl?.min(58, 12)
            Toast.makeText(this, addRes.toString() + "", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "服务端未绑定或被异常杀死，请重新绑定服务端", Toast.LENGTH_SHORT)
                    .show()

        }

    }

}
