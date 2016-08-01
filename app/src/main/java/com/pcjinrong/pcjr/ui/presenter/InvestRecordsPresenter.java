package com.pcjinrong.pcjr.ui.presenter;

import com.orhanobut.logger.Logger;
import com.pcjinrong.pcjr.bean.BaseBean;
import com.pcjinrong.pcjr.bean.InvestRecords;
import com.pcjinrong.pcjr.core.mvp.BasePresenter;
import com.pcjinrong.pcjr.core.mvp.MvpView;
import java.util.List;
import rx.Subscriber;

/**
 * Created by Mario on 2016/7/25.
 */
public class InvestRecordsPresenter extends BasePresenter<MvpView<BaseBean<List<InvestRecords>>>> {
    private int page;

    public InvestRecordsPresenter() {
        long time = System.currentTimeMillis();
        this.page = 1;
    }

    public void getInvestRecords(int type,int page_size) {
        this.mCompositeSubscription.add(this.mDataManager.getInvestRecords(type,page,page_size)
                .subscribe(new Subscriber<BaseBean<List<InvestRecords>>>() {
                    @Override
                    public void onCompleted() {
                        if (InvestRecordsPresenter.this.mCompositeSubscription != null) {
                            InvestRecordsPresenter.this.mCompositeSubscription.remove(this);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                        InvestRecordsPresenter.this.getMvpView().onFailure(e);
                    }

                    @Override
                    public void onNext(BaseBean<List<InvestRecords>> data) {
                        if (InvestRecordsPresenter.this.getMvpView() != null)
                            InvestRecordsPresenter.this.getMvpView().onSuccess(data);
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
