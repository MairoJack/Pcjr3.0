package com.pcjinrong.pcjr.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.pcjinrong.pcjr.R;

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
public class RedPacketListAdapter extends RecyclerView.Adapter<RedPacketListAdapter.ViewHolder>{

    private List<RedPacket> list;
    private int type;

    private OnDeleteClickListener listener;

    public RedPacketListAdapter(int type) {
        list = new ArrayList<>();
        this.type = type;
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

    public void delete(RedPacket data) {
        this.list.remove(data);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if(type == 0){
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_red_packet, parent, false);
            return new ViewHolderFirst(view);
        }else{
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_red_packet_gray, parent, false);
            return new ViewHolderSecond(view);
        }

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(holder instanceof ViewHolderFirst){
            ViewHolderFirst holderFirst = (ViewHolderFirst) holder;
            holderFirst.bindTo((list.get(position)));
            holderFirst.mIvBtnGetRedPacket.setOnClickListener(v-> listener.onDeleteClick(v,list.get(position)));
        }else if(holder instanceof ViewHolderSecond){
            holder.bindTo((list.get(position)));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{
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

    public static class ViewHolderFirst extends ViewHolder {
        @BindView(R.id.btn_get_red_packet) Button mIvBtnGetRedPacket;

        public ViewHolderFirst(View itemView) {
            super(itemView);
        }
    }

    public static class ViewHolderSecond extends ViewHolder {

        public ViewHolderSecond(View itemView) {
            super(itemView);
        }
    }

    public void setOnDeleteClickListener(OnDeleteClickListener listener){
        this.listener = listener;
    }

    public interface OnDeleteClickListener{
        void onDeleteClick(View view , RedPacket data);
    }
}
