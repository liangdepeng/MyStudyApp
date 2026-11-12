package com.dpjh.miji.database;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.dpzz.lib_base.GlobalContext;

/**
 * 源码数据库
 * <p>
 * Date: 2024/12/12 15:27
 * Author: ldp
 */
public class SQLiteHelper extends SQLiteOpenHelper {


    private final static class Holder {
        private final static SQLiteHelper instance = new SQLiteHelper(GlobalContext.mContext);
    }

    public static SQLiteHelper getInstance() {
        return Holder.instance;
    }

    public static final String DB_NAME = "jhdata";
    private static final int DB_VERSION = 1;

    public SQLiteHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table jhdata" + "("
                + "mj_id integer primary key autoincrement,"
                + "mj_msgtypename text,"
                + "mj_msgctxt text,"
                + "mj_msgid text,"
                + "mj_msgstr text"
                + ");";
        db.execSQL(sql);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
