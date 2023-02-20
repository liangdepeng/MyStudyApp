package com.dpzz.mvpart

import android.os.Bundle
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import com.dpzz.lib_base.BaseActivity
import com.dpzz.lib_base.ToastUtil
import com.dpzz.mvpart.databinding.ActivityMvMainBinding
import com.dpzz.mvpart.homepage.DiscussFragment
import com.dpzz.mvpart.homepage.HomeFragment
import com.dpzz.mvpart.homepage.MineFragment
import com.dpzz.mvpart.homepage.TicketFragment

class MvMainActivity : BaseActivity<ActivityMvMainBinding>(), RadioGroup.OnCheckedChangeListener {

    private val homeFragment by lazy { HomeFragment() }
    private val ticketFragment by lazy { TicketFragment() }
    private val discussFragment by lazy { DiscussFragment() }
    private val mineFragment by lazy { MineFragment() }
    private lateinit var lastFragment: Fragment

    //
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ToastUtil.init(applicationContext)
    }

    override fun initData() {
        val supportFragmentManager = supportFragmentManager
        val beginTransaction = supportFragmentManager.beginTransaction()
        beginTransaction.add(R.id.fragment_contain, homeFragment)
        beginTransaction.commitAllowingStateLoss()
        lastFragment = homeFragment
    }

    private fun showPage(fragment: Fragment) {
        if (fragment == lastFragment)
            return
        val supportFragmentManager = supportFragmentManager
        val beginTransaction = supportFragmentManager.beginTransaction()
        beginTransaction.hide(lastFragment)
        if (fragment.isAdded) {
            beginTransaction.show(fragment)
        } else {
            beginTransaction.add(R.id.fragment_contain, fragment)
        }
        beginTransaction.commitAllowingStateLoss()
        lastFragment = fragment
    }

    override fun initView() {
        mViewBinding.homeRg.setOnCheckedChangeListener(this)
    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        when (checkedId) {
            R.id.home_rb -> {
                showPage(homeFragment)
            }
            R.id.ticket_rb -> {
                showPage(ticketFragment)
            }
            R.id.discuss_rb -> {
                showPage(discussFragment)
            }
            R.id.mine_rb -> {
                showPage(mineFragment)
            }
        }
    }
}