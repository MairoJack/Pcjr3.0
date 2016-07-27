package com.pcjinrong.pcjr.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Mario on 2016/5/12.
 */
public class TabChildFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> tabFragmentList;
    private List<String> titleList;


    public TabChildFragmentAdapter(Fragment fragment, List<Fragment> tabFragmentList, List<String> titleList) {
        super(fragment.getChildFragmentManager());
        this.tabFragmentList = tabFragmentList;
        this.titleList = titleList;
    }

    @Override
    public int getCount() {
        return titleList.size();
    }

    @Override
    public Fragment getItem(int position) {
        return tabFragmentList.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position % titleList.size());
    }
}
