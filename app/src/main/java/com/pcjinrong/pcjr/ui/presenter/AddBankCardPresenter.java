package com.pcjinrong.pcjr.ui.presenter;

import com.orhanobut.logger.Logger;
import com.pcjinrong.pcjr.bean.PayBean;
import com.pcjinrong.pcjr.core.mvp.BasePresenter;
import com.pcjinrong.pcjr.ui.presenter.ivview.AddBankCardView;

import rx.Subscriber;

/**
 * Created by Mario on 2016/7/25.
 */
public class AddBankCardPresenter extends BasePresenter<AddBankCardView> {

    public AddBankCardPresenter() {
        long time = System.currentTimeMillis();
    }

    public void bindCard(String memberID, String cardNo, String phone) {
        this.mCompositeSubscription.add(this.mDataManager.bindCard(memberID,cardNo,phone)
                .subscribe(new Subscriber<PayBean>() {
                    @Override
                    public void onCompleted() {
                        if (AddBankCardPresenter.this.mCompositeSubscription != null) {
                            AddBankCardPresenter.this.mCompositeSubscription.remove(this);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                        AddBankCardPresenter.this.getMvpView().onFailure(e);
                    }

                    @Override
                    public void onNext(PayBean data) {
                        if (AddBankCardPresenter.this.getMvpView() != null)
                            AddBankCardPresenter.this.getMvpView().onBindCardSuccess(data);
                    }
                }));
    }

    public void addBankCard(String requestId, String validatecode) {
        this.mCompositeSubscription.add(this.mDataManager.confirmBindCard(requestId, validatecode)
                .subscribe(new Subscriber<PayBean>() {
                    @Override
                    public void onCompleted() {
                        if (AddBankCardPresenter.this.mCompositeSubscription != null) {
                            AddBankCardPresenter.this.mCompositeSubscription.remove(this);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                        AddBankCardPresenter.this.getMvpView().onFailure(e);
                    }

                    @Override
                    public void onNext(PayBean data) {
                        if (AddBankCardPresenter.this.getMvpView() != null)
                            AddBankCardPresenter.this.getMvpView().onAddBankCardSuccess(data);
                    }
                }));
    }
}
