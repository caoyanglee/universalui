package com.pmm.demo.base

/**
 * Author:你需要一台永动机
 * Date:2019-09-05 14:15
 * Description:
 */
interface BaseViewInit {
    //强制要实现
    fun initRender() {}

    //非强制实现
    fun initObserver() {}

    //非强制实现 交互
    fun initInteraction() {}
}