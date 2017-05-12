package com.pcjinrong.pcjr.ui.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pcjinrong.pcjr.R;
import com.pcjinrong.pcjr.bean.InvestProductRepaymentInfo;
import com.pcjinrong.pcjr.utils.DateUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 回款记录适配器
 * Created by Mario on 2017/4/14.
 */
public class PaymentRecordsListAdapter extends RecyclerView.Adapter<PaymentRecordsListAdapter.ViewHolder> {

    private List<InvestProductRepaymentInfo> list;


    public PaymentRecordsListAdapter() {
        list = new ArrayList<>();
    }

    public void setData(List<InvestProductRepaymentInfo> list) {
        this.list.clear();
        this.list = list;
        notifyDataSetChanged();
    }

    public void addAll(List<InvestProductRepaymentInfo> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_payment_record, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindTo((list.get(position)),getItemCount());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.img_status) ImageView mIvImgStatus;
        @BindView(R.id.txt_date) TextView mIvTxtDate;
        @BindView(R.id.txt_term) TextView mIvTxtTerm;
        @BindView(R.id.txt_status) TextView mIvTxtStatus;
        @BindView(R.id.txt_estimated_amount) TextView mIvTxtEstimatedAmount;
        @BindView(R.id.txt_actual_amount) TextView mIvTxtActualAmount;
        @BindView(R.id.line) View mIvLine;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindTo(InvestProductRepaymentInfo object,int size) {

            BigDecimal bd_estimated_capital = new BigDecimal(object.getEstimated_capital());
            BigDecimal bd_estimated_interest = new BigDecimal(object.getEstimated_interest());
            BigDecimal bd_actual_capital = new BigDecimal(object.getActual_capital());
            BigDecimal bd_actual_interest = new BigDecimal(object.getActual_interest());

            if(getAdapterPosition() == size - 1){
                mIvLine.setVisibility(View.INVISIBLE);
            }else{
                mIvLine.setVisibility(View.VISIBLE);
            }

            switch (object.getType()){
                case 0:mIvTxtTerm.setText("第"+object.getTerm()+"期");break;
                case 1:mIvTxtTerm.setText("补息");break;
                case 2:mIvTxtTerm.setText("加息");break;
            }

            if(object.getStatus() == 1){
                mIvTxtStatus.setText("已回款");
                mIvTxtStatus.setTextColor(Color.parseColor("#50b134"));
                mIvImgStatus.setImageResource(R.mipmap.icon_yes);
                mIvTxtDate.setText(DateUtils.dateTimeToStr(new Date(object.getActual_time() * 1000), "yyyy-MM-dd"));
            }else{
                mIvTxtStatus.setText("未回款");
                mIvTxtStatus.setTextColor(Color.parseColor("#f87573"));
                mIvImgStatus.setImageResource(R.mipmap.icon_no);
                mIvTxtDate.setText(DateUtils.dateTimeToStr(new Date(object.getEstimated_time() * 1000), "yyyy-MM-dd"));
            }

            mIvTxtEstimatedAmount.setText(bd_estimated_capital.add(bd_estimated_interest).toString());
            mIvTxtActualAmount.setText(bd_actual_capital.add(bd_actual_interest).toString());



        }
    }

}
