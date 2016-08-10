package com.pcjinrong.pcjr.ui.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pcjinrong.pcjr.R;
import com.pcjinrong.pcjr.bean.Letter;
import com.pcjinrong.pcjr.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 站内信适配器
 * Created by Mario on 2016/8/1.
 */
public class LetterListAdapter extends RecyclerView.Adapter<LetterListAdapter.ViewHolder>  implements View.OnClickListener{

    private List<Letter> list;

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public LetterListAdapter() {
        list = new ArrayList<>();
    }

    public void setData(List<Letter> list) {
        this.list.clear();
        this.list = list;
        notifyDataSetChanged();
    }

    public void addAll(List<Letter> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_msg_center, parent, false);
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
            mOnItemClickListener.onItemClick(v,(Letter)v.getTag());
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.title) TextView mIvTitle;
        @BindView(R.id.send_date) TextView mIvSendDate;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindTo(Letter object) {

            if (object.getRead_status() == 1) {
                mIvTitle.setTextColor(Color.parseColor("#ABABAB"));
                mIvSendDate.setTextColor(Color.parseColor("#ABABAB"));
            } else {
                mIvTitle.setTextColor(Color.parseColor("#333333"));
                mIvSendDate.setTextColor(Color.parseColor("#333333"));
            }
            mIvTitle.setText(object.getTitle());
            mIvSendDate.setText(DateUtils.dateTimeToStr(new Date(object.getSend_date() * 1000), "yyyy-MM-dd"));

        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public  interface OnRecyclerViewItemClickListener {
        void onItemClick(View view , Letter data);
    }

}
