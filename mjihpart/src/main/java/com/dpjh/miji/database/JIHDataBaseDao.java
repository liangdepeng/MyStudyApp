package com.dpjh.miji.database;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * java类作用描述
 * <p>
 * Date: 2024/12/12 15:51
 * Author: 唐朝
 */
public class JIHDataBaseDao {

    private final static class Holder {
        private final static JIHDataBaseDao INSTANCE = new JIHDataBaseDao();
    }

    public static JIHDataBaseDao getInstance() {
        return Holder.INSTANCE;
    }


    @Deprecated
    public void addItemData(String code, String desc) {
        SQLiteDatabase database = SQLiteHelper.getInstance().getWritableDatabase();
//        String sql = "INSERT INTO jhdata (mj_wupingdesc, mj_code) VALUES (?, ?)";
//        database.execSQL(sql,new Object[]{desc,code});
        ContentValues values = new ContentValues();
        values.put("mj_msgstr", desc);
        values.put("mj_msgid", code);
        values.put("mj_msgtypename", "");
        values.put("mj_msgctxt", "");
        database.insert(SQLiteHelper.DB_NAME, null, values);
        database.close();
    }

    public long addItemData(JHBean bean){
        try {
            SQLiteDatabase database = SQLiteHelper.getInstance().getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("mj_msgtypename",bean.getJhDataTypeName());
            values.put("mj_msgctxt",bean.getJhDataTextName());
            values.put("mj_msgid",bean.getJhCode());
            values.put("mj_msgstr",bean.getJhDesc());
            return database.insert(SQLiteHelper.DB_NAME,null,values);
        } catch (Exception e){
            e.printStackTrace();
        }
        return -1;
    }

    public void deleteItemData(String code) {
        if (TextUtils.isEmpty(code)) {
            return;
        }
        SQLiteDatabase database = SQLiteHelper.getInstance().getWritableDatabase();
        String sql = "delete from jhdata where mj_msgid = ?";
        database.execSQL(sql, new Object[]{code});
        database.close();
    }

    public void updateItemData(String oldDesc, String oldCode, String newDesc, String newCode) {
        SQLiteDatabase database = SQLiteHelper.getInstance().getWritableDatabase();
        String sql = "update jhdata set mj_msgstr = ?, mj_msgid = ? where mj_msgid = ?";
        database.execSQL(sql, new Object[]{newDesc, newCode, oldCode});
        database.close();
    }


    /**
     *
     * @return
     */
    @SuppressLint("Range")
    public List<JHBean> getDatas() {
        SQLiteDatabase database = SQLiteHelper.getInstance().getWritableDatabase();
//        Cursor cursor = database.query("jhdata", null, null, null, null, null, null);
        String sql = "select * from jhdata";
        Cursor cursor = database.rawQuery(sql, null);
        ArrayList<JHBean> jhBeans = new ArrayList<>();
        try {
            while (cursor.moveToNext()) {
                int mj_id = cursor.getInt(cursor.getColumnIndex("mj_id"));
                String mj_msgtypename = cursor.getString(cursor.getColumnIndex("mj_msgtypename"));
                String mj_msgctxt = cursor.getString(cursor.getColumnIndex("mj_msgctxt"));
                String mj_msgid = cursor.getString(cursor.getColumnIndex("mj_msgid"));
                String mj_msgstr = cursor.getString(cursor.getColumnIndex("mj_msgstr"));
                JHBean bean = new JHBean();
                bean.setJhId(String.valueOf(mj_id));
                bean.setJhDataTypeName(mj_msgtypename);
                bean.setJhDataTextName(mj_msgctxt);
                bean.setJhCode(mj_msgid);
                bean.setJhDesc(mj_msgstr);
                jhBeans.add(bean);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
      //  database.close();
        return jhBeans;
    }
}
