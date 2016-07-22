package com.pcjinrong.pcjr.ui.views.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import com.pcjinrong.pcjr.R;
import com.pcjinrong.pcjr.core.BaseFragment;


/**
 * 投资Fragment
 * Created by Mario on 2016/7/21.
 */
public class InvestFragment extends BaseFragment
{

	public static final String TAG = InvestFragment.class.getSimpleName();

	@Override
	protected int getLayoutId() {
		return R.layout.main_tab_invest;
	}

	@Override
	protected void initViews(View self, Bundle savedInstanceState) {

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
