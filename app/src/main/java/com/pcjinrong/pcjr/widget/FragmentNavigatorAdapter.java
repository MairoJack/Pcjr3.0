package com.pcjinrong.pcjr.widget;

import android.support.v4.app.Fragment;

/**
 * 导航Fragment适配器
 * Created by Mario on 16/6/1.
 */
public interface FragmentNavigatorAdapter {

    public Fragment onCreateFragment(int position);

    public String getTag(int position);

    public int getCount();

}
