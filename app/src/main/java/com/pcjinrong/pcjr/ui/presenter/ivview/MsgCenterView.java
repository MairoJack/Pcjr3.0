package com.pcjinrong.pcjr.ui.presenter.ivview;

import com.pcjinrong.pcjr.bean.Letter;
import com.pcjinrong.pcjr.core.mvp.MvpView;

import java.util.List;

/**
 * Created by Mario on 2016/7/26.
 */
public interface MsgCenterView extends MvpView {

    void onLetterListSuccess(List<Letter> list);

    void onLetterDetailSuccess(Letter data);

}
