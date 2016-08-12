package com.pcjinrong.pcjr.ui.views.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.pcjinrong.pcjr.R;
import com.pcjinrong.pcjr.bean.BaseBean;
import com.pcjinrong.pcjr.bean.Product;
import com.pcjinrong.pcjr.bean.Withdraw;
import com.pcjinrong.pcjr.constant.Constant;
import com.pcjinrong.pcjr.core.BaseToolbarActivity;
import com.pcjinrong.pcjr.core.mvp.MvpView;
import com.pcjinrong.pcjr.ui.adapter.InvestRecordsListAdapter;
import com.pcjinrong.pcjr.ui.adapter.TabFragmentAdapter;
import com.pcjinrong.pcjr.ui.presenter.InvestDetailPresenter;
import com.pcjinrong.pcjr.ui.presenter.InvestRecordsPresenter;
import com.pcjinrong.pcjr.ui.presenter.ivview.InvestDetailView;
import com.pcjinrong.pcjr.ui.views.fragment.InvestDetailInfoFragment;
import com.pcjinrong.pcjr.ui.views.fragment.InvestDetailRecordFragment;
import com.pcjinrong.pcjr.ui.views.fragment.InvestDetailRiskFragment;
import com.pcjinrong.pcjr.ui.views.fragment.InvestTicketFragment;
import com.pcjinrong.pcjr.utils.DateUtils;
import com.pcjinrong.pcjr.utils.ViewUtil;
import com.pcjinrong.pcjr.widget.Dialog;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import cn.iwgang.countdownview.CountdownView;

/**
 * 投资详情
 * Created by Mario on 2016/5/24.
 */
public class InvestDetailActivity extends BaseToolbarActivity implements InvestDetailView {

    @BindView(R.id.invest_tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.invest_tab_viewpager)
    ViewPager viewPager;
    @BindView(R.id.btn_status)
    Button btn_status;
    @BindView(R.id.layout_cv_countdown)
    LinearLayout layout_cdv;
    @BindView(R.id.cv_countdown)
    CountdownView cdv;

    private InvestDetailPresenter presenter;
    private FragmentPagerAdapter fragmentPagerAdapter;

    private List<Fragment> fragmentList;
    private List<String> titleList;

    private ProgressDialog dialog;
    private Product product;

    @Override
    protected int getLayoutId() {
        return R.layout.invest_detail;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        showBack();
        setTitle("投资详情");
        dialog = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
    }

    @Override
    protected void initListeners() {
        btn_status.setOnClickListener(v -> {
            if (ViewUtil.isFastDoubleClick()) return;
            Intent intent;
            boolean flag = false;
            if (Constant.IS_LOGIN && Constant.IS_GESTURE_LOGIN) {
                flag = true;
            } else if (!Constant.IS_LOGIN) {
                intent = new Intent(InvestDetailActivity.this, LoginActivity.class);
                intent.putExtra("tag", "invest");
                startActivity(intent);
            } else if (!Constant.IS_GESTURE_LOGIN) {

            }
            if (flag) {
                dialog.setMessage("正在加载...");
                dialog.show();
                presenter.getWithdrawInvestInfo();
            }
        });

        cdv.setOnCountdownEndListener(cv -> {
            layout_cdv.setVisibility(View.GONE);
            btn_status.setVisibility(View.VISIBLE);
            btn_status.setBackgroundResource(R.drawable.btn_primary);
            btn_status.setText("立即投资");
            btn_status.setClickable(true);
        });
    }

    @Override
    protected void initData() {
        String id = getIntent().getStringExtra("id");
        this.presenter = new InvestDetailPresenter();
        this.presenter.attachView(this);

        presenter.getProductDetail(id);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) finish();
        return false;

    }

    public void buildTabLayout(Product product) {

        fragmentList = new ArrayList<>();
        fragmentList.add(InvestDetailInfoFragment.newInstance(product));
        fragmentList.add(InvestDetailRiskFragment.newInstance(product.getRisk_control()));
        fragmentList.add(InvestDetailRecordFragment.newInstance(product.getId()));

        titleList = new ArrayList<>();
        titleList.add("项目情况");
        titleList.add("风控措施");
        titleList.add("投资记录");

        tabLayout.addTab(tabLayout.newTab().setText(titleList.get(0)));
        tabLayout.addTab(tabLayout.newTab().setText(titleList.get(1)));
        tabLayout.addTab(tabLayout.newTab().setText(titleList.get(2)));

        fragmentPagerAdapter = new TabFragmentAdapter(getSupportFragmentManager(), fragmentList, titleList);

        viewPager.setAdapter(fragmentPagerAdapter);
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);
    }


    public void refreshButton(final Product product, long current_time) {
        btn_status.setClickable(false);
        if (product.getStatus() == 1) {
            Date date = new Date(current_time);
            Date pub_date = new Date(product.getPub_date() * 1000);
            if (DateUtils.isStartDateBeforeEndDate(date, pub_date)) {
                if (DateUtils.getHoursOfTowDiffDate(date, pub_date) > 1) {
                    btn_status.setBackgroundResource(R.drawable.btn_primary);
                    btn_status.setText(DateUtils.dateTimeToStr(pub_date, "MM月dd日 HH:mm") + "开抢");
                } else {
                    btn_status.setVisibility(View.GONE);
                    layout_cdv.setVisibility(View.VISIBLE);
                    cdv.start(DateUtils.getMinusMillisOfDate(date, pub_date));
                }
            } else {
                btn_status.setBackgroundResource(R.drawable.btn_primary);
                btn_status.setText("立即投资");
                btn_status.setClickable(true);
            }

        } else if (product.getStatus() == 2 || product.getStatus() == 3) {
            btn_status.setBackgroundResource(R.drawable.btn_disable);
            btn_status.setText("募集成功");
            btn_status.setClickable(false);
        } else {
            btn_status.setBackgroundResource(R.drawable.btn_disable);
            btn_status.setText("项目结束");
            btn_status.setClickable(false);
        }
    }

    @Override
    public void onFailure(Throwable e) {
        if (dialog.isShowing()) dialog.dismiss();
        showToast(R.string.network_anomaly);
    }

    @Override
    public void onSuccess(Object data) {

    }


    @Override
    public void onProductInfoSuccess(BaseBean<Product> data) {
        this.product = data.getData();
        buildTabLayout(product);
        refreshButton(data.getData(),data.getCurrent_time() * 1000);
    }

    @Override
    public void onInvestInfoSuccess(BaseBean<Withdraw> data) {
        if (dialog.isShowing()) dialog.dismiss();
        if (data.isSuccess()) {
            Intent intent = new Intent(InvestDetailActivity.this, InvestActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("data", data.getData());
            bundle.putSerializable("product", this.product);
            intent.putExtras(bundle);
            startActivityForResult(intent, Constant.REQUSET);
        } else {
            Dialog.show(data.getMessage(), this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUSET && resultCode == RESULT_OK) {
            initData();
        }
    }
}
