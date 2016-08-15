package com.pcjinrong.pcjr.ui.views.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import com.jayfang.dropdownmenu.DropDownMenu;
import com.pcjinrong.pcjr.R;
import com.pcjinrong.pcjr.api.ApiConstant;
import com.pcjinrong.pcjr.bean.BaseBean;
import com.pcjinrong.pcjr.bean.InvestRecords;
import com.pcjinrong.pcjr.core.BaseSwipeActivity;
import com.pcjinrong.pcjr.core.mvp.MvpView;
import com.pcjinrong.pcjr.ui.adapter.InvestRecordsListAdapter;
import com.pcjinrong.pcjr.ui.presenter.InvestRecordsPresenter;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import retrofit2.adapter.rxjava.HttpException;


/**
 * 投资记录
 * Created by Mario on 2016/5/24.
 */
public class InvestRecordsActivity extends BaseSwipeActivity implements MvpView<BaseBean<List<InvestRecords>>>{
    @BindView(R.id.lv_menu) DropDownMenu lv_menu;

    private InvestRecordsPresenter presenter;
    private InvestRecordsListAdapter adapter;
    private int type = 0;

    @Override
    protected int getLayoutId() {
        return R.layout.member_invest_records;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        this.showBack();
        this.setTitle("投资记录");
        dividerHeight = (int) getResources().getDimension(R.dimen.list_divider_height);
        initMenu();
    }


    @Override
    protected void initListeners() {

    }

    @Override
    protected void initData() {
        this.presenter = new InvestRecordsPresenter();
        this.presenter.attachView(this);
        this.adapter = new InvestRecordsListAdapter();
        rv_list.setAdapter(this.adapter);

        mPtrFrame.post(()->mPtrFrame.autoRefresh());
    }


    public void initMenu() {

        final String[] arr=new String[]{"全部记录", "正在募集", "募集成功", "正在回款", "回款完毕"};

        final String[] strings=new String[]{"全部记录"};

        lv_menu.setmMenuCount(1);
        lv_menu.setmShowCount(6);
        lv_menu.setShowCheck(true);
        lv_menu.setmMenuTitleTextSize(16);
        lv_menu.setmMenuTitleTextColor(Color.BLACK);
        lv_menu.setmMenuPressedTitleTextColor(Color.GRAY);
        lv_menu.setmMenuListTextSize((int) getResources().getDimension(R.dimen.text_size_14sp));
        lv_menu.setmMenuListTextColor(Color.BLACK);
        lv_menu.setmMenuBackColor(Color.WHITE);
        lv_menu.setmMenuPressedBackColor(Color.WHITE);
        lv_menu.setmCheckIcon(R.drawable.ico_make);
        lv_menu.setmUpArrow(R.drawable.arrow_up);
        lv_menu.setmDownArrow(R.drawable.arrow_down);
        lv_menu.setDefaultMenuTitle(strings);
        lv_menu.setMenuSelectedListener((listview,RowIndex,ColumnIndex)-> {
                refresh = true;
                type = RowIndex;
                mPtrFrame.post(()-> mPtrFrame.autoRefresh());
        });

        List<String[]> items = new ArrayList<>();
        items.add(arr);
        lv_menu.setmMenuItems(items);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
        }
        return false;

    }


    @Override
    protected void refresh() {
        refresh = true;
        emptyCount = 0;
        this.presenter.setPage(1);
        this.presenter.getInvestRecords(type, ApiConstant.DEFAULT_PAGE_SIZE);
    }

    @Override
    protected void loadMore() {
        if(emptyCount < EMPTY_LIMIT && !mPtrFrame.isRefreshing()) {
            refresh = false;
            this.presenter.setPage(this.presenter.getPage() + 1);
            this.presenter.getInvestRecords(type, ApiConstant.DEFAULT_PAGE_SIZE);
        }
    }

    @Override
    public void onFailure(Throwable e) {
        mPtrFrame.refreshComplete();
        if(e instanceof HttpException){
            showToast(getString(R.string.login_expired));
            startActivity(new Intent(InvestRecordsActivity.this, LoginActivity.class));
            return;
        }
        showToast(getString(R.string.network_anomaly));
    }

    @Override
    public void onSuccess(BaseBean<List<InvestRecords>> data) {
        mPtrFrame.refreshComplete();
        if(refresh){
            if(data.getData().size() == 0) {
                empty.setVisibility(View.VISIBLE);
                rv_list.setVisibility(View.INVISIBLE);
            }else{
                empty.setVisibility(View.INVISIBLE);
                rv_list.setVisibility(View.VISIBLE);
                adapter.setData(data.getData());
            }
        }else{
            adapter.addAll(data.getData());
        }
        if(data.getData().size() == 0 ) emptyCount++;
    }
}
