package com.pcjinrong.pcjr.ui.views.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.pcjinrong.pcjr.R;
import com.pcjinrong.pcjr.bean.AvailableInterest;
import com.pcjinrong.pcjr.bean.BaseBean;
import com.pcjinrong.pcjr.bean.IdentityInfo;
import com.pcjinrong.pcjr.bean.InterestTicket;
import com.pcjinrong.pcjr.bean.Product;
import com.pcjinrong.pcjr.bean.Withdraw;
import com.pcjinrong.pcjr.constant.Constant;
import com.pcjinrong.pcjr.core.BaseToolbarActivity;
import com.pcjinrong.pcjr.ui.adapter.InterestListAdapter;
import com.pcjinrong.pcjr.ui.adapter.TabFragmentAdapter;
import com.pcjinrong.pcjr.ui.decorator.RecycleViewDivider;
import com.pcjinrong.pcjr.ui.presenter.InvestDetailPresenter;
import com.pcjinrong.pcjr.ui.presenter.ivview.InvestDetailView;
import com.pcjinrong.pcjr.ui.views.fragment.InvestDetailInfoFragment;
import com.pcjinrong.pcjr.ui.views.fragment.InvestDetailRecordFragment;
import com.pcjinrong.pcjr.ui.views.fragment.InvestDetailRiskFragment;
import com.pcjinrong.pcjr.utils.DateUtils;
import com.pcjinrong.pcjr.utils.SPUtils;
import com.pcjinrong.pcjr.utils.ViewUtil;
import com.pcjinrong.pcjr.widget.Dialog;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import cn.iwgang.countdownview.CountdownView;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

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
    @BindView(R.id.btn_jxq)
    ImageButton btn_jxq;
    @BindView(R.id.layout_cv_countdown)
    LinearLayout layout_cdv;
    @BindView(R.id.cv_countdown)
    CountdownView cdv;

    private InvestDetailPresenter presenter;
    private FragmentPagerAdapter fragmentPagerAdapter;

    private List<Fragment> fragmentList;
    private List<String> titleList;

    private ProgressDialog dialog;
    private BottomSheetDialog bottomSheetDialog;
    private Product product;
    private List<InterestTicket> interestTicketList;
    private InterestListAdapter adapter;
    private long server_time;
    @Override
    protected int getLayoutId() {
        return R.layout.invest_detail;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        showBack();
        setTitle("投资详情");
        dialog = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);
        bottomSheetDialog = new BottomSheetDialog(this);

        adapter = new InterestListAdapter();

        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.interest_list, (ViewGroup) findViewById(R.id.dialog));
        RecyclerView rv_list = (RecyclerView) view.findViewById(R.id.rv_list);
        ImageView btn_close = (ImageView) view.findViewById(R.id.btn_close);

        btn_close.setOnClickListener(v-> bottomSheetDialog.dismiss());

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_list.setLayoutManager(manager);
        rv_list.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL, 1, ContextCompat.getColor(this, R.color.color_background)));
        rv_list.setItemAnimator(new DefaultItemAnimator());
        rv_list.setAdapter(adapter);
        bottomSheetDialog.setContentView(view);
        setBehaviorCallback();
    }

    @Override
    protected void initListeners() {
        btn_status.setOnClickListener(v -> {
            if (ViewUtil.isFastDoubleClick()) return;
            Intent intent;
            if (!Constant.IS_LOGIN) {
                intent = new Intent(InvestDetailActivity.this, LoginActivity.class);
                intent.putExtra("tag", "invest");
                startActivityForResult(intent,Constant.REQUSET);
                return;
            }
            if((boolean) SPUtils.get(this,"isOpenGesture",false) && !Constant.IS_GESTURE_LOGIN){
                startActivityForResult(new Intent(InvestDetailActivity.this,GestureVerifyActivity.class),Constant.REQUSET);
                return;
            }
            dialog.setMessage("正在加载...");
            dialog.show();
            presenter.getWithdrawInvestInfo();
        });

        btn_jxq.setOnClickListener(v -> {
            if (ViewUtil.isFastDoubleClick()) return;
            if (!Constant.IS_LOGIN) {
                Dialog.show("请先登录账号后查看加息券",this);
                return;
            }
            if(interestTicketList == null || interestTicketList.size() == 0){
                Dialog.show("您没有可用的加息券",this);
            }else {
                bottomSheetDialog.show();
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

        if(!Constant.IS_LOGIN){
            Dialog.show("查看项目详情请先【注册】或【登录】",this);
        }else{
            presenter.getIdentityInfo();
        }

        dialog.setMessage("正在加载...");
        dialog.show();
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
        if(Constant.IS_LOGIN) {
            server_time = current_time;
            presenter.getInterestList();
        }
        this.product = product;
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
                    cdv.start(DateUtils.getMinusMillisOfDate(date, pub_date) + 500);
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
    public void onProductInfoSuccess(BaseBean<Product> data,long sys_time) {
        if(Constant.IS_LOGIN) {
            presenter.getInterestList();
        }
        if (dialog.isShowing()) dialog.dismiss();
        this.product = data.getData();
        buildTabLayout(product);
        //server_time = data.getCurrent_time() * 1000 + System.currentTimeMillis() - sys_time;
        server_time = data.getCurrent_time() * 1000;
        refreshButton(data.getData(),server_time);
    }

    @Override
    public void onInvestInfoSuccess(BaseBean<Withdraw> data) {
        if (dialog.isShowing()) dialog.dismiss();
        if (data.isSuccess()) {
            Intent intent = new Intent(InvestDetailActivity.this, InvestActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("data", data.getData());
            bundle.putSerializable("product", this.product);
            bundle.putLong("server_time", this.server_time);
            intent.putExtras(bundle);
            startActivityForResult(intent, Constant.REQUSET);
        } else {
            Dialog.show(data.getMessage(), this);
        }
    }

    @Override
    public void onInterestListSuccess(List<InterestTicket> list) {
        interestTicketList = list;
        adapter.setData(list,product,server_time);
    }

    @Override
    public void onIdentityInfoSuccess(BaseBean<IdentityInfo> data) {
        if(data.isSuccess()){
            Constant.IS_REALNAME = true;
        }else{
            Dialog.show("查看项目详情请先前往【我的】-【安全设置】完成实名认证",this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.REQUSET && resultCode == RESULT_OK) {
            if(!Constant.IS_LOGIN){
                Dialog.show("查看项目详情请先【注册】或【登录】",this);
            }else{
                presenter.getIdentityInfo();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.menu_share, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.btn_share) {
            ShareSDK.initSDK(this);
            OnekeyShare oks = new OnekeyShare();
            oks.disableSSOWhenAuthorize();
            oks.setTitle(product.getName()+"，预期年化收益"+product.getYear_income()+"%，"+product.getMonth());
            oks.setTitleUrl(Constant.SHARE_URL);
            oks.setImageUrl(Constant.SHARE_IMG_URL);
            oks.setUrl(Constant.SHARE_DETAIL_URL+product.getId());
            oks.show(this);
        }
        return super.onOptionsItemSelected(item);
    }

    private void setBehaviorCallback() {
        View view = bottomSheetDialog.getDelegate().findViewById(android.support.design.R.id.design_bottom_sheet);
        final BottomSheetBehavior bottomSheetBehavior = BottomSheetBehavior.from(view);
        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                    bottomSheetDialog.dismiss();
                    bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                }
            }
            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
            }
        });
    }
}
