package com.pcjinrong.pcjr.ui.views.fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.pcjinrong.pcjr.ui.views.activity.WithdrawActivity;
import com.pcjinrong.pcjr.utils.Bank;
import com.pcjinrong.pcjr.utils.BankUtils;
import com.pcjinrong.pcjr.utils.DateUtils;
import com.pcjinrong.pcjr.utils.ValidatorUtils;
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
    @BindView(R.id.btn_all) TextView btn_all;

    @BindView(R.id.txt_mention_amount) EditText txt_mention_amount;
    @BindView(R.id.txt_verify) EditText txt_verify;

    @BindView(R.id.txt_balance_prefix) TextView txt_balance_prefix;
    @BindView(R.id.txt_balance_suffix) TextView txt_balance_suffix;

    @BindView(R.id.txt_realname) TextView txt_realname;
    @BindView(R.id.txt_fee) TextView txt_fee;
    @BindView(R.id.explain) TextView explain;

    @BindView(R.id.txt_free) TextView txt_free;
    @BindView(R.id.txt_end) TextView txt_end;

    @BindView(R.id.info) ImageView info;

    @BindView(R.id.bank) TextView txt_bank_card;
    @BindView(R.id.img_bank) ImageView img_bank;

    private TimeCount time;
    private WithdrawPresenter presenter;
    private ProgressDialog dialog;

    private List<BankCard> bankCards;
    private String bank_id;
    private BigDecimal free_withdraw;
    private BigDecimal available_balance;

    private boolean isPrepared;
    private boolean mHasLoadedOnce;
    private String amount;

    private BottomSheetDialog bottomSheetDialog;
    private TextView dialog_txt_amount;
    private TextView dialog_txt_fee;
    private TextView dialog_txt_date;
    private TextView dialog_txt_actual_amount;
    private Button dialog_btn_apply;
    private String fee_amount;
    @Override
    protected int getLayoutId() {

        return R.layout.member_withdraw_new;
    }

    @Override
    protected void initViews(View self, Bundle savedInstanceState) {
        time = new TimeCount(60000, 1000);
        dialog = new ProgressDialog(getContext(), ProgressDialog.STYLE_SPINNER);
        bottomSheetDialog = new BottomSheetDialog(getContext());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.withdraw_confirm, findView(R.id.dialog));
        ImageView btn_close = (ImageView) view.findViewById(R.id.btn_close);
        dialog_txt_amount = (TextView) view.findViewById(R.id.txt_amount);
        dialog_txt_fee = (TextView) view.findViewById(R.id.txt_fee);
        dialog_txt_date = (TextView) view.findViewById(R.id.txt_date);
        dialog_txt_actual_amount = (TextView) view.findViewById(R.id.txt_actual_amount);
        dialog_btn_apply = (Button) view.findViewById(R.id.btn_apply);

        btn_close.setOnClickListener(v-> bottomSheetDialog.dismiss());


        bottomSheetDialog.setContentView(view);
        setBehaviorCallback();
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
                fee_amount = "0.00";
                if (s != null && !s.toString().equals("") && ValidatorUtils.isCorrectTwoDecimalNumber(s.toString())) {
                    BigDecimal amount = new BigDecimal(s.toString());
                    if(amount.compareTo(available_balance) > 0){
                        txt_mention_amount.setText(String.valueOf(available_balance));
                        amount = available_balance;
                    }
                    if (amount.compareTo(free_withdraw) > 0 ) {
                        BigDecimal fee = (amount.subtract(free_withdraw)).multiply(new BigDecimal("0.15")).divide(new BigDecimal("100"),2,RoundingMode.HALF_UP);
                        if(fee.compareTo(BigDecimal.ZERO) <= 0){
                            fee_amount = "0.01";
                        }else {
                            fee_amount = String.valueOf(fee);
                        }
                    }
                }
                txt_fee.setText("提现费用："+fee_amount+"元");
            }
        });

        btn_verify.setOnClickListener(v -> {
            if (ViewUtil.isFastDoubleClick()) return;
            send_verify();
        });

        btn_apply.setOnClickListener(v -> {
            if (ViewUtil.isFastDoubleClick()) return;

            String confirm_amount = txt_mention_amount.getText().toString().trim();
            String verify = txt_verify.getText().toString().trim();
            if (confirm_amount.equals("")) {
                Dialog.show("请输入提现金额", getContext());
                return;
            }

            if(!ValidatorUtils.isCorrectTwoDecimalNumber(confirm_amount)){
                Dialog.show("请正确输入提现金额", getContext());
                return;
            }

            if (Double.parseDouble(confirm_amount) <= 0) {
                Dialog.show("提现金额必须大于0", getContext());
                return;
            }
            if(bank_id == null){
                Dialog.show("未绑定银行卡", getContext());
                return;
            }
            if (verify.equals("")) {
                Dialog.show("请输入验证码", getContext());
                return;
            }

            if(amount!=null && !amount.equals("") && Double.parseDouble(confirm_amount)!=(Double.parseDouble(amount))){
                Dialog.show("提现金额应与获取验证码时的提现金额相同，请正确输入提现金额或者重新获取验证码", getContext());
                return;
            }

            buildDialog(confirm_amount,fee_amount,verify);

        });

        btn_all.setOnClickListener(v -> {
            if (ViewUtil.isFastDoubleClick()) return;
            //可用 <= 免费可提
            if(available_balance.compareTo(free_withdraw) <= 0){
                txt_mention_amount.setText(String.valueOf(available_balance));
                txt_fee.setText("提现费用：0.00元");
                return;
            }
            BigDecimal no_free = available_balance.subtract(free_withdraw);
            if(no_free.compareTo(BigDecimal.ZERO) > 0
                    && no_free.compareTo(new BigDecimal("10")) <= 0){
                if(available_balance.subtract(new BigDecimal("0.01")).compareTo(BigDecimal.ZERO) <= 0){
                    Dialog.show("余额不足，无法提现",getContext());
                } else if(available_balance.subtract(free_withdraw).compareTo(new BigDecimal("0.01")) == 0){
                    txt_mention_amount.setText(String.valueOf(free_withdraw));
                    txt_fee.setText("提现费用：0.00元");
                } else {
                    txt_mention_amount.setText(String.valueOf(available_balance.subtract(new BigDecimal("0.01"))));
                    txt_fee.setText("提现费用：0.01元");
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
            txt_fee.setText("提现费用："+String.valueOf(fee_result)+"元");
        });
    }

    @Override
    protected void initData() {

        presenter = new WithdrawPresenter();
        presenter.attachView(this);

        isPrepared = true;
        lazyLoad();

    }

    public void apply(String amount,String verify) {

        presenter.withdraw(amount, bank_id, verify);
    }

    public void send_verify() {
        amount = txt_mention_amount.getText().toString().trim();
        if (amount.equals("")) {
            Dialog.show("请输入提现金额", getContext());
            return;
        }

        if(!ValidatorUtils.isCorrectTwoDecimalNumber(amount)){
            Dialog.show("请正确输入提现金额", getContext());
            return;
        }

        if (Double.parseDouble(amount) <= 0) {
            Dialog.show("提现金额必须大于0", getContext());
            return;
        }
        if(bank_id == null){
            Dialog.show("未绑定银行卡", getContext());
            return;
        }
        btn_verify.setClickable(false);
        btn_verify.setBackgroundResource(R.drawable.btn_disable);
        time.start();
        presenter.withdrawVerify();
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
            presenter.getWithdrawInvestInfo();
            presenter.getBankCardList();
        }, 1000);
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

    @Override
    public void onWithdrawVerifySuccess(BaseBean data) {
        Dialog.show(data.getMessage(), getContext());
        btn_apply.setEnabled(true);
        btn_apply.setBackgroundResource(R.drawable.btn_primary);
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
            txt_end.setText("尾号"+bankCard.getCard_last());
            bank_id = bankCard.getId();
            Bank bank = BankUtils.getBankById(Integer.parseInt(bankCard.getBank_code()));
            txt_bank_card.setText(bankCard.getBank());
            img_bank.setImageResource(bank.getIcon());
        }
    }

    @Override
    public void onWithdrawInfoSuccess(BaseBean<Withdraw> data) {
        mHasLoadedOnce = true;
        if(data.isSuccess()){
            Withdraw withdraw = data.getData();

            String balance = withdraw.getAvailable_balance();
            String[] array = balance.split("\\.");

            available_balance = new BigDecimal(balance);
            free_withdraw = new BigDecimal(withdraw.getFree_withdraw());

            txt_balance_prefix.setText(array[0]+".");
            txt_balance_suffix.setText(array[1]+"元");

            txt_free.setHint("免费可提" + withdraw.getFree_withdraw() + "元");
            txt_realname.setText(withdraw.getRealname());
            txt_verify.setHint("手机号"+withdraw.getMobile());
        }else{
            Dialog.show(data.getMessage(),getContext());
        }
        if(dialog.isShowing())dialog.dismiss();
    }

    @Override
    public void onRechargeInfoSuccess(BaseBean data) {

    }

    public static Fragment newInstance() {
        return new WithdrawFragment();
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

    private void setBehaviorCallback() {
        View view = bottomSheetDialog.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet);
        final BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(view);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    bottomSheetDialog.dismiss();
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });
    }

    private void buildDialog(String amount,String fee_amount,String verify){
        dialog_txt_amount.setText(amount);
        dialog_txt_fee.setText(fee_amount);
        dialog_txt_date.setText(DateUtils.getNDayAfterCurrentDate(1,1));
        dialog_txt_actual_amount.setText(String.valueOf(new BigDecimal(amount).subtract(new BigDecimal(fee_amount))));
        dialog_btn_apply.setOnClickListener(v->{
            apply(amount,verify);
        });
        bottomSheetDialog.show();
    }
}
