package com.dpzz.weatherpart;

import android.text.TextUtils;

import com.dpzz.lib_base.util.SPUtil;
import com.dpzz.weatherpart.database.CustomLocationBean;
import com.dpzz.weatherpart.database.DataBaseDao;
import com.google.gson.Gson;
import com.qweather.sdk.bean.geo.GeoBean;
import com.qweather.sdk.bean.weather.WeatherNowBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

/**
 * xxxx
 * Date: 2024/11/8 11:43
 * Author: liangdp
 */
public class WeatherCacheManager {

    private final HashMap<String, String> locationMap = new HashMap<>();
    private final HashMap<String, WeatherNowBean> weatherNowMap = new HashMap<>();
    private final ArrayList<CustomLocationBean> mLocationList = new ArrayList<>();

    private final static class Holder {
        private static final WeatherCacheManager INSTANCE = new WeatherCacheManager();
    }

    public static WeatherCacheManager getInstance() {
        return Holder.INSTANCE;
    }

    private WeatherCacheManager() {
    }

    private final Gson gson = new Gson();

    public void init() {
        List<CustomLocationBean> locationBeans = DataBaseDao.getInstance().queryAllLocation();
        if (locationBeans == null || locationBeans.isEmpty())
            return;
        for (CustomLocationBean bean : locationBeans) {
            if (bean == null)
                continue;
            mLocationList.add(bean);
            locationMap.put(bean.getLocationId(), bean.getLocationName());
            if (!TextUtils.isEmpty(bean.getNowWeatherStr())) {
                weatherNowMap.put(bean.getLocationId(), gson.fromJson(bean.getNowWeatherStr(), WeatherNowBean.class));
            }
        }
        Collections.sort(mLocationList, new Comparator<CustomLocationBean>() {
            @Override
            public int compare(CustomLocationBean o1, CustomLocationBean o2) {
                if (o1 == null || o2 == null)
                    return 0;
                if (o1.getAddTileMills() < o2.getAddTileMills())
                    return 1;
                if (o1.getAddTileMills() > o2.getAddTileMills())
                    return -1;
                return 0;
            }
        });
    }

    public ArrayList<CustomLocationBean> getLocationList() {
        return mLocationList;
    }

    public void addLocation(String locationId, String locationName) {
        if (locationMap.containsKey(locationId))
            return;
        long currentTimeMillis = System.currentTimeMillis();
        locationMap.put(locationId, locationName);
        mLocationList.add(0, new CustomLocationBean(Integer.parseInt(locationId), locationName, "", true, currentTimeMillis));
        DataBaseDao.getInstance().insertWeather(locationId, locationName, "", true, currentTimeMillis);
    }

    public String getLocationName(String locationId) {
        String locationName = locationMap.get(locationId);
        if (TextUtils.isEmpty(locationName)) {
            locationName = DataBaseDao.getInstance().queryLocationName(locationId);
        }
        return locationName;
    }

    public void updateWeatherNow(String locationId, WeatherNowBean bean) {
        weatherNowMap.put(locationId, bean);
        DataBaseDao.getInstance().updateWeather(locationId, gson.toJson(bean));
    }

}
