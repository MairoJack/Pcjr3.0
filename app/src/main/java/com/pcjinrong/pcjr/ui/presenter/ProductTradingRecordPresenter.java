package com.pcjinrong.pcjr.ui.presenter;

import com.orhanobut.logger.Logger;
import com.pcjinrong.pcjr.bean.BaseBean;
import com.pcjinrong.pcjr.bean.ProductTradingRecord;
import com.pcjinrong.pcjr.core.mvp.BasePresenter;
import com.pcjinrong.pcjr.core.mvp.MvpView;

import java.util.List;

import rx.Subscriber;

/**
 * Created by Mario on 2016/7/25.
 */
public class ProductTradingRecordPresenter extends BasePresenter<MvpView<BaseBean<List<ProductTradingRecord>>>> {
    private int page;

    public ProductTradingRecordPresenter() {
        long time = System.currentTimeMillis();
        this.page = 1;
    }

    public void getProductTradingRecordList(String id,int page_size) {
        this.mCompositeSubscription.add(this.mDataManager.getProductTradingRecordList(id,page,page_size)
                .subscribe(new Subscriber<BaseBean<List<ProductTradingRecord>>>() {
                    @Override
                    public void onCompleted() {
                        if (ProductTradingRecordPresenter.this.mCompositeSubscription != null) {
                            ProductTradingRecordPresenter.this.mCompositeSubscription.remove(this);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                        ProductTradingRecordPresenter.this.getMvpView().onFailure(e);
                    }

                    @Override
                    public void onNext(BaseBean<List<ProductTradingRecord>> data) {
                        if (ProductTradingRecordPresenter.this.getMvpView() != null)
                            ProductTradingRecordPresenter.this.getMvpView().onSuccess(data);
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
