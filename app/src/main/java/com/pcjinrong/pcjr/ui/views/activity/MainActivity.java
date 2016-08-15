package com.pcjinrong.pcjr.ui.views.activity;

import android.content.Intent;
import android.os.Bundle;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.pcjinrong.pcjr.R;
import com.pcjinrong.pcjr.constant.Constant;
import com.pcjinrong.pcjr.core.BaseAppCompatActivity;
import com.pcjinrong.pcjr.ui.adapter.FragmentAdapter;
import com.pcjinrong.pcjr.utils.SPUtils;
import com.pcjinrong.pcjr.widget.FragmentNavigator;

import butterknife.BindView;

/**
 * 主Activity
 * Created by Mario on 2016/7/21.
 */
public class MainActivity extends BaseAppCompatActivity implements BottomNavigationBar.OnTabSelectedListener{

    @BindView(R.id.bottom_navigation_bar) BottomNavigationBar bottomNavigationBar;
    private FragmentNavigator mNavigator;
    private static final int DEFULT_POSITION = 0;

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
                .setFirstSelectedPosition(DEFULT_POSITION)
                .initialise();

        mNavigator = new FragmentNavigator(getSupportFragmentManager(), new FragmentAdapter(), R.id.layFrame);
        mNavigator.onCreate(savedInstanceState);
        mNavigator.setDefaultPosition(DEFULT_POSITION);
        mNavigator.showFragment(DEFULT_POSITION);
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

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mNavigator.onSaveInstanceState(outState);
    }

    @Override
    public void onTabSelected(int position) {
        if(position == 2){
            if(!Constant.IS_LOGIN){
                startActivityForResult(new Intent(MainActivity.this,LoginActivity.class),Constant.REQUSET);
                return;
            }
            if((boolean)SPUtils.get(this,"isOpenGesture",false) && !Constant.IS_GESTURE_LOGIN){
                startActivityForResult(new Intent(MainActivity.this,GestureVerifyActivity.class),Constant.REQUSET);
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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUSET && resultCode == RESULT_OK) {
            bottomNavigationBar.selectTab(2);
            mNavigator.showFragment(2);
        }else if(requestCode == Constant.LOGOUT && resultCode == RESULT_OK){
            bottomNavigationBar.selectTab(0);
            mNavigator.showFragment(0);
        }else if(requestCode == Constant.REQUSET && resultCode == RESULT_CANCELED){
            bottomNavigationBar.selectTab(0);
            mNavigator.showFragment(0);
        }
    }
}
