package com.pcjinrong.pcjr.ui.views.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;

import com.pcjinrong.pcjr.R;
import com.pcjinrong.pcjr.api.ApiConstant;
import com.pcjinrong.pcjr.bean.InterestTicket;
import com.pcjinrong.pcjr.bean.InvestTicket;
import com.pcjinrong.pcjr.core.BaseSwipeFragment;
import com.pcjinrong.pcjr.ui.adapter.InterestTicketListAdapter;
import com.pcjinrong.pcjr.ui.adapter.InvestTicketListAdapter;
import com.pcjinrong.pcjr.ui.presenter.InterestTicketPresenter;
import com.pcjinrong.pcjr.ui.presenter.InvestTicketPresenter;
import com.pcjinrong.pcjr.ui.presenter.ivview.InterestTicketView;
import com.pcjinrong.pcjr.ui.presenter.ivview.InvestTicketView;
import com.pcjinrong.pcjr.ui.views.activity.InterestTicketDetailActivity;
import com.pcjinrong.pcjr.ui.views.activity.InvestTicketDetailActivity;
import com.pcjinrong.pcjr.ui.views.activity.LoginActivity;

import java.util.List;

import retrofit2.adapter.rxjava.HttpException;


/**
 * 加息劵Fragment
 * Created by Mario on 2016/12/5.
 */
public class InterestTicketFragment extends BaseSwipeFragment implements InterestTicketView {

    private InterestTicketPresenter presenter;
    private InterestTicketListAdapter adapter;
    private ProgressDialog dialog;

    private int type;

    private boolean isPrepared;
    private boolean mHasLoadedOnce;



    @Override
    protected int getLayoutId() {
        return R.layout.interest_ticket_list;
    }

    @Override
    protected void initViews(View self, Bundle savedInstanceState) {
        dialog = new ProgressDialog(getContext(), ProgressDialog.STYLE_SPINNER);
        dividerHeight = (int) getResources().getDimension(R.dimen.list_divider_height);
    }

    @Override
    protected void initListeners() {
        adapter.setOnItemClickListener((view,data)->{
            dialog.setMessage("正在加载...");
            dialog.show();
            presenter.getInterestTicketDetail(data.getId());
        });
    }

    @Override
    protected void initData() {
        type = getArguments().getInt("type");

        this.presenter = new InterestTicketPresenter();
        this.presenter.attachView(this);
        this.adapter = new InterestTicketListAdapter(type);
        rv_list.setAdapter(this.adapter);

        isPrepared = true;
        lazyLoad();
    }

    @Override
    public void refresh() {
        refresh = true;
        emptyCount = 0;
        this.presenter.setPage(1);
        this.presenter.getInterestTicketList(type, ApiConstant.DEFAULT_PAGE_SIZE);
    }

    @Override
    public void loadMore() {
        if(emptyCount < EMPTY_LIMIT && !mPtrFrame.isRefreshing()) {
            refresh = false;
            this.presenter.setPage(this.presenter.getPage() + 1);
            this.presenter.getInterestTicketList(type, ApiConstant.DEFAULT_PAGE_SIZE);
        }
    }

    public static Fragment newInstance(int type) {
        InterestTicketFragment fragment = new InterestTicketFragment();
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
        if(dialog.isShowing())dialog.dismiss();
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
    public void onInterestTicketListSuccess(List<InterestTicket> list) {
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
    public void onInterestTicketDetailSuccess(InterestTicket data) {
        dialog.dismiss();
        Intent intent = new  Intent(getActivity(), InterestTicketDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", data);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
