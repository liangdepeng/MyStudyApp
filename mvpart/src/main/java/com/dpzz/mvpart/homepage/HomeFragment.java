package com.dpzz.mvpart.homepage;

import androidx.fragment.app.Fragment;

import com.dpzz.lib_base.BaseFragment;
import com.dpzz.mvpart.adapter.HomeFragmentPagerAdapter;
import com.dpzz.mvpart.databinding.FragmentHomeBinding;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment<FragmentHomeBinding> {

    private List<Fragment> fragments = new ArrayList<>();
    private List<String> titles = new ArrayList<>();

    @Override
    public void initContentView() {
        initData();
        HomeFragmentPagerAdapter adapter = new HomeFragmentPagerAdapter(getChildFragmentManager(), fragments, titles);
        mViewBinding.viewpager.setAdapter(adapter);
        mViewBinding.tabLayout.setupWithViewPager(mViewBinding.viewpager);
    }

    private void initData() {
        fragments.add(HomeRecommendFragment.newInstance());
        fragments.add(TestEmptyFragment.newInstance());
        fragments.add(TestEmptyFragment.newInstance());
        fragments.add(TestEmptyFragment.newInstance());
        fragments.add(TestEmptyFragment.newInstance());

        titles.add("推荐");
        titles.add("测试一");
        titles.add("测试二");
        titles.add("测试三");
        titles.add("测试四");
    }
}
