package com.pcjinrong.pcjr.core;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.pcjinrong.pcjr.R;
import com.pcjinrong.pcjr.widget.RecycleViewDivider;

import butterknife.BindView;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by Mario on 2016/7/29.
 */
public abstract class BaseSwipeFragment extends BaseFragment {

    @BindView(R.id.ptr_frame) protected PtrClassicFrameLayout mPtrFrame;
    @BindView(R.id.empty) protected LinearLayout empty;
    @BindView(R.id.rv_list) protected RecyclerView rv_list;

    protected boolean refresh;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initRefreshLayout();
        initLoadMoreListeners();
        initRefreshListeners();
    }

    private void initRefreshLayout(){
        LinearLayoutManager manager = new LinearLayoutManager(self.getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_list.setLayoutManager(manager);
        rv_list.addItemDecoration(new RecycleViewDivider(self.getContext(), LinearLayoutManager.HORIZONTAL, (int) getResources().getDimension(R.dimen.list_divider_height), ContextCompat.getColor(self.getContext(), R.color.color_background)));
        rv_list.setItemAnimator(new DefaultItemAnimator());
    }

    private void initRefreshListeners(){
        mPtrFrame.disableWhenHorizontalMove(true);
        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, rv_list, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                refresh();
            }
        });
    }

    private void initLoadMoreListeners(){
        this.rv_list.addOnScrollListener(this.getRecyclerViewOnScrollListener());
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

    protected abstract void refresh();
    protected abstract void loadMore();


}
