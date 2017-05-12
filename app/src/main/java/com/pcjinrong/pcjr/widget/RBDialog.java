package com.pcjinrong.pcjr.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

    @BindView(R.id.message) TextView message;
    @BindView(R.id.btn_apply) Button btn_apply;

    @BindView(R.id.group_star) LinearLayout group_star;

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

        group_star.removeAllViews();
        for(int i = 1;i<=rd.getDifficult();i++){
            ImageView star = new ImageView(context);
            star.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT,1.0f));
            star.setImageResource(R.mipmap.icon_star);
            group_star.addView(star);
        }
        for(int i = rd.getDifficult()+1;i<=5;i++){
            ImageView star_gray = new ImageView(context);
            star_gray.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT,1.0f));
            star_gray.setImageResource(R.mipmap.icon_star_gray);
            group_star.addView(star_gray);
        }
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
