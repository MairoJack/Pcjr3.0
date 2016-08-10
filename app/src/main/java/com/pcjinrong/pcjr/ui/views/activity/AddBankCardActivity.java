package com.pcjinrong.pcjr.ui.views.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import com.pcjinrong.pcjr.R;
import com.pcjinrong.pcjr.bean.BankCard;
import com.pcjinrong.pcjr.bean.BaseBean;
import com.pcjinrong.pcjr.constant.Constant;
import com.pcjinrong.pcjr.core.BaseToolbarActivity;
import com.pcjinrong.pcjr.ui.presenter.AddBankCardPresenter;
import com.pcjinrong.pcjr.ui.presenter.ivview.AddBankCardView;
import com.pcjinrong.pcjr.utils.ViewUtil;
import com.pcjinrong.pcjr.widget.Dialog;

import java.util.List;

import butterknife.BindView;


/**
 * 添加银行卡
 * Created by Mario on 2016/5/24.
 */
public class AddBankCardActivity extends BaseToolbarActivity implements AddBankCardView {
    @BindView(R.id.img_info) ImageView img_info;
    @BindView(R.id.img_clear) ImageView img_clear;
    @BindView(R.id.btn_save) Button btn_save;
    @BindView(R.id.txt_card_no) EditText txt_card_no;
    @BindView(R.id.txt_cardholder) TextView txt_cardholder;
    @BindView(R.id.spinner) Spinner spinner;

    private ProgressDialog dialog;
    private AddBankCardPresenter presenter;

    private List<BankCard> bankCards;
    private String bank_id;

    @Override
    protected int getLayoutId() {
        return R.layout.member_add_bank_card;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        showBack();
        setTitle("添加银行卡");
        dialog = new ProgressDialog(this, ProgressDialog.STYLE_SPINNER);

    }

    @Override
    protected void initListeners() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                bank_id = bankCards.get(position).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        img_info.setOnClickListener(v -> Dialog.show("持卡人说明", "为了你的账户资金安全，只能绑定持卡人的银行卡", this));

        img_clear.setOnClickListener(v -> txt_card_no.setText(""));

        btn_save.setOnClickListener(v -> {
            if (ViewUtil.isFastDoubleClick()) return;
            save();
        });
    }

    @Override
    protected void initData() {
        txt_cardholder.setText(Constant.REALNAME);
        presenter = new AddBankCardPresenter();
        presenter.attachView(this);
        presenter.getBankCardList();
    }

    public void save() {
        String card_no = txt_card_no.getText().toString().trim();
        if (card_no.equals("")) {
            Dialog.show("银行卡号不能为空", this);
            return;
        }
        dialog.setMessage("正在提交...");
        dialog.show();
        presenter.addBankCard(bank_id, card_no, Constant.REALNAME);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return false;

    }

    @Override
    public void onFailure(Throwable e) {
        dialog.dismiss();
        showToast(R.string.network_anomaly);
    }

    @Override
    public void onSuccess(Object data) {

    }


    @Override
    public void onBankCardListSuccess(List<BankCard> list) {
        bankCards = list;
        String[] mItems = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            mItems[i] = list.get(i).getName();
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(AddBankCardActivity.this, android.R.layout.simple_spinner_item, mItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    @Override
    public void onAddBankCardSuccess(BaseBean data) {
        dialog.dismiss();
        if (data.isSuccess()) {
            showToast(data.getMessage());
            setResult(RESULT_OK);
            finish();
        } else {
            Dialog.show(data.getMessage(), this);
        }
    }
}
