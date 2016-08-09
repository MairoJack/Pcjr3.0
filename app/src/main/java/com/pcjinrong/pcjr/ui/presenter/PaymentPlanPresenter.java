package com.pcjinrong.pcjr.ui.presenter;

import com.orhanobut.logger.Logger;
import com.pcjinrong.pcjr.bean.BaseBean;
import com.pcjinrong.pcjr.bean.PaymentPlan;
import com.pcjinrong.pcjr.core.mvp.BasePresenter;
import com.pcjinrong.pcjr.core.mvp.MvpView;
import com.pcjinrong.pcjr.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rx.Subscriber;

/**
 * Created by Mario on 2016/7/25.
 */
public class PaymentPlanPresenter extends BasePresenter<MvpView<BaseBean<List<PaymentPlan>>>> {
    private int page;

    public PaymentPlanPresenter() {
        long time = System.currentTimeMillis();
        this.page = 1;
    }

    public void getPaymentPlan(int year,int month) {
        this.mCompositeSubscription.add(this.mDataManager.getPaymentPlan(year,month)
                .subscribe(new Subscriber<BaseBean<List<PaymentPlan>>>() {
                    @Override
                    public void onCompleted() {
                        if (PaymentPlanPresenter.this.mCompositeSubscription != null) {
                            PaymentPlanPresenter.this.mCompositeSubscription.remove(this);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                        PaymentPlanPresenter.this.getMvpView().onFailure(e);
                    }

                    @Override
                    public void onNext(BaseBean<List<PaymentPlan>> data) {
                        if (PaymentPlanPresenter.this.getMvpView() != null)
                            PaymentPlanPresenter.this.getMvpView().onSuccess(data);
                    }
                }));
    }

    public  List<PaymentPlan> getPaymentPlanByDay(int day,List<PaymentPlan> list) {
        List<PaymentPlan> day_list = new ArrayList<>();
        for (PaymentPlan pp : list) {
            Date date = new Date(pp.getEstimated_time() * 1000);
            if (DateUtils.getDayOfDate(date) == day) {
                day_list.add(pp);
            }
        }
        return day_list;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
