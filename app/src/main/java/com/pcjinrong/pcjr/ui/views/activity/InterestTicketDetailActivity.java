package com.pcjinrong.pcjr.ui.views.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

import com.pcjinrong.pcjr.R;
import com.pcjinrong.pcjr.bean.InterestTicket;
import com.pcjinrong.pcjr.core.BaseToolbarActivity;
import com.pcjinrong.pcjr.utils.DateUtils;

import java.util.Date;

import butterknife.BindView;

/**
 * 加息券-详情
 * Created by Mario on 2016/12/5.
 */
public class InterestTicketDetailActivity extends BaseToolbarActivity {
    @BindView(R.id.msg) TextView msg;
    @BindView(R.id.mTitle) TextView mTitle;
    @BindView(R.id.end_time) TextView end_time;
    @BindView(R.id.introduction) TextView introduction;

    @Override
    protected int getLayoutId() {
        return R.layout.member_coupon_interest_ticket_detail;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        showBack();
        setTitle("加息券详情");
    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        InterestTicket interestTicket = (InterestTicket) intent.getSerializableExtra("data");

        msg.setText("单笔投资 "+interestTicket.getStart_amount()+" - "+interestTicket.getEnd_amount()+" 元");
        mTitle.setText("来源:" + interestTicket.getTitle());
        end_time.setText(DateUtils.dateTimeToStr(new Date(interestTicket.getEnd_time() * 1000), "yyyy-MM-dd HH:mm:ss"));
        introduction.setText(interestTicket.getActivity().getIntroduction().replace("</br>", ""));

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return false;

    }
}
