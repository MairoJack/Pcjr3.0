package com.pcjinrong.pcjr.ui.views.activity;

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
import com.pcjinrong.pcjr.utils.ValidatorUtils;
import com.pcjinrong.pcjr.utils.ViewUtil;
import com.pcjinrong.pcjr.widget.Dialog;

import butterknife.BindView;
import retrofit2.adapter.rxjava.HttpException;


/**
 * 绑定手机
 * Created by Mario on 2016/5/24.
 */
public class BindMobileActivity extends BaseToolbarActivity implements BindMobileView {
    @BindView(R.id.btn_save) Button btn_save;
    @BindView(R.id.btn_checkcode) Button btn_checkcode;
    @BindView(R.id.txt_checkcode) EditText txt_checkcode;
    @BindView(R.id.txt_mobile) TextView txt_mobile;

    private TimeCount time;
    private BindMobilePresenter presenter;


    @Override
    protected int getLayoutId() {
        return R.layout.member_safe_setting_bind_mobile;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        showBack();
        setTitle("手机绑定");
        time = new TimeCount(60000, 1000);
    }

    @Override
    protected void initListeners() {
        btn_checkcode.setOnClickListener(v -> {
            if (ViewUtil.isFastDoubleClick()) return;
            sendCheckCode();
        });

        btn_save.setOnClickListener(v -> {
            if (ViewUtil.isFastDoubleClick()) return;
            bind();
        });
    }

    @Override
    protected void initData() {
        presenter = new BindMobilePresenter();
        presenter.attachView(this);
    }

    public void bind() {
        String mobile = txt_mobile.getText().toString().trim();
        String checkCode = txt_checkcode.getText().toString().trim();
        if (mobile.equals("")) {
            Dialog.show("请输入手机号", this);
            return;
        }
        if (checkCode.equals("")) {
            Dialog.show("请输入验证码", this);
            return;
        }

        presenter.bindMobile(mobile, checkCode);
    }

    public void sendCheckCode() {
        String mobile = txt_mobile.getText().toString().trim();
        if (mobile.equals("")) {
            Dialog.show("请输入手机号", this);
            return;
        }
        if (!ValidatorUtils.isMobile(mobile)) {
            Dialog.show("手机号格式错误", this);
            return;
        }
        btn_checkcode.setClickable(false);
        btn_checkcode.setBackgroundResource(R.drawable.btn_disable);
        time.start();
        presenter.bindMobileVerify(mobile);
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
        Dialog.show(data.getMessage(), this);
    }

    @Override
    public void onBindMobileSuccess(BaseBean data) {
        if (data.isSuccess()) {
            showToast(data.getMessage());
            finish();
        } else {
            Dialog.show(data.getMessage(), this);
        }
    }

    @Override
    public void onUnBindMobileVerifySuccess(BaseBean data) {

    }

    @Override
    public void onUnBindMobileSuccess(BaseBean data) {

    }

    @Override
    public void onFailure(Throwable e) {
        if(e instanceof HttpException && ((HttpException)e).code() == 400){
            showToast(getString(R.string.login_expired));
            startActivity(new Intent(BindMobileActivity.this, LoginActivity.class));
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
            btn_checkcode.setText("发送手机验证码");
            btn_checkcode.setClickable(true);
            btn_checkcode.setBackgroundResource(R.drawable.btn_primary);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            btn_checkcode.setText(millisUntilFinished / 1000 + "秒后可重新获取");
        }
    }
}
