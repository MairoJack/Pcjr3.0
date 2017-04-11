package com.pcjinrong.pcjr.ui.views.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.pcjinrong.pcjr.R;
import com.pcjinrong.pcjr.bean.BankCard;
import com.pcjinrong.pcjr.bean.BankInfo;
import com.pcjinrong.pcjr.bean.PayBean;
import com.pcjinrong.pcjr.bean.RechargeDifficult;
import com.pcjinrong.pcjr.bean.RechargeInfo;
import com.pcjinrong.pcjr.bean.Withdraw;
import com.pcjinrong.pcjr.constant.Constant;
import com.pcjinrong.pcjr.core.BaseFragment;
import com.pcjinrong.pcjr.core.BaseToolbarActivity;
import com.pcjinrong.pcjr.ui.presenter.RechargePresenter;
import com.pcjinrong.pcjr.ui.presenter.ivview.RechargeView;
import com.pcjinrong.pcjr.utils.BankUtils;
import com.pcjinrong.pcjr.utils.ViewUtil;
import com.pcjinrong.pcjr.widget.Dialog;
import com.pcjinrong.pcjr.widget.RBDialog;

import java.util.List;

import butterknife.BindView;
import retrofit2.adapter.rxjava.HttpException;


/**
 * 充值Activity
 * Created by Mario on 2016/9/27.
 */
public class RechargeActivity extends BaseToolbarActivity implements RechargeView {

    public static final String TAG = RechargeActivity.class.getSimpleName();

    @BindView(R.id.btn_withdraw) RelativeLayout btn_withdraw;

    @BindView(R.id.btn_verify) Button btn_verify;
    @BindView(R.id.btn_apply) Button btn_apply;

    @BindView(R.id.txt_amount) EditText txt_amount;
    @BindView(R.id.txt_verify) EditText txt_verify;

    @BindView(R.id.txt_balance) TextView txt_balance;
    @BindView(R.id.txt_realname) TextView txt_realname;
    @BindView(R.id.txt_mobile) TextView txt_mobile;
    @BindView(R.id.txt_bank_card) TextView txt_bank_card;

    @BindView(R.id.info) ImageView info;

    @BindView(R.id.agree) CheckBox agree;
    @BindView(R.id.service_agreement) TextView service_agreement;

    private TimeCount time;
    private RechargePresenter presenter;
    private ProgressDialog dialog;
    private String bank_id;
    private String order_no;
    private String member_id;

    @Override
    protected int getLayoutId() {
        return R.layout.member_recharge;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        showBack();
        setTitle("提现/充值");
        time = new RechargeActivity.TimeCount(60000, 1000);
        dialog = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
    }


    @Override
    protected void initListeners() {
        info.setOnClickListener(v -> Dialog.show("充值金额", "本平台充值免手续费（已经为您垫付），已投资金额及收益提现免费，充值未投资即提现，平台将收取之前为您垫付的0.15%手续费。", this));

        btn_verify.setOnClickListener(v -> {
            if (ViewUtil.isFastDoubleClick()) return;
            send_verify();
        });

        btn_apply.setOnClickListener(v -> {
            if (ViewUtil.isFastDoubleClick()) return;
            apply();
        });

        service_agreement.setOnClickListener(v -> {
            if (ViewUtil.isFastDoubleClick()) return;
            Intent intent = new Intent(this, WebViewActivity.class);
            intent.putExtra("title", Constant.SERVICE_AGREEMENT);
            intent.putExtra("url", Constant.SERVICE_AGREEMENT_URL);
            startActivity(intent);
        });

        btn_withdraw.setOnClickListener(v -> {
            if (ViewUtil.isFastDoubleClick()) return;
            finish();
        });
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        RechargeInfo data = (RechargeInfo) intent.getSerializableExtra("data");

        txt_balance.setText("账户余额：" + data.getAvailable_balance() + "元");
        txt_realname.setText(data.getRealname());
        BankInfo bankInfo = data.getBank_info();
        bank_id = bankInfo.getId();
        member_id = data.getMember_id();
        txt_mobile.setText(bankInfo.getPhone());
        txt_bank_card.setText(BankUtils.getBankNameById(bankInfo.getBank())+" "+bankInfo.getCard_top()+" **** **** "+bankInfo.getCard_last());
        presenter = new RechargePresenter();
        presenter.attachView(this);
        presenter.difficult();
    }


    public void apply() {

        String amount = txt_amount.getText().toString().trim();
        String verify = txt_verify.getText().toString().trim();
        if (amount.equals("")) {
            Dialog.show("请输入充值金额", this);
            return;
        }
        if (Double.parseDouble(amount) <= 0) {
            Dialog.show("充值金额必须大于0", this);
            return;
        }
        if (verify.equals("")) {
            Dialog.show("请输入验证码", this);
            return;
        }
        if(!agree.isChecked()){
            Dialog.show("请先阅读并同意《一键支付服务协议》", this);
            return;
        }

        presenter.recharge(member_id, order_no, verify);
    }

    public void send_verify() {
        String amount = txt_amount.getText().toString().trim();
        if (amount.equals("")) {
            Dialog.show("请输入充值金额", this);
            return;
        }
        if (Double.parseDouble(amount) <= 0) {
            Dialog.show("充值金额必须大于0", this);
            return;
        }
        btn_verify.setClickable(false);
        btn_verify.setBackgroundResource(R.drawable.btn_disable);
        time.start();
        presenter.rechargeVerify(member_id,bank_id,amount);
    }


    @Override
    public void onRechargeVerifySuccess(PayBean data) {
        Logger.d(data);
        if(data.isSuccess()){
            order_no = data.getOrder_no();
        }else{
            Dialog.show(data.getMessage(),this);
        }
    }

    @Override
    public void onRechargeSuccess(PayBean data) {
        Logger.d(data);
        if(data.isSuccess()){
            showToast(data.getMessage());
            finish();
        }else{
            Dialog.show(data.getMessage(),this);
        }
    }

    @Override
    public void onDifficultSuccess(RechargeDifficult data) {
        if(data.getIsShow() != 0) {
            RBDialog dialog = new RBDialog(this, R.style.RatingBarDialog, data);
            dialog.show();
        }
    }

    @Override
    public void onFailure(Throwable e) {
        if(e instanceof HttpException && ((HttpException)e).code() == 400){
            showToast(getString(R.string.login_expired));
            startActivity(new Intent(RechargeActivity.this, LoginActivity.class));
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
            btn_verify.setText("获取验证码");
            btn_verify.setClickable(true);
            btn_verify.setBackgroundResource(R.drawable.btn_primary);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            btn_verify.setText(millisUntilFinished / 1000 + "秒后可重新获取");
        }
    }
}
