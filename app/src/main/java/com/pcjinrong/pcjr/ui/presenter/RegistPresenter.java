package com.pcjinrong.pcjr.ui.presenter;

import com.orhanobut.logger.Logger;
import com.pcjinrong.pcjr.bean.BaseBean;
import com.pcjinrong.pcjr.core.mvp.BasePresenter;
import com.pcjinrong.pcjr.core.mvp.MvpView;

import rx.Subscriber;

/**
 * Created by Mario on 2016/7/25.
 */
public class RegistPresenter extends BasePresenter<MvpView<BaseBean>> {

    public RegistPresenter() {
        long time = System.currentTimeMillis();
    }

    public void register(String name, String password, String recommend_person) {
        this.mCompositeSubscription.add(this.mDataManager.register(name, password,recommend_person)
                .subscribe(new Subscriber<BaseBean>() {
                    @Override
                    public void onCompleted() {
                        if (RegistPresenter.this.mCompositeSubscription != null) {
                            RegistPresenter.this.mCompositeSubscription.remove(this);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                        RegistPresenter.this.getMvpView().onFailure(e);
                    }

                    @Override
                    public void onNext(BaseBean data) {
                        if (RegistPresenter.this.getMvpView() != null)
                            RegistPresenter.this.getMvpView().onSuccess(data);
                    }
                }));
    }
}
