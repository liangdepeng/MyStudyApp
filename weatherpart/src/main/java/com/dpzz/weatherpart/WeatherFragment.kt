package com.dpzz.weatherpart

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dpzz.lib_base.base.BaseFragment
import com.dpzz.lib_base.util.ToastUtil
import com.dpzz.weatherpart.databinding.FragmentWeatherBinding
import com.google.gson.Gson
import com.qweather.sdk.bean.weather.WeatherDailyBean
import com.qweather.sdk.bean.weather.WeatherHourlyBean
import com.qweather.sdk.bean.weather.WeatherNowBean
import com.qweather.sdk.view.QWeather
import org.json.JSONObject

/**
 * xxxx
 * Date: 2024/7/25 16:27
 * Author: liangdp
 */
class WeatherFragment : BaseFragment<FragmentWeatherBinding>() {

    companion object {
        fun newInstance(locationId: String?): WeatherFragment {
            val fragment = WeatherFragment()
            val bundle = Bundle()
            bundle.putString("locationId", locationId)
            fragment.arguments = bundle
            return fragment
        }
    }

    private var location :String? = ""
    private val hourlyAdapter by lazy { HourlyAdapter(mContext) }

    override fun initContentView() {
        location = arguments?.getString("locationId", "")

        mViewBinding.hourly24Rv.layoutManager = LinearLayoutManager(mContext,RecyclerView.HORIZONTAL,false)
        mViewBinding.hourly24Rv.adapter = hourlyAdapter


        requestWeatherDetail(location)
        requestWeather7days()
    }


    private fun requestWeather7days(){
        QWeather.getWeather7D(mContext,location,object :QWeather.OnResultWeatherDailyListener{
            override fun onError(t: Throwable?) {
                Log.e("sadasd","getWeather7D ${t?.message?:"errr"}")
            }

            override fun onSuccess(bean: WeatherDailyBean?) {
                Log.e("sadasd",JSONObject(Gson().toJson(bean)).toString(2))
            }
        })
    }


    private fun requestWeatherDetail(location: String?) {
        Log.e("sadasd","$location")
       QWeather.getWeather24Hourly(mContext,location, object : QWeather.OnResultWeatherHourlyListener {
           override fun onError(t: Throwable?) {
               Log.e("sadasd","getWeather24Hourly ${t?.message?:"errr"}")
           }

           override fun onSuccess(bean: WeatherHourlyBean?) {
            //   val toJson = Gson().toJson(bean)
            //   Log.e("sadasd",JSONObject(toJson).toString(2))
               mActivity.runOnUiThread {
                   if (bean != null) {
                       hourlyAdapter.setData(bean.hourly)
                   }
               }
           }

       })
    }
}
