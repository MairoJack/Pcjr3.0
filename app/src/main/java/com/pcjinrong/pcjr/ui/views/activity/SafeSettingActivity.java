package com.pcjinrong.pcjr.ui.views.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Switch;

import com.orhanobut.logger.Logger;
import com.pcjinrong.pcjr.R;
import com.pcjinrong.pcjr.bean.BaseBean;
import com.pcjinrong.pcjr.bean.IdentityInfo;
import com.pcjinrong.pcjr.bean.MobileInfo;
import com.pcjinrong.pcjr.constant.Constant;
import com.pcjinrong.pcjr.core.BaseToolbarActivity;
import com.pcjinrong.pcjr.ui.presenter.SafeSettingPresenter;
import com.pcjinrong.pcjr.ui.presenter.ivview.SafeSettingView;
import com.pcjinrong.pcjr.utils.SPUtils;
import com.pcjinrong.pcjr.utils.ViewUtil;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.adapter.rxjava.HttpException;


/**
 * 安全设置
 * Created by Mario on 2016/5/24.
 */
public class SafeSettingActivity extends BaseToolbarActivity implements SafeSettingView {
    @BindView(R.id.realname) RelativeLayout realname;
    @BindView(R.id.bindphone) RelativeLayout bindphone;
    @BindView(R.id.changepswd) RelativeLayout changepswd;
    @BindView(R.id.gesture) RelativeLayout gesture;
    @BindView(R.id.btn_switch) Switch btn_switch;
    private ProgressDialog dialog;

    private SafeSettingPresenter presenter;

    @Override
    protected int getLayoutId() {
        return R.layout.member_safe_setting;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        this.showBack();
        this.setTitle("安全设置");
        dialog = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
        if ((Boolean) SPUtils.get(this, "isOpenGesture", false)) {
            btn_switch.setChecked(true);
            gesture.setVisibility(View.VISIBLE);
        } else {
            btn_switch.setChecked(false);
            gesture.setVisibility(View.GONE);
        }
    }


    @Override
    protected void initListeners() {
        realname.setOnClickListener(v -> {
            if (ViewUtil.isFastDoubleClick()) return;
            getIdentityInfo();
        });
        bindphone.setOnClickListener(v -> {
            if (ViewUtil.isFastDoubleClick()) return;
            getMobileInfo();
        });
        changepswd.setOnClickListener(v -> {
            if (ViewUtil.isFastDoubleClick()) return;
            startActivity(new Intent(SafeSettingActivity.this, ChangePasswordActivity.class));
        });

        btn_switch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                if ((Boolean) SPUtils.get(this, "isFirstGesture", true)) {
                    startActivityForResult(new Intent(SafeSettingActivity.this, GestureEditActivity.class),Constant.REQUSET);
                } else {
                    gesture.setVisibility(View.VISIBLE);
                }
            } else {
                SPUtils.put(this, "isOpenGesture", false);
                gesture.setVisibility(View.GONE);
            }
        });

        gesture.setOnClickListener(v -> {
            if (ViewUtil.isFastDoubleClick()) return;
            startActivityForResult(new Intent(SafeSettingActivity.this, GestureEditActivity.class),Constant.REQUSET);
        });
    }

    @Override
    protected void initData() {
        this.presenter = new SafeSettingPresenter();
        this.presenter.attachView(this);
    }

    @OnClick(R.id.btn_logout)
    void logout_btn() {
        new AlertDialog.Builder(this)
                .setTitle("确认注销?")
                .setPositiveButton("确认", (dialog, which) -> {
                    logout();
                    dialog.dismiss();
                }).show();
    }


    public void getMobileInfo() {
        dialog.setMessage("正在加载...");
        dialog.show();
        this.presenter.getMobileInfo();

    }

    public void getIdentityInfo() {
        dialog.setMessage("正在加载...");
        dialog.show();
        this.presenter.getIdentityInfo();
    }

    public void logout() {
        dialog.setMessage("正在注销...");
        dialog.show();
        this.presenter.logout();
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
    public void onMobileInfoSuccess(BaseBean<MobileInfo> data) {
        dialog.dismiss();
        if (data.isSuccess()) {
            Intent intent = new Intent(SafeSettingActivity.this, UnbindMobileActivity.class);
            intent.putExtra("mobile", data.getData().getMobile());
            startActivity(intent);
        } else {
            startActivity(new Intent(SafeSettingActivity.this, BindMobileActivity.class));
        }

    }

    @Override
    public void onLogoutSuccess(BaseBean data) {
        dialog.dismiss();
        if (data.isSuccess()) {
            showToast("注销成功");
            setResult(RESULT_OK);
            Constant.IS_LOGIN = false;
            Constant.IS_GESTURE_LOGIN = false;
            SPUtils.clear(this);
            finish();
        } else {
            showToast("注销失败:" + data.getMessage());
        }
    }

    @Override
    public void onIdentityInfoSuccess(BaseBean<IdentityInfo> data) {
        dialog.dismiss();
        if(data.isSuccess()){
            Intent intent = new Intent(SafeSettingActivity.this, RealNameVerifiedActivity.class);
            intent.putExtra("realname",data.getData().getRealname());
            intent.putExtra("identity",data.getData().getIdentity());
            startActivity(intent);
        }else{
            Intent intent = new Intent(SafeSettingActivity.this, UnRealNameVerifiedActivity.class);
            startActivity(intent);
        }


    }

    @Override
    public void onFailure(Throwable e) {
        dialog.dismiss();
        if(e instanceof HttpException){
            showToast(getString(R.string.login_expired));
            startActivity(new Intent(SafeSettingActivity.this, LoginActivity.class));
            return;
        }
        showToast(R.string.network_anomaly);
    }

    @Override
    public void onSuccess(Object data) {

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUSET && resultCode == RESULT_OK) {
            btn_switch.setChecked(true);
            SPUtils.put(this, "isFirstGesture", false);
            SPUtils.put(this, "isOpenGesture", true);
            gesture.setVisibility(View.VISIBLE);
        }else if(requestCode == Constant.REQUSET && resultCode == RESULT_CANCELED){
            btn_switch.setChecked(false);
        }
    }
}
