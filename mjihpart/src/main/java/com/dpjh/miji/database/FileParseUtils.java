package com.dpjh.miji.database;

import android.util.Log;

import com.dpjh.miji.jihuang.GlobalData;
import com.dpzz.lib_base.GlobalContext;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * java类作用描述
 * <p>
 * Date: 2024/12/27 16:22
 * Author: 唐朝
 *
 * msgtypename strings.names.abigail
 * msgctxt "strings.names.abigail"
 * msgid "abigail"
 * msgstr "阿比盖尔"
 *
 */
public class FileParseUtils {

    public static void initAssetDbFile() {
        InputStream inputStream = null;

        List<JHBean> datas = JIHDataBaseDao.getInstance().getDatas();

        if (datas.size()>0){
            GlobalData.dataBaseSearchList.clear();
            GlobalData.dataBaseSearchList.addAll(datas);
            GlobalData.dataBaseInitFinished.set(true);
            Log.e("jihuang","已初始化数据库  "+datas.size());
            return;
        }

        try {
            inputStream = GlobalContext.mContext.getAssets().open("db_code.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while ((line = reader.readLine()) != null) {
                if (line.isEmpty())
                    continue;
                JHBean jhBean = new JHBean();
                if (line.contains("msgtypename")){
                    jhBean.setJhDataTypeName(line.replace("msgtypename ",""));
                }

                for (int i=0;i<3;i++){
                    line = reader.readLine();
                    if (line.contains("msgctxt")){
                        jhBean.setJhDataTextName(line.replace("msgctxt \"","").replace("\"",""));
                    }else if (line.contains("msgid")){
                        jhBean.setJhCode(line.replace("msgid \"","").replace("\"",""));
                    }else if (line.contains("msgstr")){
                        jhBean.setJhDesc(line.replace("msgstr \"","").replace("\"",""));
                    }
                }

                long index = JIHDataBaseDao.getInstance().addItemData(jhBean);
                Log.e("jihuang","index = "+index+"   插入"+(index>=0?"成功":"失败"));
            }

            List<JHBean> datas2 = JIHDataBaseDao.getInstance().getDatas();

            if (datas2.size()>0){
                GlobalData.dataBaseSearchList.clear();
                GlobalData.dataBaseSearchList.addAll(datas2);
                Log.e("jihuang","首次初始化数据库  "+datas2.size());
            }

            GlobalData.dataBaseInitFinished.set(true);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
