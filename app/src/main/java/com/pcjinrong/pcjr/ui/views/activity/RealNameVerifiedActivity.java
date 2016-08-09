package com.pcjinrong.pcjr.ui.views.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;
import com.pcjinrong.pcjr.R;
import com.pcjinrong.pcjr.core.BaseToolbarActivity;
import butterknife.BindView;


/**
 * 实名认证
 * Created by Mario on 2016/5/24.
 */
public class RealNameVerifiedActivity extends BaseToolbarActivity {
    @BindView(R.id.txt_realname) TextView txt_realname;
    @BindView(R.id.txt_idcard) TextView txt_idcard;

    @Override
    protected int getLayoutId() {
        return R.layout.member_safe_setting_realname;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        showBack();
        setTitle("实名认证");
    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        String realname = intent.getStringExtra("realname");
        String identity = intent.getStringExtra("identity");
        txt_realname.setText(realname);
        txt_idcard.setText(identity);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return false;

    }
}
