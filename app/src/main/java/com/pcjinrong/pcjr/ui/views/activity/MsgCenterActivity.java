package com.pcjinrong.pcjr.ui.views.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.pcjinrong.pcjr.R;
import com.pcjinrong.pcjr.api.ApiConstant;
import com.pcjinrong.pcjr.bean.Letter;
import com.pcjinrong.pcjr.core.BaseSwipeActivity;
import com.pcjinrong.pcjr.ui.adapter.LetterListAdapter;
import com.pcjinrong.pcjr.ui.presenter.MsgCenterPresenter;
import com.pcjinrong.pcjr.ui.presenter.ivview.MsgCenterView;

import java.util.List;

import retrofit2.adapter.rxjava.HttpException;


/**
 * 消息中心
 * Created by Mario on 2016/5/24.
 */
public class MsgCenterActivity extends BaseSwipeActivity implements MsgCenterView {

    private MsgCenterPresenter presenter;
    private LetterListAdapter adapter;

    private ProgressDialog dialog;

    @Override
    protected int getLayoutId() {
        return R.layout.member_msg_center;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        this.showBack();
        this.setTitle("消息中心");
        dialog = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
        dividerHeight = (int) getResources().getDimension(R.dimen.list_divider_height_1dp);
    }


    @Override
    protected void initListeners() {
        adapter.setOnItemClickListener((view, data) -> {
            data.setRead_status(1);
            adapter.notifyDataSetChanged();
            dialog.setMessage("正在加载...");
            dialog.show();
            presenter.getLetterDetail(data.getId());
        });
    }

    @Override
    protected void initData() {
        this.presenter = new MsgCenterPresenter();
        this.presenter.attachView(this);
        this.adapter = new LetterListAdapter();
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
        this.presenter.setPage(1);
        this.presenter.getLetterList(ApiConstant.DEFAULT_PAGE_SIZE);
    }

    @Override
    protected void loadMore() {
        if (emptyCount < EMPTY_LIMIT && !mPtrFrame.isRefreshing()) {
            refresh = false;
            this.presenter.setPage(this.presenter.getPage() + 1);
            this.presenter.getLetterList(ApiConstant.DEFAULT_PAGE_SIZE);
        }
    }

    @Override
    public void onFailure(Throwable e) {
        mPtrFrame.refreshComplete();
        if(e instanceof HttpException){
            showToast(getString(R.string.login_expired));
            startActivity(new Intent(MsgCenterActivity.this, LoginActivity.class));
            return;
        }
        showToast(getString(R.string.network_anomaly));
    }

    @Override
    public void onSuccess(Object data) {

    }

    @Override
    public void onLetterListSuccess(List<Letter> list) {
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

    @Override
    public void onLetterDetailSuccess(Letter data) {
        dialog.dismiss();
        Intent intent = new  Intent(MsgCenterActivity.this, MsgDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", data);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
