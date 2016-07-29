package com.pcjinrong.pcjr.model;

import com.pcjinrong.pcjr.bean.FinanceRecords;
import com.pcjinrong.pcjr.bean.MemberIndex;

import java.util.List;

import rx.Observable;

/**
 * Created by Mario on 2016/7/25.
 */
public interface IOAuthModel {
    Observable<MemberIndex> getMemberIndex();
    Observable<FinanceRecords> getMemberFinanceData();
}
