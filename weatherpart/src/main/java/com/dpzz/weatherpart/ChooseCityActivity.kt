package com.dpzz.weatherpart

import android.content.Intent
import android.text.TextUtils
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dpzz.lib_base.base.BaseActivity
import com.dpzz.lib_base.recyclerview.BaseRecyclerViewAdapter
import com.dpzz.lib_base.recyclerview.OnMyItemClickListener
import com.dpzz.lib_base.util.ToastUtil
import com.dpzz.weatherpart.adapter.LocationAdapter
import com.dpzz.weatherpart.adapter.SearchResultAdapter
import com.dpzz.weatherpart.databinding.ActivityChooseCityBinding
import com.dpzz.weatherpart.util.KeyBoardUtil
import com.google.gson.Gson
import com.qweather.sdk.bean.base.Lang
import com.qweather.sdk.bean.base.Range
import com.qweather.sdk.bean.geo.GeoBean
import com.qweather.sdk.view.QWeather

class ChooseCityActivity : BaseActivity<ActivityChooseCityBinding>(), OnMyItemClickListener {

    private val locationAdapter by lazy { LocationAdapter(this) }
    private val searchAdapter by lazy { SearchResultAdapter(this) }
    private var pageFrom = ""
    override fun initData() {
        pageFrom = intent?.getStringExtra(Constants.KEY_PAGE_FROM)?:""
    }

    override fun initView() {
        mViewBinding.progressBar.visibility = View.VISIBLE
        mViewBinding.hotRv.layoutManager = GridLayoutManager(this, 3)
        locationAdapter.setItemClickListener(this)
        mViewBinding.hotRv.adapter = locationAdapter
        mViewBinding.searchEt.setOnEditorActionListener(object :TextView.OnEditorActionListener{
            override fun onEditorAction(v: TextView?, actionId: Int, event: KeyEvent?): Boolean {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    val search = mViewBinding.searchEt.text.toString()
                    searchCity(search)
                    return true
                }
                return false
            }
        })
        mViewBinding.searchResultRv.layoutManager = LinearLayoutManager(this)
        mViewBinding.searchResultRv.adapter=searchAdapter
        searchAdapter.setItemClickListener { adapter, position ->
            val itemData = searchAdapter.getItemData(position)
            if (itemData != null){
                WeatherCacheManager.getInstance().addLocation(itemData.id,itemData.name)
                if (TextUtils.equals(pageFrom, Constants.KEY_PAGE_WEATHER_MAIN)){
                    val intent = Intent()
                    intent.putExtra(Constants.KEY_LOCATION_ID,itemData.id)
                        .putExtra(Constants.KEY_LOCATION_NAME,itemData.name)
                    setResult(RESULT_OK,intent)
                }else{
                    val intent = Intent(this, WeatherMainActivity::class.java)
                        .putExtra(Constants.KEY_LOCATION_ID,itemData.id)
                        .putExtra(Constants.KEY_LOCATION_NAME,itemData.name)
                    startActivity(intent)
                }
                finish()
            }
        }
        getHotCitys()
    }

    private fun getHotCitys(){
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

    fun searchCity(searchKey :String?){
        if (searchKey.isNullOrEmpty())
            ToastUtil.show2("搜索词不能为空")

        KeyBoardUtil.dismissInputMethod(mViewBinding.searchEt)

        QWeather.getGeoCityLookup(this,searchKey,Range.CN,20, Lang.ZH_HANS, object : QWeather.OnResultGeoListener{
            override fun onError(t: Throwable?) {
                runOnUiThread { ToastUtil.show2(t?.message?:"请求异常，请稍后重试") }
            }

            override fun onSuccess(bean: GeoBean?) {
               runOnUiThread {
                   val string = Gson().toJson(bean).toString()
                   if (!bean?.locationBean.isNullOrEmpty()){
                       mViewBinding.searchResultRv.visibility = View.VISIBLE
                       searchAdapter.setData(bean?.locationBean)
                   }else{
                       mViewBinding.searchResultRv.visibility = View.GONE
                   }

                   Log.e("tetexxd",string)
               }
            }
        })
    }

    override fun onItemClick(adapter: BaseRecyclerViewAdapter<*, *>?, position: Int) {
        val locationBean = locationAdapter.getItemData(position)
        WeatherCacheManager.getInstance().addLocation(locationBean?.id,locationBean?.name)
        if (TextUtils.equals(pageFrom, Constants.KEY_PAGE_WEATHER_MAIN)){
            val intent = Intent()
            intent.putExtra(Constants.KEY_LOCATION_ID,locationBean?.id)
            intent.putExtra(Constants.KEY_LOCATION_NAME,locationBean?.name)
            setResult(RESULT_OK,intent)
        }else{
            val intent = Intent(this, WeatherMainActivity::class.java)
                .putExtra(Constants.KEY_LOCATION_ID,locationBean?.id)
                .putExtra(Constants.KEY_LOCATION_NAME,locationBean?.name)
            startActivity(intent)
        }
        finish()
    }
}