package com.pcjinrong.pcjr.ui.views.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pcjinrong.pcjr.R;
import com.pcjinrong.pcjr.core.BaseToolbarActivity;
import com.pcjinrong.pcjr.utils.SPUtils;
import com.pcjinrong.pcjr.widget.GestureContentView;
import com.pcjinrong.pcjr.widget.GestureDrawline;
import com.pcjinrong.pcjr.widget.LockIndicator;

import butterknife.BindView;


/**
 *
 * 手势密码设置界面
 *
 */
public class GestureEditActivity extends BaseToolbarActivity{
	/** 首次提示绘制手势密码，可以选择跳过 */
	public static final String PARAM_IS_FIRST_ADVICE = "PARAM_IS_FIRST_ADVICE";

	@BindView(R.id.text_reset) TextView mTextReset;
	@BindView(R.id.text_tip) TextView mTextTip;
	@BindView(R.id.gesture_container) FrameLayout mGestureContainer;
	@BindView(R.id.lock_indicator) LockIndicator mLockIndicator;

	private GestureContentView mGestureContentView;
	private String mParamSetUpcode = null;
	private String mParamPhoneNumber;
	private boolean mIsFirstInput = true;
	private String mFirstPassword = null;
	private String mConfirmPassword = null;
	private int mParamIntentCode;
    private RelativeLayout back;

	@Override
	protected int getLayoutId() {
		return R.layout.member_safe_setting_gesture_edit;
	}


	@Override
	protected void initListeners() {

	}

	@Override
	protected void initData() {

	}
	@Override
	protected void initViews(Bundle savedInstanceState) {

		setTitle("设置手势密码");
		// 初始化一个显示各个点的viewGroup
		mGestureContentView = new GestureContentView(this, false, "", new GestureDrawline.GestureCallBack() {
			@Override
			public void onGestureCodeInput(String inputCode) {
				if (!isInputPassValidate(inputCode)) {
					mTextTip.setText("最少链接4个点, 请重新输入");
					mGestureContentView.clearDrawlineState(0L);
					return;
				}
				if (mIsFirstInput) {
					mFirstPassword = inputCode;
					updateCodeList(inputCode);
					mGestureContentView.clearDrawlineState(0L);
					mTextReset.setClickable(true);
					mTextReset.setText(getString(R.string.reset_gesture_code));
				} else {
					if (inputCode.equals(mFirstPassword)) {
						SPUtils.put(GestureEditActivity.this, "gesture", inputCode);
                        Toast.makeText(GestureEditActivity.this, "设置成功", Toast.LENGTH_SHORT).show();
						mGestureContentView.clearDrawlineState(0L);
						setResult(RESULT_OK);
						GestureEditActivity.this.finish();
					} else {
						mTextTip.setText("与上一次绘制不一致，请重新绘制");
						// 左右移动动画
						Animation shakeAnimation = AnimationUtils.loadAnimation(GestureEditActivity.this, R.anim.shake);
						mTextTip.startAnimation(shakeAnimation);
						// 保持绘制的线，1.5秒后清除
						mGestureContentView.clearDrawlineState(1300L);
					}
				}
				mIsFirstInput = false;
			}

			@Override
			public void checkedSuccess() {
				
			}

			@Override
			public void checkedFail() {
				
			}
		});
		// 设置手势解锁显示到哪个布局里面
		mGestureContentView.setParentView(mGestureContainer);
		updateCodeList("");

		mTextReset.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				mIsFirstInput = true;
				updateCodeList("");
				mTextTip.setText(getString(R.string.set_gesture_pattern));
			}
		});
	}
	

	
	private void updateCodeList(String inputCode) {
		// 更新选择的图案
		mLockIndicator.setPath(inputCode);
	}


	
	private boolean isInputPassValidate(String inputPassword) {
		if (TextUtils.isEmpty(inputPassword) || inputPassword.length() < 4) {
			return false;
		}
		return true;
	}

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if (keyCode == KeyEvent.KEYCODE_BACK )
        {
            setResult(RESULT_CANCELED);
            finish();
        }
        return false;

    }
	
}
