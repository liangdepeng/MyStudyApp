package com.dpzz.weatherpart

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.dpzz.lib_base.imageload.ImageLoader
import com.dpzz.lib_base.recyclerview.BaseQuickAdapter
import com.dpzz.lib_base.recyclerview.BaseViewHolder
import com.dpzz.lib_base.util.PxUtils
import com.dpzz.weatherpart.databinding.ItemHourlyLayoutBinding
import com.qweather.sdk.bean.weather.WeatherHourlyBean

/**
 * xxxx
 * Date: 2024/7/26 11:47
 * Author: liangdp
 */
class HourlyAdapter(mContext: Context?) :
    BaseQuickAdapter<WeatherHourlyBean.HourlyBean?, ItemHourlyLayoutBinding>(mContext) {

        private val svgWidth by lazy { PxUtils.dip2px(mContext, 30f) }

    override fun getItemViewBinding(parent: ViewGroup): ItemHourlyLayoutBinding {
        return ItemHourlyLayoutBinding.inflate(LayoutInflater.from(mContext), parent, false)
    }

    override fun onBindMyViewHolder(holder: BaseViewHolder<ItemHourlyLayoutBinding>?, item: WeatherHourlyBean.HourlyBean?, position: Int) {
        if (item == null || holder==null) return
        holder.mViewBinding?.tvTemp?.text = "天气:${item.text} ${item.temp}℃"
        holder.mViewBinding?.tvHumidity?.text = "相对湿度:${item.humidity}%"
        holder.mViewBinding?.tvRain?.text = "降水概率:${item.pop}% 降水量:${item.precip}mm"
        holder.mViewBinding?.tvWind?.text = "风向:${item.windDir} 风力:${item.windScale}级 风速:${item.windSpeed}km/h"
        holder.mViewBinding?.tvCloud?.text = "云量:${item.cloud}"
        holder.mViewBinding?.tvTime?.text = DateUtil.transformISO8601Date(item.fxTime)
        ImageLoader.getInstance().loadAssetsSvgImage(holder.mViewBinding?.statusIv,"icons/${item.icon}.svg",svgWidth,svgWidth)
    }
}
