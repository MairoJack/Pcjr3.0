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
import com.pcjinrong.pcjr.ui.views.fragment.RedPacketFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 红包
 * Created by Mario on 2016/5/24.
 */
public class RedPacketActivity extends BaseToolbarActivity {

    @BindView(R.id.tab_layout) TabLayout tabLayout;
    @BindView(R.id.tab_viewpager) ViewPager viewPager;

    private FragmentPagerAdapter fragmentPagerAdapter;

    private List<Fragment> fragmentList;
    private List<String> titleList;


    @Override
    protected int getLayoutId() {
        return R.layout.member_coupon_red_packet;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        showBack();
        setTitle("红包");

        fragmentList = new ArrayList<>();
        fragmentList.add(RedPacketFragment.newInstance(0));
        fragmentList.add(RedPacketFragment.newInstance(1));

        titleList = new ArrayList<>();
        titleList.add("未领取");
        titleList.add("已领取");

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
        getMenuInflater().inflate(R.menu.menu_tips, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.ask) {
            Intent intent = new Intent(RedPacketActivity.this, WebViewActivity.class);
            intent.putExtra("title", Constant.TIPS);
            intent.putExtra("url", Constant.REDPACKET_TIPS_URL);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
