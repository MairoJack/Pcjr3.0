package com.pcjinrong.pcjr.ui.views.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pcjinrong.pcjr.R;
import com.pcjinrong.pcjr.bean.BankCard;
import com.pcjinrong.pcjr.bean.BaseBean;
import com.pcjinrong.pcjr.bean.RechargeInfo;
import com.pcjinrong.pcjr.bean.Withdraw;
import com.pcjinrong.pcjr.constant.Constant;
import com.pcjinrong.pcjr.core.BaseToolbarActivity;
import com.pcjinrong.pcjr.ui.presenter.WithdrawPresenter;
import com.pcjinrong.pcjr.ui.presenter.ivview.WithdrawView;
import com.pcjinrong.pcjr.utils.ValidatorUtils;
import com.pcjinrong.pcjr.utils.ViewUtil;
import com.pcjinrong.pcjr.widget.Dialog;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import butterknife.BindView;
import retrofit2.adapter.rxjava.HttpException;


/**
 * 提现
 * Created by Mario on 2016/5/24.
 */
public class WithdrawActivity extends BaseToolbarActivity implements WithdrawView {
    @BindView(R.id.btn_recharge) RelativeLayout btn_recharge;

    @BindView(R.id.btn_verify) Button btn_verify;
    @BindView(R.id.btn_apply) Button btn_apply;

    @BindView(R.id.txt_mention_amount) EditText txt_mention_amount;
    @BindView(R.id.txt_verify) EditText txt_verify;

    @BindView(R.id.txt_balance) TextView txt_balance;
    @BindView(R.id.txt_realname) TextView txt_realname;
    @BindView(R.id.txt_fee) TextView txt_fee;
    @BindView(R.id.txt_mobile) TextView txt_mobile;
    @BindView(R.id.explain) TextView explain;

    @BindView(R.id.info) ImageView info;

    @BindView(R.id.txt_bank_card) TextView txt_bank_card;

    @BindView(R.id.btn_all) Button btn_all;

    private TimeCount time;
    private WithdrawPresenter presenter;
    private ProgressDialog dialog;

    private String bank_id;
    private BigDecimal free_withdraw;
    private BigDecimal available_balance;

    private String amount;

    @Override
    protected int getLayoutId() {
        return R.layout.member_withdraw;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        showBack();
        setTitle("提现/充值");
        time = new TimeCount(60000, 1000);
        dialog = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
    }

    @Override
    protected void initListeners() {

        btn_recharge.setOnClickListener(v -> {
            if (ViewUtil.isFastDoubleClick()) return;
            presenter.rechargeInfo();
        });

        info.setOnClickListener(v -> Dialog.show("提现金额", "已赚取利息与到期本金之和即为您可免费提现的总额，充值未投资金额则需收取0.15%手续费", this));

        explain.setOnClickListener(v -> {
            if (ViewUtil.isFastDoubleClick()) return;
            Intent intent = new Intent(WithdrawActivity.this, WebViewActivity.class);
            intent.putExtra("title", Constant.WITHDRAW_RULES);
            intent.putExtra("url", Constant.WITHDRAW_RULES_URL);
            startActivity(intent);
        });

        txt_mention_amount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null && !s.toString().equals("") && ValidatorUtils.isCorrectTwoDecimalNumber(s.toString())) {
                    BigDecimal amount = new BigDecimal(s.toString());
                    if(amount.compareTo(available_balance) > 0){
                        txt_mention_amount.setText(String.valueOf(available_balance));
                        amount = available_balance;
                    }
                    if (amount.compareTo(free_withdraw) > 0 ) {
                        BigDecimal fee = (amount.subtract(free_withdraw)).multiply(new BigDecimal("0.15")).divide(new BigDecimal("100"),2,RoundingMode.HALF_UP);
                        if(fee.compareTo(BigDecimal.ZERO) <= 0){
                            txt_fee.setText("0.01");
                        }else {
                            txt_fee.setText(String.valueOf(fee));
                        }
                    }else{
                        txt_fee.setText("0.00");
                    }
                }else{
                    txt_fee.setText("0.00");
                }
            }
        });

        btn_verify.setOnClickListener(v -> {
            if (ViewUtil.isFastDoubleClick()) return;
            send_verify();
        });

        btn_apply.setOnClickListener(v -> {
            if (ViewUtil.isFastDoubleClick()) return;
            apply();
        });

        btn_all.setOnClickListener(v -> {
            if (ViewUtil.isFastDoubleClick()) return;
            //可用 <= 免费可提
            if(available_balance.compareTo(free_withdraw) <= 0){
                txt_mention_amount.setText(String.valueOf(available_balance));
                txt_fee.setText("0.00");
                return;
            }
            BigDecimal no_free = available_balance.subtract(free_withdraw);
            if(no_free.compareTo(BigDecimal.ZERO) > 0
                    && no_free.compareTo(new BigDecimal("10")) <= 0){
                if(available_balance.subtract(new BigDecimal("0.01")).compareTo(BigDecimal.ZERO) <= 0){
                    Dialog.show("余额不足，无法提现",this);
                } else if(available_balance.subtract(free_withdraw).compareTo(new BigDecimal("0.01")) == 0){
                    txt_mention_amount.setText(String.valueOf(free_withdraw));
                    txt_fee.setText("0.00");
                } else {
                    txt_mention_amount.setText(String.valueOf(available_balance.subtract(new BigDecimal("0.01"))));
                    txt_fee.setText("0.01");
                }
                return;
            }
            BigDecimal fee = new BigDecimal("0.15").multiply(free_withdraw);
            BigDecimal amount = (new BigDecimal("100").multiply(available_balance).add(fee))
                    .divide(new BigDecimal("100.15"),2,RoundingMode.HALF_UP);

            BigDecimal fee_result = available_balance.subtract(amount);
            BigDecimal act_result = (amount.subtract(free_withdraw)).multiply(new BigDecimal("0.0015")).setScale(2,RoundingMode.HALF_UP);

            if(fee_result.compareTo(act_result) < 0){
                amount = amount.subtract(new BigDecimal("0.01")).setScale(2,RoundingMode.HALF_UP);
            }
            txt_mention_amount.setText(String.valueOf(amount));
            txt_fee.setText(String.valueOf(fee_result));
        });
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        Withdraw withdraw = (Withdraw) intent.getSerializableExtra("data");

        available_balance = new BigDecimal(withdraw.getAvailable_balance());
        free_withdraw = new BigDecimal(withdraw.getFree_withdraw());

        txt_balance.setText(withdraw.getAvailable_balance());
        txt_mention_amount.setHint("免费可提" + withdraw.getFree_withdraw() + "元");
        txt_realname.setText(withdraw.getRealname());
        txt_mobile.setText(withdraw.getMobile());

        presenter = new WithdrawPresenter();
        presenter.attachView(this);

        presenter.getBankCardList();
    }

    public void apply() {
        String confirm_amount = txt_mention_amount.getText().toString().trim();
        String verify = txt_verify.getText().toString().trim();
        if (confirm_amount.equals("")) {
            Dialog.show("请输入提现金额", this);
            return;
        }

        if(!ValidatorUtils.isCorrectTwoDecimalNumber(confirm_amount)){
            Dialog.show("请正确输入提现金额", this);
            return;
        }

        if (Double.parseDouble(confirm_amount) <= 0) {
            Dialog.show("提现金额必须大于0", this);
            return;
        }
        if(bank_id == null){
            Dialog.show("未绑定银行卡", WithdrawActivity.this);
            return;
        }
        if (verify.equals("")) {
            Dialog.show("请输入验证码", this);
            return;
        }

        if(amount!=null && !amount.equals("") && Double.parseDouble(confirm_amount)!=(Double.parseDouble(amount))){
            Dialog.show("提现金额应与获取验证码时的提现金额相同，请正确输入提现金额或者重新获取验证码", this);
            return;
        }

        presenter.withdraw(confirm_amount, bank_id, verify);
    }

    public void send_verify() {
        amount = txt_mention_amount.getText().toString().trim();
        if (amount.equals("")) {
            Dialog.show("请输入提现金额", WithdrawActivity.this);
            return;
        }

        if(!ValidatorUtils.isCorrectTwoDecimalNumber(amount)){
            Dialog.show("请正确输入提现金额", this);
            return;
        }

        if (Double.parseDouble(amount) <= 0) {
            Dialog.show("提现金额必须大于0", WithdrawActivity.this);
            return;
        }
        if(bank_id == null){
            Dialog.show("未绑定银行卡", WithdrawActivity.this);
            return;
        }
        btn_verify.setClickable(false);
        btn_verify.setBackgroundResource(R.drawable.btn_disable);
        time.start();
        presenter.withdrawVerify();
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
    public void onFailure(Throwable e) {
        if(e instanceof HttpException && ((HttpException)e).code() == 400){
            showToast(getString(R.string.login_expired));
            startActivity(new Intent(WithdrawActivity.this, LoginActivity.class));
            return;
        }
        showToast(R.string.network_anomaly);
    }

    @Override
    public void onSuccess(Object data) {

    }

    @Override
    public void onWithdrawVerifySuccess(BaseBean data) {
        Dialog.show(data.getMessage(), this);
    }

    @Override
    public void onWithdrawSuccess(BaseBean data) {
        if (data.isSuccess()) {
            showToast(data.getMessage());
            finish();
        } else {
            Dialog.show(data.getMessage(), this);
        }
    }

    @Override
    public void onBankCardListSuccess(List<BankCard> list) {
        if(list != null && list.size() > 0) {
            BankCard bankCard = list.get(0);
            txt_bank_card.setText(bankCard.getBank() + " " + bankCard.getCard_top()+" **** **** "+bankCard.getCard_last());
            bank_id = bankCard.getId();
        }
    }

    @Override
    public void onRechargeInfoSuccess(BaseBean<RechargeInfo> data) {
        if(data.isSuccess()) {
            if(data.getData().getBank_info() == null){
                Dialog.show("未绑定银行卡", this);
                return;
            }
            if(data.getData().isFinish_assessment()==0){
                Intent intent = new Intent(WithdrawActivity.this, WebViewActivity.class);
                intent.putExtra("title", Constant.RISK_ASSESS);
                intent.putExtra("url", Constant.RISK_ASSESS_URL);
                intent.putExtra("assessType",1);
                startActivity(intent);
                return;
            }
            Intent intent = new Intent(WithdrawActivity.this, RechargeActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("data", data.getData());
            intent.putExtras(bundle);
            startActivity(intent);
        }else{
            Dialog.show(data.getMessage(), this);
        }
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
