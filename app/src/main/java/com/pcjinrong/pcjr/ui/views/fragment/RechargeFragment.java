package com.pcjinrong.pcjr.ui.views.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.pcjinrong.pcjr.R;
import com.pcjinrong.pcjr.bean.BankInfo;
import com.pcjinrong.pcjr.bean.BaseBean;
import com.pcjinrong.pcjr.bean.PayBean;
import com.pcjinrong.pcjr.bean.RechargeDifficult;
import com.pcjinrong.pcjr.bean.RechargeInfo;
import com.pcjinrong.pcjr.constant.Constant;
import com.pcjinrong.pcjr.core.BaseFragment;
import com.pcjinrong.pcjr.ui.presenter.RechargePresenter;
import com.pcjinrong.pcjr.ui.presenter.ivview.RechargeView;
import com.pcjinrong.pcjr.ui.views.activity.LoginActivity;
import com.pcjinrong.pcjr.ui.views.activity.WebViewActivity;
import com.pcjinrong.pcjr.ui.views.activity.WithdrawRechargeActivity;
import com.pcjinrong.pcjr.utils.Bank;
import com.pcjinrong.pcjr.utils.BankUtils;
import com.pcjinrong.pcjr.utils.ValidatorUtils;
import com.pcjinrong.pcjr.utils.ViewUtil;
import com.pcjinrong.pcjr.widget.Dialog;
import com.pcjinrong.pcjr.widget.RBDialog;

import butterknife.BindView;
import retrofit2.adapter.rxjava.HttpException;


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
    @BindView(R.id.txt_end) TextView txt_end;

    @BindView(R.id.txt_balance_prefix) TextView txt_balance_prefix;
    @BindView(R.id.txt_balance_suffix) TextView txt_balance_suffix;

    @BindView(R.id.txt_realname) TextView txt_realname;
    @BindView(R.id.bank) TextView txt_bank_card;
    @BindView(R.id.img_bank) ImageView img_bank;
    @BindView(R.id.txt_limit) TextView txt_limit;

    @BindView(R.id.info) ImageView info;

    @BindView(R.id.agree) CheckBox agree;
    @BindView(R.id.service_agreement) TextView service_agreement;

    private TimeCount time;
    private RechargePresenter presenter;
    private ProgressDialog dialog;
    private String bank_id;
    private String order_no;
    private String member_id;
    private String amount;

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
        presenter = new RechargePresenter();
        presenter.attachView(this);
        isPrepared = true;
        lazyLoad();
    }

    @Override
    protected void lazyLoad() {
        if (!isPrepared || !isVisible || mHasLoadedOnce) {
            return;
        }

        dialog.setMessage("正在加载...");
        dialog.show();

        Handler mHandler = new Handler();
        mHandler.postDelayed(()->{
            presenter.getRechargeInfo();
        }, 500);

    }

    public void apply() {
        String confirm_amount = txt_amount.getText().toString().trim();
        String verify = txt_verify.getText().toString().trim();
        if (confirm_amount.equals("")) {
            Dialog.show("请输入充值金额", getContext());
            return;
        }

        if(!ValidatorUtils.isCorrectTwoDecimalNumber(confirm_amount)){
            Dialog.show("请正确输入充值金额", getContext());
            return;
        }

        if (Double.parseDouble(confirm_amount) <= 0) {
            Dialog.show("充值金额必须大于0", getContext());
            return;
        }
        if (verify.equals("")) {
            Dialog.show("请输入验证码", getContext());
            return;
        }
        if(amount!=null && !amount.equals("") && Double.parseDouble(confirm_amount)!=(Double.parseDouble(amount))){
            Dialog.show("充值金额应与获取验证码时的充值金额相同，请正确输入充值金额或者重新获取验证码", getContext());
            return;
        }
        if(!agree.isChecked()){
            Dialog.show("请先阅读并同意《一键支付服务协议》", getContext());
            return;
        }

        presenter.recharge(member_id, order_no, verify);
    }

    public void send_verify() {
        amount = txt_amount.getText().toString().trim();

        if (amount.equals("")) {
            Dialog.show("请输入充值金额", getContext());
            return;
        }

        if(!ValidatorUtils.isCorrectTwoDecimalNumber(amount)){
            Dialog.show("请正确输入充值金额", getContext());
            return;
        }

        if (Double.parseDouble(amount) <= 0) {
            Dialog.show("充值金额必须大于0", getContext());
            return;
        }
        btn_verify.setClickable(false);
        btn_verify.setBackgroundResource(R.drawable.btn_disable);
        time.start();
        presenter.rechargeVerify(member_id,bank_id,amount);
    }


    public static Fragment newInstance() {
        return  new RechargeFragment();
    }

    @Override
    public void onRechargeVerifySuccess(PayBean data) {
        if(data.isSuccess()){
            Dialog.show(data.getMessage(), getContext());
            order_no = data.getOrder_no();
            btn_apply.setEnabled(true);
            btn_apply.setBackgroundResource(R.drawable.btn_primary);
        }else{
            Dialog.show(data.getMessage(),getContext());
        }
    }

    @Override
    public void onRechargeSuccess(PayBean data) {
        if(data.isSuccess()){
            showToast(data.getMessage());
            getActivity().finish();
        }else{
            Dialog.show(data.getMessage(),getContext());
        }
    }

    @Override
    public void onDifficultSuccess(RechargeDifficult data) {
        if(data.getIsShow() != 0) {
            RBDialog dialog = new RBDialog(getContext(), R.style.RatingBarDialog, data);
            dialog.show();
        }
    }

    @Override
    public void onRechargeInfoSuccess(BaseBean<RechargeInfo> data) {
        if(dialog.isShowing())dialog.dismiss();
        if(data.isSuccess()) {
            RechargeInfo rechargeInfo = data.getData();

            if(rechargeInfo.isFinish_assessment()==0){
                Intent intent = new Intent(getContext(), WebViewActivity.class);
                intent.putExtra("title", Constant.RISK_ASSESS);
                intent.putExtra("url", Constant.RISK_ASSESS_URL);
                intent.putExtra("assessType",1);
                startActivity(intent);
                new Handler().postDelayed(()->((WithdrawRechargeActivity)getActivity()).select(),500);
                return;
            }

            String balance = rechargeInfo.getAvailable_balance();
            String[] array = balance.split("\\.");

            txt_balance_prefix.setText(array[0]+".");
            txt_balance_suffix.setText(array[1]+"元");

            mHasLoadedOnce = true;
            if(rechargeInfo.getBank_info() == null){
                setBackData("未绑定银行卡");
                return;
            }
            presenter.difficult();

            txt_realname.setText(rechargeInfo.getRealname());
            BankInfo bankInfo = rechargeInfo.getBank_info();
            bank_id = bankInfo.getId();
            member_id = rechargeInfo.getMember_id();
            txt_verify.setHint("预留手机号"+bankInfo.getPhone());

            Bank bank = BankUtils.getBankById(bankInfo.getBank());
            txt_bank_card.setText(bank.getName());
            txt_end.setText("尾号"+bankInfo.getCard_last());
            img_bank.setImageResource(bank.getIcon());
            txt_limit.setText(bank.getInfo());

        }else{
            Dialog.show(data.getMessage(), getContext());
        }
    }

    @Override
    public void onFailure(Throwable e) {
        if(dialog.isShowing())dialog.dismiss();
        if(e instanceof HttpException && ((HttpException)e).code() == 400){
            showToast(getString(R.string.login_expired));
            startActivity(new Intent(getContext(), LoginActivity.class));
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
            btn_verify.setText("发送验证码");
            btn_verify.setClickable(true);
            btn_verify.setBackgroundResource(R.drawable.btn_primary);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            btn_verify.setText(millisUntilFinished / 1000 + "秒后重发");
        }
    }

}
