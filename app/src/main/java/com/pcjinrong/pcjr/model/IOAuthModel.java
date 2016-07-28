package com.pcjinrong.pcjr.model;

import com.pcjinrong.pcjr.bean.MemberIndex;

import rx.Observable;

/**
 * Created by Mario on 2016/7/25.
 */
public interface IOAuthModel {
    Observable<MemberIndex> getMemberIndex(String access_token);

}
