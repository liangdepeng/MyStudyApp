package com.dpzz.weatherpart

import android.app.AlertDialog
import android.content.Intent
import android.graphics.drawable.VectorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.dpzz.lib_base.base.BaseActivity
import com.dpzz.lib_base.jump.JumpUtils
import com.dpzz.lib_base.setonMyClickListener
import com.dpzz.lib_base.util.SPUtil
import com.dpzz.lib_base.util.SPUtil.KEY_IS_SHOW_PRIVACY_DIALOG
import com.dpzz.weatherpart.databinding.ActivitySplashBinding
import com.dpzz.weatherpart.databinding.DialogYinsiLayoutBinding
import kotlin.system.exitProcess

// TODO SplashApi
class WelcomeActivity : BaseActivity<ActivitySplashBinding>() {

    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        isAnim = false
        super.onCreate(savedInstanceState)
    }

    override fun initData() {
        WeatherCacheManager.getInstance().init()
//        handler.postDelayed({
//            JumpUtils.into(this, ChooseCityActivity::class.java)
//        }, 1500)
    }

    override fun initView() {
        if (!SPUtil.getBoolean(KEY_IS_SHOW_PRIVACY_DIALOG, false)) {
            getPrivaryDialog().show()
        } else {
            handler.postDelayed({
                val locations = WeatherCacheManager.getInstance().locationList
                if (!locations.isNullOrEmpty()){
                    val bean = locations.get(0)
                    if (!bean.locationId.isNullOrEmpty()){
                        startActivity(Intent(this, WeatherMainActivity::class.java)
                            .putExtra(Constants.KEY_LOCATION_ID,bean.locationId)
                            .putExtra(Constants.KEY_LOCATION_NAME,bean.locationName))
                        finish()
                        return@postDelayed
                    }
                }
                JumpUtils.into(this, ChooseCityActivity::class.java)
                finish()
            }, 1000)
        }
    }

    private fun getPrivaryDialog(): AlertDialog {

        val binding = DialogYinsiLayoutBinding.inflate(layoutInflater)

        binding.cancelTv.setonMyClickListener {
            exitProcess(0)
        }

        binding.okTv.setonMyClickListener {
            SPUtil.put(KEY_IS_SHOW_PRIVACY_DIALOG, true)
            WeatherApp.isCanInitSdk = true
            WeatherApp.initSdk()
            JumpUtils.into(this, ChooseCityActivity::class.java)
            finish()
        }

        return AlertDialog.Builder(this, R.style.standard_dialog_style1)
            .setCancelable(false)
            .setView(binding.root)
            .create()
    }
}