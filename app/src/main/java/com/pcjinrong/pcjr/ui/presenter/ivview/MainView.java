package com.pcjinrong.pcjr.ui.presenter.ivview;

import com.pcjinrong.pcjr.bean.IndexFocusInfo;
import com.pcjinrong.pcjr.bean.Product;
import com.pcjinrong.pcjr.core.mvp.MvpView;

import java.util.List;

/**
 * Created by Mario on 2016/7/26.
 */
public interface MainView extends MvpView {


    void onGetIndexListSuccess(List<Product> list,long current_time);


    void onGetIndexFocusSuccess(IndexFocusInfo data);


}
