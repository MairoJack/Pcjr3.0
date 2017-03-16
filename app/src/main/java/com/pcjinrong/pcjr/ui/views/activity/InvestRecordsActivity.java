package com.pcjinrong.pcjr.ui.views.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.pcjinrong.pcjr.R;
import com.pcjinrong.pcjr.api.ApiConstant;
import com.pcjinrong.pcjr.bean.BaseBean;
import com.pcjinrong.pcjr.bean.InvestRecords;
import com.pcjinrong.pcjr.core.BaseSwipeActivity;
import com.pcjinrong.pcjr.core.mvp.MvpView;
import com.pcjinrong.pcjr.ui.adapter.InvestRecordsListAdapter;
import com.pcjinrong.pcjr.ui.presenter.InvestRecordsPresenter;
import java.util.List;
import retrofit2.adapter.rxjava.HttpException;


/**
 * 投资记录
 * Created by Mario on 2016/5/24.
 */
public class InvestRecordsActivity extends BaseSwipeActivity implements MvpView<BaseBean<List<InvestRecords>>>{

    private InvestRecordsPresenter presenter;
    private InvestRecordsListAdapter adapter;
    private int type = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.member_invest_records;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        this.showBack();
        this.setTitle("投资记录");
        mToolbar.setOverflowIcon(ContextCompat.getDrawable(this,R.mipmap.ic_more_horiz_white_24dp));
        dividerHeight = (int) getResources().getDimension(R.dimen.list_divider_height);
    }


    @Override
    protected void initListeners() {

    }

    @Override
    protected void initData() {
        this.presenter = new InvestRecordsPresenter();
        this.presenter.attachView(this);
        this.adapter = new InvestRecordsListAdapter();
        rv_list.setAdapter(this.adapter);

        mPtrFrame.post(()->mPtrFrame.autoRefresh());
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_invest_record, menu);
        return true;
    }

    @Override
    protected void refresh() {
        refresh = true;
        emptyCount = 0;
        this.presenter.setPage(1);
        this.presenter.getInvestRecords(type, ApiConstant.DEFAULT_PAGE_SIZE);
    }

    @Override
    protected void loadMore() {
        if(emptyCount < EMPTY_LIMIT && !mPtrFrame.isRefreshing()) {
            refresh = false;
            this.presenter.setPage(this.presenter.getPage() + 1);
            this.presenter.getInvestRecords(type, ApiConstant.DEFAULT_PAGE_SIZE);
        }
    }

    @Override
    public void onFailure(Throwable e) {
        mPtrFrame.refreshComplete();
        if(e instanceof HttpException && ((HttpException)e).code() == 400){
            showToast(getString(R.string.login_expired));
            startActivity(new Intent(InvestRecordsActivity.this, LoginActivity.class));
            return;
        }
        showToast(getString(R.string.network_anomaly));
    }

    @Override
    public void onSuccess(BaseBean<List<InvestRecords>> data) {
        mPtrFrame.refreshComplete();
        if(refresh){
            if(data.getData().size() == 0) {
                empty.setVisibility(View.VISIBLE);
                rv_list.setVisibility(View.INVISIBLE);
            }else{
                empty.setVisibility(View.INVISIBLE);
                rv_list.setVisibility(View.VISIBLE);
                adapter.setData(data.getData());
            }
        }else{
            adapter.addAll(data.getData());
        }
        if(data.getData().size() == 0 ) emptyCount++;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            this.onBackPressed();
            return true;
        }
        refresh = true;
        type = item.getOrder();
        mTitle.setText(item.getTitle());
        mPtrFrame.post(()-> mPtrFrame.autoRefresh());
        return true;
    }
}
