package com.pcjinrong.pcjr.ui.views.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;

import com.pcjinrong.pcjr.R;
import com.pcjinrong.pcjr.bean.Letter;
import com.pcjinrong.pcjr.core.BaseToolbarActivity;
import com.pcjinrong.pcjr.utils.DateUtils;

import java.util.Date;

import butterknife.BindView;


/**
 * 消息中心-详情
 * Created by Mario on 2016/5/24.
 */
public class MsgDetailActivity extends BaseToolbarActivity {
    @BindView(R.id.msg_title) TextView msg_title;
    @BindView(R.id.msg_time) TextView msg_time;
    @BindView(R.id.msg_content) TextView msg_content;

    @Override
    protected int getLayoutId() {
        return R.layout.member_msg_detail;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        showBack();
        setTitle("消息中心");
    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        Letter letter = (Letter) intent.getSerializableExtra("data");
        msg_title.setText(letter.getTitle());
        msg_time.setText(DateUtils.dateTimeToStr(new Date(letter.getSend_date()*1000),"yyyy-MM-dd HH:mm:ss"));
        msg_content.setText(letter.getContent());
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return false;

    }
}
