package com.dpzz.lib_base.base

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.dpzz.lib_base.R
import java.lang.reflect.ParameterizedType

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    lateinit var mViewBinding: VB
    lateinit var mActivity: Activity
    protected var isAnim = true

    override fun onCreate(savedInstanceState: Bundle?) {
        if (isAnim) {
            overridePendingTransition(R.anim.activity_open, R.anim.activity_stay)
        }
        super.onCreate(savedInstanceState)
        mActivity = this
        val superclass = javaClass.genericSuperclass
        if (superclass is ParameterizedType) {
            // 获取当前类的泛型参数类型 0 第一个 即 VB
            val vbClass = superclass.actualTypeArguments[0] as Class<VB>
            // 反射获取 对应的方法调用 获取 viewbinding
            val method = vbClass.getDeclaredMethod("inflate", LayoutInflater::class.java)
            mViewBinding = method.invoke(null, layoutInflater) as VB
            setContentView((mViewBinding as ViewBinding).root)
            initData()
            initView()
        }
    }

    abstract fun initData()

    abstract fun initView()

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun finish() {
        super.finish()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}