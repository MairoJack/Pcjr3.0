package com.pcjinrong.pcjr.ui.views.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.pcjinrong.pcjr.R;
import com.pcjinrong.pcjr.bean.BaseBean;
import com.pcjinrong.pcjr.core.BaseToolbarActivity;
import com.pcjinrong.pcjr.ui.presenter.BindMobilePresenter;
import com.pcjinrong.pcjr.ui.presenter.ivview.BindMobileView;
import com.pcjinrong.pcjr.utils.ViewUtil;
import com.pcjinrong.pcjr.widget.Dialog;

import butterknife.BindView;
import retrofit2.adapter.rxjava.HttpException;

/**
 * 解绑手机
 * Created by Mario on 2016/5/24.
 */
public class UnbindMobileActivity extends BaseToolbarActivity implements BindMobileView {
    @BindView(R.id.btn_save) Button btn_save;
    @BindView(R.id.btn_checkcode) Button btn_checkcode;
    @BindView(R.id.txt_old_mobile) TextView txt_old_mobile;
    @BindView(R.id.txt_checkcode) EditText txt_checkcode;

    private TimeCount time;
    private BindMobilePresenter presenter;

    @Override
    protected int getLayoutId() {
        return R.layout.member_safe_setting_unbind_mobile;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        this.showBack();
        this.setTitle("手机解绑");
        time = new TimeCount(60000, 1000);
    }

    @Override
    protected void initListeners() {
        btn_checkcode.setOnClickListener(v -> {
            if (ViewUtil.isFastDoubleClick()) return;
            btn_checkcode.setClickable(false);
            btn_checkcode.setBackgroundResource(R.drawable.btn_disable);
            time.start();
            sendCheckCode();
        });
        btn_save.setOnClickListener(v -> {
            if (ViewUtil.isFastDoubleClick()) return;
            unbind();
        });
    }


    public void initData() {
        String oldMobile = getIntent().getStringExtra("mobile");
        txt_old_mobile.setText(oldMobile);
        presenter = new BindMobilePresenter();
        presenter.attachView(this);
    }

    public void unbind() {
        String checkCode = txt_checkcode.getText().toString().trim();
        if (checkCode.equals("")) {
            Dialog.show("请输入验证码", this);
            return;
        }

        presenter.unBindMobile(checkCode);

    }

    public void sendCheckCode() {
        presenter.unBindMobileVerify();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
        }
        return false;

    }

    @Override
    public void onBindMobileVerifySuccess(BaseBean data) {

    }

    @Override
    public void onBindMobileSuccess(BaseBean data) {

    }

    @Override
    public void onUnBindMobileVerifySuccess(BaseBean data) {
        Dialog.show(data.getMessage(), this);
    }

    @Override
    public void onUnBindMobileSuccess(BaseBean data) {
        if (data.isSuccess()) {
            finish();
            startActivity(new Intent(UnbindMobileActivity.this, BindMobileActivity.class));
        } else {
            Dialog.show(data.getMessage(), this);
        }
    }

    @Override
    public void onFailure(Throwable e) {
        if(e instanceof HttpException){
            showToast(getString(R.string.login_expired));
            startActivity(new Intent(UnbindMobileActivity.this, LoginActivity.class));
            return;
        }
        showToast(R.string.network_anomaly);
    }

    @Override
    public void onSuccess(Object data) {

    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            btn_checkcode.setText("获取验证码");
            btn_checkcode.setClickable(true);
            btn_checkcode.setBackgroundResource(R.drawable.btn_primary);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            btn_checkcode.setText(millisUntilFinished / 1000 + "秒后可重新获取");
        }
    }
}
