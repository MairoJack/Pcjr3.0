package com.pcjinrong.pcjr.ui.views.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;

import com.pcjinrong.pcjr.R;
import com.pcjinrong.pcjr.core.BaseFragment;
import com.pcjinrong.pcjr.ui.views.activity.IntroActivity;
import com.pcjinrong.pcjr.ui.views.activity.MainActivity;
import com.pcjinrong.pcjr.ui.views.activity.SplashActivity;

import butterknife.BindView;


/**
 * 引导页Fragment 1
 * Created by Mario on 2016/7/21.
 */
public class IntroThirdFragment extends BaseFragment {

    public static final String TAG = IntroThirdFragment.class.getSimpleName();

    @BindView(R.id.btn_open) Button btn_open;
    @Override
    protected int getLayoutId() {
        return R.layout.intro_third;
    }

    @Override
    protected void initViews(View self, Bundle savedInstanceState) {

    }

    @Override
    protected void initListeners() {
        btn_open.setOnClickListener(v->{
            getActivity().finish();
            startActivity(new Intent(getActivity(), MainActivity.class));
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void lazyLoad() {

    }

    public static Fragment newInstance(String content) {
        IntroThirdFragment fragment = new IntroThirdFragment();
        return fragment;
    }
}
