package com.dpzz.lib_base.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {

    lateinit var mViewBinding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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

    override fun onDestroy() {
        super.onDestroy()
    }
}