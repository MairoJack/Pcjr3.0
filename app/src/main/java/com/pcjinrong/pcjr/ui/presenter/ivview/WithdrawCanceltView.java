package com.pcjinrong.pcjr.ui.presenter.ivview;

import com.pcjinrong.pcjr.bean.BaseBean;
import com.pcjinrong.pcjr.bean.WithdrawCancel;
import com.pcjinrong.pcjr.core.mvp.MvpView;

import java.util.List;

/**
 *  Created by Mario on 2017/6/21上午9:03
 */
public interface WithdrawCanceltView extends MvpView {

    void onTodayWithdrawListSuccess(List<WithdrawCancel> list);

    void onCancelWithdrawSuccess(BaseBean data);

}
