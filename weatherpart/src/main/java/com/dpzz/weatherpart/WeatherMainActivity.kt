package com.dpzz.weatherpart

import android.content.Intent
import android.text.TextUtils
import android.view.View
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager.widget.ViewPager
import com.dpzz.lib_base.base.BaseActivity
import com.dpzz.lib_base.imageload.ImageLoader
import com.dpzz.lib_base.setonMyClickListener
import com.dpzz.lib_base.util.PxUtils
import com.dpzz.lib_base.util.SPUtil
import com.dpzz.lib_base.util.ToastUtil
import com.dpzz.weatherpart.adapter.MyPageAdapter
import com.dpzz.weatherpart.adapter.NowAdapter
import com.dpzz.weatherpart.databinding.ActivityWeatherMainBinding
import com.qweather.sdk.bean.weather.WeatherNowBean
import com.qweather.sdk.view.QWeather

class WeatherMainActivity : BaseActivity<ActivityWeatherMainBinding>() {

    private var currentLocationId: String? = ""
    private var locationName: String? = ""
    private var mPagerAdapter: MyPageAdapter? = null
    private val pageMap = hashMapOf<String, Fragment>()
    private var nowWeatherMap = hashMapOf<String, WeatherNowBean>()
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    // 0 简单模式  1 详细模式
    private var showMode = 1
    override fun initData() {
        showMode = intent?.getIntExtra(Constants.KEY_PAGE_MODE, 1) ?: 1
        currentLocationId = intent?.getStringExtra(Constants.KEY_LOCATION_ID)
        locationName = intent?.getStringExtra(Constants.KEY_LOCATION_NAME)
        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult(), object :
                ActivityResultCallback<ActivityResult> {
                override fun onActivityResult(result: ActivityResult?) {
                    if (result == null)
                        return
                    if (result.resultCode == RESULT_OK) {
                        val locationId = result.data?.getStringExtra(Constants.KEY_LOCATION_ID)
                        val locationName = result.data?.getStringExtra(Constants.KEY_LOCATION_NAME)
                        if (locationId.isNullOrEmpty())
                            return
                        currentLocationId = locationId
                        if (pageMap.get(locationId) == null) {
                            requestWeatherDetail(locationId, locationName)
                            mViewBinding.titleTv.text = locationName

                            pageMap.clear()
                            val locations = WeatherCacheManager.getInstance().locationList
                            val fragments = arrayListOf<Fragment>()

                            for (bean in locations) {
                                if (!bean.locationId.isNullOrEmpty()) {
                                    val fragment = WeatherFragment.newInstance(bean.locationId)
                                    if (TextUtils.equals(locationId, bean.locationId)) {
                                        fragments.add(0, fragment)
                                    } else {
                                        fragments.add(fragment)
                                    }
                                    pageMap.put(bean.locationId, fragment)
                                }
                            }
                            mPagerAdapter = MyPageAdapter(supportFragmentManager, fragments)
                            mViewBinding.viewpager.adapter = mPagerAdapter
                            currentLocationId = locationId
                            mViewBinding.topCard.indicatorLl.initIndicator(fragments.size,0)
                        } else {
                            val fragment = pageMap.get(locationId)
                            val index = mPagerAdapter?.fragments?.indexOf(fragment)
                            if (index != null){
                                mViewBinding.viewpager.setCurrentItem(index)
                                mViewBinding.topCard.indicatorLl.setCurrentPosition(index)
                            }

                        }
                    }
                }
            })
    }

    override fun initView() {
        mViewBinding.titleTv.text = locationName
        mViewBinding.chooseTvv.setonMyClickListener {
            val intent = Intent(this, ChooseCityActivity::class.java)
            intent.putExtra(Constants.KEY_PAGE_FROM, Constants.KEY_PAGE_WEATHER_MAIN)
            resultLauncher.launch(intent)
        }
        mViewBinding.settingTvv.setonMyClickListener {
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }

        mViewBinding.topCard.card.switchUIMode(showMode)

        val locations = WeatherCacheManager.getInstance().locationList
        val fragments = arrayListOf<Fragment>()
        for (bean in locations) {
            if (!bean.locationId.isNullOrEmpty()) {
                val fragment = WeatherFragment.newInstance(bean.locationId)
                if (TextUtils.equals(currentLocationId, bean.locationId)) {
                    fragments.add(0, fragment)
                } else {
                    fragments.add(fragment)
                }
                pageMap.put(bean.locationId, fragment)
            }
        }

        mViewBinding.viewpager.offscreenPageLimit = fragments.size
        mPagerAdapter = MyPageAdapter(supportFragmentManager, fragments)
        mViewBinding.viewpager.adapter = mPagerAdapter
        mViewBinding.viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                mViewBinding.topCard.indicatorLl.setCurrentPosition(position)

                val fragment = mPagerAdapter?.getItem(position)
                pageMap.forEach {
                    if (it.value == fragment) {
                        // title
                        mViewBinding.titleTv.text =
                            WeatherCacheManager.getInstance().getLocationName(it.key)
                        // 顶部天气
                        val nowBean = nowWeatherMap[it.key]

                      mViewBinding.topCard.card.updateTopWeather(nowBean)
                        return@forEach
                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

        mViewBinding.topCard.indicatorLl.initIndicator(fragments.size,0)

        requestWeatherDetail(currentLocationId, locationName)
        WeatherCacheManager.getInstance().locationList.forEach {
            if (!TextUtils.equals(currentLocationId, it.locationId)) {
                requestWeatherDetail(it.locationId, it.locationName)
            }
        }
    }



    override fun onBackPressed() {
        if (isTaskRoot) {
            moveTaskToBack(false)
        } else {
            super.onBackPressed()
        }
    }


    private fun requestWeatherDetail(locationId: String?, locationName: String?) {
        QWeather.getWeatherNow(this, locationId, object : QWeather.OnResultWeatherNowListener {
            override fun onError(e: Throwable?) {
                if (locationId == currentLocationId)
                    ToastUtil.show2(e?.message)
            }

            override fun onSuccess(bean: WeatherNowBean?) {
                runOnUiThread {
                    if (locationId != null && bean != null) {
                        WeatherCacheManager.getInstance().updateWeatherNow(locationId, bean)
                        nowWeatherMap.put(locationId, bean)
                    }
                    if (currentLocationId == locationId) {
                        mViewBinding.topCard.card.updateTopWeather(bean)
                    }
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        val mode = SPUtil.getInt(Constants.KEY_PAGE_MODE,1)
        if (mode!=showMode){
            showMode = mode
            mViewBinding.topCard.card.switchUIMode(showMode)
        }
    }
}