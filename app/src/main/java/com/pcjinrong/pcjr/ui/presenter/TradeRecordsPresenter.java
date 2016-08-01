package com.pcjinrong.pcjr.ui.presenter;

import com.orhanobut.logger.Logger;
import com.pcjinrong.pcjr.bean.BaseBean;
import com.pcjinrong.pcjr.bean.TradeRecords;
import com.pcjinrong.pcjr.core.mvp.BasePresenter;
import com.pcjinrong.pcjr.core.mvp.MvpView;
import java.util.List;
import rx.Subscriber;

/**
 * Created by Mario on 2016/7/25.
 */
public class TradeRecordsPresenter extends BasePresenter<MvpView<BaseBean<List<TradeRecords>>>> {
    private int page;

    public TradeRecordsPresenter() {
        long time = System.currentTimeMillis();
        this.page = 1;
    }

    public void getTradeRecords(int type,int page_size) {
        this.mCompositeSubscription.add(this.mDataManager.getTradeRecords(type,page,page_size)
                .subscribe(new Subscriber<BaseBean<List<TradeRecords>>>() {
                    @Override
                    public void onCompleted() {
                        if (TradeRecordsPresenter.this.mCompositeSubscription != null) {
                            TradeRecordsPresenter.this.mCompositeSubscription.remove(this);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                        TradeRecordsPresenter.this.getMvpView().onFailure(e);
                    }

                    @Override
                    public void onNext(BaseBean<List<TradeRecords>> data) {
                        if (TradeRecordsPresenter.this.getMvpView() != null)
                            TradeRecordsPresenter.this.getMvpView().onSuccess(data);
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
