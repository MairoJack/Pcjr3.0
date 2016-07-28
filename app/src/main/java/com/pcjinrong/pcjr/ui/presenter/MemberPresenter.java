package com.pcjinrong.pcjr.ui.presenter;

import com.orhanobut.logger.Logger;
import com.pcjinrong.pcjr.bean.MemberIndex;
import com.pcjinrong.pcjr.bean.Token;
import com.pcjinrong.pcjr.core.mvp.BasePresenter;
import com.pcjinrong.pcjr.core.mvp.MvpView;

import rx.Subscriber;

/**
 * Created by Mario on 2016/7/25.
 */
public class MemberPresenter extends BasePresenter<MvpView<MemberIndex>> {

    public MemberPresenter() {
        long time = System.currentTimeMillis();
    }

    public void getMemberIndex() {
        this.mCompositeSubscription.add(this.mDataManager.getMemberIndex()
                .subscribe(new Subscriber<MemberIndex>() {
                    @Override
                    public void onCompleted() {
                        if (MemberPresenter.this.mCompositeSubscription != null) {
                            MemberPresenter.this.mCompositeSubscription.remove(this);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                        MemberPresenter.this.getMvpView().onFailure(e);
                    }

                    @Override
                    public void onNext(MemberIndex data) {
                        if (MemberPresenter.this.getMvpView() != null)
                            MemberPresenter.this.getMvpView().onSuccess(data);
                    }
                }));
    }
}
