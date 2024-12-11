package com.dpzz.weatherpart.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import com.dpzz.lib_base.imageload.ImageLoader
import com.dpzz.lib_base.util.PxUtils
import com.dpzz.weatherpart.adapter.NowAdapter
import com.dpzz.weatherpart.databinding.PartTopMainWeatherLayoutBinding
import com.qweather.sdk.bean.weather.WeatherNowBean

/**
 * xxxx
 * Date: 2024/11/14 10:05
 * Author: liangdp
 */
class TopMainWeatherView : CardView {

    private lateinit var mViewBinding: PartTopMainWeatherLayoutBinding
    private val nowDetailAdapter by lazy { NowAdapter(context) }

    constructor(context: Context) : super(context){

    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    override fun onFinishInflate() {
        super.onFinishInflate()
        mViewBinding = PartTopMainWeatherLayoutBinding.bind(this)
        initView()
    }

    private fun initView() {
        mViewBinding.nowRv.layoutManager = LinearLayoutManager(context)
        mViewBinding.nowRv.adapter = nowDetailAdapter
    }

    fun updateTopWeather(nowBean: WeatherNowBean?) {
        val dp66 = PxUtils.dip2px(context, 66f)
        nowDetailAdapter.setData(getDataList(nowBean))
        ImageLoader.getInstance().loadAssetsSvgImage(mViewBinding.nowIv,"icons/${nowBean?.now?.icon}.svg",dp66,dp66)

        mViewBinding.tvTemp.text = "${nowBean?.now?.temp}℃"
        mViewBinding.tvTempDesc.text = "${nowBean?.now?.text} 最高0℃ 最低0℃"
        mViewBinding.tvWindyDesc.text = "${nowBean?.now?.windDir} ${nowBean?.now?.windScale}级"
        ImageLoader.getInstance().loadAssetsSvgImage(mViewBinding.ivTempp, "icons/${nowBean?.now?.icon}.svg", dp66, dp66)
    }

    fun switchUIMode(showMode :Int){
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
