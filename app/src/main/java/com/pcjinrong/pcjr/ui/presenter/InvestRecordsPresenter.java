package com.pcjinrong.pcjr.ui.presenter;

import com.orhanobut.logger.Logger;
import com.pcjinrong.pcjr.bean.BaseBean;
import com.pcjinrong.pcjr.bean.InvestProductDetail;
import com.pcjinrong.pcjr.bean.InvestRecords;
import com.pcjinrong.pcjr.core.mvp.BasePresenter;
import com.pcjinrong.pcjr.core.mvp.MvpView;
import com.pcjinrong.pcjr.ui.presenter.ivview.InvestRecordsView;

import java.util.List;
import rx.Subscriber;

/**
 * Created by Mario on 2016/7/25.
 */
public class InvestRecordsPresenter extends BasePresenter<InvestRecordsView> {
    private int page;

    public InvestRecordsPresenter() {
        long time = System.currentTimeMillis();
        this.page = 1;
    }

    public void getInvestRecords(int type,int page_size) {
        this.mCompositeSubscription.add(this.mDataManager.getInvestRecords(type,page,page_size)
                .subscribe(new Subscriber<BaseBean<List<InvestRecords>>>() {
                    @Override
                    public void onCompleted() {
                        if (InvestRecordsPresenter.this.mCompositeSubscription != null) {
                            InvestRecordsPresenter.this.mCompositeSubscription.remove(this);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                        InvestRecordsPresenter.this.getMvpView().onFailure(e);
                    }

                    @Override
                    public void onNext(BaseBean<List<InvestRecords>> data) {
                        if (InvestRecordsPresenter.this.getMvpView() != null)
                            InvestRecordsPresenter.this.getMvpView().onInvestRecordsListSuccess(data.getData());
                    }
                }));
    }

    public void getInvestProductDetail(String id) {
        this.mCompositeSubscription.add(this.mDataManager.getInvestProductDetail(id)
                .subscribe(new Subscriber<BaseBean<InvestProductDetail>>() {
                    @Override
                    public void onCompleted() {
                        if (InvestRecordsPresenter.this.mCompositeSubscription != null) {
                            InvestRecordsPresenter.this.mCompositeSubscription.remove(this);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                        InvestRecordsPresenter.this.getMvpView().onFailure(e);
                    }

                    @Override
                    public void onNext(BaseBean<InvestProductDetail> data) {
                        if (InvestRecordsPresenter.this.getMvpView() != null) {
                            InvestRecordsPresenter.this.getMvpView().onInvestProductDetailSuccess(data.getInfo());
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
