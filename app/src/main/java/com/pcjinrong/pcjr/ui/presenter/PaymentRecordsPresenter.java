package com.pcjinrong.pcjr.ui.presenter;

import com.orhanobut.logger.Logger;
import com.pcjinrong.pcjr.bean.BaseBean;
import com.pcjinrong.pcjr.bean.InvestProductRepaymentInfo;
import com.pcjinrong.pcjr.bean.PaymentRecords;
import com.pcjinrong.pcjr.core.mvp.BasePresenter;
import com.pcjinrong.pcjr.core.mvp.MvpView;

import java.util.List;

import rx.Subscriber;

/**
 * Created by Mario on 2017/4/14.
 */
public class PaymentRecordsPresenter extends BasePresenter<MvpView<List<InvestProductRepaymentInfo>>> {
    private int page;
    private long sys_time;

    public PaymentRecordsPresenter() {
        this.page = 1;
    }

    public void getInvestProductRepaymentInfo(String id) {
        this.mCompositeSubscription.add(this.mDataManager.getInvestProductRepaymentInfo(id)
                .subscribe(new Subscriber<BaseBean<List<InvestProductRepaymentInfo>>>() {

                    @Override
                    public void onStart() {
                        sys_time = System.currentTimeMillis();
                    }

                    @Override
                    public void onCompleted() {
                        if (PaymentRecordsPresenter.this.mCompositeSubscription != null) {
                            PaymentRecordsPresenter.this.mCompositeSubscription.remove(this);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                        PaymentRecordsPresenter.this.getMvpView().onFailure(e);
                    }

                    @Override
                    public void onNext(BaseBean<List<InvestProductRepaymentInfo>> data) {
                        if (PaymentRecordsPresenter.this.getMvpView() != null) {
                            PaymentRecordsPresenter.this.getMvpView().onSuccess(data.getInfo());
                        }
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
