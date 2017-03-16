package com.pcjinrong.pcjr.ui.presenter;

import com.orhanobut.logger.Logger;
import com.pcjinrong.pcjr.bean.BaseBean;
import com.pcjinrong.pcjr.bean.IndexFocusInfo;
import com.pcjinrong.pcjr.bean.Product;
import com.pcjinrong.pcjr.core.mvp.BasePresenter;
import com.pcjinrong.pcjr.core.mvp.MvpView;
import com.pcjinrong.pcjr.ui.presenter.ivview.MainView;

import java.util.List;

import rx.Subscriber;

/**
 * Created by Mario on 2016/7/25.
 */
public class MainPresenter extends BasePresenter<MainView> {
    private int page;
    private long sys_time;

    public MainPresenter() {
        this.page = 1;
    }

    public void getIndexProductList() {
        this.mCompositeSubscription.add(this.mDataManager.getIndexProductList()
                .subscribe(new Subscriber<BaseBean<List<Product>>>() {
                    @Override
                    public void onStart() {
                        sys_time = System.currentTimeMillis();
                    }

                    @Override
                    public void onCompleted() {
                        if (MainPresenter.this.mCompositeSubscription != null) {
                            MainPresenter.this.mCompositeSubscription.remove(this);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                        MainPresenter.this.getMvpView().onFailure(e);
                    }

                    @Override
                    public void onNext(BaseBean<List<Product>> data) {
                        if (MainPresenter.this.getMvpView() != null){
                            //data.setCurrent_time(data.getCurrent_time() * 1000 + System.currentTimeMillis() - sys_time);
                            MainPresenter.this.getMvpView().onGetIndexListSuccess(data.getData(),data.getCurrent_time());
                        }
                    }
                }));
    }

    public void getIndexFocusInfo() {
        this.mCompositeSubscription.add(this.mDataManager.getIndexFocusInfo()
                .subscribe(new Subscriber<IndexFocusInfo>() {
                    @Override
                    public void onCompleted() {
                        if (MainPresenter.this.mCompositeSubscription != null) {
                            MainPresenter.this.mCompositeSubscription.remove(this);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                        MainPresenter.this.getMvpView().onFailure(e);
                    }

                    @Override
                    public void onNext(IndexFocusInfo data) {
                        if (MainPresenter.this.getMvpView() != null)
                            MainPresenter.this.getMvpView().onGetIndexFocusSuccess(data);
                    }
                }));
    }
}
