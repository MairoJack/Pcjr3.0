package com.pcjinrong.pcjr.ui.views.fragment;

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


import java.util.ArrayList;
import java.util.List;


/**
 * 投资列表Fragment
 * Created by Mario on 2016/5/12.
 */
public class InvestListFragment extends BaseSwipeFragment implements MvpView<BaseBean<List<Product>>> {

    private InvestListPresenter presenter;
    private ProductListAdapter adapter;

    private int type;
    private int emptyCount = 0;
    private static final int EMPTY_LIMIT = 2;
    private List<Product> list = new ArrayList<>();

    private boolean isPrepared;
    private boolean mHasLoadedOnce;

    private long current_time = System.currentTimeMillis();


    @Override
    protected int getLayoutId() {
        return R.layout.invest_list;
    }

    @Override
    protected void initViews(View self, Bundle savedInstanceState) {

    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void initData() {
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
        showToast("网络异常");
    }

    @Override
    public void onSuccess(BaseBean<List<Product>> data) {
        mHasLoadedOnce = true;
        mPtrFrame.refreshComplete();
        if(refresh){
            adapter.setData(data.getData(), data.getCurrent_time());
        }else{
            adapter.addAll(data.getData(), data.getCurrent_time());
        }

        if(data.getData().size() == 0 ) emptyCount++;
    }
}
