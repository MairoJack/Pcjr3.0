package com.pcjinrong.pcjr.ui.views.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;
import com.pcjinrong.pcjr.R;
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
        String risk_control = getArguments().getString("risk");
        txt_risk_control.setText(risk_control);
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




}
