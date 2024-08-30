package com.dpim.application.im;

import android.text.TextUtils;

import com.dpim.application.CurrentUserManager;
import com.dpim.application.UserInfoBean;
import com.dpim.application.base.SPUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashSet;

public class FriendManager {

    private static final String KEY_FRIEND_LIST = "KEY_FRIEND_LIST";
    private final ArrayList<UserInfoBean> friendList = new ArrayList<>();
    private final HashSet<String> friendLSet = new HashSet<>();
    private boolean init = false;

    public boolean isAdd(String userId) {
        if (!friendLSet.add(userId)) {
            return true;
        } else {
            friendLSet.remove(userId);
            return false;
        }
    }

    public void addFriend(UserInfoBean bean) {
        if (bean == null) {
            return;
        }
        if (!friendLSet.add(bean.getUserId())) {
            //  Toast.makeText(BaseApp.appCtx, "请勿重复添加", Toast.LENGTH_SHORT).show();
            return;
        }
        friendList.add(bean);
        SPUtils.put(KEY_FRIEND_LIST + "_" + CurrentUserManager.getInstance().getUserId(), new Gson().toJson(friendList));
    }

    public ArrayList<UserInfoBean> getFriendList() {
        if (!init) {
            initFriendList();
        }
        return friendList;
    }

    public void clear() {
        friendList.clear();
        friendLSet.clear();
        init = false;
    }

    public static FriendManager getInstance() {
        return Manager.Instance;
    }

    private final static class Manager {
        private final static FriendManager Instance = new FriendManager();
    }

    private FriendManager() {
        initFriendList();
    }

    private void initFriendList() {
        init = true;
        String listStr = SPUtils.getString(KEY_FRIEND_LIST + "_" + CurrentUserManager.getInstance().getUserId());
        if (TextUtils.isEmpty(listStr)) {
            return;
        }
        ArrayList<UserInfoBean> beans = new Gson().fromJson(listStr, new TypeToken<ArrayList<UserInfoBean>>() {
        }.getType());
        if (beans != null) {
            friendList.clear();
            friendLSet.clear();
            for (int i = 0; i < beans.size(); i++) {
                UserInfoBean infoBean = beans.get(i);
                if (infoBean != null) {
                    friendLSet.add(infoBean.getUserId());
                    friendList.add(infoBean);
                }
            }
        }
    }

}
