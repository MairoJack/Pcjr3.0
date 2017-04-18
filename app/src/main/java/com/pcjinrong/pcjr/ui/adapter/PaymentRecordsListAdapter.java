package com.pcjinrong.pcjr.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pcjinrong.pcjr.R;
import com.pcjinrong.pcjr.bean.PaymentRecords;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 回款记录适配器
 * Created by Mario on 2017/4/14.
 */
public class PaymentRecordsListAdapter extends RecyclerView.Adapter<PaymentRecordsListAdapter.ViewHolder> {

    private List<PaymentRecords> list;


    public PaymentRecordsListAdapter() {
        list = new ArrayList<>();
    }

    public void setData(List<PaymentRecords> list) {
        this.list.clear();
        this.list = list;
        notifyDataSetChanged();
    }

    public void addAll(List<PaymentRecords> list) {
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
        holder.bindTo((list.get(position)));
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

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindTo(PaymentRecords object) {
            mIvImgStatus.setImageResource(R.mipmap.icon_yes);
            mIvTxtDate.setText("2017-05-16");
            mIvTxtTerm.setText("第一期");
            mIvTxtStatus.setText("已回款");
            mIvTxtEstimatedAmount.setText("85490.61");
            mIvTxtActualAmount.setText("85490.61");
        }
    }

}
