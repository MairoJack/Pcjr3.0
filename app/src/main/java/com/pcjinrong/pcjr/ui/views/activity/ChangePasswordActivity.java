package com.pcjinrong.pcjr.ui.views.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;

import com.pcjinrong.pcjr.R;
import com.pcjinrong.pcjr.bean.BaseBean;
import com.pcjinrong.pcjr.core.BaseToolbarActivity;
import com.pcjinrong.pcjr.core.mvp.MvpView;
import com.pcjinrong.pcjr.ui.presenter.ChangPasswordPresenter;
import com.pcjinrong.pcjr.utils.ViewUtil;
import com.pcjinrong.pcjr.widget.Dialog;

import butterknife.BindView;
import retrofit2.adapter.rxjava.HttpException;


/**
 * 修改密码
 * Created by Mario on 2016/5/24.
 */
public class ChangePasswordActivity extends BaseToolbarActivity implements MvpView<BaseBean> {
    @BindView(R.id.btn_save) Button btn_save;
    @BindView(R.id.txt_old_password) EditText txt_old_password;
    @BindView(R.id.txt_password) EditText txt_password;
    @BindView(R.id.txt_confirm_password) EditText txt_confirm_password;

    private ProgressDialog dialog;
    private ChangPasswordPresenter presenter;


    @Override
    protected int getLayoutId() {
        return R.layout.member_safe_setting_change_password;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        showBack();
        setTitle("修改密码");
        dialog = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);

    }

    @Override
    protected void initListeners() {
        btn_save.setOnClickListener(v -> {
            if (ViewUtil.isFastDoubleClick()) return;
            change();
        });
    }

    @Override
    protected void initData() {
        presenter = new ChangPasswordPresenter();
        presenter.attachView(this);
    }

    public void change() {
        String oldPassword = txt_old_password.getText().toString().trim();
        String newPassword = txt_password.getText().toString().trim();
        String confirm_password = txt_confirm_password.getText().toString().trim();
        if (oldPassword.equals("")) {
            Dialog.show("原密码不能为空", this);
            return;
        }
        if (newPassword.equals("")) {
            Dialog.show("新密码不能为空", this);
            return;
        }
        if (confirm_password.equals("")) {
            Dialog.show("确认密码不能为空", this);
            return;
        }
        if (!newPassword.equals(confirm_password)) {
            Dialog.show("两次密码不相同", this);
            return;
        }
        dialog.setMessage("正在提交...");
        dialog.show();
        presenter.changePassword(oldPassword, newPassword);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return false;

    }

    @Override
    public void onFailure(Throwable e) {
        dialog.dismiss();
        if(e instanceof HttpException){
            showToast(getString(R.string.login_expired));
            startActivity(new Intent(ChangePasswordActivity.this, LoginActivity.class));
            return;
        }
        showToast(R.string.network_anomaly);
    }

    @Override
    public void onSuccess(BaseBean data) {
        dialog.dismiss();
        if (data.isSuccess()) {
            Dialog.show(data.getMessage(), this);
            finish();
        } else {
            Dialog.show(data.getMessage(), this);
        }
    }

}
