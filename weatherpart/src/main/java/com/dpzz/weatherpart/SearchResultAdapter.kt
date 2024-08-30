package com.dpzz.weatherpart

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import com.dpzz.lib_base.recyclerview.BaseQuickAdapter
import com.dpzz.lib_base.recyclerview.BaseViewHolder
import com.dpzz.weatherpart.databinding.ItemSearchResultLayoutBinding
import com.qweather.sdk.bean.geo.GeoBean

/**
 *
 * Date: 2024/8/12 16:09
 * Author: liangdp
 */
class SearchResultAdapter(context: Context) : BaseQuickAdapter<GeoBean.LocationBean, ItemSearchResultLayoutBinding>(context) {
    override fun onBindMyViewHolder(holder: BaseViewHolder<ItemSearchResultLayoutBinding>, item: GeoBean.LocationBean?, position: Int) {
        holder.mViewBinding.locationNameTv.text = item?.name
    }

    override fun getItemViewBinding(parent: ViewGroup?): ItemSearchResultLayoutBinding {
        return ItemSearchResultLayoutBinding.inflate(LayoutInflater.from(mContext), parent, false)
    }
}