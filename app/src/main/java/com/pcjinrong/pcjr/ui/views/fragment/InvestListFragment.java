package com.pcjinrong.pcjr.ui.views.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import com.pcjinrong.pcjr.R;
import com.pcjinrong.pcjr.api.ApiConstant;
import com.pcjinrong.pcjr.bean.BaseBean;
import com.pcjinrong.pcjr.bean.Product;
import com.pcjinrong.pcjr.core.BaseSwipeFragment;
import com.pcjinrong.pcjr.core.mvp.MvpView;
import com.pcjinrong.pcjr.ui.adapter.ProductListAdapter;
import com.pcjinrong.pcjr.ui.presenter.InvestListPresenter;
import com.pcjinrong.pcjr.ui.views.activity.InvestDetailActivity;

import java.util.List;


/**
 * 投资列表Fragment
 * Created by Mario on 2016/5/12.
 */
public class InvestListFragment extends BaseSwipeFragment implements MvpView<BaseBean<List<Product>>> {

    private InvestListPresenter presenter;
    private ProductListAdapter adapter;

    private int type;

    private boolean isPrepared;
    private boolean mHasLoadedOnce;

    private long current_time = System.currentTimeMillis();


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
        adapter.setOnItemClickListener((view, id) -> {
            Intent intent = new Intent(getActivity(), InvestDetailActivity.class);
            intent.putExtra("id",id);
            startActivity(intent);
        });
    }

    @Override
    protected void initData() {
        type = getArguments().getInt("type");

        this.presenter = new InvestListPresenter();
        this.presenter.attachView(this);
        this.adapter = new ProductListAdapter();
        rv_list.setAdapter(this.adapter);
        isPrepared = true;
        lazyLoad();
    }

    @Override
    public void refresh() {
        refresh = true;
        emptyCount = 0;
        this.presenter.setPage(1);
        this.presenter.getInvestProductList(type, ApiConstant.DEFAULT_PAGE_SIZE);
    }

    @Override
    public void loadMore() {
        if(emptyCount < EMPTY_LIMIT && !mPtrFrame.isRefreshing()) {
            refresh = false;
            this.presenter.setPage(this.presenter.getPage() + 1);
            this.presenter.getInvestProductList(type, ApiConstant.DEFAULT_PAGE_SIZE);
        }
    }

    public static Fragment newInstance(int type) {
        InvestListFragment fragment = new InvestListFragment();
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
        showToast(getString(R.string.network_anomaly));
    }

    @Override
    public void onSuccess(BaseBean<List<Product>> data) {
        mHasLoadedOnce = true;
        mPtrFrame.refreshComplete();
        if(refresh){
            adapter.setData(data.getData(), data.getCurrent_time(),System.currentTimeMillis());
        }else{
            adapter.addAll(data.getData(), data.getCurrent_time(),System.currentTimeMillis());
        }

        if(data.getData().size() == 0 ) emptyCount++;
    }
}
