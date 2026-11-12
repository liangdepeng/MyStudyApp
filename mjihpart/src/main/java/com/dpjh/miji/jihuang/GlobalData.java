package com.dpjh.miji.jihuang;

import com.dpjh.miji.database.JHBean;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class GlobalData {
    public final static ArrayList<String> globalSearchList1 = new ArrayList<>();
    public final static ArrayList<String> globalSearchSortList = new ArrayList<>();
    public final static LinkedHashMap<String, ArrayList<String>> globalSearchMap = new LinkedHashMap<>();

    public final static ArrayList<JHBean> dataBaseSearchList = new ArrayList<>();
    public static AtomicBoolean dataBaseInitFinished = new AtomicBoolean(false);
}
