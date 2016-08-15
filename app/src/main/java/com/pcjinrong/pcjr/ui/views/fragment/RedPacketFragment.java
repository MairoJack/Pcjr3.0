package com.pcjinrong.pcjr.ui.views.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.pcjinrong.pcjr.R;
import com.pcjinrong.pcjr.api.ApiConstant;
import com.pcjinrong.pcjr.bean.BaseBean;
import com.pcjinrong.pcjr.bean.RedPacket;
import com.pcjinrong.pcjr.core.BaseSwipeFragment;
import com.pcjinrong.pcjr.ui.adapter.RedPacketListAdapter;
import com.pcjinrong.pcjr.ui.presenter.RedPacketPresenter;
import com.pcjinrong.pcjr.ui.presenter.ivview.RedPacketView;
import com.pcjinrong.pcjr.ui.views.activity.LoginActivity;
import com.pcjinrong.pcjr.utils.ViewUtil;
import com.pcjinrong.pcjr.widget.Dialog;

import java.util.List;

import retrofit2.adapter.rxjava.HttpException;


/**
 * 红包Fragment
 * Created by Mario on 2016/5/12.
 */
public class RedPacketFragment extends BaseSwipeFragment implements RedPacketView {

    private RedPacketPresenter presenter;
    private RedPacketListAdapter adapter;

    private int type;

    private boolean isPrepared;
    private boolean mHasLoadedOnce;

    private RedPacket redPacket;


    @Override
    protected int getLayoutId() {
        return R.layout.invest_list;
    }

    @Override
    protected void initViews(View self, Bundle savedInstanceState) {
        dividerHeight = (int) getResources().getDimension(R.dimen.list_divider_height);
    }

    @Override
    protected void initListeners() {
        adapter.setOnDeleteClickListener((view,data)-> {
            if(ViewUtil.isFastDoubleClick())return;
            redPacket = data;
            presenter.getRedPacketReward(data.getId());
        });
    }

    @Override
    protected void initData() {
        type = getArguments().getInt("type");

        this.presenter = new RedPacketPresenter();
        this.presenter.attachView(this);
        this.adapter = new RedPacketListAdapter(type);
        rv_list.setAdapter(this.adapter);

        isPrepared = true;
        lazyLoad();
    }

    @Override
    public void refresh() {
        refresh = true;
        emptyCount = 0;
        this.presenter.setPage(1);
        this.presenter.getRedPacketList(type, ApiConstant.DEFAULT_PAGE_SIZE);
    }

    @Override
    public void loadMore() {
        if(emptyCount < EMPTY_LIMIT && !mPtrFrame.isRefreshing()) {
            refresh = false;
            this.presenter.setPage(this.presenter.getPage() + 1);
            this.presenter.getRedPacketList(type, ApiConstant.DEFAULT_PAGE_SIZE);
        }
    }

    public static Fragment newInstance(int type) {
        RedPacketFragment fragment = new RedPacketFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    public void lazyLoad() {
        if (!isPrepared || !isVisible || mHasLoadedOnce) {
            return;
        }
        mPtrFrame.post(() -> mPtrFrame.autoRefresh());
    }

    @Override
    public void onFailure(Throwable e) {
        mPtrFrame.refreshComplete();
        if(e instanceof HttpException){
            showToast(getString(R.string.login_expired));
            startActivity(new Intent(getActivity(), LoginActivity.class));
            return;
        }
        showToast(getString(R.string.network_anomaly));
    }

    @Override
    public void onSuccess(Object data) {

    }

    @Override
    public void onRedPacketListSuccess(List<RedPacket> list) {
        mHasLoadedOnce = true;
        mPtrFrame.refreshComplete();
        if(refresh){
            if (list.size() == 0) {
                empty.setVisibility(View.VISIBLE);
                rv_list.setVisibility(View.INVISIBLE);
            } else {
                empty.setVisibility(View.INVISIBLE);
                rv_list.setVisibility(View.VISIBLE);
                adapter.setData(list);
            }
        }else{
            adapter.addAll(list);
        }

        if(list.size() == 0 ) emptyCount++;
    }

    @Override
    public void onRedPacketRewardSuccess(BaseBean data) {
        if(data.isSuccess()) {
            Dialog.show("领取成功","红包金额已转入您的账户余额内",getContext());
            isPrepared = false;
            adapter.delete(redPacket);
        }else{
            Dialog.show(data.getMessage(),getContext());
        }
    }
}
