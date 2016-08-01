package com.pcjinrong.pcjr.ui.views.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.pcjinrong.pcjr.R;
import com.pcjinrong.pcjr.bean.FinanceRecords;
import com.pcjinrong.pcjr.bean.MemberIndex;
import com.pcjinrong.pcjr.core.BaseFragment;
import com.pcjinrong.pcjr.ui.presenter.MemberPresenter;
import com.pcjinrong.pcjr.ui.presenter.ivview.MemberView;
import com.pcjinrong.pcjr.ui.views.activity.FinancialRecordsActivity;
import com.pcjinrong.pcjr.ui.views.activity.InvestRecordsActivity;
import com.pcjinrong.pcjr.ui.views.activity.LoginActivity;
import com.pcjinrong.pcjr.ui.views.activity.TradeRecordsActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import retrofit2.adapter.rxjava.HttpException;


/**
 * 用户中心Fragment
 * Created by Mario on 2016/7/21.
 */
public class MemberFragment extends BaseFragment implements MemberView{

    public static final String TAG = MemberFragment.class.getSimpleName();

    @BindView(R.id.ptr_frame) PtrClassicFrameLayout mPtrFrame;
    @BindView(R.id.scroll_view) ScrollView scrollView;
    @BindView(R.id.financial_records)  RelativeLayout financial_records;
    @BindView(R.id.invest_records)  RelativeLayout invest_records;
    @BindView(R.id.trade_records)  RelativeLayout trade_records;
    @BindView(R.id.safe_setting)  RelativeLayout safe_setting;
    @BindView(R.id.bank_card)  RelativeLayout bank_card;
    @BindView(R.id.msg_center)  RelativeLayout msg_center;
    @BindView(R.id.payment_plan)  RelativeLayout payment_plan;
    @BindView(R.id.withdraw_recharge)  RelativeLayout withdraw_recharge;
    @BindView(R.id.coupon)  RelativeLayout coupon;
    @BindView(R.id.username) TextView username;
    @BindView(R.id.available_balance) TextView available_balance;
    @BindView(R.id.sum_assets) TextView sum_assets;
    @BindView(R.id.uncollected_interest_sum) TextView uncollected_interest_sum;


    private MemberPresenter presenter;
    private ProgressDialog dialog;

    @Override
    protected int getLayoutId() {
        return R.layout.main_tab_usercenter;
    }

    @Override
    protected void initViews(View self, Bundle savedInstanceState) {
        ButterKnife.bind(this, self);
        dialog = new ProgressDialog(self.getContext(), ProgressDialog.STYLE_SPINNER);
        this.presenter = new MemberPresenter();
        this.presenter.attachView(this);
        mPtrFrame.disableWhenHorizontalMove(true);
        mPtrFrame.setLastUpdateTimeRelateObject(this);
    }

    @Override
    protected void initListeners() {
        financial_records.setOnClickListener(v -> {
            dialog.setMessage("正在加载...");
            dialog.show();
            presenter.getMemberFinanceData();
        });
        invest_records.setOnClickListener(v -> startActivity(new Intent(getActivity(), InvestRecordsActivity.class)));
        trade_records.setOnClickListener(v->startActivity(new Intent(getActivity(), TradeRecordsActivity.class)));
        safe_setting.setOnClickListener(v->{});
        bank_card.setOnClickListener(v->{});
        msg_center.setOnClickListener(v->{});
        payment_plan.setOnClickListener(v->{});
        withdraw_recharge.setOnClickListener(v->{});
        coupon.setOnClickListener(v->{});
    }

    @Override
    protected void initData() {
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, scrollView, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                refreshData();
            }
        });

        refreshData();
    }

    @Override
    protected void lazyLoad() {

    }

    public static Fragment newInstance(String content) {
        MemberFragment fragment = new MemberFragment();
        return fragment;
    }

    private void refreshData() {
        this.presenter.getMemberIndex();
    }

    @Override
    public void onFailure(Throwable e) {
        if(dialog.isShowing()) dialog.dismiss();
        if(e instanceof HttpException){
            showToast(getString(R.string.login_expired));
            startActivity(new Intent(getActivity(), LoginActivity.class));
            return;
        }
        mPtrFrame.refreshComplete();
        showToast(getString(R.string.network_anomaly));
    }

    @Override
    public void onMemberIndexSuccess(MemberIndex data) {
        mPtrFrame.refreshComplete();
        username.setText(data.getUser_name());
        available_balance.setText(data.getAvailable_balance());
        sum_assets.setText(data.getTotal());
        uncollected_interest_sum.setText(data.getInterest());
    }

    @Override
    public void onFinancialRecordsSuccess(FinanceRecords data) {
        dialog.dismiss();
        Intent intent = new Intent(getActivity(), FinancialRecordsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("data",data);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onSuccess(Object data) {

    }
}
