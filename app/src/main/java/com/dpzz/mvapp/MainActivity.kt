package com.dpzz.mvapp


import android.os.Bundle
import android.widget.Toast
import com.dpzz.lib_base.base.BaseActivity
import com.dpzz.mvapp.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initData() {

    }

    override fun initView() {
        mViewBinding.hello.text = "123321"
        Toast.makeText(this, "kaiji", Toast.LENGTH_LONG).show()
    }
}