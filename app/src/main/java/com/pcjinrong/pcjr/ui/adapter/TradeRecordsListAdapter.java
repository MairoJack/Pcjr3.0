package com.pcjinrong.pcjr.ui.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.pcjinrong.pcjr.R;
import com.pcjinrong.pcjr.bean.TradeRecords;
import com.pcjinrong.pcjr.utils.DateUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 交易记录适配器
 * Created by Mario on 2016/8/1.
 */
public class TradeRecordsListAdapter extends RecyclerView.Adapter<TradeRecordsListAdapter.ViewHolder> {

    private List<TradeRecords> list;

    public TradeRecordsListAdapter() {
        list = new ArrayList<>();
    }

    public void setData(List<TradeRecords> list) {
        this.list.clear();
        this.list = list;
        notifyDataSetChanged();
    }

    public void addAll(List<TradeRecords> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_trade_records, parent, false);
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
        @BindView(R.id.remark) TextView mIvRemark;
        @BindView(R.id.fee) TextView mIvFee;
        @BindView(R.id.amount) TextView mIvAmount;
        @BindView(R.id.balance) TextView mIvBalance;
        @BindView(R.id.date) TextView mIvDate;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindTo(TradeRecords object) {
            mIvRemark.setText(object.getRemark());
            mIvBalance.setText(object.getBalance() + "元");
            mIvDate.setText(DateUtils.dateTimeToStr(new Date(object.getJoin_date() * 1000), "yyyy年MM月dd日"));
            if (object.getDirection() == 0) {
                mIvAmount.setTextColor(Color.parseColor("#fd4c4a"));
                mIvFee.setTextColor(Color.parseColor("#fd4c4a"));
                mIvAmount.setText("- " + object.getAmount() + "元");
                mIvFee.setText("- " + object.getFee() + "元");
            } else {
                mIvAmount.setTextColor(Color.parseColor("#0FB61E"));
                mIvFee.setTextColor(Color.parseColor("#0FB61E"));
                mIvAmount.setText("+ " + object.getAmount() + "元");
                mIvFee.setText("+ " + object.getFee() + "元");
            }
        }
    }

}
