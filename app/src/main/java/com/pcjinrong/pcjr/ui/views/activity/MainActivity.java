package com.pcjinrong.pcjr.ui.views.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.orhanobut.logger.Logger;
import com.pcjinrong.pcjr.R;
import com.pcjinrong.pcjr.constant.Constant;
import com.pcjinrong.pcjr.core.BaseAppCompatActivity;
import com.pcjinrong.pcjr.ui.adapter.FragmentAdapter;
import com.pcjinrong.pcjr.utils.SPUtils;
import com.pcjinrong.pcjr.utils.UpdateManager;
import com.pcjinrong.pcjr.widget.Dialog;
import com.pcjinrong.pcjr.widget.FragmentNavigator;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;
import com.tencent.android.tpush.service.XGPushServiceV3;

import butterknife.BindView;

/**
 * 主Activity
 * Created by Mario on 2016/7/21.
 */
public class MainActivity extends BaseAppCompatActivity implements BottomNavigationBar.OnTabSelectedListener {

    @BindView(R.id.bottom_navigation_bar)
    BottomNavigationBar bottomNavigationBar;
    private FragmentNavigator mNavigator;

    private static final int DEFAULT_POSITION = 0;
    private long exitTime = 0;
    private RxPermissions rxPermissions;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.mipmap.bottom_tab_index, "主页").setActiveColorResource(R.color.colorPrimary))
                .addItem(new BottomNavigationItem(R.mipmap.bottom_tab_invest, "投资").setActiveColorResource(R.color.colorPrimary))
                .addItem(new BottomNavigationItem(R.mipmap.bottom_tab_member, "我的").setActiveColorResource(R.color.colorPrimary))
                .addItem(new BottomNavigationItem(R.mipmap.bottom_tab_news, "更多").setActiveColorResource(R.color.colorPrimary))
                .setFirstSelectedPosition(DEFAULT_POSITION)
                .initialise();

        mNavigator = new FragmentNavigator(getSupportFragmentManager(), new FragmentAdapter(), R.id.layFrame);
        mNavigator.onCreate(savedInstanceState);
        mNavigator.setDefaultPosition(DEFAULT_POSITION);
        mNavigator.showFragment(DEFAULT_POSITION);

        rxPermissions = new RxPermissions(this);
    }

    @Override
    protected void initToolbar(Bundle savedInstanceState) {

    }

    @Override
    protected void initListeners() {
        bottomNavigationBar.setTabSelectedListener(this);
    }

    @Override
    protected void initData() {
        // 检查软件更新
        UpdateManager manager = new UpdateManager(MainActivity.this);
        manager.check();

        //获取权限
        rxPermissions
                .request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(granted -> {
                    if (granted) {
                    } else {
                        Dialog.show("您可以自行在设置-应用中勾选\"存储数据\"权限", this);
                    }
                });

        // 信鸽推送
        XGAction();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mNavigator.onSaveInstanceState(outState);
    }

    @Override
    public void onTabSelected(int position) {
        if (position == 2) {
            if (!Constant.IS_LOGIN) {
                startActivityForResult(new Intent(MainActivity.this, LoginActivity.class), Constant.REQUSET);
                return;
            }
            if ((boolean) SPUtils.get(this, "isOpenGesture", false) && !Constant.IS_GESTURE_LOGIN) {
                startActivityForResult(new Intent(MainActivity.this, GestureVerifyActivity.class), Constant.REQUSET);
                return;
            }
        }
        mNavigator.showFragment(position);
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    public void tabSelect(int position) {
        bottomNavigationBar.selectTab(position);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUSET && resultCode == RESULT_OK) {
            bottomNavigationBar.selectTab(2);
            mNavigator.showFragment(2);
        } else if (requestCode == Constant.LOGOUT && resultCode == RESULT_OK) {
            bottomNavigationBar.selectTab(0);
            mNavigator.showFragment(0);
        } else if (requestCode == Constant.REQUSET && resultCode == RESULT_CANCELED) {
            bottomNavigationBar.selectTab(0);
            mNavigator.showFragment(0);
        } else if (requestCode == Constant.WITHDRAW && resultCode == RESULT_OK) {
            Dialog.show(data.getStringExtra("msg"), this);
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                showToast("再按一次退出程序");
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 开启信鸽推送
     */
    public void XGAction() {
        Context context = getApplicationContext();
        XGPushManager.registerPush(context, new XGIOperateCallback() {

            @Override
            public void onSuccess(Object data, int flag) {
                Constant.DEVICE_TOKEN = data.toString();
                Logger.i("DEVICE_TOKEN:" + data.toString());
            }

            @Override
            public void onFail(Object data, int errCode, String msg) {
                Logger.e("register push fail. token:" + data
                        + ", errCode:" + errCode + ",msg:"
                        + msg);
            }
        });

        Intent service = new Intent(context, XGPushServiceV3.class);
        context.startService(service);

    }
}
