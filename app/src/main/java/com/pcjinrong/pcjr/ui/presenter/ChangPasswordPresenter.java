package com.pcjinrong.pcjr.ui.presenter;

import com.orhanobut.logger.Logger;
import com.pcjinrong.pcjr.bean.BaseBean;
import com.pcjinrong.pcjr.core.mvp.BasePresenter;
import com.pcjinrong.pcjr.core.mvp.MvpView;

import rx.Subscriber;

/**
 * Created by Mario on 2016/7/25.
 */
public class ChangPasswordPresenter extends BasePresenter<MvpView> {

    public ChangPasswordPresenter() {
        long time = System.currentTimeMillis();
    }


    public void changePassword(String old_password, String new_password) {
        this.mCompositeSubscription.add(this.mDataManager.changePassword(old_password,new_password)
                .subscribe(new Subscriber<BaseBean>() {
                    @Override
                    public void onCompleted() {
                        if (ChangPasswordPresenter.this.mCompositeSubscription != null) {
                            ChangPasswordPresenter.this.mCompositeSubscription.remove(this);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                        ChangPasswordPresenter.this.getMvpView().onFailure(e);
                    }

                    @Override
                    public void onNext(BaseBean data) {
                        if (ChangPasswordPresenter.this.getMvpView() != null)
                            ChangPasswordPresenter.this.getMvpView().onSuccess(data);
                    }
                }));
    }
}
