package com.dpzz.weatherpart.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * xxxx
 * Date: 2024/11/12 14:56
 * Author: liangdp
 */
public class DataBaseDao {
    private static final String TAG = "DataBaseDao";

    private static final class Holder {
        private static final DataBaseDao INSTANCE = new DataBaseDao();
    }

    public static DataBaseDao getInstance() {
        return Holder.INSTANCE;
    }

    private DataBaseDao() {
    }


    public void insertWeather(String locationId, String locationName, String nowWeatherStr, boolean isUserAdd, long addTileMills) {
        SQLiteDatabase database = DBOpenHelper.getInstance().getWritableDatabase();
//        database.execSQL("INSERT INTO weather1 (location_id,location_name,now_weather_str) VALUES(?,?,?)", new Object[]{locationId, locationName, nowWeatherStr});
//        database.close();

        ContentValues values = new ContentValues();
        values.put("location_id", locationId);
        values.put("location_name", locationName);
        values.put("now_weather_str", nowWeatherStr);
        values.put("is_user_add", isUserAdd ? 1 : 0);
        values.put("add_time", addTileMills);
        database.insert("weather1", null, values);
        database.close();
    }

    public void deleteWeather(String locationId) {
        SQLiteDatabase database = DBOpenHelper.getInstance().getWritableDatabase();
        database.execSQL("delete from weather1 where location_id = ?", new Object[]{locationId});
        database.close();
    }

    public void deleteAll() {
        SQLiteDatabase database = DBOpenHelper.getInstance().getWritableDatabase();
        database.execSQL("delete from weather1");
        database.close();
    }

    public void updateWeather(String locationId, String nowWeatherStr) {
        SQLiteDatabase database = DBOpenHelper.getInstance().getWritableDatabase();
        database.execSQL("update weather1 set now_weather_str = ? where location_id = ?", new Object[]{nowWeatherStr, locationId});
        database.close();
    }

    public void updateAddTime(String locationId, long addTileMills) {
        SQLiteDatabase database = DBOpenHelper.getInstance().getWritableDatabase();
        database.execSQL("update weather1 set add_time = ? where location_id = ?", new Object[]{addTileMills, locationId});
        database.close();
    }

    @SuppressLint("Range")
    public List<CustomLocationBean> queryAllLocation() {
        SQLiteDatabase database = DBOpenHelper.getInstance().getReadableDatabase();
        ArrayList<CustomLocationBean> locationBeans = new ArrayList<>();
        try {
            Cursor cursor = database.query("weather1", null, null, null, null, null, null);
            while (cursor.moveToNext()) {
                int locationId = cursor.getInt(cursor.getColumnIndex("location_id"));
                String locationName = cursor.getString(cursor.getColumnIndex("location_name"));
                String nowWeatherStr = cursor.getString(cursor.getColumnIndex("now_weather_str"));
                boolean isUserAdd = 1 == cursor.getInt(cursor.getColumnIndex("is_user_add"));
                long addTime = cursor.getLong(cursor.getColumnIndex("add_time"));
                locationBeans.add(new CustomLocationBean(locationId, locationName, nowWeatherStr, isUserAdd, addTime));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

//        String sql = "select now_weather_str from weather1 where location_id = ?";
//        String nowWeatherStr = null;
//        try {
//            nowWeatherStr = database.rawQuery(sql, new String[]{locationId}).getString(0);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        database.close();
        return locationBeans;
    }

    public String queryNowWeatherStr(String locationId) {
        SQLiteDatabase database = DBOpenHelper.getInstance().getReadableDatabase();
        String sql = "select now_weather_str from weather1 where location_id = ?";
        String nowWeatherStr = null;
        try {
            nowWeatherStr = database.rawQuery(sql, new String[]{locationId}).getString(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        database.close();
        return nowWeatherStr;
    }

    public String queryLocationName(String locationId) {
        SQLiteDatabase database = DBOpenHelper.getInstance().getReadableDatabase();
        String sql = "select location_name from weather1 where location_id = ?";
        String locationName = null;
        try {
            locationName = database.rawQuery(sql, new String[]{locationId}).getString(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        database.close();
        return locationName;
    }

    public boolean isLocationExists(String locationId) {
        SQLiteDatabase database = DBOpenHelper.getInstance().getReadableDatabase();
        String sql = "select * from weather1 where location_id = ?";
        try {
            return database.rawQuery(sql, new String[]{locationId}).getCount() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.close();
        }
        return false;
    }

}
