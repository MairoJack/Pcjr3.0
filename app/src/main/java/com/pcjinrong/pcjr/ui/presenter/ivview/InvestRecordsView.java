package com.pcjinrong.pcjr.ui.presenter.ivview;

import com.pcjinrong.pcjr.bean.BaseBean;
import com.pcjinrong.pcjr.bean.InterestTicket;
import com.pcjinrong.pcjr.bean.InvestProductDetail;
import com.pcjinrong.pcjr.bean.InvestRecords;
import com.pcjinrong.pcjr.core.mvp.MvpView;

import java.util.List;

/**
 * Created by Mario on 2017/4/19.
 */
public interface InvestRecordsView extends MvpView {

    void onInvestRecordsListSuccess(List<InvestRecords> data);

    void onInvestProductDetailSuccess(InvestProductDetail data);

}
