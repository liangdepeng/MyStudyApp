package com.dpzz.weatherpart

import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dpzz.lib_base.base.BaseActivity
import com.dpzz.lib_base.util.ToastUtil
import com.dpzz.weatherpart.databinding.ActivityWeatherMainBinding
import com.qweather.sdk.bean.weather.WeatherNowBean
import com.qweather.sdk.view.QWeather

class WeatherMainActivity : BaseActivity<ActivityWeatherMainBinding>() {

    private var locationId: String? = ""
    private var locationName: String? = ""
    private val nowDetailAdapter by lazy { NowAdapter(this) }
    override fun initData() {
        locationId = intent?.getStringExtra(Constants.KEY_LOCATION_ID)
        locationName = intent?.getStringExtra(Constants.KEY_LOCATION_NAME)
    }

    override fun initView() {
        mViewBinding.titleTv.text = locationName
        mViewBinding.nowRv.layoutManager = LinearLayoutManager(this)
        mViewBinding.nowRv.adapter = nowDetailAdapter

        val fragments = arrayListOf<Fragment>()
        fragments.add(WeatherFragment.newInstance(locationId))
//        fragments.add(WeatherFragment.newInstance(1))
//        fragments.add(WeatherFragment.newInstance(2))
//        fragments.add(WeatherFragment.newInstance(3))
        mViewBinding.viewpager.offscreenPageLimit = fragments.size
        mViewBinding.viewpager.adapter = MyPageAdapter(supportFragmentManager, fragments)


        requestWeatherDetail(locationId)
    }

    override fun onBackPressed() {
        if (isTaskRoot) {
            moveTaskToBack(false)
        } else {
            super.onBackPressed()
        }
    }


    private fun requestWeatherDetail(location: String?) {
        QWeather.getWeatherNow(this, location, object : QWeather.OnResultWeatherNowListener {
            override fun onError(e: Throwable?) {
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
                    nowDetailAdapter.setData(getDataList(bean))
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
}