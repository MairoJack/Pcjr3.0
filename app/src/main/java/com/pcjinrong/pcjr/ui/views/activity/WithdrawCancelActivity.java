package com.pcjinrong.pcjr.ui.views.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.View;

import com.pcjinrong.pcjr.R;
import com.pcjinrong.pcjr.bean.BaseBean;
import com.pcjinrong.pcjr.bean.InvestProductRepaymentInfo;
import com.pcjinrong.pcjr.bean.WithdrawCancel;
import com.pcjinrong.pcjr.core.BaseSwipeActivity;
import com.pcjinrong.pcjr.core.mvp.MvpView;
import com.pcjinrong.pcjr.ui.adapter.WithdrawCancelListAdapter;
import com.pcjinrong.pcjr.ui.presenter.WithdrawCancelPresenter;
import com.pcjinrong.pcjr.ui.presenter.ivview.WithdrawCanceltView;
import com.pcjinrong.pcjr.widget.Dialog;

import java.util.List;

import retrofit2.adapter.rxjava.HttpException;


/**
 *  提现取消
 *  Created by Mario on 2017/5/16上午10:17
 */
public class WithdrawCancelActivity extends BaseSwipeActivity implements WithdrawCanceltView {

    private WithdrawCancelPresenter presenter;
    private WithdrawCancelListAdapter adapter;

    private String id;

    @Override
    protected int getLayoutId() {
        return R.layout.member_withdraw_cancel;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        this.showBack();
        this.setTitle("提现取消");
        dividerHeight = (int) getResources().getDimension(R.dimen.list_divider_height_1dp);
    }


    @Override
    protected void initListeners() {
        adapter.setOnCancelClickListener((v,data)->{
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("提示");
            builder.setMessage("你确定要取消此提现申请?");
            builder.setPositiveButton("确定", (dialog,which)->{
                presenter.cancelWithdraw(data.getId());
            });
            builder.setNegativeButton("取消", (dialog,which)->{
                dialog.dismiss();
            });
            builder.setCancelable(false);
            AlertDialog noticeDialog = builder.create();
            noticeDialog.show();
        });
    }

    @Override
    protected void initData() {
        this.presenter = new WithdrawCancelPresenter();
        this.presenter.attachView(this);
        this.adapter = new WithdrawCancelListAdapter();
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
        this.presenter.getWithdrawCancel();
    }

    @Override
    protected void loadMore() {

    }

    @Override
    public void onFailure(Throwable e) {
        mPtrFrame.refreshComplete();
        if(e instanceof HttpException && ((HttpException)e).code() == 400){
            showToast(getString(R.string.login_expired));
            startActivity(new Intent(WithdrawCancelActivity.this, LoginActivity.class));
            return;
        }
        showToast(getString(R.string.network_anomaly));
    }

    @Override
    public void onSuccess(Object data) {

    }


    @Override
    public void onTodayWithdrawListSuccess(List<WithdrawCancel> list) {
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
    public void onCancelWithdrawSuccess(BaseBean data) {
        if(data.isSuccess()){
            showToast(data.getMessage());
            mPtrFrame.post(() -> mPtrFrame.autoRefresh());
        }else{
            Dialog.show(data.getMessage(),this);
        }

    }
}
