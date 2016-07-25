package com.pcjinrong.pcjr.ui.presenter;

import com.orhanobut.logger.Logger;
import com.pcjinrong.pcjr.bean.BaseBean;
import com.pcjinrong.pcjr.bean.Product;
import com.pcjinrong.pcjr.core.mvp.BasePresenter;
import com.pcjinrong.pcjr.core.mvp.MvpView;

import java.util.List;

import rx.Subscriber;

/**
 * Created by Mario on 2016/7/25.
 */
public class MainPresenter extends BasePresenter<MvpView> {
    private int page;

    public MainPresenter() {
        long time = System.currentTimeMillis();
        this.page = 1;
    }

    public void getData() {
        this.mCompositeSubscription.add(this.mDataManager.getIndexProductList()
                .subscribe(new Subscriber<BaseBean<List<Product>>>() {
                    @Override
                    public void onCompleted() {
                        if(MainPresenter.this.mCompositeSubscription != null){
                            MainPresenter.this.mCompositeSubscription.remove(this);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.d(e.getMessage());
                        MainPresenter.this.getMvpView().onFailure(e);
                    }

                    @Override
                    public void onNext(BaseBean<List<Product>> bean) {
                        if (MainPresenter.this.getMvpView() != null)
                            MainPresenter.this.getMvpView().onSuccess(bean.getData());
                    }
                }));
    }
}
