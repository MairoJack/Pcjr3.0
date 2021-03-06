package com.pcjinrong.pcjr.ui.views.fragment;


import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.RelativeLayout;

import com.bigkoo.alertview.AlertView;
import com.pcjinrong.pcjr.R;
import com.pcjinrong.pcjr.constant.Constant;
import com.pcjinrong.pcjr.core.BaseFragment;
import com.pcjinrong.pcjr.ui.views.activity.WebViewActivity;
import com.pcjinrong.pcjr.utils.ViewUtil;
import com.pcjinrong.pcjr.widget.Dialog;
import com.tbruyelle.rxpermissions.RxPermissions;
import butterknife.BindView;

/**
 * 更多设置Fragment
 * Created by Mario on 2016/7/21.
 */
public class MoreFragment extends BaseFragment{

    public static final String TAG = MoreFragment.class.getSimpleName();

    @BindView(R.id.news) RelativeLayout news;
    @BindView(R.id.aboutus) RelativeLayout aboutus;
    @BindView(R.id.kefu) RelativeLayout kefu;

    private RxPermissions rxPermissions;
    @Override
    protected int getLayoutId() {
        return R.layout.main_tab_more;
    }

    @Override
    protected void initViews(View self, Bundle savedInstanceState) {
         rxPermissions = new RxPermissions(getActivity());
    }

    @Override
    protected void initListeners() {
        news.setOnClickListener(v->{
            if(ViewUtil.isFastDoubleClick()) return;
            Intent intent = new Intent(getActivity(), WebViewActivity.class);
            intent.putExtra("title", Constant.PLATFORM_ANNOUNCEMENT);
            intent.putExtra("url", Constant.PLATFORM_NEWS_URL);
            startActivity(intent);
        });

        aboutus.setOnClickListener(v->{
            if(ViewUtil.isFastDoubleClick()) return;
            Intent intent = new Intent(getActivity(), WebViewActivity.class);
            intent.putExtra("title", Constant.ABOUT_US);
            intent.putExtra("url", Constant.ABOUT_US_URL);
            startActivity(intent);
        });
        kefu.setOnClickListener(v-> {

            new AlertView("皮城金融竭诚为您服务", "服务时间：8:30-20:00", "取消", null,
                    new String[]{"拨打电话 400-101-3339"},
                    getContext(), AlertView.Style.ActionSheet, (o,position)->{
                        if (position == 0) {
                            rxPermissions
                                    .requestEach(Manifest.permission.CALL_PHONE)
                                    .subscribe(permission -> {
                                        if (permission.granted) {
                                            Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:400-101-3339"));
                                            startActivity(intent);
                                        }
                                        else if (permission.shouldShowRequestPermissionRationale) {
                                        } else {
                                            Dialog.show("您未开启皮城金融拨打电话的权限，您可以在“设置-应用”中为此应用打开该权限",getContext());
                                        }
                                    });

                        }
            }).show();
        });
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void lazyLoad() {

    }

    public static Fragment newInstance(String content) {
        MoreFragment fragment = new MoreFragment();
        return fragment;
    }

}
