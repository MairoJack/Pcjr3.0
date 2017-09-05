package com.pcjinrong.pcjr.ui.views.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.pcjinrong.pcjr.R;
import com.pcjinrong.pcjr.bean.BaseBean;
import com.pcjinrong.pcjr.constant.Constant;
import com.pcjinrong.pcjr.core.BaseToolbarActivity;
import com.pcjinrong.pcjr.core.mvp.MvpView;
import com.pcjinrong.pcjr.ui.presenter.RealNamePresenter;
import com.pcjinrong.pcjr.utils.ViewUtil;
import com.pcjinrong.pcjr.widget.Dialog;

import butterknife.BindView;
import retrofit2.adapter.rxjava.HttpException;


/**
 * 实名认证
 * Created by Mario on 2016/5/24.
 */
public class UnRealNameVerifiedActivity extends BaseToolbarActivity implements MvpView<BaseBean> {
    @BindView(R.id.btn_save) Button btn_save;
    @BindView(R.id.txt_realname) EditText txt_realname;
    @BindView(R.id.txt_idcard) EditText txt_idcard;

    @BindView(R.id.agree) CheckBox agree;
    @BindView(R.id.service_agreement) TextView service_agreement;

    private ProgressDialog dialog;
    private RealNamePresenter presenter;


    @Override
    protected int getLayoutId() {
        return R.layout.member_safe_setting_unrealname;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        showBack();
        setTitle("实名认证");
        dialog = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);

    }

    @Override
    protected void initListeners() {
        btn_save.setOnClickListener(v -> {
            if (ViewUtil.isFastDoubleClick()) return;
            verify();
        });

        service_agreement.setOnClickListener(v -> {
            if (ViewUtil.isFastDoubleClick()) return;
            Intent intent = new Intent(this, WebViewActivity.class);
            intent.putExtra("title", Constant.CREDIT);
            intent.putExtra("url", Constant.CREDIT_URL);
            startActivity(intent);
        });
    }

    @Override
    protected void initData() {
        presenter = new RealNamePresenter();
        presenter.attachView(this);
    }

    public void verify() {
        String realname = txt_realname.getText().toString().trim();
        String idcard = txt_idcard.getText().toString().trim();
        txt_realname.clearFocus();
        txt_idcard.clearFocus();
        if (realname.equals("")) {
            Dialog.show("真实姓名不能为空", this);
            return;
        }
        if (idcard.equals("")) {
            Dialog.show("身份证号码不能为空", this);
            return;
        }

        if(!agree.isChecked()){
            Dialog.show("请先阅读并同意《征信查询授权书》",this);
            return;
        }

        dialog.setMessage("正在提交...");
        dialog.show();
        presenter.verifyIdentity(realname, idcard);
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
        if(e instanceof HttpException && ((HttpException)e).code() == 400){
            showToast(getString(R.string.login_expired));
            startActivity(new Intent(UnRealNameVerifiedActivity.this, LoginActivity.class));
            return;
        }
        showToast(R.string.network_anomaly);
    }

    @Override
    public void onSuccess(BaseBean data) {
        dialog.dismiss();
        if (data.isSuccess()) {
            showToast("实名认证成功");
            finish();
        } else {
            if (null == data.getMessage()) {
                Dialog.show("实名认证失败,请联系客服", this);
            } else {
                Dialog.show(data.getMessage(), this);
            }
        }
    }

}
