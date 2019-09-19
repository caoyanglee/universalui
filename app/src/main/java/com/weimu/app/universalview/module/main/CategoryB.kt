package com.weimu.app.universalview.module.main

import com.pmm.ui.core.BaseB

/**
 * Author:你需要一台永动机
 * Date:2018/9/29 17:04
 * Description:主页的类型
 */
class CategoryB : BaseB {
    var primaryTitle = ""
    var subTitle = ""


    constructor() : super()

    constructor(primaryTitle: String, subTitle: String) : super() {
        this.primaryTitle = primaryTitle
        this.subTitle = subTitle
    }


}