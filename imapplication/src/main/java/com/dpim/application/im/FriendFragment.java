package com.dpim.application.im;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.dpim.application.CurrentUserManager;
import com.dpim.application.ToastUtil;
import com.dpim.application.UserInfoBean;
import com.dpim.application.base.BaseFragment;
import com.dpim.application.databinding.FragmentFriendBinding;
import com.dpim.application.http.HttpCallback;
import com.dpim.application.http.HttpHelper;

import org.json.JSONObject;

import io.rong.callkit.RongCallKit;
import io.rong.imkit.utils.RouteUtils;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.ConversationIdentifier;

public class FriendFragment extends BaseFragment {

    private FragmentFriendBinding binding;
    private FriendAdapter friendAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFriendBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        binding.addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userId = binding.userIdEt.getText().toString();
                if (TextUtils.isEmpty(userId)) {
                    ToastUtil.show("user is null");
                    return;
                }
                if (TextUtils.equals(userId, CurrentUserManager.getInstance().getUserId())) {
                    ToastUtil.show("不能添加自己为好友");
                    return;
                }
                if (FriendManager.getInstance().isAdd(userId)) {
                    ToastUtil.show("请勿重复添加");
                    return;
                }
                addFriend(userId);
            }
        });
        binding.rv.setLayoutManager(new LinearLayoutManager(mContext));
        friendAdapter = new FriendAdapter(mContext);
        binding.rv.setAdapter(friendAdapter);
        friendAdapter.setData(FriendManager.getInstance().getFriendList());
        friendAdapter.setItemClickListener(new FriendAdapter.ItemClickListener() {
            @Override
            public void onItemClick(UserInfoBean bean, int position) {
                String targetId = bean.getUserId();
                ConversationIdentifier conversationIdentifier = new ConversationIdentifier(Conversation.ConversationType.PRIVATE, targetId);
                RouteUtils.routeToConversationActivity(mContext, conversationIdentifier, false, null);
            }
        });
    }

    private void addFriend(String userId) {
        HttpHelper.getInstance().requestQueryInfo(userId, new HttpCallback() {
            @Override
            public void onSuccess(Object response, String msg) {
                JSONObject jsonObject = (JSONObject) response;
                String userName = jsonObject.optString("userName");
                UserInfoBean userInfoBean = new UserInfoBean();
                userInfoBean.setUserId(userId);
                userInfoBean.setUserName(userName);
                FriendManager.getInstance().addFriend(userInfoBean);
                friendAdapter.setData(FriendManager.getInstance().getFriendList());
                ToastUtil.show("添加好友 " + userName + " 成功");
            }

            @Override
            public void onFailed(String msg) {
                ToastUtil.show(msg);
            }
        });
    }
}
