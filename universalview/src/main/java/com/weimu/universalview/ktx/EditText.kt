package com.weimu.universalview.ktx

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.text.InputType
import android.text.TextUtils
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import com.weimu.universalview.interfaces.MyTextWatcher


//扩展函数&扩展属性

//清除所有的的内容
fun Any.clearAllContent(vararg texts: TextView) {
    for (item in texts) {
        item.text = ""
    }
}

//使光标指向末尾处
fun EditText.setSelectionEnd() {
    this.setSelection(this.text.length)
}


//删除编辑框的内容
fun EditText.clearContent() {
    setText("")
}


//查看是否有空的值
fun EditText.isEmpty(): Boolean {
    val str = text.toString().trimMargin()
    if (TextUtils.isEmpty(str)) {
        return true
    }
    return false
}


//展示密码 InputTypes
fun EditText.showPassword() {
    inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
    setSelectionEnd()
}

//隐藏密码  InputType
fun EditText.hidePassword() {
    inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
    setSelectionEnd()
}

//是否显示密码
fun EditText.changePasswordStatus(isShowPwd: Boolean) {
    if (isShowPwd)
        showPassword()
    else
        hidePassword()
}

//隐藏键盘
fun EditText.hideKeyBoard() {
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    if (inputMethodManager.isActive(this)) {
        clearFocus()
        inputMethodManager.hideSoftInputFromWindow(windowToken, 0) //强制隐藏键盘
    }
}

//显示键盘
fun EditText.showKeyBoard() {
    val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    requestFocus()
    inputMethodManager.showSoftInput(this, 0) //强制显示键盘
}

//监听键盘是否显示
fun EditText.addKeyBoardListener(activity: Activity, fn: (keyBoardHeight: Int) -> Unit) {
    this.viewTreeObserver.addOnGlobalLayoutListener {
        //当键盘弹出隐藏的时候会 调用此方法。
        val r = Rect()
        activity.window.decorView.getWindowVisibleDisplayFrame(r)
        //屏幕高度

        val screenHeight = activity.window.decorView.findViewById<FrameLayout>(android.R.id.content).height
        //此处就是用来获取键盘的高度的， 在键盘没有弹出的时候 此高度为0 键盘弹出的时候为一个正数
        val heightDifference = screenHeight - r.bottom
        //Logger.e("屏幕的高度=$screenHeight 键盘高度=$heightDifference")

        fn.invoke(heightDifference)
    }
}


/**
 * EditText点击键盘右下角按钮的反应
 * @param targetActionId 有很多种类型，例如：EditorInfo.IME_ACTION_SEARCH
 */
fun EditText.addEditorActionListener(targetActionId: Int, callBack: ((keyWork: String) -> Unit)) {
    setSingleLine()//必须设置此属性才会生效
    imeOptions = targetActionId
    setOnEditorActionListener { v, actionId, event ->
        if (actionId == targetActionId) {
            callBack(getContent())
            hideKeyBoard()
        }
        return@setOnEditorActionListener true
    }
}

/**
 *检查是否为空并吐司
 */
fun EditText.checkIsNullAndToast(): Boolean {
    val mobile = this.getContent()
    if (mobile.isBlank()) {
        this.requestFocus()
        toast(this.hint)
        return true
    }
    return false
}

/**
 * 增加清除视图
 */
fun EditText.addClearView(clearView: View? = null, keyWordListener: ((keyWord: String) -> Unit)? = null) {
    //默认隐藏处理
    clearView?.gone()
    this.addTextChangedListener(object : MyTextWatcher {
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val keyword = s.toString()

            if (keyword.isNotBlank()) {
                clearView?.visible()
            } else {
                clearView?.gone()
            }

            keyWordListener?.invoke(keyword)
        }
    })

    clearView?.setOnClickListenerPro {
        this.clearContent()
    }
}

/**
 * 设置文本时 自动将角标显示都尾部
 */
fun EditText.setTextPro(text: CharSequence) {
    this.setText(text)
    this.setSelectionEnd()
}


