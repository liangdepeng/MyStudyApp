package com.dpjh.miji.jihuang;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class ViewPagerMyAdapter extends FragmentPagerAdapter {

    private final ArrayList<String> titles = new ArrayList<>();
    private final ArrayList<Fragment> fragments = new ArrayList<>();

    public ViewPagerMyAdapter(@NonNull FragmentManager fm, ArrayList<Fragment> fragments, ArrayList<String> titles) {
        super(fm);
        this.titles.clear();
        this.fragments.clear();
        if (titles != null)
            this.titles.addAll(titles);
        if (fragments != null)
            this.fragments.addAll(fragments);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
