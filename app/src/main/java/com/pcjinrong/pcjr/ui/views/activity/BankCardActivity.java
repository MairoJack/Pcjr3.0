package com.pcjinrong.pcjr.ui.views.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.pcjinrong.pcjr.R;
import com.pcjinrong.pcjr.bean.BankCard;
import com.pcjinrong.pcjr.bean.BaseBean;
import com.pcjinrong.pcjr.constant.Constant;
import com.pcjinrong.pcjr.core.BaseSwipeActivity;
import com.pcjinrong.pcjr.ui.adapter.BankCardListAdapter;
import com.pcjinrong.pcjr.ui.presenter.BankCardPresenter;
import com.pcjinrong.pcjr.ui.presenter.ivview.BankCardView;
import com.pcjinrong.pcjr.utils.ViewUtil;

import java.util.List;

import butterknife.BindView;


/**
 * 银行卡
 * Created by Mario on 2016/5/24.
 */
public class BankCardActivity extends BaseSwipeActivity implements BankCardView{
    @BindView(R.id.btn_addbankcard) Button btn_addbankcard;
    private BankCardPresenter presenter;
    private BankCardListAdapter adapter;

    @Override
    protected int getLayoutId() {
        return R.layout.member_safe_setting_bank_card;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        this.showBack();
        this.setTitle("银行卡");
        dividerHeight = (int) getResources().getDimension(R.dimen.list_divider_height);
    }


    @Override
    protected void initListeners() {
        btn_addbankcard.setOnClickListener(v-> {
            if(ViewUtil.isFastDoubleClick())return;
            //startActivityForResult(new Intent(BankCardActivity.this, AddBankCardActivity.class), Constant.REQUSET);
        });
    }

    @Override
    protected void initData() {
        this.presenter = new BankCardPresenter();
        this.presenter.attachView(this);
        this.adapter = new BankCardListAdapter();
        rv_list.setAdapter(this.adapter);

        mPtrFrame.post(()->mPtrFrame.autoRefresh());
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
        this.presenter.getBankCardList();
    }

    @Override
    protected void loadMore() {

    }

    @Override
    public void onFailure(Throwable e) {
        mPtrFrame.refreshComplete();
        showToast(getString(R.string.network_anomaly));
    }

    @Override
    public void onSuccess(Object data) {

    }



    @Override
    public void onBankCardListSuccess(List<BankCard> list,String realname) {
        mPtrFrame.refreshComplete();
        if(list.size() == 0) {
            empty.setVisibility(View.VISIBLE);
            rv_list.setVisibility(View.INVISIBLE);
        }else{
            empty.setVisibility(View.INVISIBLE);
            rv_list.setVisibility(View.VISIBLE);
            adapter.setData(list,realname);
        }
    }

    @Override
    public void onAddBankCardSuccess(BaseBean data) {

    }

    @Override
    public void onDelBankCardSuccess(BaseBean data) {

    }
}
