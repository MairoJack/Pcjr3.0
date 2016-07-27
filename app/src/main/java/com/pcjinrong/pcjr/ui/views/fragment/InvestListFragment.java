package com.pcjinrong.pcjr.ui.views.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.pcjinrong.pcjr.R;

import com.pcjinrong.pcjr.api.ApiConstant;
import com.pcjinrong.pcjr.bean.BaseBean;
import com.pcjinrong.pcjr.bean.Product;
import com.pcjinrong.pcjr.constant.Constant;
import com.pcjinrong.pcjr.core.BaseFragment;
import com.pcjinrong.pcjr.core.mvp.MvpView;
import com.pcjinrong.pcjr.ui.adapter.ProductListAdapter;
import com.pcjinrong.pcjr.ui.presenter.InvestListPresenter;
import com.pcjinrong.pcjr.ui.presenter.MainPresenter;
import com.pcjinrong.pcjr.ui.presenter.ivview.MainView;
import com.pcjinrong.pcjr.widget.RecycleViewDivider;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import in.srain.cube.views.loadmore.LoadMoreContainer;
import in.srain.cube.views.loadmore.LoadMoreHandler;
import in.srain.cube.views.loadmore.LoadMoreListViewContainer;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 投资列表Fragment
 * Created by Mario on 2016/5/12.
 */
public class InvestListFragment extends BaseFragment implements MvpView<BaseBean<List<Product>>> {
    @BindView(R.id.ptr_frame) PtrClassicFrameLayout mPtrFrame;
    @BindView(R.id.empty) LinearLayout empty;
    @BindView(R.id.rv_list) RecyclerView rv_list;

    private InvestListPresenter presenter;
    private ProductListAdapter adapter;

    private int type;
    private int page = 1;
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
        ButterKnife.bind(this, self);
        LinearLayoutManager manager = new LinearLayoutManager(self.getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_list.setLayoutManager(manager);
        rv_list.addItemDecoration(new RecycleViewDivider(self.getContext(), LinearLayoutManager.HORIZONTAL, (int) getResources().getDimension(R.dimen.list_divider_height), ContextCompat.getColor(self.getContext(), R.color.color_background)));

    }

    @Override
    protected void initListeners() {
    }

    @Override
    protected void initData() {
        this.presenter = new InvestListPresenter();
        this.presenter.attachView(this);
        this.adapter = new ProductListAdapter();
        this.rv_list.setAdapter(this.adapter);

        mPtrFrame.disableWhenHorizontalMove(true);
        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, rv_list, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                page = 1;
                refresh();
            }
        });

        isPrepared = true;
        lazyLoad();
    }




    public RecyclerView.OnScrollListener getRecyclerViewOnScrollListener() {
        return new RecyclerView.OnScrollListener() {
            private boolean toLast = false;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                toLast = dy > 0 ? true : false;
            }
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager manager = (LinearLayoutManager) layoutManager;
                    if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                        if (toLast && manager.findLastCompletelyVisibleItemPosition() ==
                                (manager.getItemCount() - 1)) {
                            loadMore();
                        }
                    }
                }
            }
        };
    }

    public void refresh() {
        this.presenter.getInvestProductList(type, page, ApiConstant.DEFAULT_PAGE_SIZE);
    }

    public void loadMore() {
        this.presenter.getInvestProductList(type, page, ApiConstant.DEFAULT_PAGE_SIZE);
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
        //自动刷新
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
        adapter.setData(data.getData(), data.getCurrent_time());
    }
}
