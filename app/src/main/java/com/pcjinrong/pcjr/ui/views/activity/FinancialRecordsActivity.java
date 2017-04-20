package com.pcjinrong.pcjr.ui.views.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.pcjinrong.pcjr.R;
import com.pcjinrong.pcjr.bean.FinanceRecords;
import com.pcjinrong.pcjr.core.BaseToolbarActivity;
import com.pcjinrong.pcjr.utils.SPUtils;
import com.pcjinrong.pcjr.utils.ViewUtil;
import com.pcjinrong.pcjr.widget.Dialog;

import butterknife.BindView;

/**
 * 资金记录
 * Created by Mario on 2016/5/20.
 */
public class FinancialRecordsActivity extends BaseToolbarActivity{

    @BindView(R.id.available_balance) TextView available_balance;
    @BindView(R.id.capital) TextView capital;
    @BindView(R.id.interest) TextView interest;
    @BindView(R.id.earned_interest) TextView earned_interest;
    @BindView(R.id.reward_amount) TextView reward_amount;
    @BindView(R.id.total_reward_amount) TextView total_reward_amount;
    @BindView(R.id.total_amount) TextView total_amount;
    @BindView(R.id.recharge_success_amount) TextView recharge_success_amount;
    @BindView(R.id.invest_success_amount) TextView invest_success_amount;
    @BindView(R.id.withdraw_success_amount) TextView withdraw_success_amount;

    @BindView(R.id.but_eye) ImageView but_eye;
    @BindView(R.id.but_tips) ImageView but_tips;

    private FinanceRecords data;
    @Override
    protected int getLayoutId() {
        return R.layout.member_financial_records;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        this.showBack();
        this.setTitle("资金记录");
    }


    @Override
    protected void initListeners() {
        but_eye.setOnClickListener(v->{
            if(ViewUtil.isFastDoubleClick())return;
            if((boolean)SPUtils.get(this,"isOpenEye48",true)){
                hide();
                SPUtils.put(this,"isOpenEye48",false);
                but_eye.setImageResource(R.mipmap.icon_close_eye_48);
            }else{
                if(data!=null) {
                    show(data);
                    SPUtils.put(this,"isOpenEye48",true);
                    but_eye.setImageResource(R.mipmap.icon_open_eye_48);
                }
            }
        });

        but_tips.setOnClickListener(v->{
            if(ViewUtil.isFastDoubleClick())return;
            Dialog.show("该金额会因提前还款有所变动，具体以实际到账为准",this);
        });
    }

    @Override
    protected void initData() {
        data = (FinanceRecords) getIntent().getSerializableExtra("data");

        if((boolean) SPUtils.get(this,"isOpenEye48",true)){
            show(data);
            but_eye.setImageResource(R.mipmap.icon_open_eye_48);
        }else{
            hide();
            but_eye.setImageResource(R.mipmap.icon_close_eye_48);
        }



    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK )
        {
            finish();
            overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
        }
        return false;

    }

    private void show(FinanceRecords data){
        total_amount.setText("总资产（元） "+data.getTotal_amount());
        available_balance.setText(data.getAvailable_balance()+"元");
        capital.setText(data.getCapital());
        interest.setText(data.getInterest());
        earned_interest.setText(data.getEarned_interest()+"元");
        reward_amount.setText(data.getReward_amount()+"元");
        total_reward_amount.setText(data.getTotal_reward_amount()+"元");
        recharge_success_amount.setText(data.getRecharge_success_amount()+"元");
        invest_success_amount.setText(data.getInvest_success_amount()+"元");
        withdraw_success_amount.setText(data.getWithdraw_success_amount()+"元");
    }

    private void hide(){
        total_amount.setText("总资产（元） ******");
        available_balance.setText("******");
        capital.setText("******");
        interest.setText("******");
        earned_interest.setText("******");
        reward_amount.setText("******");
        total_reward_amount.setText("******");
        recharge_success_amount.setText("******");
        invest_success_amount.setText("******");
        withdraw_success_amount.setText("******");
    }
}
