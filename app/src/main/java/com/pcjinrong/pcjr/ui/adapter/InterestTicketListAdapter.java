package com.pcjinrong.pcjr.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pcjinrong.pcjr.R;
import com.pcjinrong.pcjr.bean.InterestTicket;
import com.pcjinrong.pcjr.bean.InvestTicket;
import com.pcjinrong.pcjr.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 加息券-个人中心 适配器
 * Created by Mario on 2016/12/5.
 */
public class InterestTicketListAdapter extends RecyclerView.Adapter<InterestTicketListAdapter.ViewHolder> implements View.OnClickListener {

    private List<InterestTicket> list;
    private int type;

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public InterestTicketListAdapter(int type) {
        list = new ArrayList<>();
        this.type = type;
    }

    public void setData(List<InterestTicket> list) {
        this.list.clear();
        this.list = list;
        notifyDataSetChanged();
    }

    public void addAll(List<InterestTicket> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(type == 0){
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_interest_ticket, parent, false);
        }else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_interest_ticket_gray, parent, false);
        }
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindTo((list.get(position)));
        holder.itemView.setTag(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, (InterestTicket) v.getTag());
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.amount) TextView mIvAmount;
        @BindView(R.id.title) TextView mIvTitle;
        @BindView(R.id.end_time) TextView mIvEndTime;
        @BindView(R.id.msg) TextView mIvMsg;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindTo(InterestTicket object) {

            mIvAmount.setText(object.getRate());
            mIvMsg.setText("单笔投资 "+object.getStart_amount().replace(".00","")+" - "+object.getEnd_amount().replace(".00","")+" 元");
            mIvTitle.setText("来源:"+object.getTitle());
            mIvEndTime.setText("有效期至:"+ DateUtils.dateTimeToStr(new Date(object.getEnd_time() * 1000), "yyyy-MM-dd"));
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, InterestTicket data);
    }

}
