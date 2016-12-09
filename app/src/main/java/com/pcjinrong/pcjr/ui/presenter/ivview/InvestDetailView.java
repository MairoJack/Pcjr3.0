package com.pcjinrong.pcjr.ui.presenter.ivview;

import com.pcjinrong.pcjr.bean.BaseBean;
import com.pcjinrong.pcjr.bean.InterestTicket;
import com.pcjinrong.pcjr.bean.Product;
import com.pcjinrong.pcjr.bean.Withdraw;
import com.pcjinrong.pcjr.core.mvp.MvpView;

import java.util.List;


/**
 * Created by Mario on 2016/7/26.
 */
public interface InvestDetailView extends MvpView {

    void onProductInfoSuccess(BaseBean<Product> data,long sys_time);

    void onInvestInfoSuccess(BaseBean<Withdraw> data);

    void onInterestListSuccess(List<InterestTicket> list);
}
