package com.pmm.demo.module.kotlin.coroutines

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.pmm.demo.R
import com.pmm.demo.base.BaseViewActivity
import com.pmm.ui.core.dialog.ProgressDialog
import com.pmm.ui.ktx.click
import com.pmm.ui.ktx.toast
import kotlinx.android.synthetic.main.activity_coroutine.*
import kotlinx.coroutines.*

/**
 * 协程
 */
class CoroutineActivity : BaseViewActivity() {


    companion object {
        fun newIntent(context: Context): Intent {
            return Intent(context, CoroutineActivity::class.java)
        }
    }

    override fun getLayoutResID() = R.layout.activity_coroutine


    override fun afterViewAttach(savedInstanceState: Bundle?) {
        mToolBar.with(this)
                .centerTitle { this.text = "协程" }
                .navigationIcon { this.click { onBackPressed() } }



        btn_coroutine.setOnClickListener {
            test2()
        }
    }

    private fun test2() {


        GlobalScope.launch {
            repeat(1000) { i ->
                Log.e("weimu", "I'm sleeping $i ...")
                delay(500L)
            }
            delay(1300L)

//            Log.e("weimu", "click the button")
//            val c1 = launch {
//                //非阻塞线程3s
//                delay(3000L)
//                launch(Dispatchers.Main) {
//                    toast("呵呵哒")
//                }
//                Log.e("weimu", "This is a coroutines1")
//            }
//            Log.e("weimu", "加入了")
//            c1.join()
//            Log.e("weimu", "加入了=结束")
//            val c2 = launch {
//                //非阻塞线程4s
//                delay(4000L)
//                Log.e("weimu", "This is a coroutines2")
//            }
//
//
//            // 阻塞线程2s，保证JVM存活，协程可正常执行完
//            Thread.sleep(2000L)
//            Log.e("weimu", "main end")

        }
    }


    private fun test1() {
        ProgressDialog.show(this)
        GlobalScope.launch(Dispatchers.IO) {
            val deferred = async { getToken() }

            withContext(Dispatchers.Main) {
                //update ui
                toast("这是令牌1=" + deferred.await())
            }

//            launch(Dispatchers.Main) {
//                toast("这是令牌2=" + deferred.await())
//            }

        }
    }


    /**
     * 暂停函数 挂起函数 suspend
     */
    private suspend fun getToken(): String {
        delay(3000L)
        GlobalScope.launch(Dispatchers.Main) {
            ProgressDialog.hide()
        }
        return "67243jk4b5jf4g93820-54f5d4d5h435g3"
    }

}
