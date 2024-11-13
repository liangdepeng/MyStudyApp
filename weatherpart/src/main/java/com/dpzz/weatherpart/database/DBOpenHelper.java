package com.dpzz.weatherpart.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.dpzz.lib_base.GlobalContext;

/**
 * xxxx
 * Date: 2024/11/12 13:51
 * Author: liangdp
 */
public class DBOpenHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "hfweather.db";
    private static final int DB_VERSION = 1;

    private static final class Holder {
        private static final DBOpenHelper instance = new DBOpenHelper(GlobalContext.mContext);
    }

    public static DBOpenHelper getInstance() {
        return Holder.instance;
    }


    private DBOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createSql = "create table weather1 " + "("
                + "location_id integer primary key,"
                + "location_name text,"
                + "now_weather_str text,"
                + "is_user_add integer,"
                + "add_time long"
                + ");";
        db.execSQL(createSql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }



}
