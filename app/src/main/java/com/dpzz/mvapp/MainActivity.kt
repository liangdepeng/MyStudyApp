package com.dpzz.mvapp


import android.content.Intent
import android.os.Bundle
import com.dpjh.miji.jihuang.JiHMainActivity
import com.dpzz.lib_base.base.BaseActivity
import com.dpzz.lib_base.setonMyClickListener
import com.dpzz.mvapp.databinding.ActivityMainBinding
import com.dpzz.mvpart.MvLauncherActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initData() {

    }

    override fun initView() {
        mViewBinding.mvBtn.setonMyClickListener {
            startActivity(Intent(this,MvLauncherActivity::class.java))
        }
        mViewBinding.jhBtn.setonMyClickListener {
            startActivity(Intent(this,JiHMainActivity::class.java))
        }
    }

    override fun onBackPressed() {
        moveTaskToBack(false)
    }
}