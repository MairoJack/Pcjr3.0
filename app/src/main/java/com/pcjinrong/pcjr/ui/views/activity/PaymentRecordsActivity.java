package com.pcjinrong.pcjr.ui.views.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.pcjinrong.pcjr.R;
import com.pcjinrong.pcjr.bean.PaymentRecords;
import com.pcjinrong.pcjr.core.BaseSwipeActivity;
import com.pcjinrong.pcjr.core.mvp.MvpView;
import com.pcjinrong.pcjr.ui.adapter.PaymentRecordsListAdapter;
import com.pcjinrong.pcjr.ui.presenter.PaymentRecordsPresenter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.adapter.rxjava.HttpException;


/**
 * 回款记录
 * Created by Mario on 2017/4/14.
 */
public class PaymentRecordsActivity extends BaseSwipeActivity implements MvpView<List<PaymentRecords>> {

    private PaymentRecordsPresenter presenter;
    private PaymentRecordsListAdapter adapter;

    private String id;

    @Override
    protected int getLayoutId() {
        return R.layout.member_payment_records;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        this.showBack();
        this.setTitle("回款记录");
        dividerHeight = (int) getResources().getDimension(R.dimen.list_divider_height_1dp);
    }


    @Override
    protected void initListeners() {

    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        this.presenter = new PaymentRecordsPresenter();
        this.presenter.attachView(this);
        this.adapter = new PaymentRecordsListAdapter();
        rv_list.setAdapter(this.adapter);

        mPtrFrame.post(() -> mPtrFrame.autoRefresh());
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
        refresh = true;
        emptyCount = 0;
        List<PaymentRecords> list = new ArrayList<>();
        PaymentRecords paymentRecords = new PaymentRecords();
        paymentRecords.setAmount("123");
        list.add(paymentRecords);
        list.add(paymentRecords);
        list.add(paymentRecords);
        adapter.setData(list);
       // this.presenter.getPaymentRecords(id);
    }

    @Override
    protected void loadMore() {

    }

    @Override
    public void onFailure(Throwable e) {
        mPtrFrame.refreshComplete();
        if(e instanceof HttpException && ((HttpException)e).code() == 400){
            showToast(getString(R.string.login_expired));
            startActivity(new Intent(PaymentRecordsActivity.this, LoginActivity.class));
            return;
        }
        showToast(getString(R.string.network_anomaly));
    }

    @Override
    public void onSuccess(List<PaymentRecords> list) {
        mPtrFrame.refreshComplete();
        if (refresh) {
            if (list.size() == 0) {
                empty.setVisibility(View.VISIBLE);
                rv_list.setVisibility(View.INVISIBLE);
            } else {
                empty.setVisibility(View.INVISIBLE);
                rv_list.setVisibility(View.VISIBLE);
                adapter.setData(list);
            }
        } else {
            adapter.addAll(list);
        }
        if (list.size() == 0) emptyCount++;
    }
}
