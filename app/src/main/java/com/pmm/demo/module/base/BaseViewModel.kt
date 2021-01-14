package com.pmm.demo.module.base

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

/**
 * Author:你需要一台永动机
 * Date:2018/10/14 14:25
 * Description:基础的P层实现
 */

abstract class BaseViewModel(application: Application) : AndroidViewModel(application) {

    private val viewModelJob = SupervisorJob()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)

    fun test(){
        uiScope.launch {  }
    }

    /**
     * 销毁后 关闭所有流
     */
    override fun onCleared() {
        viewModelJob.cancel()
    }


}