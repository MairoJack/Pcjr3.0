package com.pcjinrong.pcjr.ui.views.activity;

import android.content.Intent;
import android.os.Bundle;
import com.orhanobut.logger.Logger;
import com.pcjinrong.pcjr.R;
import com.pcjinrong.pcjr.bean.IndexFocusInfo;
import com.pcjinrong.pcjr.bean.Token;
import com.pcjinrong.pcjr.constant.Constant;
import com.pcjinrong.pcjr.core.BaseAppCompatActivity;
import com.pcjinrong.pcjr.ui.presenter.SplashPresenter;
import com.pcjinrong.pcjr.ui.presenter.ivview.SplashView;
import com.pcjinrong.pcjr.utils.SPUtils;


/**
 * 启动屏
 * Created by Mario on 2016/5/24.
 */
public class SplashActivity extends BaseAppCompatActivity implements SplashView{

    private SplashPresenter presenter;
    @Override
    protected int getLayoutId() {
        return R.layout.splash;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {

    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {

    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void initData() {
        presenter = new SplashPresenter();
        presenter.attachView(this);
        presenter.getIndexFocusInfo();
        presenter.refreshToken();
    }


    @Override
    public void onGetIndexFocusSuccess(IndexFocusInfo data) {
        Constant.INDEX_FOCUS_INFO = data;
        finish();
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
    }

    @Override
    public void onRefeshTokenSuccess(Token data) {
        SPUtils.putToken(this,data);
        Constant.IS_LOGIN = true;

    }

    @Override
    public void onFailure(Throwable e) {
        Logger.e(e.getMessage());
        finish();
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
    }

    @Override
    public void onSuccess(Object data) {

    }
}
