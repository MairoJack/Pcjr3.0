package com.pcjinrong.pcjr.ui.presenter;

import com.orhanobut.logger.Logger;
import com.pcjinrong.pcjr.bean.BaseBean;
import com.pcjinrong.pcjr.bean.InterestTicket;
import com.pcjinrong.pcjr.core.mvp.BasePresenter;
import com.pcjinrong.pcjr.ui.presenter.ivview.InterestTicketView;

import java.util.List;

import rx.Subscriber;

/**
 * Created by Mario on 2016/7/25.
 */
public class InterestTicketPresenter extends BasePresenter<InterestTicketView> {
    private int page;

    public InterestTicketPresenter() {
        long time = System.currentTimeMillis();
        this.page = 1;
    }

    public void getInterestTicketList(int type,int page_size) {
        this.mCompositeSubscription.add(this.mDataManager.getInterestTicketList(type,page,page_size)
                .subscribe(new Subscriber<BaseBean<List<InterestTicket>>>() {
                    @Override
                    public void onCompleted() {
                        if (InterestTicketPresenter.this.mCompositeSubscription != null) {
                            InterestTicketPresenter.this.mCompositeSubscription.remove(this);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                        InterestTicketPresenter.this.getMvpView().onFailure(e);
                    }

                    @Override
                    public void onNext(BaseBean<List<InterestTicket>> data) {
                        if (InterestTicketPresenter.this.getMvpView() != null)
                            InterestTicketPresenter.this.getMvpView().onInterestTicketListSuccess(data.getData());
                    }
                }));
    }

    public void getInterestTicketDetail(String id) {
        this.mCompositeSubscription.add(this.mDataManager.getInterestTicketDetail(id)
                .subscribe(new Subscriber<BaseBean<InterestTicket>>() {
                    @Override
                    public void onCompleted() {
                        if (InterestTicketPresenter.this.mCompositeSubscription != null) {
                            InterestTicketPresenter.this.mCompositeSubscription.remove(this);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                        InterestTicketPresenter.this.getMvpView().onFailure(e);
                    }

                    @Override
                    public void onNext(BaseBean<InterestTicket> data) {
                        if (InterestTicketPresenter.this.getMvpView() != null)
                            InterestTicketPresenter.this.getMvpView().onInterestTicketDetailSuccess(data.getData());
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
