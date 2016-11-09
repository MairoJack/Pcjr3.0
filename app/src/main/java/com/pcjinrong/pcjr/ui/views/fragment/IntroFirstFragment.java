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
public class IntroFirstFragment extends BaseFragment {

    public static final String TAG = IntroFirstFragment.class.getSimpleName();

    @Override
    protected int getLayoutId() {
        return R.layout.intro_first;
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
        IntroFirstFragment fragment = new IntroFirstFragment();
        return fragment;
    }
}
