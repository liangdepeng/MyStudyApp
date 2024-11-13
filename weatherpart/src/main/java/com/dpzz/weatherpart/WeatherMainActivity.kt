package com.dpzz.weatherpart

import android.content.Intent
import android.text.TextUtils
import android.util.Log
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
import com.dpzz.weatherpart.database.DataBaseDao
import com.dpzz.weatherpart.databinding.ActivityWeatherMainBinding
import com.google.gson.Gson
import com.qweather.sdk.bean.weather.WeatherNowBean
import com.qweather.sdk.view.QWeather

class WeatherMainActivity : BaseActivity<ActivityWeatherMainBinding>() {

    private var currentLocationId: String? = ""
    private var locationName: String? = ""
    private val nowDetailAdapter by lazy { NowAdapter(this) }
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
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult(), object :
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
                            mViewBinding.indicatorLl.initIndicator(fragments.size,0)
                        } else {
                            val fragment = pageMap.get(locationId)
                            val index = mPagerAdapter?.fragments?.indexOf(fragment)
                            if (index != null){
                                mViewBinding.viewpager.setCurrentItem(index)
                                mViewBinding.indicatorLl.setCurrentPosition(index)
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
        mViewBinding.nowRv.layoutManager = LinearLayoutManager(this)
        mViewBinding.nowRv.adapter = nowDetailAdapter

        switchUIMode()

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
//        fragments.add(WeatherFragment.newInstance(1))
//        fragments.add(WeatherFragment.newInstance(2))
//        fragments.add(WeatherFragment.newInstance(3))
        mViewBinding.viewpager.offscreenPageLimit = fragments.size
        mPagerAdapter = MyPageAdapter(supportFragmentManager, fragments)
        mViewBinding.viewpager.adapter = mPagerAdapter
        mViewBinding.viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {

                mViewBinding.indicatorLl.setCurrentPosition(position)

                val fragment = mPagerAdapter?.getItem(position)
                pageMap.forEach {
                    if (it.value == fragment) {
                        // title
                        mViewBinding.titleTv.text =
                            WeatherCacheManager.getInstance().getLocationName(it.key)
                        // 顶部天气
                        val nowBean = nowWeatherMap[it.key]

                        updateTopWeather(nowBean)
                        return@forEach
                    }
                }
            }

            override fun onPageScrollStateChanged(state: Int) {

            }
        })

        mViewBinding.indicatorLl.initIndicator(fragments.size,0)

        requestWeatherDetail(currentLocationId, locationName)
        WeatherCacheManager.getInstance().locationList.forEach {
            if (!TextUtils.equals(currentLocationId, it.locationId)) {
                requestWeatherDetail(it.locationId, it.locationName)
            }
        }
    }

    private fun updateTopWeather(nowBean: WeatherNowBean?) {
        val dp66 = PxUtils.dip2px(this@WeatherMainActivity, 66f)
        nowDetailAdapter.setData(getDataList(nowBean))
        ImageLoader.getInstance().loadAssetsSvgImage(mViewBinding.nowIv,"icons/${nowBean?.now?.icon}.svg",dp66,dp66)

        mViewBinding.tvTemp.text = "${nowBean?.now?.temp}℃"
        mViewBinding.tvTempDesc.text = "${nowBean?.now?.text} 最高0℃ 最低0℃"
        mViewBinding.tvWindyDesc.text = "${nowBean?.now?.windDir} ${nowBean?.now?.windScale}级"
        ImageLoader.getInstance().loadAssetsSvgImage(mViewBinding.ivTempp, "icons/${nowBean?.now?.icon}.svg", dp66, dp66)
    }

    private fun switchUIMode(){
        if (showMode == 1){
            mViewBinding.simpleTempCl.visibility = View.GONE
            mViewBinding.nowRv.visibility = View.VISIBLE
            mViewBinding.nowIv.visibility = View.VISIBLE

        }else{
            mViewBinding.simpleTempCl.visibility = View.VISIBLE
            mViewBinding.nowRv.visibility = View.GONE
            mViewBinding.nowIv.visibility = View.GONE
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

            /**
             * getObsTime	实况观测时间	2013-12-30T13:14+08:00
             * getFeelsLike	体感温度，默认单位：摄氏度	23
             * getTemp	温度，默认单位：摄氏度	21
             * getIcon	天气状况的图标代码，另请参考天气图标项目	100
             * getText	天气状况的文字描述，包括阴晴雨雪等天气状态的描述	晴
             * getWind360	风向360角度	305
             * getWindDir	风向	西北
             * getWindScale	风力等级	3-4
             * getWindSpeed	风速，公里/小时	15
             * getHumidity	相对湿度	40
             * getPrecip	降水量	0
             * getPressure	大气压强	1020
             * getVis	能见度，默认单位：公里	10
             * getCloud	云量	23
             * getDew	露点温度
             */
            override fun onSuccess(bean: WeatherNowBean?) {
                runOnUiThread {
                    if (locationId != null && bean != null) {
                        WeatherCacheManager.getInstance().updateWeatherNow(locationId, bean)
                        nowWeatherMap.put(locationId, bean)
                    }
                    if (currentLocationId == locationId) {
                        updateTopWeather(bean)
                    }
                }
            }
        })
    }

    private fun getDataList(bean: WeatherNowBean?): List<String> {
        if (bean == null)
            return emptyList()
        val datas = arrayListOf<String>()
        datas.add(buildString {
            append("观测时间:")
            append(bean.now?.obsTime)
        })
        datas.add(buildString {
            append("天气状况:")
            append(bean.now?.text)
        })
        datas.add(buildString {
            append("温度:")
            append(bean.now?.temp)
            append("℃")
        })
        datas.add(buildString {
            append("体感温度:")
            append(bean.now?.feelsLike)
            append("℃")
        })
        datas.add(buildString {
            append("湿度:")
            append(bean.now?.humidity)
            append("%")
        })
        datas.add(buildString {
            append("降水量:")
            append(bean.now?.precip)
            append("mm")
        })
        datas.add(buildString {
            append("风:")
            append(bean.now?.windDir)
            append(" ")
            append(bean.now?.windScale)
            append("级")
            append(" ")
            append(bean.now?.windSpeed)
            append("km/h")
        })
        datas.add(buildString {
            append("能见度:")
            append(bean.now?.vis)
            append("km")
        })
        return datas
    }

    override fun onResume() {
        super.onResume()
        val mode = SPUtil.getInt(Constants.KEY_PAGE_MODE,1)
        if (mode!=showMode){
            showMode = mode
            switchUIMode()
        }
    }
}