package com.pcjinrong.pcjr.ui.presenter;

import com.orhanobut.logger.Logger;
import com.pcjinrong.pcjr.bean.BaseBean;
import com.pcjinrong.pcjr.core.mvp.BasePresenter;
import com.pcjinrong.pcjr.core.mvp.MvpView;

import rx.Subscriber;

/**
 * Created by Mario on 2016/7/25.
 */
public class InvestPresenter extends BasePresenter<MvpView<BaseBean>> {

    public InvestPresenter() {
        long time = System.currentTimeMillis();
    }

    public void investProduct(String amount, String id, String password) {
        this.mCompositeSubscription.add(this.mDataManager.investProduct(amount,id,password)
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
                            InvestPresenter.this.getMvpView().onSuccess(data);
                    }
                }));
    }
}
