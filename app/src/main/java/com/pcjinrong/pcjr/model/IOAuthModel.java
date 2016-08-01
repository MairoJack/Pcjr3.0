package com.pcjinrong.pcjr.model;

import com.pcjinrong.pcjr.bean.BaseBean;
import com.pcjinrong.pcjr.bean.FinanceRecords;
import com.pcjinrong.pcjr.bean.InvestRecords;
import com.pcjinrong.pcjr.bean.MemberIndex;
import com.pcjinrong.pcjr.bean.TradeRecords;

import java.util.List;

import rx.Observable;

/**
 * Created by Mario on 2016/7/25.
 */
public interface IOAuthModel {
    Observable<MemberIndex> getMemberIndex();
    Observable<FinanceRecords> getMemberFinanceData();
    Observable<BaseBean<List<InvestRecords>>> getInvestRecords(int type,int page,int page_size);
    Observable<BaseBean<List<TradeRecords>>> getTradeRecords(int type, int page, int page_size);
}
