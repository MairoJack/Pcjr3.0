package com.pcjinrong.pcjr.ui.views.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import com.pcjinrong.pcjr.R;
import com.pcjinrong.pcjr.core.BaseFragment;
import com.pcjinrong.pcjr.ui.adapter.TabChildFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * 投资Fragment
 * Created by Mario on 2016/7/21.
 */
public class InvestFragment extends BaseFragment
{

	@BindView(R.id.invest_tab_layout) TabLayout tabLayout;
	@BindView(R.id.invest_tab_viewpager) ViewPager viewPager;

	private FragmentPagerAdapter fragmentPagerAdapter;

	private List<Fragment> fragmentList;
	private List<String> titleList;

	public static final String TAG = InvestFragment.class.getSimpleName();

	@Override
	protected int getLayoutId() {
		return R.layout.main_tab_invest;
	}

	@Override
	protected void initViews(View self, Bundle savedInstanceState) {
		ButterKnife.bind(this, self);
		fragmentList = new ArrayList<>();
		fragmentList.add(InvestListFragment.newInstance(0));
		fragmentList.add(InvestListFragment.newInstance(1));
		fragmentList.add(InvestListFragment.newInstance(2));
		fragmentList.add(InvestListFragment.newInstance(3));

		titleList = new ArrayList<>();
		titleList.add("全部");
		titleList.add("大城小爱");
		titleList.add("国泰民安");
		titleList.add("珠联璧合");

		tabLayout.addTab(tabLayout.newTab().setText(titleList.get(0)));
		tabLayout.addTab(tabLayout.newTab().setText(titleList.get(1)));
		tabLayout.addTab(tabLayout.newTab().setText(titleList.get(2)));
		tabLayout.addTab(tabLayout.newTab().setText(titleList.get(3)));
		fragmentPagerAdapter = new TabChildFragmentAdapter(this,fragmentList,titleList);

		viewPager.setAdapter(fragmentPagerAdapter);
		viewPager.setOffscreenPageLimit(4);
		tabLayout.setupWithViewPager(viewPager);
	}

	@Override
	protected void initListeners() {

	}

	@Override
	protected void initData() {

	}

	@Override
	protected void lazyLoad() {

	}

	public static Fragment newInstance(String content) {
		InvestFragment fragment = new InvestFragment();
		return fragment;
	}
}
