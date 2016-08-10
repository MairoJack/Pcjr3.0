package com.pcjinrong.pcjr.ui.presenter.ivview;

import com.pcjinrong.pcjr.bean.BaseBean;
import com.pcjinrong.pcjr.bean.Coupon;
import com.pcjinrong.pcjr.bean.FinanceRecords;
import com.pcjinrong.pcjr.bean.IndexFocusInfo;
import com.pcjinrong.pcjr.bean.MemberIndex;
import com.pcjinrong.pcjr.bean.Product;
import com.pcjinrong.pcjr.bean.Withdraw;
import com.pcjinrong.pcjr.core.mvp.MvpView;

import java.util.List;

/**
 * Created by Mario on 2016/7/26.
 */
public interface MemberView extends MvpView {

    void onMemberIndexSuccess(MemberIndex data);

    void onFinancialRecordsSuccess(FinanceRecords data);

    void onWithdrawInfoSuccess(BaseBean<Withdraw> data);

    void onCouponNumSuccess(Coupon data);
}
