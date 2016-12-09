package com.pcjinrong.pcjr.ui.views.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pcjinrong.pcjr.R;
import com.pcjinrong.pcjr.bean.Coupon;
import com.pcjinrong.pcjr.core.BaseToolbarActivity;
import com.pcjinrong.pcjr.utils.ViewUtil;

import butterknife.BindView;

/**
 * 优惠券
 * Created by Mario on 2016/5/24.
 */
public class CouponActivity extends BaseToolbarActivity {
    @BindView(R.id.interest_ticket) RelativeLayout interest_ticket;
    @BindView(R.id.invest_certificate) RelativeLayout invest_certificate;
    @BindView(R.id.red_packet) RelativeLayout red_packet;

    @BindView(R.id.interest_ticket_number) TextView interest_ticket_number;
    @BindView(R.id.invest_ticket_number) TextView invest_ticket_number;
    @BindView(R.id.red_packet_number) TextView red_packet_number;


    @Override
    protected int getLayoutId() {
        return R.layout.member_coupon;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        showBack();
        setTitle("优惠券");
    }

    @Override
    protected void initListeners() {
        invest_certificate.setOnClickListener(v -> {
            if (ViewUtil.isFastDoubleClick()) return;
            startActivity(new Intent(CouponActivity.this, InvestTicketActivity.class));
        });

        interest_ticket.setOnClickListener(v -> {
            if (ViewUtil.isFastDoubleClick()) return;
            startActivity(new Intent(CouponActivity.this, InterestTicketActivity.class));
        });

        red_packet.setOnClickListener(v -> {
            if (ViewUtil.isFastDoubleClick()) return;
            startActivity(new Intent(CouponActivity.this, RedPacketActivity.class));
        });
    }


    public void initData() {
        Intent intent = getIntent();
        Coupon coupon = (Coupon) intent.getSerializableExtra("data");
        int investTicketNum = coupon.getInvest_ticket_num();
        int interestTicketNum = coupon.getInterest_ticket_num();
        int redPacketNum = coupon.getRed_packet_num();
        if (investTicketNum != 0) {
            invest_ticket_number.setVisibility(View.VISIBLE);
            invest_ticket_number.setText(String.valueOf(investTicketNum));
        } else {
            invest_ticket_number.setVisibility(View.GONE);
        }
        if (interestTicketNum != 0) {
            interest_ticket_number.setVisibility(View.VISIBLE);
            interest_ticket_number.setText(String.valueOf(interestTicketNum));
        } else {
            interest_ticket_number.setVisibility(View.GONE);
        }
        if (redPacketNum != 0) {
            red_packet_number.setVisibility(View.VISIBLE);
            red_packet_number.setText(String.valueOf(redPacketNum));
        } else {
            red_packet_number.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
        }
        return false;

    }

}
