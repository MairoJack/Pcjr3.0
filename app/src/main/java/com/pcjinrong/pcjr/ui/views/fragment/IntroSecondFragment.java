package com.pcjinrong.pcjr.ui.views.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.pcjinrong.pcjr.R;
import com.pcjinrong.pcjr.core.BaseFragment;


/**
 * 引导页Fragment 1
 * Created by Mario on 2016/7/21.
 */
public class IntroSecondFragment extends BaseFragment {

    public static final String TAG = IntroSecondFragment.class.getSimpleName();

    @Override
    protected int getLayoutId() {
        return R.layout.intro_second;
    }

    @Override
    protected void initViews(View self, Bundle savedInstanceState) {

    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void lazyLoad() {

    }

    public static Fragment newInstance(String content) {
        IntroSecondFragment fragment = new IntroSecondFragment();
        return fragment;
    }
}
