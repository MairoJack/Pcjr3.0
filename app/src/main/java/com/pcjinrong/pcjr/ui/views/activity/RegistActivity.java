package com.pcjinrong.pcjr.ui.views.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.pcjinrong.pcjr.R;
import com.pcjinrong.pcjr.bean.BaseBean;
import com.pcjinrong.pcjr.constant.Constant;
import com.pcjinrong.pcjr.core.BaseAppCompatActivity;
import com.pcjinrong.pcjr.core.BaseToolbarActivity;
import com.pcjinrong.pcjr.core.mvp.MvpView;
import com.pcjinrong.pcjr.ui.presenter.RegistPresenter;
import com.pcjinrong.pcjr.utils.ValidatorUtils;
import com.pcjinrong.pcjr.utils.ViewUtil;
import com.pcjinrong.pcjr.widget.Dialog;

import butterknife.BindView;

/**
 * 注册
 * Created by Mario on 2016/6/7.
 */
public class RegistActivity extends BaseToolbarActivity implements View.OnClickListener,MvpView<BaseBean> {

    @BindView(R.id.syxy) TextView syxy;
    @BindView(R.id.ystk) TextView ystk;
    @BindView(R.id.username) EditText text_username;
    @BindView(R.id.password) EditText text_password;
    @BindView(R.id.text_confirm_password) EditText text_confirm_password;
    @BindView(R.id.txt_recommend) EditText txt_recommend;
    @BindView(R.id.registbtn) Button but_regist;

    private RegistPresenter presenter;
    private ProgressDialog dialog;


    @Override
    protected int getLayoutId() {
        return R.layout.register;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setTitle("注册");
        dialog = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
    }

    @Override
    protected void initListeners() {
        syxy.setOnClickListener(this);
        ystk.setOnClickListener(this);
        but_regist.setOnClickListener(this);
    }

    @Override
    protected void initData() {
        presenter = new RegistPresenter();
        presenter.attachView(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.syxy:
                if(ViewUtil.isFastDoubleClick()) return;
                intent = new Intent(RegistActivity.this, WebViewActivity.class);
                intent.putExtra("title", Constant.USE_AGREEMENT);
                intent.putExtra("url", Constant.USE_AGREEMENT_URL);
                startActivity(intent);
                break;
            case R.id.ystk:
                if(ViewUtil.isFastDoubleClick()) return;
                intent = new Intent(RegistActivity.this, WebViewActivity.class);
                intent.putExtra("title", Constant.PRIVACY_POLICY);
                intent.putExtra("url", Constant.PRIVACY_POLICY_URL);
                startActivity(intent);
                break;
            case R.id.registbtn:
                if(ViewUtil.isFastDoubleClick()) return;
                regist();
                break;
        }
    }

    public void regist() {
        String username = text_username.getText().toString().trim();
        String password = text_password.getText().toString().trim();
        String confirm_password = text_confirm_password.getText().toString().trim();
        String recommend = txt_recommend.getText().toString().trim();
        if (username.equals("")) {
            Dialog.show("用户名不能为空",this);
            return;
        }
        if (password.equals("")) {
            Dialog.show("密码不能为空",this);
            return;
        }
        if (confirm_password.equals("")) {
            Dialog.show("确认密码不能为空",this);
            return;
        }
        if (!password.equals(confirm_password)) {
            Dialog.show("两次密码不相同",this);
            return;
        }
        if(!recommend.equals("") && !ValidatorUtils.isMobile(recommend)){
            Dialog.show("推荐人手机号错误",this);
            return;
        }
        dialog.setMessage("正在注册...");
        dialog.show();
        presenter.register(username,password,recommend);
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
        showToast(R.string.network_anomaly);
    }

    @Override
    public void onSuccess(BaseBean data) {
        dialog.dismiss();
        if(data.isSuccess()){
            showToast("注册成功");
            finish();
        }else{
            Dialog.show(data.getMessage(),this);
        }

    }
}
