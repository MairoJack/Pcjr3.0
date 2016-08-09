package com.pcjinrong.pcjr.ui.presenter.ivview;

import com.pcjinrong.pcjr.bean.BankCard;
import com.pcjinrong.pcjr.bean.BaseBean;
import com.pcjinrong.pcjr.core.mvp.MvpView;

import java.util.List;

/**
 * Created by Mario on 2016/7/26.
 */
public interface BankCardView extends MvpView {

    void onBankCardListSuccess(List<BankCard> list,String realname);

    void onAddBankCardSuccess(BaseBean data);

    void onDelBankCardSuccess(BaseBean data);

}
