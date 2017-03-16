package com.pcjinrong.pcjr.ui.views.fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.pcjinrong.pcjr.R;
import com.pcjinrong.pcjr.bean.BankCard;
import com.pcjinrong.pcjr.bean.BaseBean;
import com.pcjinrong.pcjr.bean.Withdraw;
import com.pcjinrong.pcjr.constant.Constant;
import com.pcjinrong.pcjr.core.BaseFragment;
import com.pcjinrong.pcjr.ui.presenter.WithdrawPresenter;
import com.pcjinrong.pcjr.ui.presenter.ivview.WithdrawView;
import com.pcjinrong.pcjr.ui.views.activity.LoginActivity;
import com.pcjinrong.pcjr.ui.views.activity.WebViewActivity;
import com.pcjinrong.pcjr.utils.ViewUtil;
import com.pcjinrong.pcjr.widget.Dialog;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import butterknife.BindView;
import retrofit2.adapter.rxjava.HttpException;

/**
 * 提现Fragment
 * Created by Mario on 2016/9/27.
 */
public class WithdrawFragment extends BaseFragment implements WithdrawView {

    public static final String TAG = WithdrawFragment.class.getSimpleName();

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

    private TimeCount time;
    private WithdrawPresenter presenter;
    private ProgressDialog dialog;

    private List<BankCard> bankCards;
    private String bank_id;
    private BigDecimal free_withdraw;
    private BigDecimal available_balance;

    @Override
    protected int getLayoutId() {
        return R.layout.member_withdraw;
    }

    @Override
    protected void initViews(View self, Bundle savedInstanceState) {
        time = new TimeCount(60000, 1000);
        dialog = new ProgressDialog(getContext(), ProgressDialog.STYLE_SPINNER);
    }

    @Override
    protected void initListeners() {

        info.setOnClickListener(v -> Dialog.show("提现金额", "已赚取利息与到期本金之和即为您可免费提现的总额，充值未投资金额则需收取0.15%手续费", getContext()));

        explain.setOnClickListener(v -> {
            if (ViewUtil.isFastDoubleClick()) return;
            Intent intent = new Intent(getContext(), WebViewActivity.class);
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
                if (s != null && !s.toString().equals("")) {
                    double amount = Double.parseDouble(s.toString());
                    if(amount > available_balance.doubleValue()){
                        txt_mention_amount.setText(String.valueOf(available_balance));
                        amount = available_balance.doubleValue();
                    }
                    if (amount > free_withdraw.doubleValue()) {
                        double fee = (amount - free_withdraw.doubleValue()) * 0.15 / 100;
                        BigDecimal b = new BigDecimal(String.valueOf(fee));
                        double f = b.setScale(2, RoundingMode.HALF_UP).doubleValue();
                        if(f<=0){
                            txt_fee.setText("0.01");
                        }else {
                            txt_fee.setText(String.valueOf(f));
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
    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        Withdraw withdraw = (Withdraw) bundle.getSerializable("withdraw");

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
        String amount = txt_mention_amount.getText().toString().trim();
        String verify = txt_verify.getText().toString().trim();
        if (amount.equals("")) {
            Dialog.show("请输入提现金额", getContext());
            return;
        }
        if (Double.parseDouble(amount) <= 0) {
            Dialog.show("提现金额必须大于0", getContext());
            return;
        }
        if (verify.equals("")) {
            Dialog.show("请输入验证码", getContext());
            return;
        }

        presenter.withdraw(amount, bank_id, verify);
    }

    public void send_verify() {
        String amount = txt_mention_amount.getText().toString().trim();
        if (amount.equals("")) {
            Dialog.show("请输入提现金额", getContext());
            return;
        }
        if (Double.parseDouble(amount) <= 0) {
            Dialog.show("提现金额必须大于0", getContext());
            return;
        }
        btn_verify.setClickable(false);
        btn_verify.setBackgroundResource(R.drawable.btn_disable);
        time.start();
        presenter.withdrawVerify();
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onFailure(Throwable e) {
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

    @Override
    public void onWithdrawVerifySuccess(BaseBean data) {
        Dialog.show(data.getMessage(), getContext());
    }

    @Override
    public void onWithdrawSuccess(BaseBean data) {
        if (data.isSuccess()) {
            showToast(data.getMessage());
            getActivity().finish();
        } else {
            Dialog.show(data.getMessage(), getContext());
        }
    }

    @Override
    public void onBankCardListSuccess(List<BankCard> list) {
        if(list != null && list.size() > 0) {
            BankCard bankCard = list.get(0);
            txt_bank_card.setText(bankCard.getBank() + " " + bankCard.getCard_no());
            bank_id = bankCard.getId();
        }
    }

    @Override
    public void onRechargeInfoSuccess(BaseBean data) {

    }

    public static Fragment newInstance(Withdraw withdraw) {
        WithdrawFragment fragment = new WithdrawFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("withdraw", withdraw);
        fragment.setArguments(bundle);
        return fragment;
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
