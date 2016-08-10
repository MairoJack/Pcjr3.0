package com.pcjinrong.pcjr.ui.views.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

import com.pcjinrong.pcjr.R;
import com.pcjinrong.pcjr.bean.InvestTicket;
import com.pcjinrong.pcjr.core.BaseToolbarActivity;
import com.pcjinrong.pcjr.utils.DateUtils;

import java.util.Date;

import butterknife.BindView;


/**
 * 投资券-详情
 * Created by Mario on 2016/5/24.
 */
public class InvestTicketDetailActivity extends BaseToolbarActivity {
    @BindView(R.id.msg) TextView msg;
    @BindView(R.id.title) TextView title;
    @BindView(R.id.end_time) TextView end_time;
    @BindView(R.id.introduction) TextView introduction;

    @Override
    protected int getLayoutId() {
        return R.layout.member_coupon_invest_ticket_detail;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        showBack();
        setTitle("投资券详情");
    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        InvestTicket investTicket = (InvestTicket) intent.getSerializableExtra("data");

        msg.setText("满 " + investTicket.getReach_amount() + " 元返 " + investTicket.getAmount() + " 元");
        title.setText("来源:" + investTicket.getTitle());
        end_time.setText(DateUtils.dateTimeToStr(new Date(investTicket.getEnd_time() * 1000), "yyyy-MM-dd HH:mm:ss"));
        introduction.setText(investTicket.getActivity().getIntroduction().replace("</br>", ""));

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return false;

    }
}
