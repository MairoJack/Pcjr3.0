package com.pcjinrong.pcjr.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.pcjinrong.pcjr.R;
import com.pcjinrong.pcjr.bean.RechargeDifficult;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * RatingBar Dialog
 * Created by Mario on 2017/3/22.
 */

public class RBDialog extends android.app.Dialog implements android.view.View.OnClickListener{

    @BindView(R.id.ratingBar) RatingBar mRb;
    @BindView(R.id.message) TextView message;
    @BindView(R.id.btn_apply) Button btn_apply;


    private MyDialogListener listener;

    private Context context;
    private RechargeDifficult rd;

    public RBDialog(Context context) {
        super(context);
        this.context = context;
    }

    public RBDialog(Context context, int theme,RechargeDifficult rd){
        super(context, theme);
        this.context = context;
        this.rd = rd;
    }
    public RBDialog(Context context, int theme, MyDialogListener listener){
        super(context, theme);
        this.context = context;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.rb_dialog);
        ButterKnife.bind(this);

        LayerDrawable ld_stars = (LayerDrawable) mRb.getProgressDrawable();
        ld_stars.getDrawable(2).setColorFilter(Color.parseColor("#FCCE33"), PorterDuff.Mode.SRC_ATOP);
        ld_stars.getDrawable(1).setColorFilter(Color.parseColor("#FCCE33"), PorterDuff.Mode.SRC_ATOP);
        mRb.setRating(rd.getDifficult());
        message.setText(rd.getMessage());
        btn_apply.setOnClickListener( view-> dismiss());
    }

    @Override
    public void onClick(View view) {
        listener.onClick(view);
    }

    public interface MyDialogListener{
        void onClick(View view);
    }
}
