package com.pcjinrong.pcjr.ui.views.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.pcjinrong.pcjr.R;
import com.pcjinrong.pcjr.bean.Withdraw;
import com.pcjinrong.pcjr.constant.Constant;
import com.pcjinrong.pcjr.core.BaseToolbarActivity;
import com.pcjinrong.pcjr.ui.adapter.TabFragmentAdapter;
import com.pcjinrong.pcjr.ui.views.fragment.RechargeFragment;
import com.pcjinrong.pcjr.ui.views.fragment.RedPacketFragment;
import com.pcjinrong.pcjr.ui.views.fragment.WithdrawFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 提现/充值
 * Created by Mario on 2016/5/24.
 */
public class WithdrawRechargeActivity extends BaseToolbarActivity {

    @BindView(R.id.tab_layout) TabLayout tabLayout;
    @BindView(R.id.tab_viewpager) ViewPager viewPager;

    private FragmentPagerAdapter fragmentPagerAdapter;

    private List<Fragment> fragmentList;
    private List<String> titleList;


    @Override
    protected int getLayoutId() {
        return R.layout.member_coupon_invest_ticket;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        showBack();
        setTitle("提现/充值");

        fragmentList = new ArrayList<>();
        fragmentList.add(WithdrawFragment.newInstance());
        fragmentList.add(RechargeFragment.newInstance());

        titleList = new ArrayList<>();
        titleList.add("提现");
        titleList.add("充值");

        tabLayout.addTab(tabLayout.newTab().setText(titleList.get(0)));
        tabLayout.addTab(tabLayout.newTab().setText(titleList.get(1)));

        fragmentPagerAdapter = new TabFragmentAdapter(getSupportFragmentManager(),fragmentList,titleList);
        viewPager.setAdapter(fragmentPagerAdapter);
        viewPager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK ) finish();
        return false;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_withdraw, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.cancel) {
            Intent intent = new Intent(WithdrawRechargeActivity.this, WithdrawCancelActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }



    public void select(){
        tabLayout.getTabAt(0).select();
    }
}
