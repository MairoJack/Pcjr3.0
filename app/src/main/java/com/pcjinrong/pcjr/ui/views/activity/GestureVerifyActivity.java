package com.pcjinrong.pcjr.ui.views.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.pcjinrong.pcjr.R;
import com.pcjinrong.pcjr.constant.Constant;
import com.pcjinrong.pcjr.core.BaseToolbarActivity;
import com.pcjinrong.pcjr.utils.SPUtils;
import com.pcjinrong.pcjr.widget.GestureContentView;
import com.pcjinrong.pcjr.widget.GestureDrawline;


import butterknife.BindView;


/**
 *
 * 手势绘制/校验界面
 *
 */
public class GestureVerifyActivity extends BaseToolbarActivity {

	@BindView(R.id.text_tip) TextView mTextTip;
	@BindView(R.id.forget) TextView forget;
	@BindView(R.id.gesture_container) FrameLayout mGestureContainer;

	private GestureContentView mGestureContentView;

	private long mExitTime = 0;



	@Override
	protected int getLayoutId() {
		return R.layout.member_safe_setting_gesture_verify;
	}


	@Override
	protected void initListeners() {

	}

	@Override
	protected void initData() {

	}


	@Override
	protected void initViews(Bundle savedInstanceState) {
		setTitle("手势解锁");
        // 初始化一个显示各个点的viewGroup
		mGestureContentView = new GestureContentView(this, true, (String) SPUtils.get(GestureVerifyActivity.this, "gesture",""),
				new GestureDrawline.GestureCallBack() {

					@Override
					public void onGestureCodeInput(String inputCode) {

					}

					@Override
					public void checkedSuccess() {
						mGestureContentView.clearDrawlineState(0L);
						Constant.IS_GESTURE_LOGIN = true;
                        setResult(RESULT_OK);
                        GestureVerifyActivity.this.finish();

					}

					@Override
					public void checkedFail() {
						mGestureContentView.clearDrawlineState(1300L);
						mTextTip.setVisibility(View.VISIBLE);
						mTextTip.setText("密码错误");
						// 左右移动动画
						Animation shakeAnimation = AnimationUtils.loadAnimation(GestureVerifyActivity.this, R.anim.shake);
						mTextTip.startAnimation(shakeAnimation);
					}
				});
		// 设置手势解锁显示到哪个布局里面
		mGestureContentView.setParentView(mGestureContainer);


		forget.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				finish();
                startActivity(new Intent(GestureVerifyActivity.this,LoginActivity.class));
			}
		});

	}

}
