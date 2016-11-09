package com.pcjinrong.pcjr.ui.presenter.ivview;

import com.pcjinrong.pcjr.bean.PayBean;
import com.pcjinrong.pcjr.core.mvp.MvpView;


/**
 * Created by Mario on 2016/7/26.
 */
public interface RechargeView extends MvpView {

    void onRechargeVerifySuccess(PayBean data);

    void onRechargeSuccess(PayBean data);

}
