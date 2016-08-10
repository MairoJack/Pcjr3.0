package com.pcjinrong.pcjr.ui.presenter;

import com.orhanobut.logger.Logger;
import com.pcjinrong.pcjr.bean.BaseBean;
import com.pcjinrong.pcjr.bean.InvestTicket;
import com.pcjinrong.pcjr.core.mvp.BasePresenter;
import com.pcjinrong.pcjr.ui.presenter.ivview.InvestTicketView;

import java.util.List;

import rx.Subscriber;

/**
 * Created by Mario on 2016/7/25.
 */
public class InvestTicketPresenter extends BasePresenter<InvestTicketView> {
    private int page;

    public InvestTicketPresenter() {
        long time = System.currentTimeMillis();
        this.page = 1;
    }

    public void getInvestTicketList(int type,int page_size) {
        this.mCompositeSubscription.add(this.mDataManager.getInvestTicketList(type,page,page_size)
                .subscribe(new Subscriber<BaseBean<List<InvestTicket>>>() {
                    @Override
                    public void onCompleted() {
                        if (InvestTicketPresenter.this.mCompositeSubscription != null) {
                            InvestTicketPresenter.this.mCompositeSubscription.remove(this);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                        InvestTicketPresenter.this.getMvpView().onFailure(e);
                    }

                    @Override
                    public void onNext(BaseBean<List<InvestTicket>> data) {
                        if (InvestTicketPresenter.this.getMvpView() != null)
                            InvestTicketPresenter.this.getMvpView().onInvestTicketListSuccess(data.getData());
                    }
                }));
    }

    public void getInvestTicketDetail(String id) {
        this.mCompositeSubscription.add(this.mDataManager.getInvestTicketDetail(id)
                .subscribe(new Subscriber<BaseBean<InvestTicket>>() {
                    @Override
                    public void onCompleted() {
                        if (InvestTicketPresenter.this.mCompositeSubscription != null) {
                            InvestTicketPresenter.this.mCompositeSubscription.remove(this);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                        InvestTicketPresenter.this.getMvpView().onFailure(e);
                    }

                    @Override
                    public void onNext(BaseBean<InvestTicket> data) {
                        if (InvestTicketPresenter.this.getMvpView() != null)
                            InvestTicketPresenter.this.getMvpView().onInvestTicketDetailSuccess(data.getData());
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
