package com.pcjinrong.pcjr.ui.presenter.ivview;

import com.pcjinrong.pcjr.bean.BaseBean;
import com.pcjinrong.pcjr.core.mvp.MvpView;

/**
 * Created by Mario on 2016/7/26.
 */
public interface BindMobileView extends MvpView {

    void onBindMobileVerifySuccess(BaseBean data);

    void onBindMobileSuccess(BaseBean data);

    void onUnBindMobileVerifySuccess(BaseBean data);

    void onUnBindMobileSuccess(BaseBean data);
}
