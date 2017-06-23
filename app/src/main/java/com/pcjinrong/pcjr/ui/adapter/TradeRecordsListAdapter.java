package com.pcjinrong.pcjr.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.pcjinrong.pcjr.R;
import com.pcjinrong.pcjr.bean.TradeRecords;
import com.pcjinrong.pcjr.utils.DateUtils;

import java.math.BigDecimal;
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
        holder.bindTo(list,position);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.remark) TextView mIvRemark;
        @BindView(R.id.img_status) ImageView mImgStauts;
        @BindView(R.id.amount) TextView mIvAmount;
        @BindView(R.id.fee) TextView mIvFee;
        @BindView(R.id.balance) TextView mIvBalance;
        @BindView(R.id.date) TextView mIvDate;
        @BindView(R.id.header) LinearLayout mIvHeader;
        @BindView(R.id.header_date) TextView mIvHeaderDate;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindTo(List<TradeRecords> list,int position) {

            TradeRecords object = list.get(position);
            String currentDate = DateUtils.dateTimeToStr(new Date(object.getJoin_date() * 1000), "yyyy年MM");

            if(position == 0){
                mIvHeader.setVisibility(View.VISIBLE);
                mIvHeaderDate.setText(currentDate);
            }else {
                String prevDate = DateUtils.dateTimeToStr(new Date(list.get(position - 1 ).getJoin_date() * 1000), "yyyy年MM");
                if(currentDate.equals(prevDate)){
                    mIvHeader.setVisibility(View.GONE);
                }else{
                    mIvHeader.setVisibility(View.VISIBLE);
                    mIvHeaderDate.setText(currentDate);
                }
            }

            mIvRemark.setText(object.getRemark());
            mIvBalance.setText("余额:" + object.getBalance());
            mIvDate.setText(DateUtils.dateTimeToStr(new Date(object.getJoin_date() * 1000), "yyyy-MM-dd"));

            if(new BigDecimal(object.getFee()).compareTo(BigDecimal.ZERO) > 0){
                mIvFee.setText("手续费:"+object.getFee());
                mIvFee.setVisibility(View.VISIBLE);
            } else {
                mIvFee.setVisibility(View.GONE);
            }
            if (object.getDirection() == 0) {
                mImgStauts.setImageResource(R.mipmap.icon_out);
                mIvAmount.setText("-" + object.getAmount());
            } else {
                mImgStauts.setImageResource(R.mipmap.icon_income);
                mIvAmount.setText("+" + object.getAmount());
            }
        }
    }

}
