package com.pcjinrong.pcjr.ui.views.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.pcjinrong.pcjr.R;
import com.pcjinrong.pcjr.bean.BankCard;
import com.pcjinrong.pcjr.bean.BaseBean;
import com.pcjinrong.pcjr.bean.IdentityInfo;
import com.pcjinrong.pcjr.constant.Constant;
import com.pcjinrong.pcjr.core.BaseSwipeActivity;
import com.pcjinrong.pcjr.ui.adapter.BankCardListAdapter;
import com.pcjinrong.pcjr.ui.presenter.BankCardPresenter;
import com.pcjinrong.pcjr.ui.presenter.ivview.BankCardView;
import com.pcjinrong.pcjr.utils.ViewUtil;
import com.pcjinrong.pcjr.widget.Dialog;

import java.util.List;

import butterknife.BindView;
import retrofit2.adapter.rxjava.HttpException;


/**
 * 银行卡
 * Created by Mario on 2016/5/24.
 */
public class BankCardActivity extends BaseSwipeActivity implements BankCardView{
    @BindView(R.id.btn_addbankcard) Button btn_addbankcard;
    @BindView(R.id.rl_list) RelativeLayout rl_list;
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
            presenter.getIdentityInfo();
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
        if(e instanceof HttpException){
            showToast(getString(R.string.login_expired));
            startActivity(new Intent(BankCardActivity.this, LoginActivity.class));
            return;
        }
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
            rl_list.setVisibility(View.GONE);
            btn_addbankcard.setVisibility(View.VISIBLE);
        }else{
            rl_list.setVisibility(View.VISIBLE);
            btn_addbankcard.setVisibility(View.GONE);
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
    public void onIdentityInfoSuccess(BaseBean<IdentityInfo> data) {
        if (data.isSuccess()) {
            Intent intent = new Intent(BankCardActivity.this, AddBankCardActivity.class);
            intent.putExtra("data",data.getData());
            startActivityForResult(intent, Constant.REQUSET);
        } else {
            Dialog.show("未实名认证",this);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Constant.REQUSET && resultCode == RESULT_OK){
            refresh();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_bank, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.explain) {
            Intent intent = new Intent(BankCardActivity.this, WebViewActivity.class);
            intent.putExtra("title", Constant.CARD_EXPLAIN);
            intent.putExtra("url", Constant.CARD_EXPLAIN_URL);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
