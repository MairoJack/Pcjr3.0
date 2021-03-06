package com.pcjinrong.pcjr.ui.presenter;

import com.orhanobut.logger.Logger;
import com.pcjinrong.pcjr.bean.BaseBean;
import com.pcjinrong.pcjr.bean.IdentityInfo;
import com.pcjinrong.pcjr.bean.InterestTicket;
import com.pcjinrong.pcjr.bean.Product;
import com.pcjinrong.pcjr.bean.Withdraw;
import com.pcjinrong.pcjr.core.mvp.BasePresenter;
import com.pcjinrong.pcjr.core.mvp.MvpView;
import com.pcjinrong.pcjr.ui.presenter.ivview.InvestDetailView;

import java.util.List;

import rx.Subscriber;

/**
 * Created by Mario on 2016/7/25.
 */
public class InvestDetailPresenter extends BasePresenter<InvestDetailView> {
    private long sys_time;
    public InvestDetailPresenter() {

    }

    public void getProductDetail(String id) {
        this.mCompositeSubscription.add(this.mDataManager.getProductDetail(id)
                .subscribe(new Subscriber<BaseBean<Product>>() {
                    @Override
                    public void onStart() {
                        sys_time = System.currentTimeMillis();
                    }

                    @Override
                    public void onCompleted() {
                        if (InvestDetailPresenter.this.mCompositeSubscription != null) {
                            InvestDetailPresenter.this.mCompositeSubscription.remove(this);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                        InvestDetailPresenter.this.getMvpView().onFailure(e);
                    }

                    @Override
                    public void onNext(BaseBean<Product> data) {
                        if (InvestDetailPresenter.this.getMvpView() != null)
                            InvestDetailPresenter.this.getMvpView().onProductInfoSuccess(data,sys_time);
                    }
                }));
    }

    public void getWithdrawInvestInfo() {
        this.mCompositeSubscription.add(this.mDataManager.getWithdrawInvestInfo()
                .subscribe(new Subscriber<BaseBean<Withdraw>>() {
                    @Override
                    public void onCompleted() {
                        if (InvestDetailPresenter.this.mCompositeSubscription != null) {
                            InvestDetailPresenter.this.mCompositeSubscription.remove(this);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                        InvestDetailPresenter.this.getMvpView().onFailure(e);
                    }

                    @Override
                    public void onNext(BaseBean<Withdraw> data) {
                        if (InvestDetailPresenter.this.getMvpView() != null)
                            InvestDetailPresenter.this.getMvpView().onInvestInfoSuccess(data);
                    }
                }));
    }

    public void getInterestList() {
        this.mCompositeSubscription.add(this.mDataManager.getAvailableInterestTicketList()
                .subscribe(new Subscriber<BaseBean<List<InterestTicket>>>() {
                    @Override
                    public void onCompleted() {
                        if (InvestDetailPresenter.this.mCompositeSubscription != null) {
                            InvestDetailPresenter.this.mCompositeSubscription.remove(this);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                        InvestDetailPresenter.this.getMvpView().onFailure(e);
                    }

                    @Override
                    public void onNext(BaseBean<List<InterestTicket>> data) {
                        if (InvestDetailPresenter.this.getMvpView() != null)
                            InvestDetailPresenter.this.getMvpView().onInterestListSuccess(data.getData());
                    }
                }));
    }

    public void getIdentityInfo() {
        this.mCompositeSubscription.add(this.mDataManager.getIdentityInfo()
                .subscribe(new Subscriber<BaseBean<IdentityInfo>>() {
                    @Override
                    public void onCompleted() {
                        if (InvestDetailPresenter.this.mCompositeSubscription != null) {
                            InvestDetailPresenter.this.mCompositeSubscription.remove(this);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                        InvestDetailPresenter.this.getMvpView().onFailure(e);
                    }

                    @Override
                    public void onNext(BaseBean<IdentityInfo> data) {
                        if (InvestDetailPresenter.this.getMvpView() != null)
                            InvestDetailPresenter.this.getMvpView().onIdentityInfoSuccess(data);
                    }
                }));
    }
}
