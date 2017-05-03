package com.mozre.find.module.part;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * Created by MOZRE on 2016/6/20.
 */
public class MineHomeViewPagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragments;
    private List<String> titles;

    public MineHomeViewPagerAdapter(FragmentManager fm, List<Fragment> fragments, List<String> titles) {
        super(fm);
//        super(fm);
        this.fragments = fragments;
        this.titles = titles;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return titles.get(position);
    }
}
