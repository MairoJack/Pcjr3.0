package com.pcjinrong.pcjr.ui.views.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.pcjinrong.pcjr.R;
import com.pcjinrong.pcjr.bean.BaseBean;
import com.pcjinrong.pcjr.bean.PaymentPlan;
import com.pcjinrong.pcjr.core.BaseSwipeActivity;
import com.pcjinrong.pcjr.core.mvp.MvpView;
import com.pcjinrong.pcjr.ui.adapter.PaymentPlanListAdapter;
import com.pcjinrong.pcjr.ui.decorator.DotDecorator;
import com.pcjinrong.pcjr.ui.decorator.DotGrayDecorator;
import com.pcjinrong.pcjr.ui.presenter.PaymentPlanPresenter;
import com.pcjinrong.pcjr.utils.DateUtils;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import retrofit2.adapter.rxjava.HttpException;


/**
 * 投资记录
 * Created by Mario on 2016/5/24.
 */
public class PaymentPlanActivity extends BaseSwipeActivity implements MvpView<BaseBean<List<PaymentPlan>>> {

    @BindView(R.id.calendarView) MaterialCalendarView calendarView;

    private PaymentPlanPresenter presenter;
    private PaymentPlanListAdapter adapter;

    private int year, month;
    private List<PaymentPlan> list = new ArrayList<>();
    private List<PaymentPlan> day_list = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.member_payment_plan;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        this.showBack();
        this.setTitle("回款计划");
        dividerHeight = (int) getResources().getDimension(R.dimen.list_divider_height_1dp);
        DotGrayDecorator dotGrayDecorator = new DotGrayDecorator();
        calendarView.addDecorator(dotGrayDecorator);
    }


    @Override
    protected void initListeners() {
        calendarView.setOnMonthChangedListener((widget, date) -> {
            year = date.getYear();
            month = date.getMonth() + 1;
            mPtrFrame.postDelayed(() -> refresh(), 100);
        });

        calendarView.setOnDateChangedListener((widget, date, selected) -> loadData(date.getDay()));
    }

    @Override
    protected void initData() {
        Date date = new Date();
        year = DateUtils.getYearOfDate(date);
        month = DateUtils.getMonthOfDate(date);

        this.presenter = new PaymentPlanPresenter();
        this.presenter.attachView(this);
        this.adapter = new PaymentPlanListAdapter();
        rv_list.setAdapter(this.adapter);

        mPtrFrame.post(() -> mPtrFrame.autoRefresh());
    }


    public void loadData(int day) {
        day_list =  this.presenter.getPaymentPlanByDay(day,list);
        if (day_list.isEmpty()) {
            empty.setVisibility(View.VISIBLE);
            rv_list.setVisibility(View.INVISIBLE);
        } else {
            adapter.setData(day_list);
            empty.setVisibility(View.INVISIBLE);
            rv_list.setVisibility(View.VISIBLE);
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


    @Override
    protected void refresh() {
        this.presenter.setPage(1);
        this.presenter.getPaymentPlan(year, month);
    }

    @Override
    protected void loadMore() {

    }


    @Override
    public void onFailure(Throwable e) {
        mPtrFrame.refreshComplete();
        if(e instanceof HttpException && ((HttpException)e).code() == 400){
            showToast(getString(R.string.login_expired));
            startActivity(new Intent(PaymentPlanActivity.this, LoginActivity.class));
            return;
        }
        showToast(getString(R.string.network_anomaly));
    }

    @Override
    public void onSuccess(BaseBean<List<PaymentPlan>> data) {
        mPtrFrame.refreshComplete();
        if (data.getData().size() == 0) {
            empty.setVisibility(View.VISIBLE);
            rv_list.setVisibility(View.INVISIBLE);
        } else {
            list.clear();
            day_list.clear();
            list.addAll(data.getData());
            day_list.addAll(data.getData());

            for (PaymentPlan pp : list) {
                DotDecorator dot = new DotDecorator();
                dot.setDate(new Date(pp.getEstimated_time() * 1000));
                calendarView.addDecorator(dot);
            }

            empty.setVisibility(View.INVISIBLE);
            rv_list.setVisibility(View.VISIBLE);
            adapter.setData(data.getData());
        }
    }
}
