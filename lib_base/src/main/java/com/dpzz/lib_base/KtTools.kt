package com.dpzz.lib_base

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import com.google.gson.Gson
import com.hjq.toast.ToastUtils
import org.json.JSONObject

/**
 * Kotlin 扩展函数
 */

// 添加一个临时变量保存点击时间
var <T : View> T.lastClickMills: Long
    get() {
        val tag = getTag(R.id.last_time_key)
        return if (tag == null) 0L else (tag as? Long) ?: 0L
    }
    set(value) {
        setTag(R.id.last_time_key, value)
    }

// 防止频繁点击
fun <T : View> T.setonMyClickListener(delayMills: Long = 500L, block: (T) -> Unit) {
    setOnClickListener {
        if (System.currentTimeMillis() - lastClickMills > delayMills) {
            block.invoke(this)
            lastClickMills = System.currentTimeMillis()
        }
    }
}

// 安全弹出 Toast
fun Any?.simpleToast() {
    if (this == null) return
    ToastUtils.show(this.toString())
}

// 处理接口返回的数据 外部拦截异常
// 通用结构：
// {
//  "Code": 0,
//  "Message": "message",
//  "Data": {}
// }
fun dealResponse(
    jsonObject: JSONObject?,
    onSuccess: (jsonObject: JSONObject, message: String) -> Unit,
    onError: () -> Unit = {
        if (!isSilentRequest)
            ToastUtils.show("请求失败")
    },
    isSilentRequest: Boolean = false
) {
    if (jsonObject != null) {
        tryCatch({
            val code = jsonObject.optInt("Code")
            val message = jsonObject.optString("Message")
            if (code == 0) {
                onSuccess.invoke(jsonObject, message)
            } else {
                if (!isSilentRequest)
                    ToastUtils.show(message)
            }
        }) {
            if (!isSilentRequest)
                ToastUtils.show("请求数据异常")
        }
    } else {
        onError.invoke()
    }
}

// 简写 监听EditText文字内容改变
fun <T : EditText> T.addContentChangedListener(afterTextChanged: (s: CharSequence) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(s: Editable?) {
            afterTextChanged.invoke(s?.toString() ?: "")
        }
    })
}

// 异常捕获
fun tryCatch(action: () -> Unit, onError: (e: Exception) -> Unit = {}) {
    try {
        action.invoke()
    } catch (e: Exception) {
        onError.invoke(e)
        e.printStackTrace()
    }
}

fun <T> String?.toJavaBean(javaClass: Class<T>): T? {
    if (this == null)
        return null
    return Gson().fromJson<T>(this, javaClass)
}

fun <T> ArrayList<T>?.addAllSafely(data: List<T>?) {
    if (this == null)
        return
    if (!data.isNullOrEmpty()) {
        this.addAll(data)
    }
}
