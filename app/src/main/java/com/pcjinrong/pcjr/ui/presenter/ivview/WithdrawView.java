package com.pcjinrong.pcjr.ui.presenter.ivview;

import com.pcjinrong.pcjr.bean.BankCard;
import com.pcjinrong.pcjr.bean.BaseBean;
import com.pcjinrong.pcjr.bean.RechargeInfo;
import com.pcjinrong.pcjr.bean.Withdraw;
import com.pcjinrong.pcjr.core.mvp.MvpView;

import java.util.List;

/**
 * Created by Mario on 2016/7/26.
 */
public interface WithdrawView extends MvpView {

    void onWithdrawVerifySuccess(BaseBean data);

    void onWithdrawSuccess(BaseBean data);

    void onBankCardListSuccess(List<BankCard> list);

    void onRechargeInfoSuccess(BaseBean<RechargeInfo> data);

    void onWithdrawInfoSuccess(BaseBean<Withdraw> data);
}
