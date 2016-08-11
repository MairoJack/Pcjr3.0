package com.pcjinrong.pcjr.ui.views.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;

import com.pcjinrong.pcjr.R;
import com.pcjinrong.pcjr.core.BaseToolbarActivity;
import com.pcjinrong.pcjr.ui.adapter.TabFragmentAdapter;
import com.pcjinrong.pcjr.ui.views.fragment.InvestTicketFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 投资详情
 * Created by Mario on 2016/5/24.
 */
public class InvestDetailActivity extends BaseToolbarActivity {

    @BindView(R.id.tab_layout) TabLayout tabLayout;
    @BindView(R.id.tab_viewpager) ViewPager viewPager;

    private FragmentPagerAdapter fragmentPagerAdapter;

    private List<Fragment> fragmentList;
    private List<String> titleList;


    @Override
    protected int getLayoutId() {
        return R.layout.invest_detail;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        showBack();
        setTitle("投资详情");

        fragmentList = new ArrayList<>();
        fragmentList.add(InvestTicketFragment.newInstance(0));
        fragmentList.add(InvestTicketFragment.newInstance(1));
        fragmentList.add(InvestTicketFragment.newInstance(2));

        titleList = new ArrayList<>();
        titleList.add("项目情况");
        titleList.add("风控措施");
        titleList.add("投资记录");

        tabLayout.addTab(tabLayout.newTab().setText(titleList.get(0)));
        tabLayout.addTab(tabLayout.newTab().setText(titleList.get(1)));
        tabLayout.addTab(tabLayout.newTab().setText(titleList.get(2)));

        fragmentPagerAdapter = new TabFragmentAdapter(getSupportFragmentManager(),fragmentList,titleList);
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
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK )
        {
            finish();
            overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
        }
        return false;

    }

}
