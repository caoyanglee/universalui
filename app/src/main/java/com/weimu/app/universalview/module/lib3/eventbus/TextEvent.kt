package com.weimu.app.universalview.module.lib3.eventbus

import com.weimu.universalib.origin.BaseB

/**
 * Author:你需要一台永动机
 * Date:2018/11/9 14:30
 * Description:
 */
class TextEvent : BaseB {
    var message = ""

    constructor(message: String) : super() {
        this.message = message
    }
}