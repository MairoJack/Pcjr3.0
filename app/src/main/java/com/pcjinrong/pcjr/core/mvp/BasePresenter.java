package com.pcjinrong.pcjr.core.mvp;


import com.pcjinrong.pcjr.data.DataManager;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by Mario on 2016/7/8.
 */
public class BasePresenter<T extends MvpView> implements Presenter<T>{

    private T mMvpView;
    public CompositeSubscription mCompositeSubscription;
    public DataManager mDataManager;

    @Override
    public void attachView(T mvpView) {
        this.mMvpView = mvpView;
        this.mCompositeSubscription = new CompositeSubscription();
        this.mDataManager = DataManager.getInstance();
    }

    @Override
    public void detachView() {
        this.mMvpView = null;
        this.mCompositeSubscription.unsubscribe();
        this.mCompositeSubscription = null;
        this.mDataManager = null;
    }

    public boolean isViewAttached(){
        return mMvpView != null;
    }

    public T getMvpView(){
        return mMvpView;
    }

    public void checkViewAttached(){
        if(!isViewAttached()) throw new MvpViewNotAttachedException();
    }

    public static class MvpViewNotAttachedException extends RuntimeException{
        public MvpViewNotAttachedException(){
            super("Please call Presenter.attachView(MvpView) before" +
                    " requesting data to the Presenter");
        }
    }
}
