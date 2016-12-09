package com.pcjinrong.pcjr.ui.presenter;

import com.orhanobut.logger.Logger;
import com.pcjinrong.pcjr.bean.BaseBean;
import com.pcjinrong.pcjr.bean.InterestTicket;
import com.pcjinrong.pcjr.core.mvp.BasePresenter;
import com.pcjinrong.pcjr.core.mvp.MvpView;
import com.pcjinrong.pcjr.ui.presenter.ivview.InvestView;

import java.util.List;

import rx.Subscriber;

/**
 * Created by Mario on 2016/7/25.
 */
public class InvestPresenter extends BasePresenter<InvestView> {

    public InvestPresenter() {
        long time = System.currentTimeMillis();
    }

    public void investProduct(String amount, String id, String password,String interestTicketId) {
        this.mCompositeSubscription.add(this.mDataManager.investProduct(amount,id,password,interestTicketId)
                .subscribe(new Subscriber<BaseBean>() {
                    @Override
                    public void onCompleted() {
                        if (InvestPresenter.this.mCompositeSubscription != null) {
                            InvestPresenter.this.mCompositeSubscription.remove(this);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                        InvestPresenter.this.getMvpView().onFailure(e);
                    }

                    @Override
                    public void onNext(BaseBean data) {
                        if (InvestPresenter.this.getMvpView() != null)
                            InvestPresenter.this.getMvpView().onInvestSuccess(data);
                    }
                }));
    }

    public void getInterestList() {
        this.mCompositeSubscription.add(this.mDataManager.getAvailableInterestTicketList()
                .subscribe(new Subscriber<BaseBean<List<InterestTicket>>>() {
                    @Override
                    public void onCompleted() {
                        if (InvestPresenter.this.mCompositeSubscription != null) {
                            InvestPresenter.this.mCompositeSubscription.remove(this);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                        InvestPresenter.this.getMvpView().onFailure(e);
                    }

                    @Override
                    public void onNext(BaseBean<List<InterestTicket>> data) {
                        if (InvestPresenter.this.getMvpView() != null)
                            InvestPresenter.this.getMvpView().onInterestListSuccess(data.getData());
                    }
                }));
    }
}
