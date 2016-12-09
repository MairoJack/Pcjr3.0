package com.pcjinrong.pcjr.ui.views.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;

import com.pcjinrong.pcjr.R;
import com.pcjinrong.pcjr.bean.AvailableInterest;
import com.pcjinrong.pcjr.bean.BaseBean;
import com.pcjinrong.pcjr.bean.InterestTicket;
import com.pcjinrong.pcjr.bean.Product;
import com.pcjinrong.pcjr.bean.Withdraw;
import com.pcjinrong.pcjr.constant.Constant;
import com.pcjinrong.pcjr.core.BaseFragment;
import com.pcjinrong.pcjr.ui.presenter.InvestDetailPresenter;
import com.pcjinrong.pcjr.ui.presenter.ivview.InvestDetailView;
import com.pcjinrong.pcjr.ui.views.activity.InvestDetailActivity;
import com.pcjinrong.pcjr.ui.views.activity.WebViewActivity;
import com.pcjinrong.pcjr.utils.DateUtils;
import com.pcjinrong.pcjr.widget.ProgressWheel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;


/**
 * 投资项目详情-信息
 * Created by Mario on 2016/5/12.
 */
public class InvestDetailInfoFragment extends BaseFragment implements InvestDetailView {
    @BindView(R.id.ptr_frame) PtrClassicFrameLayout mPtrFrame;
    @BindView(R.id.pw_spinner) ProgressWheel progressWheel;
    @BindView(R.id.scroll_view) ScrollView scrollView;

    @BindView(R.id.txt_preview_repayment) TextView txt_preview_repayment;
    @BindView(R.id.txt_debx) TextView txt_debx;
    @BindView(R.id.year_income) TextView year_income;
    @BindView(R.id.product_no) TextView product_no;
    @BindView(R.id.name) TextView name;
    @BindView(R.id.threshold_amount) TextView threshold_amount;
    @BindView(R.id.increasing_amount) TextView increasing_amount;
    @BindView(R.id.txt_repayment) TextView txt_repayment;
    @BindView(R.id.month) TextView month;
    @BindView(R.id.invest_amount) TextView invest_amount;
    @BindView(R.id.amount) TextView amount;
    @BindView(R.id.repayment_date) TextView repayment_date;
    @BindView(R.id.value_date) TextView value_date;
    @BindView(R.id.guarantors_name) TextView guarantors_name;
    @BindView(R.id.intro) TextView intro;
    @BindView(R.id.company_intro) TextView borrower_intro;
    @BindView(R.id.welfare) TextView welfare;

    @BindView(R.id.preview_repayment) LinearLayout preview_repayment;
    @BindView(R.id.debx) LinearLayout debx;

    private InvestDetailPresenter presenter;

    private Product product;
    private int index = 0;
    private boolean is_not_first = false;

    @Override
    protected int getLayoutId() {
        return R.layout.invest_detail_info;
    }

    @Override
    protected void initViews(View self, Bundle savedInstanceState) {

    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void initData() {
        Bundle bundle = getArguments();
        product = (Product) bundle.getSerializable("product");

        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, scrollView, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                refresh();
            }
        });

        this.presenter = new InvestDetailPresenter();
        this.presenter.attachView(this);

        bindData(product);
    }

    private void bindData(Product product) {
        if (product.getIs_preview_repayment() == 1) {
            String html_preview_repayment;
            if (product.getFinish_preview_repayment() == 1) {
                html_preview_repayment = "* 此为 <font color='#dc4d07'>提前回款</font> 产品，原借款时长为 <font color='#dc4d07'>" + product.getMonth() + "</font> ，现提前至 <font color='#dc4d07'>" + DateUtils.dateTimeToStr(new Date(product.getPreview_repayment_date() * 1000),"yyyy-MM-dd") + "</font> ，故补偿<font color='#dc4d07'>" + product.getPay_interest_day() + "</font>天利息 于投资人,利息计算方法请 点击此处";
            } else {
                html_preview_repayment = "* 本产品具有 <font color='#dc4d07'>提前回款</font> 可能，平台确保此产品最短借款时长为 <font color='#dc4d07'>" + product.getMin_repayment_date() + "</font> ，如提前回款则补偿本产品 <font color='#dc4d07'>" + product.getPay_interest_day() + "天利息</font> 于投资人,利息计算方法请 点击此处";
            }
            preview_repayment.setVisibility(View.VISIBLE);
            txt_preview_repayment.setText(Html.fromHtml(html_preview_repayment));
            preview_repayment.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("title", Constant.PLATFORM_ANNOUNCEMENT);
                intent.putExtra("url", Constant.PREPAYMENT_URL);
                startActivity(intent);
            });
        }
        if (product.getRepayment() == 2) {
            String html_debx = "* 本产品为 <font color='#dc4d07'>等额本息</font> 产品，每投资1000元预期收益为 <font color='#dc4d07'>" + product.getEstimate_interest() + "</font> 元，按月还本付息，资金更灵活，理财更安心,具体收益计算公式请 点击此处";
            debx.setVisibility(View.VISIBLE);
            txt_debx.setText(Html.fromHtml(html_debx));
            debx.setOnClickListener(v -> {
                Intent intent = new Intent(getActivity(), WebViewActivity.class);
                intent.putExtra("title", Constant.PLATFORM_ANNOUNCEMENT);
                intent.putExtra("url", Constant.PREPAYMENT_DEBX_URL);
                startActivity(intent);
            });
        }
        int repayment = product.getRepayment();
        BigDecimal bd_amount = new BigDecimal(product.getAmount());
        BigDecimal bd_product_amount = new BigDecimal(product.getProduct_amount());
        if(product.getIs_welfare() == 1){
            welfare.setVisibility(View.VISIBLE);
        }else{
            welfare.setVisibility(View.GONE);
        }
        switch (repayment) {
            case 0:txt_repayment.setText("一次还本付息");break;
            case 1:txt_repayment.setText("先息后本(按月付息)");break;
            case 2:txt_repayment.setText("等额本息");break;
            case 3:txt_repayment.setText("先息后本(按季付息)");break;
        }

        year_income.setText(product.getYear_income() + "%");
        name.setText(product.getName());
        threshold_amount.setText(product.getThreshold_amount() + "元起购");
        increasing_amount.setText(product.getIncreasing_amount() + "元递增");
        month.setText(product.getMonth());

        amount.setText(String.format("%.2f", bd_amount.divide(new BigDecimal(10000))));
        invest_amount.setText(String.format("%.2f", bd_amount.subtract(bd_product_amount).divide(new BigDecimal(10000))));
        product_no.setText(product.getProduct_no());

        repayment_date.setText(DateUtils.dateTimeToStr(new Date(product.getRepayment_date() * 1000), "yyyy-MM-dd"));
        value_date.setText(DateUtils.dateTimeToStr(new Date(product.getValue_date() * 1000), "yyyy-MM-dd"));
        if (product.getGuarantors_name().equals("null")) {
            guarantors_name.setVisibility(View.GONE);
        } else {
            guarantors_name.setText(product.getGuarantors_name());
        }
        intro.setText(product.getIntro());
        borrower_intro.setText(product.getBorrower_intro());
        final Handler mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (index <= (product.getRate() * 18 / 5)) {
                    progressWheel.setProgress(index);
                    index += 2;
                    mHandler.postDelayed(this, 4);
                } else {
                    mHandler.removeCallbacks(this);
                }
            }
        }, 1000);
    }

    @Override
    protected void lazyLoad() {

    }


    public static Fragment newInstance(Product product) {
        InvestDetailInfoFragment fragment = new InvestDetailInfoFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("product", product);
        fragment.setArguments(bundle);
        return fragment;
    }

    public void refresh(){
        presenter.getProductDetail(product.getId());
    }

    @Override
    public void onProductInfoSuccess(BaseBean<Product> data,long sys_time) {
        mPtrFrame.refreshComplete();
        product = data.getData();
        bindData(product);
        if(getActivity()!=null) {
            long server_time = data.getCurrent_time() * 1000 + System.currentTimeMillis() - sys_time;
            ((InvestDetailActivity) getActivity()).refreshButton(data.getData(), server_time);
        }
    }

    @Override
    public void onInvestInfoSuccess(BaseBean<Withdraw> data) {

    }

    @Override
    public void onInterestListSuccess(List<InterestTicket> list) {

    }

    @Override
    public void onFailure(Throwable e) {
        if (mPtrFrame.isRefreshing()) mPtrFrame.refreshComplete();
        showToast(R.string.network_anomaly);
    }

    @Override
    public void onSuccess(Object data) {

    }

    @Override
    public void onResume() {
        super.onResume();
        if(is_not_first) {
            refresh();
        }
        is_not_first = true;
    }
}
