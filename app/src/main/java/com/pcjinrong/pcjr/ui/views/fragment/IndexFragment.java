package com.pcjinrong.pcjr.ui.views.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ScrollView;
import android.widget.TextSwitcher;
import android.widget.TextView;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.orhanobut.logger.Logger;
import com.pcjinrong.pcjr.R;
import com.pcjinrong.pcjr.bean.Announce;
import com.pcjinrong.pcjr.bean.FocusImg;
import com.pcjinrong.pcjr.bean.IndexFocusInfo;
import com.pcjinrong.pcjr.bean.Product;
import com.pcjinrong.pcjr.constant.Constant;
import com.pcjinrong.pcjr.core.BaseFragment;
import com.pcjinrong.pcjr.ui.adapter.ProductListAdapter;
import com.pcjinrong.pcjr.ui.decorator.RecycleViewDivider;
import com.pcjinrong.pcjr.ui.presenter.MainPresenter;
import com.pcjinrong.pcjr.ui.presenter.ivview.MainView;
import com.pcjinrong.pcjr.ui.views.activity.InvestDetailActivity;
import com.pcjinrong.pcjr.ui.views.activity.LoginActivity;
import com.pcjinrong.pcjr.ui.views.activity.MainActivity;
import com.pcjinrong.pcjr.ui.views.activity.WebViewActivity;
import com.pcjinrong.pcjr.widget.NetworkImageHolderView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;


/**
 * 首页Fragment
 * Created by Mario on 2016/7/21.
 */
public class IndexFragment extends BaseFragment implements MainView {

    public static final String TAG = IndexFragment.class.getSimpleName();

    @BindView(R.id.rv_list) RecyclerView rv_list;
    @BindView(R.id.slider) ConvenientBanner sliderLayout;
    @BindView(R.id.slider_small) ConvenientBanner sliderLayoutSmall;
    @BindView(R.id.ptr_frame) PtrClassicFrameLayout mPtrFrame;
    @BindView(R.id.scroll_view) ScrollView scrollView;
    @BindView(R.id.text_switcher) TextSwitcher textSwitcher;
    private MainPresenter presenter;
    private ProductListAdapter adapter;

    private List<FocusImg> focusImgs;
    private List<FocusImg> midFocusImgs;
    private List<Announce> announces;
    private int mCounter;
    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        public void run() {
            if (announces != null && announces.size() > 0) {
                handler.postDelayed(this, 3000);
                textSwitcher.setInAnimation(AnimationUtils.loadAnimation(
                        getContext(), R.anim.slide_up_in));
                textSwitcher.setOutAnimation(AnimationUtils.loadAnimation(
                        getContext(), R.anim.slide_up_out));
                mCounter = mCounter >= announces.size() - 1 ? 0 : mCounter + 1;
                textSwitcher.setText(announces.get(mCounter).getTitle());
            }
        }
    };

    @OnClick(R.id.cpyg) void cpyg() {
        Intent intent = new Intent(getActivity(), WebViewActivity.class);
        intent.putExtra("title", Constant.PRODUCT_NOTICE);
        intent.putExtra("url",Constant.PRODUCT_NOTICE_URL);
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);

    }

    @OnClick(R.id.dcxa) void dcxa() {
        MainActivity mainActivity  = (MainActivity) getContext();
        mainActivity.tabSelect(1);
        Constant.TYPE = 1;
    }

    @OnClick(R.id.stby) void stby() {
        MainActivity mainActivity  = (MainActivity) getContext();
        mainActivity.tabSelect(1);
        Constant.TYPE = 2;
    }

    @OnClick(R.id.gtma) void gtma() {
        MainActivity mainActivity  = (MainActivity) getContext();
        mainActivity.tabSelect(1);
        Constant.TYPE = 3;
    }

    @OnClick(R.id.zlbh) void zlbh() {
        MainActivity mainActivity  = (MainActivity) getContext();
        mainActivity.tabSelect(1);
        Constant.TYPE = 4;

    }

    @OnClick(R.id.all_invest) void allInvest() {
        MainActivity mainActivity  = (MainActivity) getContext();
        mainActivity.tabSelect(1);
        Constant.TYPE = 0;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.main_tab_index;
    }

    @Override
    protected void initViews(View self, Bundle savedInstanceState) {
        ButterKnife.bind(this, self);
        LinearLayoutManager manager = new LinearLayoutManager(self.getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_list.setLayoutManager(manager);
        rv_list.addItemDecoration(new RecycleViewDivider(self.getContext(), LinearLayoutManager.HORIZONTAL, (int) getResources().getDimension(R.dimen.list_divider_height), ContextCompat.getColor(self.getContext(), R.color.color_background)));
        textSwitcher.setFactory(() -> new TextView(getContext()));

        this.presenter = new MainPresenter();
        this.presenter.attachView(this);
        mPtrFrame.disableWhenHorizontalMove(true);
        mPtrFrame.setLastUpdateTimeRelateObject(this);
    }

    @Override
    protected void initListeners() {
        textSwitcher.setOnClickListener((v) -> {
            Intent intent = new Intent(getActivity(), WebViewActivity.class);
            String url = announces.get(mCounter).getUrl();
            if (url != null && !url.equals("")) {
                intent.putExtra("title","新闻公告 ");
                intent.putExtra("url", announces.get(mCounter).getUrl());
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        adapter.setOnItemClickListener((view, id) -> {
            Intent intent = new Intent(getActivity(), InvestDetailActivity.class);
            intent.putExtra("id",id);
            startActivity(intent);
        });
    }

    @Override
    protected void initData() {
        this.adapter = new ProductListAdapter();
        this.rv_list.setAdapter(this.adapter);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, scrollView, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                refreshData();
            }
        });

        if(Constant.INDEX_FOCUS_INFO != null){
            onGetIndexFocusSuccess(Constant.INDEX_FOCUS_INFO);
            this.presenter.getIndexProductList();
        }else{
            refreshData();
        }


    }

    @Override
    protected void lazyLoad() {

    }

    public static Fragment newInstance(String content) {
        return new IndexFragment();
    }

    @Override
    public void onFailure(Throwable e) {
        mPtrFrame.refreshComplete();
        showToast("网络异常");
    }

    @Override
    public void onSuccess(Object data) {

    }


    private void refreshData() {
        handler.removeCallbacks(runnable);
        sliderLayout.stopTurning();
        sliderLayoutSmall.stopTurning();

        this.presenter.getIndexFocusInfo();
        this.presenter.getIndexProductList();
    }


    /**
     * 初始化图片轮播
     *
     * @param focusImgs
     * @param midFocusImgs
     */
    public void initSlider(List<FocusImg> focusImgs, List<FocusImg> midFocusImgs) {

        initImageLoader();
        sliderLayout.setPages(() -> new NetworkImageHolderView(), focusImgs)
                .setPageIndicator(new int[]{R.mipmap.ic_page_indicator, R.mipmap.ic_page_indicator_focused})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnItemClickListener(new SliderLayoutOnItemClick(focusImgs));


        sliderLayoutSmall.setPages(() -> new NetworkImageHolderView(), midFocusImgs)
                .setPageIndicator(new int[]{R.mipmap.ic_page_red_indicator_focused, R.mipmap.ic_page_red_indicator})
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
                .setOnItemClickListener(new SliderLayoutSmallOnItemClick(midFocusImgs));
    }

    private void initImageLoader() {
        DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
                .showImageOnLoading(R.mipmap.ic_default_adimage)
                .showImageOnFail(R.mipmap.ic_default_adimage)
                .showImageForEmptyUri(R.mipmap.ic_default_adimage)
                .cacheInMemory(true).cacheOnDisk(true).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                getActivity().getApplicationContext()).defaultDisplayImageOptions(defaultOptions)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .denyCacheImageMultipleSizesInMemory()
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO).build();


        ImageLoader.getInstance().init(config);
    }

    @Override
    public void onGetIndexListSuccess(List<Product> list,long current_time) {
        if(mPtrFrame.isRefreshing()) mPtrFrame.refreshComplete();
        adapter.setData(list,current_time,System.currentTimeMillis());
    }

    @Override
    public void onGetIndexFocusSuccess(IndexFocusInfo data) {
        if(mPtrFrame.isRefreshing()) mPtrFrame.refreshComplete();
        initSlider(data.getTop_focus_img(), data.getMiddle_focus_img());
        announces = data.getAnnounce();
        mCounter = announces.size();
        handler.post(runnable);
    }

    class SliderLayoutOnItemClick implements OnItemClickListener {
        private List<FocusImg> focusImgs;

        public SliderLayoutOnItemClick(List<FocusImg> focusImgs) {
            this.focusImgs = focusImgs;
        }

        @Override
        public void onItemClick(int position) {
            Intent intent = new Intent(getActivity(), WebViewActivity.class);
            String url = focusImgs.get(position).getUrl();
            if (url != null && !url.equals("")) {
                intent.putExtra("url", url);
                startActivity(intent);
            }
        }
    }

    class SliderLayoutSmallOnItemClick implements OnItemClickListener {
        private List<FocusImg> focusImgs;

        public SliderLayoutSmallOnItemClick(List<FocusImg> focusImgs) {
            this.focusImgs = focusImgs;
        }

        @Override
        public void onItemClick(int position) {
            Intent intent = new Intent(getActivity(), WebViewActivity.class);
            String url = focusImgs.get(position).getUrl();
            if (url != null && !url.equals("")) {
                intent.putExtra("url", url);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onStart() {
        handler.post(runnable);
        sliderLayout.startTurning(5000);
        sliderLayoutSmall.startTurning(5000);
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        handler.removeCallbacks(runnable);
        sliderLayout.stopTurning();
        sliderLayoutSmall.stopTurning();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.presenter.detachView();
    }
}
