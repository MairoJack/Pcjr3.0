package com.pcjinrong.pcjr.ui.presenter.ivview;

import com.pcjinrong.pcjr.bean.BaseBean;
import com.pcjinrong.pcjr.bean.Letter;
import com.pcjinrong.pcjr.bean.RedPacket;
import com.pcjinrong.pcjr.core.mvp.MvpView;

import java.util.List;

/**
 * Created by Mario on 2016/7/26.
 */
public interface RedPacketView extends MvpView {

    void onRedPacketListSuccess(List<RedPacket> list);

    void onRedPacketRewardSuccess(BaseBean data);

}
