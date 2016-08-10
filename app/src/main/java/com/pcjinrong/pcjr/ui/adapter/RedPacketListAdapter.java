package com.pcjinrong.pcjr.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pcjinrong.pcjr.R;
import com.pcjinrong.pcjr.bean.Letter;
import com.pcjinrong.pcjr.bean.RedPacket;
import com.pcjinrong.pcjr.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 红包适配器
 * Created by Mario on 2016/8/1.
 */
public class RedPacketListAdapter extends RecyclerView.Adapter<RedPacketListAdapter.ViewHolder> implements View.OnClickListener {

    private List<RedPacket> list;

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public RedPacketListAdapter() {
        list = new ArrayList<>();
    }

    public void setData(List<RedPacket> list) {
        this.list.clear();
        this.list = list;
        notifyDataSetChanged();
    }

    public void addAll(List<RedPacket> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_red_packet, parent, false);
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
            mOnItemClickListener.onItemClick(v, (RedPacket) v.getTag());
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.amount) TextView mIvAmount;
        @BindView(R.id.title) TextView mIvTitle;
        @BindView(R.id.join_date) TextView mIvJoinDate;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindTo(RedPacket object) {

            mIvAmount.setText("￥" + object.getAmount());
            mIvTitle.setText("来源:" + object.getTitle());
            mIvJoinDate.setText("红包获得时间:" + DateUtils.dateTimeToStr(new Date(object.getJoin_date() * 1000), "yyyy-MM-dd"));

        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, RedPacket data);
    }

}
