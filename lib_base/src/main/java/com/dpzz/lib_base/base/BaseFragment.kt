package com.dpzz.lib_base.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

abstract class BaseFragment<VB :ViewBinding> : Fragment() {

    protected lateinit var mViewBinding :VB
    protected lateinit var mContext :Context
    protected lateinit var mActivity :Activity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        mActivity = activity
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val superclass = javaClass.genericSuperclass
        if (superclass is ParameterizedType) {
            // 获取当前类的泛型参数类型 0 第一个 即 VB
            val vbClass = superclass.actualTypeArguments[0] as Class<VB>
            // 反射获取 对应的方法调用 获取 viewbinding
            val method = vbClass.getDeclaredMethod("inflate", LayoutInflater::class.java)
            mViewBinding = method.invoke(null, layoutInflater) as VB
            return mViewBinding.root
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initContentView()
    }

    abstract fun initContentView()

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

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onDetach() {
        super.onDetach()
    }
}