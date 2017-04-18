package com.pcjinrong.pcjr.ui.views.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pcjinrong.pcjr.R;
import com.pcjinrong.pcjr.bean.InvestTicket;
import com.pcjinrong.pcjr.bean.PaymentRecords;
import com.pcjinrong.pcjr.core.BaseToolbarActivity;
import com.pcjinrong.pcjr.utils.DateUtils;
import com.pcjinrong.pcjr.utils.ViewUtil;

import java.util.Date;

import butterknife.BindView;


/**
 * 投资记录-详情
 * Created by Mario on 2017/4/14.
 */
public class InvestRecordsDetailActivity extends BaseToolbarActivity {
    @BindView(R.id.txt_tqhk) TextView txt_tqhk;
    @BindView(R.id.txt_title) TextView txt_title;
    @BindView(R.id.txt_repayment_type) TextView txt_repayment_type;
    @BindView(R.id.txt_status) TextView txt_status;
    @BindView(R.id.txt_invest_amount) TextView txt_invest_amount;
    @BindView(R.id.txt_year_income) TextView txt_year_income;
    @BindView(R.id.txt_interest) TextView txt_interest;
    @BindView(R.id.txt_jxq) LinearLayout txt_jxq;
    @BindView(R.id.txt_month) TextView txt_month;
    @BindView(R.id.txt_earned_sum) TextView txt_earned_sum;
    @BindView(R.id.txt_uncollected_sum) TextView txt_uncollected_sum;
    @BindView(R.id.txt_invest_date) TextView txt_invest_date;
    @BindView(R.id.txt_value_date) TextView txt_value_date;
    @BindView(R.id.txt_end_date) TextView txt_end_date;
    @BindView(R.id.but_item_detail) Button but_item_detail;
    @BindView(R.id.but_payment_records) Button but_payment_records;

    private String id;
    @Override
    protected int getLayoutId() {
        return R.layout.member_invest_records_detail;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        showBack();
        setTitle("投资详情");
    }

    @Override
    protected void initListeners() {
        but_item_detail.setOnClickListener(v->{
            if (ViewUtil.isFastDoubleClick()) return;
            Intent intent = new Intent(this, InvestDetailActivity.class);
            //intent.putExtra("id",id);
            intent.putExtra("id",239);
            startActivity(intent);
        });

        but_payment_records.setOnClickListener(v->{
            if (ViewUtil.isFastDoubleClick()) return;
            Intent intent = new Intent(this, PaymentRecordsActivity.class);
            //intent.putExtra("id",id);
            intent.putExtra("id",239);
            startActivity(intent);
        });
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        id = intent.getStringExtra("id");

        txt_tqhk.setText("* 本产品具有 提前回款 可能， 如提前回款则补偿本产品 10天利息 于投资人，利息计算方法请点击此处");
        txt_title.setText("大城小爱NO.280-2016");
        txt_repayment_type.setText("先息后本(按月付息)");
        txt_status.setText("正在回款");
        txt_invest_amount.setText("99999.00");
        txt_year_income.setText("8");
        txt_interest.setText("+0.5");
        txt_month.setText("3个月");
        txt_earned_sum.setText("0.00");
        txt_uncollected_sum.setText("500.00");
        txt_invest_date.setText("2017-04-11");
        txt_value_date.setText("2018-05-11");
        txt_end_date.setText("2019-05-11");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return false;

    }
}
