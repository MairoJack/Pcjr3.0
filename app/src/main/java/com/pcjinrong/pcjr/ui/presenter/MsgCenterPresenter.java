package com.pcjinrong.pcjr.ui.presenter;

import com.orhanobut.logger.Logger;
import com.pcjinrong.pcjr.bean.BaseBean;
import com.pcjinrong.pcjr.bean.Letter;
import com.pcjinrong.pcjr.core.mvp.BasePresenter;
import com.pcjinrong.pcjr.ui.presenter.ivview.MsgCenterView;

import java.util.List;

import rx.Subscriber;

/**
 * Created by Mario on 2016/7/25.
 */
public class MsgCenterPresenter extends BasePresenter<MsgCenterView> {
    private int page;

    public MsgCenterPresenter() {
        long time = System.currentTimeMillis();
        this.page = 1;
    }

    public void getLetterList(int page_size) {
        this.mCompositeSubscription.add(this.mDataManager.getLetterList(page,page_size)
                .subscribe(new Subscriber<BaseBean<List<Letter>>>() {
                    @Override
                    public void onCompleted() {
                        if (MsgCenterPresenter.this.mCompositeSubscription != null) {
                            MsgCenterPresenter.this.mCompositeSubscription.remove(this);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                        MsgCenterPresenter.this.getMvpView().onFailure(e);
                    }

                    @Override
                    public void onNext(BaseBean<List<Letter>> data) {
                        if (MsgCenterPresenter.this.getMvpView() != null)
                            MsgCenterPresenter.this.getMvpView().onLetterListSuccess(data.getData());
                    }
                }));
    }

    public void getLetterDetail(String id) {
        this.mCompositeSubscription.add(this.mDataManager.getLetterDetail(id)
                .subscribe(new Subscriber<BaseBean<Letter>>() {
                    @Override
                    public void onCompleted() {
                        if (MsgCenterPresenter.this.mCompositeSubscription != null) {
                            MsgCenterPresenter.this.mCompositeSubscription.remove(this);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                        MsgCenterPresenter.this.getMvpView().onFailure(e);
                    }

                    @Override
                    public void onNext(BaseBean<Letter> data) {
                        if (MsgCenterPresenter.this.getMvpView() != null)
                            MsgCenterPresenter.this.getMvpView().onLetterDetailSuccess(data.getData());
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
