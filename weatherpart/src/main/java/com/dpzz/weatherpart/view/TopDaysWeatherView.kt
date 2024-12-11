package com.dpzz.weatherpart.view

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dpzz.weatherpart.adapter.HourlyAdapter
import com.dpzz.weatherpart.adapter.HourlySimpleAdapter
import com.dpzz.weatherpart.databinding.PartTopDaysWeatherLayoutBinding
import com.dpzz.weatherpart.databinding.PartTopHourlyWeatherLayoutBinding
import com.qweather.sdk.bean.weather.WeatherHourlyBean

/**
 * xxxx
 * Date: 2024/11/14 10:36
 * Author: liangdp
 */
class TopDaysWeatherView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    FrameLayout(context, attrs, defStyleAttr) {

    private lateinit var mViewBinding: PartTopDaysWeatherLayoutBinding

    private val hourlyAdapter by lazy { HourlyAdapter(getContext()) }
    private val hourlySimpleAdapter by lazy { HourlySimpleAdapter(getContext()) }

    override fun onFinishInflate() {
        super.onFinishInflate()
        mViewBinding = PartTopDaysWeatherLayoutBinding.bind(this)
    }

    fun switchModeUI(showMode: Int) {
        if (showMode == 1) {
            mViewBinding.daysRv.layoutManager =
                LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            mViewBinding.daysRv.adapter = hourlyAdapter
        } else {
            mViewBinding.daysRv.layoutManager =
                LinearLayoutManager(context, RecyclerView.HORIZONTAL, false)
            mViewBinding.daysRv.adapter = hourlySimpleAdapter
        }
    }

    fun setData(bean: WeatherHourlyBean?, showMode: Int) {
        if (bean != null) {
            if (showMode == 1) {
                hourlyAdapter.setData(bean.hourly)
            } else {
                hourlySimpleAdapter.setData(bean.hourly)
            }
        }
    }
}
