package com.pcjinrong.pcjr.ui.presenter.ivview;

import com.pcjinrong.pcjr.bean.Coupon;
import com.pcjinrong.pcjr.bean.FinanceRecords;
import com.pcjinrong.pcjr.bean.MemberIndex;
import com.pcjinrong.pcjr.core.mvp.MvpView;

/**
 * Created by Mario on 2016/7/26.
 */
public interface MemberView extends MvpView {

    void onMemberIndexSuccess(MemberIndex data);

    void onFinancialRecordsSuccess(FinanceRecords data);

    void onCouponNumSuccess(Coupon data);
}
