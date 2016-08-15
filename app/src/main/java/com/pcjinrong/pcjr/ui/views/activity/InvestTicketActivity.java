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

import com.pcjinrong.pcjr.R;
import com.pcjinrong.pcjr.constant.Constant;
import com.pcjinrong.pcjr.core.BaseToolbarActivity;
import com.pcjinrong.pcjr.ui.adapter.TabFragmentAdapter;
import com.pcjinrong.pcjr.ui.views.fragment.InvestTicketFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 投资券
 * Created by Mario on 2016/5/24.
 */
public class InvestTicketActivity extends BaseToolbarActivity {

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
        setTitle("投资券");

        fragmentList = new ArrayList<>();
        fragmentList.add(InvestTicketFragment.newInstance(0));
        fragmentList.add(InvestTicketFragment.newInstance(1));
        fragmentList.add(InvestTicketFragment.newInstance(2));

        titleList = new ArrayList<>();
        titleList.add("未使用");
        titleList.add("已使用");
        titleList.add("已过期");

        tabLayout.addTab(tabLayout.newTab().setText(titleList.get(0)));
        tabLayout.addTab(tabLayout.newTab().setText(titleList.get(1)));
        tabLayout.addTab(tabLayout.newTab().setText(titleList.get(2)));

        fragmentPagerAdapter = new TabFragmentAdapter(getSupportFragmentManager(), fragmentList, titleList);
        viewPager.setAdapter(fragmentPagerAdapter);
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
            finish();
        return false;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_coupon, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.ask) {
            Intent intent = new Intent(InvestTicketActivity.this, WebViewActivity.class);
            intent.putExtra("title", Constant.TIPS);
            intent.putExtra("url", Constant.COUPONS_TIPS_URL);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
