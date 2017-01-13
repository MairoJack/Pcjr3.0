package com.pcjinrong.pcjr.ui.presenter.ivview;

import com.pcjinrong.pcjr.bean.BaseBean;
import com.pcjinrong.pcjr.bean.IdentityInfo;
import com.pcjinrong.pcjr.bean.MobileInfo;
import com.pcjinrong.pcjr.bean.RiskAssessmentScore;
import com.pcjinrong.pcjr.core.mvp.MvpView;

/**
 * Created by Mario on 2016/7/26.
 */
public interface SafeSettingView extends MvpView {

    void onMobileInfoSuccess(BaseBean<MobileInfo> data);

    void onLogoutSuccess(BaseBean data);

    void onIdentityInfoSuccess(BaseBean<IdentityInfo> data);

    void onRiskAssessmentScoreSuccess(RiskAssessmentScore data);

}
