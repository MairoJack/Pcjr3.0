package com.pcjinrong.pcjr.ui.presenter;

import com.orhanobut.logger.Logger;
import com.pcjinrong.pcjr.bean.BaseBean;
import com.pcjinrong.pcjr.bean.InvestProductRepaymentInfo;
import com.pcjinrong.pcjr.bean.WithdrawCancel;
import com.pcjinrong.pcjr.core.mvp.BasePresenter;
import com.pcjinrong.pcjr.ui.presenter.ivview.WithdrawCanceltView;

import java.util.List;

import rx.Subscriber;

/**
 *  提现取消
 *  Created by Mario on 2017/6/21上午9:03
 */
public class WithdrawCancelPresenter extends BasePresenter<WithdrawCanceltView> {
    private int page;

    public WithdrawCancelPresenter() {
        this.page = 1;
    }

    public void getWithdrawCancel() {
        this.mCompositeSubscription.add(this.mDataManager.getTodayWithdrawList()
                .subscribe(new Subscriber<BaseBean<List<WithdrawCancel>>>() {

                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onCompleted() {
                        if (WithdrawCancelPresenter.this.mCompositeSubscription != null) {
                            WithdrawCancelPresenter.this.mCompositeSubscription.remove(this);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                        WithdrawCancelPresenter.this.getMvpView().onFailure(e);
                    }

                    @Override
                    public void onNext(BaseBean<List<WithdrawCancel>> data) {
                        if (WithdrawCancelPresenter.this.getMvpView() != null) {
                            WithdrawCancelPresenter.this.getMvpView().onTodayWithdrawListSuccess(data.getInfo());
                        }
                    }
                }));
    }

    public void cancelWithdraw(String id) {
        this.mCompositeSubscription.add(this.mDataManager.cancelWithdraw(id)
                .subscribe(new Subscriber<BaseBean>() {
                    @Override
                    public void onCompleted() {
                        if (WithdrawCancelPresenter.this.mCompositeSubscription != null) {
                            WithdrawCancelPresenter.this.mCompositeSubscription.remove(this);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                        WithdrawCancelPresenter.this.getMvpView().onFailure(e);
                    }

                    @Override
                    public void onNext(BaseBean data) {
                        if (WithdrawCancelPresenter.this.getMvpView() != null)
                            WithdrawCancelPresenter.this.getMvpView().onCancelWithdrawSuccess(data);
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
