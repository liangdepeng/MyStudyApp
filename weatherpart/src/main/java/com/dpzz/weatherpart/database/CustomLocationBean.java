package com.dpzz.weatherpart.database;

/**
 * xxxx
 * Date: 2024/11/12 14:41
 * Author: liangdp
 */
public class CustomLocationBean {
    private String locationId;
    private String locationName;
    private String nowWeatherStr;

    private boolean isUserAdd;

    private long addTileMills;

    public CustomLocationBean(int locationId, String locationName, String nowWeatherStr, boolean isUserAdd,long addTileMills) {
        this.locationId = String.valueOf(locationId);
        this.locationName = locationName;
        this.nowWeatherStr = nowWeatherStr;
        this.isUserAdd = isUserAdd;
        this.addTileMills = addTileMills;
    }

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public String getNowWeatherStr() {
        return nowWeatherStr;
    }

    public void setNowWeatherStr(String nowWeatherStr) {
        this.nowWeatherStr = nowWeatherStr;
    }

    public boolean isUserAdd() {
        return isUserAdd;
    }

    public void setUserAdd(boolean userAdd) {
        isUserAdd = userAdd;
    }

    public long getAddTileMills() {
        return addTileMills;
    }

    public void setAddTileMills(long addTileMills) {
        this.addTileMills = addTileMills;
    }
}
