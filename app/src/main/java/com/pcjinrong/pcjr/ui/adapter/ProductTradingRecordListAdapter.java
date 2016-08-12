package com.pcjinrong.pcjr.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pcjinrong.pcjr.R;
import com.pcjinrong.pcjr.bean.ProductTradingRecord;
import com.pcjinrong.pcjr.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 产品投资记录适配器
 * Created by Mario on 2016/8/1.
 */
public class ProductTradingRecordListAdapter extends RecyclerView.Adapter<ProductTradingRecordListAdapter.ViewHolder>{

    private List<ProductTradingRecord> list;

    public ProductTradingRecordListAdapter() {
        list = new ArrayList<>();
    }

    public void setData(List<ProductTradingRecord> list) {
        this.list.clear();
        this.list = list;
        notifyDataSetChanged();
    }

    public void addAll(List<ProductTradingRecord> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_trading_record, parent, false);
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
        @BindView(R.id.member_name) TextView mIvMemberName;
        @BindView(R.id.amount) TextView mIvAmount;
        @BindView(R.id.join_date) TextView mIvJoinDate;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindTo(ProductTradingRecord object) {

            mIvMemberName.setText(object.getMember_name());
            mIvAmount.setText(object.getAmount()+"元");
            mIvJoinDate.setText(DateUtils.dateTimeToStr(new Date(object.getJoin_date() * 1000), "yyyy-MM-dd HH:mm"));

        }
    }
}
