package com.pcjinrong.pcjr.ui.views.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;
import com.pcjinrong.pcjr.R;
import com.pcjinrong.pcjr.bean.BankCard;
import com.pcjinrong.pcjr.bean.PayBean;
import com.pcjinrong.pcjr.bean.Withdraw;
import com.pcjinrong.pcjr.constant.Constant;
import com.pcjinrong.pcjr.core.BaseFragment;
import com.pcjinrong.pcjr.ui.presenter.RechargePresenter;
import com.pcjinrong.pcjr.ui.presenter.ivview.RechargeView;
import com.pcjinrong.pcjr.ui.views.activity.WebViewActivity;
import com.pcjinrong.pcjr.utils.ViewUtil;
import com.pcjinrong.pcjr.widget.Dialog;

import java.util.List;

import butterknife.BindView;


/**
 * 充值Fragment
 * Created by Mario on 2016/9/27.
 */
public class RechargeFragment extends BaseFragment implements RechargeView {

    public static final String TAG = RechargeFragment.class.getSimpleName();

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

    private boolean isPrepared;
    private boolean mHasLoadedOnce;

    @Override
    protected int getLayoutId() {
        return R.layout.member_recharge;
    }

    @Override
    protected void initViews(View self, Bundle savedInstanceState) {
        time = new TimeCount(60000, 1000);
        dialog = new ProgressDialog(getContext(), ProgressDialog.STYLE_SPINNER);
    }

    @Override
    protected void initListeners() {
        info.setOnClickListener(v -> Dialog.show("充值金额", "本平台充值免手续费（已经为您垫付），已投资金额及收益提现免费，充值未投资即提现，平台将收取之前为您垫付的0.15%手续费。", getContext()));

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
            Intent intent = new Intent(getContext(), WebViewActivity.class);
            intent.putExtra("title", Constant.SERVICE_AGREEMENT);
            intent.putExtra("url", Constant.SERVICE_AGREEMENT_URL);
            startActivity(intent);
        });
    }

    @Override
    protected void initData() {
        isPrepared = true;
        Bundle bundle = getArguments();
        Withdraw withdraw = (Withdraw) bundle.getSerializable("withdraw");

        txt_balance.setText("账户余额：" + withdraw.getAvailable_balance() + "元");
        txt_realname.setText(withdraw.getRealname());
        txt_mobile.setText(withdraw.getMobile());

        presenter = new RechargePresenter();
        presenter.attachView(this);

    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible || mHasLoadedOnce) {
            return;
        }

    }

    public void apply() {
        Logger.e("abc");
        String amount = txt_amount.getText().toString().trim();
        String verify = txt_verify.getText().toString().trim();
        if (amount.equals("")) {
            Dialog.show("请输入充值金额", getContext());
            return;
        }
        if (Double.parseDouble(amount) <= 0) {
            Dialog.show("充值金额必须大于0", getContext());
            return;
        }
        if (verify.equals("")) {
            Dialog.show("请输入验证码", getContext());
            return;
        }
        if(!agree.isChecked()){
            Dialog.show("请先阅读并同意《一键支付服务协议》", getContext());
            return;
        }
        presenter.recharge("628", order_no, verify);
    }

    public void send_verify() {
        String amount = txt_amount.getText().toString().trim();
        if (amount.equals("")) {
            Dialog.show("请输入充值金额", getContext());
            return;
        }
        if (Double.parseDouble(amount) <= 0) {
            Dialog.show("充值金额必须大于0", getContext());
            return;
        }
        btn_verify.setClickable(false);
        btn_verify.setBackgroundResource(R.drawable.btn_disable);
        time.start();
        presenter.rechargeVerify("628",bank_id,amount);
    }


    public static Fragment newInstance(Withdraw withdraw) {
        RechargeFragment fragment = new RechargeFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("withdraw", withdraw);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onRechargeVerifySuccess(PayBean data) {
        Logger.d(data);
        if(data.isSuccess()){
            order_no = data.getOrder_no();
        }else{
            Dialog.show(data.getMessage(),getContext());
        }
    }

    @Override
    public void onRechargeSuccess(PayBean data) {
        Logger.d(data);
        if(data.isSuccess()){
            showToast(data.getMessage());
            getActivity().finish();
        }else{
            Dialog.show(data.getMessage(),getContext());
        }
    }

    @Override
    public void onFailure(Throwable e) {

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
