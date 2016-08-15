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
    private long sys_time;

    public InvestListPresenter() {
        this.page = 1;
    }

    public void getInvestProductList(int type,int page_size) {
        this.mCompositeSubscription.add(this.mDataManager.getInvestProductList(type,page,page_size)
                .subscribe(new Subscriber<BaseBean<List<Product>>>() {

                    @Override
                    public void onStart() {
                        sys_time = System.currentTimeMillis();
                    }

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
                    public void onNext(BaseBean<List<Product>> data) {
                        if (InvestListPresenter.this.getMvpView() != null) {
                            data.setCurrent_time(data.getCurrent_time() + System.currentTimeMillis() - sys_time);
                            InvestListPresenter.this.getMvpView().onSuccess(data);
                        }
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
