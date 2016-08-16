package com.pcjinrong.pcjr.ui.views.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.pcjinrong.pcjr.R;
import com.pcjinrong.pcjr.bean.BaseBean;
import com.pcjinrong.pcjr.bean.Product;
import com.pcjinrong.pcjr.bean.Withdraw;
import com.pcjinrong.pcjr.constant.Constant;
import com.pcjinrong.pcjr.core.BaseToolbarActivity;
import com.pcjinrong.pcjr.core.mvp.MvpView;
import com.pcjinrong.pcjr.ui.presenter.InvestPresenter;
import com.pcjinrong.pcjr.utils.DateUtils;
import com.pcjinrong.pcjr.utils.ViewUtil;
import com.pcjinrong.pcjr.widget.Dialog;

import java.math.BigDecimal;
import java.util.Date;

import butterknife.BindView;
import retrofit2.adapter.rxjava.HttpException;


/**
 * 投资
 * Created by Mario on 2016/5/24.
 */
public class InvestActivity extends BaseToolbarActivity implements MvpView<BaseBean> {

    @BindView(R.id.btn_invest) Button btn_invest;
    @BindView(R.id.btn_allin) Button btn_allin;

    @BindView(R.id.txt_invest_amount) EditText txt_invest_amount;

    @BindView(R.id.txt_preview_repayment) TextView txt_preview_repayment;
    @BindView(R.id.txt_threshold_amount) TextView txt_threshold_amount;
    @BindView(R.id.txt_balance) TextView txt_balance;
    @BindView(R.id.txt_repayment) TextView txt_repayment;
    @BindView(R.id.txt_year_income) TextView txt_year_income;
    @BindView(R.id.txt_repayment_date) TextView txt_repayment_date;
    @BindView(R.id.txt_month) TextView txt_month;

    @BindView(R.id.preview_repayment)
    LinearLayout preview_repayment;

    private InvestPresenter presenter;
    private ProgressDialog dialog;

    private BigDecimal available_balance;
    private Product product;

    @Override
    protected int getLayoutId() {
        return R.layout.invest;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        showBack();
        dialog = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
    }

    @Override
    protected void initListeners() {


        btn_invest.setOnClickListener(v -> {
            if (ViewUtil.isFastDoubleClick()) return;
            BigDecimal can_invest_amount = new BigDecimal(product.getAmount()).subtract(new BigDecimal(product.getProduct_amount()));
            final String amount = txt_invest_amount.getText().toString().trim();
            if (amount.equals("")) {
                Dialog.show("请输入投资金额",this);
                return;
            }
            BigDecimal bd_amount = new BigDecimal(amount);
            BigDecimal bd_threshold_amount = new BigDecimal(product.getThreshold_amount());
            BigDecimal bd_increasing_amount = new BigDecimal(product.getIncreasing_amount());
            if (bd_amount.compareTo(can_invest_amount) > 0) {
                Dialog.show("金额超过可投最大值", this);
                return;
            }
            if (bd_amount.compareTo(bd_threshold_amount)< 0) {
                Dialog.show("金额不能小于起投金额", this);
                return;
            }
            if (bd_amount.compareTo(available_balance) > 0) {
                Dialog.show("可用余额不足", this);
                return;
            }
            if(bd_increasing_amount.compareTo(BigDecimal.ZERO) != 0) {
                if ((bd_amount.remainder(bd_increasing_amount).compareTo(BigDecimal.ZERO)!=0)) {
                    Dialog.show("金额没有按递增金额填写", this);
                    return;
                }
            }
            new MaterialDialog.Builder(InvestActivity.this)
                    .title("输入密码")
                    .content("投资金额：" + amount + "元")
                    .positiveColor(Color.parseColor("#FF6602"))
                    .inputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)
                    .input("请输入密码", "", (dialog,input)-> {
                        if (input.toString().equals("")) {
                            Dialog.show("请输入密码", InvestActivity.this);
                        } else {
                            invest(input.toString(), amount);
                        }
                    }).show();
        });

        btn_allin.setOnClickListener(v -> {
            if (ViewUtil.isFastDoubleClick()) return;
            int amount;
            BigDecimal can_invest_amount = new BigDecimal(product.getAmount()).subtract(new BigDecimal(product.getProduct_amount()));
            BigDecimal bd_threshold_amount = new BigDecimal(product.getThreshold_amount());
            BigDecimal bd_increasing_amount = new BigDecimal(product.getIncreasing_amount());
            if (available_balance.compareTo(bd_threshold_amount) < 0) {
                Dialog.show("可用余额不足", InvestActivity.this);
                return;
            }
            if (can_invest_amount.compareTo(available_balance) > 0) {
                if(bd_increasing_amount.compareTo(BigDecimal.ZERO) == 0){
                    amount = available_balance.intValue();
                }else if(available_balance.remainder(bd_increasing_amount).compareTo(BigDecimal.ZERO) == 0){
                    amount = available_balance.intValue();
                }else{
                    amount = available_balance.subtract(available_balance.remainder(bd_increasing_amount)).intValue();
                }
            } else {
                if(bd_increasing_amount.compareTo(BigDecimal.ZERO) == 0){
                    amount = can_invest_amount.intValue();
                }else if(can_invest_amount.remainder(bd_increasing_amount).compareTo(BigDecimal.ZERO) == 0){
                    amount = can_invest_amount.intValue();
                }else{
                    amount = can_invest_amount.subtract(can_invest_amount.remainder(bd_increasing_amount)).intValue();
                }

            }
            if(amount <= 0){
                Dialog.show("可用余额不足", InvestActivity.this);
                return;
            }
            new MaterialDialog.Builder(InvestActivity.this)
                    .title("输入密码")
                    .content("投资金额：" + amount + "元")
                    .positiveColor(Color.parseColor("#FF6602"))
                    .inputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)
                    .input("请输入密码", "", (dialog,input)-> {
                        if (input.toString().equals("")) {
                            Dialog.show("请输入密码", InvestActivity.this);
                        } else {
                            invest(input.toString(), String.valueOf(amount));
                        }
                    }).show();
        });
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        Withdraw withdraw = (Withdraw) intent.getSerializableExtra("data");
        product = (Product) intent.getSerializableExtra("product");

        setTitle(product.getName());
        txt_balance.setText(withdraw.getAvailable_balance() + "元");
        available_balance = new BigDecimal(withdraw.getAvailable_balance());
        if (product.getIs_preview_repayment() == 1) {
            String html_preview_repayment = "* 本产品具有 <font color='#dc4d07'>提前回款</font> 可能，平台确保此产品最短借款时长为 <font color='#dc4d07'>" + product.getMin_repayment_date() + "</font> ，如提前回款则补偿本产品 <font color='#dc4d07'>" + product.getPay_interest_day() + "天利息</font> 于投资人";
            preview_repayment.setVisibility(View.VISIBLE);
            txt_preview_repayment.setText(Html.fromHtml(html_preview_repayment));
        }
        txt_invest_amount.setHint("可投" + String.format("%.2f", (new BigDecimal(product.getAmount()).subtract(new BigDecimal(product.getProduct_amount())))) + "元");
        txt_threshold_amount.setText("起投/递增金额:" + product.getThreshold_amount() + "元/" + product.getIncreasing_amount() + " 元");
        int repayment = product.getRepayment();
        switch (repayment) {
            case 0:txt_repayment.setText("一次还本付息");break;
            case 1:txt_repayment.setText("先息后本(按月付息)");break;
            case 2:txt_repayment.setText("等额本息");break;
            case 3:txt_repayment.setText("先息后本(按季付息)");break;
        }
        txt_month.setText(product.getMonth());
        txt_year_income.setText(product.getYear_income() + "%");
        txt_repayment_date.setText(DateUtils.dateTimeToStr(new Date(product.getRepayment_date() * 1000),"yyyy-MM-dd"));

        presenter = new InvestPresenter();
        presenter.attachView(this);

    }

    public void invest(String password, String amount) {
        dialog.setMessage("正在提交...");
        dialog.show();
        presenter.investProduct(amount,product.getId(),password);
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
        if(e instanceof HttpException){
            showToast(getString(R.string.login_expired));
            startActivity(new Intent(InvestActivity.this, LoginActivity.class));
            return;
        }
        showToast(R.string.network_anomaly);
    }

    @Override
    public void onSuccess(BaseBean data) {
        if(dialog.isShowing()) dialog.dismiss();
        if (data.isSuccess()) {
            showToast(data.getMessage());
            finish();
            startActivity(new Intent(InvestActivity.this, InvestRecordsActivity.class));
        } else {
            Dialog.show(data.getMessage(), this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tips, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.ask) {
            Dialog.show("投资提示", "若您选择全投，且您的可用余额大于该产品剩余可投金额，投资金额将自动填写为该产品剩余可投金额，有一定概率投资失败，请谨慎操作", this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
