package com.pcjinrong.pcjr.ui.presenter;

import com.orhanobut.logger.Logger;
import com.pcjinrong.pcjr.bean.BaseBean;
import com.pcjinrong.pcjr.core.mvp.BasePresenter;
import com.pcjinrong.pcjr.core.mvp.MvpView;

import rx.Subscriber;

/**
 * Created by Mario on 2016/7/25.
 */
public class RealNamePresenter extends BasePresenter<MvpView> {

    public RealNamePresenter() {
        long time = System.currentTimeMillis();
    }


    public void verifyIdentity(String rela_name, String identity) {
        this.mCompositeSubscription.add(this.mDataManager.verifyIdentity(rela_name,identity)
                .subscribe(new Subscriber<BaseBean>() {
                    @Override
                    public void onCompleted() {
                        if (RealNamePresenter.this.mCompositeSubscription != null) {
                            RealNamePresenter.this.mCompositeSubscription.remove(this);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                        RealNamePresenter.this.getMvpView().onFailure(e);
                    }

                    @Override
                    public void onNext(BaseBean data) {
                        if (RealNamePresenter.this.getMvpView() != null)
                            RealNamePresenter.this.getMvpView().onSuccess(data);
                    }
                }));
    }
}
