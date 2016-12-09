package com.pcjinrong.pcjr.ui.presenter.ivview;

import com.pcjinrong.pcjr.bean.InterestTicket;
import com.pcjinrong.pcjr.core.mvp.MvpView;

import java.util.List;

/**
 * Created by Mario on 2016/7/26.
 */
public interface InterestTicketView extends MvpView {

    void onInterestTicketListSuccess(List<InterestTicket> list);

    void onInterestTicketDetailSuccess(InterestTicket data);

}
