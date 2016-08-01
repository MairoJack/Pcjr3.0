package com.pcjinrong.pcjr.ui.views.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.pcjinrong.pcjr.R;
import com.pcjinrong.pcjr.bean.Token;
import com.pcjinrong.pcjr.constant.Constant;
import com.pcjinrong.pcjr.core.BaseAppCompatActivity;
import com.pcjinrong.pcjr.core.mvp.MvpView;
import com.pcjinrong.pcjr.ui.presenter.LoginPresenter;
import com.pcjinrong.pcjr.utils.ViewUtil;
import com.pcjinrong.pcjr.widget.Dialog;
import butterknife.BindView;

/**
 * 登陆
 * Created by Mario on 2016/6/7.
 */
public class LoginActivity extends BaseAppCompatActivity implements View.OnClickListener,MvpView<Token> {

    public static final String TAG = LoginActivity.class.getSimpleName();
    @BindView(R.id.reg) TextView reg;
    @BindView(R.id.forget) TextView forget;
    @BindView(R.id.index) TextView index;
    @BindView(R.id.username) EditText text_username;
    @BindView(R.id.password) EditText text_password;
    @BindView(R.id.loginbtn) Button but_login;

    private LoginPresenter presenter;
    private ProgressDialog dialog;


    @Override
    protected int getLayoutId() {
        return R.layout.login;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        dialog = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {

    }

    @Override
    protected void initListeners() {
        reg.setOnClickListener(this);
        forget.setOnClickListener(this);
        index.setOnClickListener(this);
        but_login.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        presenter = new LoginPresenter();
        presenter.attachView(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.index:
                setResult(2);
                finish();
                break;

            case R.id.reg:
                if(ViewUtil.isFastDoubleClick()){
                    return;
                }
               /* intent = new Intent(LoginActivity.this, RegistActivity.class);
                startActivity(intent);*/
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
            case R.id.forget:
                if(ViewUtil.isFastDoubleClick()){
                    return;
                }
                intent = new Intent(LoginActivity.this, WebViewActivity.class);
                intent.putExtra("url", Constant.FORGET_PASSWORD_URL);
                intent.putExtra("title", Constant.FORGET_PASSWORD);
                startActivity(intent);
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
                break;
            case R.id.loginbtn:
                login();
                break;
        }
    }

    public void login() {
        final String username = text_username.getText().toString().trim();
        final String password = text_password.getText().toString().trim();
        if (username.equals("")) {
            Dialog.show("用户名不能为空",this);
            return;
        }
        if (password.equals("")) {
            Dialog.show("密码不能为空",this);
            return;
        }
        dialog.setMessage("正在登录...");
        dialog.show();
        presenter.login(username,password);
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
        Dialog.show("用户名或密码错误",this);
    }

    @Override
    public void onSuccess(Token data) {
        dialog.dismiss();
        Dialog.show("登陆成功",this);
        finish();
    }
}
