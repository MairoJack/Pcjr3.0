package com.pcjinrong.pcjr.ui.views.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.pcjinrong.pcjr.R;
import com.pcjinrong.pcjr.bean.PayBean;
import com.pcjinrong.pcjr.constant.Constant;
import com.pcjinrong.pcjr.core.BaseToolbarActivity;
import com.pcjinrong.pcjr.ui.presenter.AddBankCardPresenter;
import com.pcjinrong.pcjr.ui.presenter.ivview.AddBankCardView;
import com.pcjinrong.pcjr.utils.ValidatorUtils;
import com.pcjinrong.pcjr.utils.ViewUtil;
import com.pcjinrong.pcjr.widget.Dialog;

import butterknife.BindView;
import retrofit2.adapter.rxjava.HttpException;


/**
 * 添加银行卡
 * Created by Mario on 2016/5/24.
 */
public class AddBankCardActivity extends BaseToolbarActivity implements AddBankCardView {
    @BindView(R.id.img_info) ImageView img_info;
    @BindView(R.id.btn_save) Button btn_save;
    @BindView(R.id.txt_card_no) EditText txt_card_no;
    @BindView(R.id.txt_verify) EditText txt_verify;
    @BindView(R.id.txt_cardholder) TextView txt_cardholder;
    @BindView(R.id.txt_mobile) TextView txt_mobile;
    @BindView(R.id.btn_verify) Button btn_verify;
    @BindView(R.id.bksm) LinearLayout bksm;

    private TimeCount time;
    private ProgressDialog dialog;
    private AddBankCardPresenter presenter;

    private String requestId;

    @Override
    protected int getLayoutId() {
        return R.layout.member_add_bank_card;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        showBack();
        setTitle("添加银行卡");
        dialog = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
        time = new TimeCount(60000, 1000);
    }

    @Override
    protected void initListeners() {

        img_info.setOnClickListener(v -> Dialog.show("持卡人说明", "为了你的账户资金安全，只能绑定持卡人的银行卡", this));

        bksm.setOnClickListener(v -> {
            if (ViewUtil.isFastDoubleClick()) return;
            Intent intent = new Intent(AddBankCardActivity.this, WebViewActivity.class);
            intent.putExtra("title", Constant.USE_AGREEMENT);
            intent.putExtra("url", Constant.USE_AGREEMENT_URL);
            startActivity(intent);
        });

        btn_verify.setOnClickListener(v -> {
            if (ViewUtil.isFastDoubleClick()) return;
            sendCheckCode();
        });

        btn_save.setOnClickListener(v -> {
            if (ViewUtil.isFastDoubleClick()) return;
            save();
        });
    }

    @Override
    protected void initData() {
        txt_cardholder.setText(Constant.REALNAME);
        presenter = new AddBankCardPresenter();
        presenter.attachView(this);
    }

    public void sendCheckCode() {
        String mobile = txt_mobile.getText().toString().trim();
        String cardNo = txt_card_no.getText().toString().trim();
        if (cardNo.equals("")) {
            Dialog.show("请输入银行卡号", this);
            return;
        }
        if (mobile.equals("")) {
            Dialog.show("请输入手机号", this);
            return;
        }
        if (!ValidatorUtils.isMobile(mobile)) {
            Dialog.show("手机号格式错误", this);
            return;
        }
        btn_verify.setClickable(false);
        btn_verify.setBackgroundResource(R.drawable.btn_disable);
        time.start();
        presenter.bindCard("628",cardNo,mobile);
    }

    public void save() {
        String mobile = txt_mobile.getText().toString().trim();
        String card_no = txt_card_no.getText().toString().trim();
        String checkCode = txt_verify.getText().toString().trim();
        if (card_no.equals("")) {
            Dialog.show("银行卡号不能为空", this);
            return;
        }
        if (mobile.equals("")) {
            Dialog.show("请输入手机号", this);
            return;
        }
        if (!ValidatorUtils.isMobile(mobile)) {
            Dialog.show("手机号格式错误", this);
            return;
        }
        if (checkCode.equals("")) {
            Dialog.show("请输入验证码", this);
            return;
        }
        dialog.setMessage("正在提交...");
        dialog.show();
        presenter.addBankCard(requestId, card_no);
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
            startActivity(new Intent(AddBankCardActivity.this, LoginActivity.class));
            return;
        }
        showToast(R.string.network_anomaly);
    }

    @Override
    public void onSuccess(Object data) {

    }


    @Override
    public void onBindCardSuccess(PayBean data) {
        if(data.isSuccess()) {
            Logger.i(data.toString());
            requestId = data.getRequestid();
        }else{
            Dialog.show(data.getMessage(), this);
        }
    }

    @Override
    public void onAddBankCardSuccess(PayBean data) {
        dialog.dismiss();
        if (data.isSuccess()) {
            showToast(data.getMessage());
            setResult(RESULT_OK);
            finish();
        } else {
            Dialog.show(data.getMessage(), this);
        }
    }

    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            btn_verify.setText("发送手机验证码");
            btn_verify.setClickable(true);
            btn_verify.setBackgroundResource(R.drawable.btn_primary);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            btn_verify.setText(millisUntilFinished / 1000 + "秒后可重新获取");
        }
    }
}
