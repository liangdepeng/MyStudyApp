package com.dpzz.weatherpart

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.dpzz.lib_base.recyclerview.BaseQuickAdapter
import com.dpzz.lib_base.recyclerview.BaseViewHolder
import com.dpzz.weatherpart.databinding.ItemSingleLocationBinding
import com.qweather.sdk.bean.geo.GeoBean

class LocationAdapter(val context: Context?) :
    BaseQuickAdapter<GeoBean.LocationBean, ItemSingleLocationBinding>(context) {

    override fun getItemViewBinding(parent: ViewGroup?): ItemSingleLocationBinding {
        return ItemSingleLocationBinding.inflate(LayoutInflater.from(context), parent, false)
    }

    override fun onBindMyViewHolder(
        holder: BaseViewHolder<ItemSingleLocationBinding>?,
        item: GeoBean.LocationBean?,
        position: Int
    ) {
        if (holder == null) return
        holder.mViewBinding.cityTv.text = item?.name
    }
}