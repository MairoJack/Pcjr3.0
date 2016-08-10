package com.pcjinrong.pcjr.ui.presenter;

import com.orhanobut.logger.Logger;
import com.pcjinrong.pcjr.bean.BaseBean;
import com.pcjinrong.pcjr.bean.RedPacket;
import com.pcjinrong.pcjr.core.mvp.BasePresenter;
import com.pcjinrong.pcjr.ui.presenter.ivview.RedPacketView;

import java.util.List;

import rx.Subscriber;

/**
 * Created by Mario on 2016/7/25.
 */
public class RedPacketPresenter extends BasePresenter<RedPacketView> {
    private int page;

    public RedPacketPresenter() {
        long time = System.currentTimeMillis();
        this.page = 1;
    }

    public void getRedPacketList(int type,int page_size) {
        this.mCompositeSubscription.add(this.mDataManager.getRedPacketList(type,page,page_size)
                .subscribe(new Subscriber<BaseBean<List<RedPacket>>>() {
                    @Override
                    public void onCompleted() {
                        if (RedPacketPresenter.this.mCompositeSubscription != null) {
                            RedPacketPresenter.this.mCompositeSubscription.remove(this);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                        RedPacketPresenter.this.getMvpView().onFailure(e);
                    }

                    @Override
                    public void onNext(BaseBean<List<RedPacket>> data) {
                        if (RedPacketPresenter.this.getMvpView() != null)
                            RedPacketPresenter.this.getMvpView().onRedPacketListSuccess(data.getData());
                    }
                }));
    }

    public void getRedPacketReward(String id) {
        this.mCompositeSubscription.add(this.mDataManager.getRedPacketReward(id)
                .subscribe(new Subscriber<BaseBean>() {
                    @Override
                    public void onCompleted() {
                        if (RedPacketPresenter.this.mCompositeSubscription != null) {
                            RedPacketPresenter.this.mCompositeSubscription.remove(this);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Logger.e(e.getMessage());
                        RedPacketPresenter.this.getMvpView().onFailure(e);
                    }

                    @Override
                    public void onNext(BaseBean data) {
                        if (RedPacketPresenter.this.getMvpView() != null)
                            RedPacketPresenter.this.getMvpView().onRedPacketRewardSuccess(data);
                    }
                }));
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }
}
