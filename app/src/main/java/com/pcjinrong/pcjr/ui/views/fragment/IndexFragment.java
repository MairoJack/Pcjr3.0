package com.pcjinrong.pcjr.ui.views.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.pcjinrong.pcjr.R;
import com.pcjinrong.pcjr.bean.Product;
import com.pcjinrong.pcjr.core.BaseFragment;
import com.pcjinrong.pcjr.core.mvp.MvpView;
import com.pcjinrong.pcjr.ui.adapter.ProductListAdapter;
import com.pcjinrong.pcjr.ui.presenter.MainPresenter;
import com.pcjinrong.pcjr.widget.RecycleViewDivider;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 首页Fragment
 * Created by Mario on 2016/7/21.
 */
public class IndexFragment extends BaseFragment implements MvpView<List<Product>>{

    public static final String TAG = IndexFragment.class.getSimpleName();

    @BindView(R.id.rv_list) RecyclerView rv_list;
    @BindView(R.id.img_cpyg)
    ImageView img_cpyg;
    private MainPresenter presenter;
    private ProductListAdapter adapter;

    @OnClick(R.id.cpyg) void refresh(){
        refreshData();
    }
    @Override
    protected int getLayoutId() {
        return R.layout.main_tab_index;
    }

    @Override
    protected void initViews(View self, Bundle savedInstanceState) {
        ButterKnife.bind(this,self);
        LinearLayoutManager manager = new LinearLayoutManager(self.getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_list.setLayoutManager(manager);
        rv_list.addItemDecoration(new RecycleViewDivider(self.getContext(),LinearLayoutManager.HORIZONTAL,(int)getResources().getDimension(R.dimen.list_divider_height), ContextCompat.getColor(self.getContext(),R.color.color_background)));
    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void initData() {
        this.presenter = new MainPresenter();
        this.presenter.attachView(this);
        this.adapter = new ProductListAdapter();
        this.rv_list.setAdapter(this.adapter);
        refreshData();
    }

    @Override
    protected void lazyLoad() {

    }

    public static Fragment newInstance(String content) {
        IndexFragment fragment = new IndexFragment();
        return fragment;
    }

    @Override
    public void onFailure(Throwable e) {

    }

    @Override
    public void onSuccess(List<Product> data) {
        adapter.setData(data);
    }

    private void refreshData() {
        this.presenter.getData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.presenter.detachView();
    }
}
