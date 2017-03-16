package com.pcjinrong.pcjr.ui.views.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;

import com.pcjinrong.pcjr.R;
import com.pcjinrong.pcjr.constant.Constant;
import com.pcjinrong.pcjr.core.BaseFragment;
import com.pcjinrong.pcjr.ui.adapter.TabChildFragmentAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;


/**
 * 投资Fragment
 * Created by Mario on 2016/7/21.
 */
public class InvestFragment extends BaseFragment
{

	@BindView(R.id.invest_tab_layout) TabLayout tabLayout;
	@BindView(R.id.invest_tab_viewpager) ViewPager viewPager;
	@BindView(R.id.btn_share) Button btn_share;


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
		fragmentList.add(InvestListFragment.newInstance(4));
		fragmentList.add(InvestListFragment.newInstance(2));
		fragmentList.add(InvestListFragment.newInstance(3));

		titleList = new ArrayList<>();
		titleList.add("全部");
		titleList.add("大城小爱");
		titleList.add("商通保盈");
		titleList.add("国泰民安");
		titleList.add("珠联璧合");

		tabLayout.addTab(tabLayout.newTab().setText(titleList.get(0)));
		tabLayout.addTab(tabLayout.newTab().setText(titleList.get(1)));
		tabLayout.addTab(tabLayout.newTab().setText(titleList.get(2)));
		tabLayout.addTab(tabLayout.newTab().setText(titleList.get(3)));
		tabLayout.addTab(tabLayout.newTab().setText(titleList.get(4)));
		fragmentPagerAdapter = new TabChildFragmentAdapter(this,fragmentList,titleList);

		viewPager.setAdapter(fragmentPagerAdapter);
		viewPager.setOffscreenPageLimit(5);
		tabLayout.setupWithViewPager(viewPager);
		tabLayout.getTabAt(Constant.TYPE).select();
	}

	@Override
	protected void initListeners() {
		btn_share.setOnClickListener(v->{
			ShareSDK.initSDK(getActivity());
			OnekeyShare oks = new OnekeyShare();
			oks.disableSSOWhenAuthorize();
			oks.setTitle("皮城金融");
			oks.setTitleUrl(Constant.SHARE_URL);
			oks.setText("皮城金融投资项目");
			oks.setImageUrl(Constant.SHARE_IMG_URL);
			oks.setUrl(Constant.SHARE_LIST_URL);
			oks.show(getActivity());
		});
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

	@Override
	public void onHiddenChanged(boolean hidden) {
		super.onHiddenChanged(hidden);
		if(!hidden) {
			tabLayout.getTabAt(Constant.TYPE).select();
		}else{
			Constant.TYPE = 0;
		}
	}
}
