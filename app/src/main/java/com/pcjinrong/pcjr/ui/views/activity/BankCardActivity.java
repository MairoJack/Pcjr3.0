package com.pcjinrong.pcjr.ui.views.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
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
import com.pcjinrong.pcjr.widget.Dialog;

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

    private BankCard bankCard;
    @Override
    protected int getLayoutId() {
        return R.layout.member_bank_card;
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
            startActivityForResult(new Intent(BankCardActivity.this, AddBankCardActivity.class), Constant.REQUSET);
        });

        adapter.setOnDeleteClickListener((view,data)-> {
            if(ViewUtil.isFastDoubleClick())return;
            bankCard = data;
            new AlertDialog.Builder(this)
                    .setTitle("确认删除尾号为" + bankCard.getCard_no().substring(bankCard.getCard_no().length() - 4) + "的银行卡吗?")
                    .setPositiveButton("确认", (dialog, which) -> {
                        presenter.delBankCard(bankCard.getId());
                    })
                    .setNegativeButton("取消", (dialog, which) -> {
                        dialog.dismiss();
                    }).create().show();
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
        this.presenter.getBankCardInfo();
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
    public void onBankCardInfoSuccess(List<BankCard> list,String realname) {
        mPtrFrame.refreshComplete();
        Constant.REALNAME = realname;
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
    public void onDelBankCardSuccess(BaseBean data) {
        if(data.isSuccess()) {
            adapter.delete(bankCard);
        }else{
            Dialog.show(data.getMessage(),this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Constant.REQUSET && resultCode == RESULT_OK){
            refresh();
        }
    }
}
