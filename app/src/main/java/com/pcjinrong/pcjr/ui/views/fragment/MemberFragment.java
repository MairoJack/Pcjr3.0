package com.pcjinrong.pcjr.ui.views.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.pcjinrong.pcjr.R;
import com.pcjinrong.pcjr.bean.BaseBean;
import com.pcjinrong.pcjr.bean.Coupon;
import com.pcjinrong.pcjr.bean.FinanceRecords;
import com.pcjinrong.pcjr.bean.MemberIndex;
import com.pcjinrong.pcjr.bean.Withdraw;
import com.pcjinrong.pcjr.constant.Constant;
import com.pcjinrong.pcjr.core.BaseFragment;
import com.pcjinrong.pcjr.ui.presenter.MemberPresenter;
import com.pcjinrong.pcjr.ui.presenter.ivview.MemberView;
import com.pcjinrong.pcjr.ui.views.activity.BankCardActivity;
import com.pcjinrong.pcjr.ui.views.activity.CouponActivity;
import com.pcjinrong.pcjr.ui.views.activity.FinancialRecordsActivity;
import com.pcjinrong.pcjr.ui.views.activity.InvestRecordsActivity;
import com.pcjinrong.pcjr.ui.views.activity.LoginActivity;
import com.pcjinrong.pcjr.ui.views.activity.MsgCenterActivity;
import com.pcjinrong.pcjr.ui.views.activity.PaymentPlanActivity;
import com.pcjinrong.pcjr.ui.views.activity.SafeSettingActivity;
import com.pcjinrong.pcjr.ui.views.activity.TradeRecordsActivity;
import com.pcjinrong.pcjr.ui.views.activity.WithdrawActivity;
import com.pcjinrong.pcjr.ui.views.activity.WithdrawRechargeActivity;
import com.pcjinrong.pcjr.utils.SPUtils;
import com.pcjinrong.pcjr.utils.ViewUtil;
import com.pcjinrong.pcjr.widget.Dialog;

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
    @BindView(R.id.btn_eye) ImageView btn_eye;
    @BindView(R.id.btn_tips) ImageView btn_tips;

    private MemberPresenter presenter;
    private ProgressDialog dialog;

    private MemberIndex memberIndex;
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
        safe_setting.setOnClickListener(v->getActivity().startActivityForResult(new Intent(getActivity(), SafeSettingActivity.class), Constant.LOGOUT));
        bank_card.setOnClickListener(v->startActivity(new Intent(getActivity(), BankCardActivity.class)));
        msg_center.setOnClickListener(v->startActivity(new Intent(getActivity(), MsgCenterActivity.class)));
        payment_plan.setOnClickListener(v->startActivity(new Intent(getActivity(), PaymentPlanActivity.class)));
        withdraw_recharge.setOnClickListener(v->{
            dialog.setMessage("正在加载...");
            dialog.show();
            presenter.getWithdrawInvestInfo();
        });
        coupon.setOnClickListener(v->{
            dialog.setMessage("正在加载...");
            dialog.show();
            presenter.getUnusedCouponsNum();
        });
        btn_eye.setOnClickListener( v -> {
            if((boolean)SPUtils.get(getContext(),"isOpenEye",true)){
                available_balance.setText("******");
                sum_assets.setText("******");
                uncollected_interest_sum.setText("******");
                SPUtils.put(getContext(),"isOpenEye",false);
                btn_eye.setImageResource(R.mipmap.icon_close_eye);
            }else{
                if(memberIndex!=null) {
                    available_balance.setText(memberIndex.getAvailable_balance());
                    sum_assets.setText(memberIndex.getTotal());
                    uncollected_interest_sum.setText(memberIndex.getInterest());
                    SPUtils.put(getContext(),"isOpenEye",true);
                    btn_eye.setImageResource(R.mipmap.icon_open_eye);
                }
            }

        });

        btn_tips.setOnClickListener(v->{
            if(ViewUtil.isFastDoubleClick())return;
            Dialog.show("该金额会因提前还款有所变动，具体以实际到账为准",getActivity());
        });
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

        //refreshData();
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
        if(mPtrFrame.isRefreshing()) mPtrFrame.refreshComplete();
        if(e instanceof HttpException && ((HttpException)e).code() == 400){
            showToast(getString(R.string.login_expired));
            startActivity(new Intent(getActivity(), LoginActivity.class));
            return;
        }
        showToast(getString(R.string.network_anomaly));
    }

    @Override
    public void onMemberIndexSuccess(MemberIndex data) {
        mPtrFrame.refreshComplete();
        memberIndex = data;
        username.setText(data.getUser_name());

        if((boolean)SPUtils.get(getContext(),"isOpenEye",true)){
            available_balance.setText(memberIndex.getAvailable_balance());
            sum_assets.setText(memberIndex.getTotal());
            uncollected_interest_sum.setText(memberIndex.getInterest());
            btn_eye.setImageResource(R.mipmap.icon_open_eye);
        }else{
            available_balance.setText("******");
            sum_assets.setText("******");
            uncollected_interest_sum.setText("******");
            btn_eye.setImageResource(R.mipmap.icon_close_eye);
        }

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
    public void onWithdrawInfoSuccess(BaseBean<Withdraw> data) {
        dialog.dismiss();
        if(data.isSuccess()) {
            Intent intent = new Intent(getActivity(), WithdrawActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("data", data.getData());
            intent.putExtras(bundle);
            startActivity(intent);
        }else{
            Dialog.show(data.getMessage(),getContext());
        }
    }

    @Override
    public void onCouponNumSuccess(Coupon data) {
        dialog.dismiss();
        Intent intent = new Intent(getActivity(), CouponActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", data);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onSuccess(Object data) {

    }

    @Override
    public void onResume() {
        super.onResume();
        if(Constant.IS_LOGIN)
        refreshData();
    }
}
