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
public class InvestListPresenter extends BasePresenter<MvpView<BaseBean<List<Product>>>> {
    private int page;

    public InvestListPresenter() {
        long time = System.currentTimeMillis();
        this.page = 1;
    }

    public void getInvestProductList(int type,int page_size) {
        this.mCompositeSubscription.add(this.mDataManager.getInvestProductList(type,page,page_size)
                .subscribe(new Subscriber<BaseBean<List<Product>>>() {
                    @Override
                    public void onCompleted() {
                        if (InvestListPresenter.this.mCompositeSubscription != null) {
                            InvestListPresenter.this.mCompositeSubscription.remove(this);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                        InvestListPresenter.this.getMvpView().onFailure(e);
                    }

                    @Override
                    public void onNext(BaseBean<List<Product>> list) {
                        if (InvestListPresenter.this.getMvpView() != null)
                            InvestListPresenter.this.getMvpView().onSuccess(list);
                    }
                }));
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
