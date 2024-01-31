package com.dpzz.weatherpart

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.dpzz.lib_base.base.BaseActivity
import com.dpzz.lib_base.recyclerview.BaseRecyclerViewAdapter
import com.dpzz.lib_base.recyclerview.OnMyItemClickListener
import com.dpzz.weatherpart.databinding.ActivityChooseCityBinding
import com.qweather.sdk.bean.base.Lang
import com.qweather.sdk.bean.base.Range
import com.qweather.sdk.bean.geo.GeoBean
import com.qweather.sdk.view.QWeather

class ChooseCityActivity : BaseActivity<ActivityChooseCityBinding>(), OnMyItemClickListener {

    private val locationAdapter by lazy { LocationAdapter(this) }

    override fun initData() {
    }

    override fun initView() {
        mViewBinding.progressBar.visibility = View.VISIBLE
        mViewBinding.hotRv.layoutManager = GridLayoutManager(this, 3)
        locationAdapter.setItemClickListener(this)
        mViewBinding.hotRv.adapter = locationAdapter

        QWeather.getGeoTopCity(this, 20, Range.CN, Lang.ZH_HANS,
            object : QWeather.OnResultGeoListener {
                override fun onError(throwable: Throwable?) {
                    runOnUiThread {
                        mViewBinding.progressBar.visibility = View.GONE
                    }
                }

                override fun onSuccess(geoBean: GeoBean?) {
                    runOnUiThread {
                        mViewBinding.progressBar.visibility = View.GONE
                        if (geoBean != null && !geoBean.locationBean.isNullOrEmpty()) {
                            locationAdapter.setData(geoBean.locationBean)
                        }
                    }
                }
            })
    }

    override fun onItemClick(adapter: BaseRecyclerViewAdapter<*, *>?, position: Int) {

    }
}