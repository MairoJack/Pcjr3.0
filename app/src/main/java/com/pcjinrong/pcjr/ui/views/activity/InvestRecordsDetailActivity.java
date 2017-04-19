package com.pcjinrong.pcjr.ui.views.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pcjinrong.pcjr.R;
import com.pcjinrong.pcjr.bean.InvestProductDetail;
import com.pcjinrong.pcjr.constant.Constant;
import com.pcjinrong.pcjr.core.BaseToolbarActivity;
import com.pcjinrong.pcjr.utils.DateUtils;
import com.pcjinrong.pcjr.utils.ViewUtil;

import java.math.BigDecimal;
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
    private String product_id;
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
            intent.putExtra("id",product_id);
            startActivity(intent);
        });

        but_payment_records.setOnClickListener(v->{
            if (ViewUtil.isFastDoubleClick()) return;
            Intent intent = new Intent(this, PaymentRecordsActivity.class);
            intent.putExtra("id",id);
            startActivity(intent);
        });
    }

    @Override
    protected void initData() {
        Intent  intent = getIntent();
        id = intent.getStringExtra("id");
        product_id = intent.getStringExtra("product_id");
        InvestProductDetail object = (InvestProductDetail) intent.getSerializableExtra("data");

        int repayment = object.getRepayment();

        txt_title.setText(object.getName());
        txt_invest_amount.setText(object.getAmount());
        txt_year_income.setText(object.getYear_income());
        txt_month.setText(object.getMonth());
        txt_earned_sum.setText(object.getActual_amount());
        txt_uncollected_sum.setText(object.getEstimated_amount());
        txt_invest_date.setText(DateUtils.dateTimeToStr(new Date(object.getJoin_date() * 1000), "yyyy-MM-dd"));
        txt_value_date.setText(DateUtils.dateTimeToStr(new Date(object.getValue_date() * 1000), "yyyy-MM-dd"));
        txt_end_date.setText(DateUtils.dateTimeToStr(new Date(object.getDeadline() * 1000), "yyyy-MM-dd"));

        if (object.getIs_preview_repayment() == 1) {
            String html_preview_repayment;
            if (object.getFinish_preview_repayment() == 1) {
                html_preview_repayment = "* 此为 <font color='#dc4d07'>提前回款</font> 产品，原借款时长为 <font color='#dc4d07'>" + object.getMonth() + "</font> ，现提前至 <font color='#dc4d07'>" + DateUtils.dateTimeToStr(new Date(object.getPreview_repayment_date() * 1000),"yyyy-MM-dd") + "</font> ，故补偿<font color='#dc4d07'>" + object.getPay_interest_day() + "</font>天利息 于投资人,利息计算方法请 点击此处";
            } else {
                String tqhk = "";
                html_preview_repayment = "* 本产品具有 <font color='#dc4d07'>提前回款</font> 可能，" + tqhk + "如提前回款则补偿本产品 <font color='#dc4d07'>" + object.getPay_interest_day() + "天利息</font> 于投资人,利息计算方法请 点击此处";
            }
            txt_tqhk.setVisibility(View.VISIBLE);
            txt_tqhk.setText(Html.fromHtml(html_preview_repayment));
            txt_tqhk.setOnClickListener(v -> {
                Intent tqhk_intent = new Intent(this, WebViewActivity.class);
                tqhk_intent.putExtra("title", Constant.PLATFORM_ANNOUNCEMENT);
                tqhk_intent.putExtra("url", Constant.PREPAYMENT_URL);
                startActivity(tqhk_intent);
            });
        }

        switch (repayment) {
            case 0:txt_repayment_type.setText("一次还本付息");break;
            case 1:txt_repayment_type.setText("先息后本(月)");break;
            case 2:txt_repayment_type.setText("等额本息");break;
            case 3:txt_repayment_type.setText("先息后本(季)");break;
        }
        if(object.getStatus() == 1){
            txt_status.setText("正在回款");
        }else{
            txt_status.setText("未回款");
        }

        if(new BigDecimal(object.getInterest_year_income()).compareTo(BigDecimal.ZERO)>0){
            txt_interest.setText("+"+object.getInterest_year_income());
            txt_jxq.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return false;

    }
}
