package com.dpzz.weatherpart.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import com.dpzz.lib_base.imageload.ImageLoader
import com.dpzz.lib_base.recyclerview.BaseQuickAdapter
import com.dpzz.lib_base.recyclerview.BaseViewHolder
import com.dpzz.lib_base.util.PxUtils
import com.dpzz.weatherpart.databinding.ItemSimpleHourlyLayoutBinding
import com.dpzz.weatherpart.util.DateUtil
import com.qweather.sdk.bean.weather.WeatherHourlyBean

/**
 * xxxx
 * Date: 2024/7/26 11:47
 * Author: liangdp
 */
class HourlySimpleAdapter(mContext: Context?) :
    BaseQuickAdapter<WeatherHourlyBean.HourlyBean?, ItemSimpleHourlyLayoutBinding>(mContext) {

    private val svgWidth by lazy { PxUtils.dip2px(mContext, 30f) }

    override fun getItemViewBinding(parent: ViewGroup): ItemSimpleHourlyLayoutBinding {
        return ItemSimpleHourlyLayoutBinding.inflate(LayoutInflater.from(mContext), parent, false)
    }

    override fun onBindMyViewHolder(holder: BaseViewHolder<ItemSimpleHourlyLayoutBinding>?, item: WeatherHourlyBean.HourlyBean?, position: Int) {
        if (item == null || holder == null) return
        holder.itemView.layoutParams = holder.itemView.layoutParams.apply {
            if (this is MarginLayoutParams) {
                leftMargin = if (position == 0) 0 else PxUtils.dip2px(mContext, 8f)
            }
        }
        holder.mViewBinding?.tvTemp?.text = "${item.text} ${item.temp}℃"
        // holder.mViewBinding?.tvHumidity?.text = "相对湿度:${item.humidity}%"
        // holder.mViewBinding?.tvRain?.text = "降水概率:${item.pop}% 降水量:${item.precip}mm"
        holder.mViewBinding?.tvWind?.text = "${item.windDir}\n${item.windScale}级"
        //  holder.mViewBinding?.tvCloud?.text = "云量:${item.cloud}"
        holder.mViewBinding?.tvTime?.text = DateUtil.transformISO8601Date(item.fxTime)
        ImageLoader.getInstance()
            .loadAssetsSvgImage(holder.mViewBinding?.statusIv, "icons/${item.icon}.svg", svgWidth, svgWidth)
    }
}
