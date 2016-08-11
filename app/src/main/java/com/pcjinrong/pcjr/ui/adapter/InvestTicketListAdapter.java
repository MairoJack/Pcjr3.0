package com.pcjinrong.pcjr.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pcjinrong.pcjr.R;
import com.pcjinrong.pcjr.bean.InvestTicket;
import com.pcjinrong.pcjr.bean.Letter;
import com.pcjinrong.pcjr.bean.RedPacket;
import com.pcjinrong.pcjr.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 投资券适配器
 * Created by Mario on 2016/8/1.
 */
public class InvestTicketListAdapter extends RecyclerView.Adapter<InvestTicketListAdapter.ViewHolder> implements View.OnClickListener {

    private List<InvestTicket> list;
    private int type;

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public InvestTicketListAdapter(int type) {
        list = new ArrayList<>();
        this.type = type;
    }

    public void setData(List<InvestTicket> list) {
        this.list.clear();
        this.list = list;
        notifyDataSetChanged();
    }

    public void addAll(List<InvestTicket> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(type == 0){
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_invest_ticket, parent, false);
        }else {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_invest_ticket_gray, parent, false);
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
            mOnItemClickListener.onItemClick(v, (InvestTicket) v.getTag());
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

        public void bindTo(InvestTicket object) {

            mIvAmount.setText(object.getAmount().replace(".00",""));
            mIvMsg.setText("满 "+object.getReach_amount().replace(".00","")+" 元返 "+object.getAmount().replace(".00","")+" 元");
            mIvTitle.setText("来源:"+object.getTitle());
            mIvEndTime.setText("有效期至:"+ DateUtils.dateTimeToStr(new Date(object.getEnd_time() * 1000), "yyyy-MM-dd"));
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, InvestTicket data);
    }

}
