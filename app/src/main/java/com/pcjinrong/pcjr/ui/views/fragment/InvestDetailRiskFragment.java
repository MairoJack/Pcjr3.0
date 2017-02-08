package com.pcjinrong.pcjr.ui.views.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;
import com.pcjinrong.pcjr.R;
import com.pcjinrong.pcjr.constant.Constant;
import com.pcjinrong.pcjr.core.BaseFragment;

import butterknife.BindView;


/**
 * 投资项目详情-风控
 * Created by Mario on 2016/5/12.
 */
public class InvestDetailRiskFragment extends BaseFragment {

    @BindView(R.id.risk_control) TextView txt_risk_control;

    @Override
    protected int getLayoutId() {
        return R.layout.invest_detail_risk;
    }

    @Override
    protected void initViews(View self, Bundle savedInstanceState) {

    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void initData() {
        if(!Constant.IS_LOGIN){
            txt_risk_control.setText("查看项目详情请先【注册】或【登录】");
        }else if(!Constant.IS_REALNAME){
            txt_risk_control.setText("查看项目详情请先前往【我的】-【安全设置】完成实名认证");
        } else {
            String risk_control = getArguments().getString("risk");
            txt_risk_control.setText(risk_control);
        }

    }

    @Override
    protected void lazyLoad() {

    }


    public static Fragment newInstance(String risk) {
        InvestDetailRiskFragment fragment = new InvestDetailRiskFragment();
        Bundle bundle = new Bundle();
        bundle.putString("risk", risk);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onResume() {
        super.onResume();
        txt_risk_control.setText("");
        Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                initData();
            }
        }, 2000);
    }


}
