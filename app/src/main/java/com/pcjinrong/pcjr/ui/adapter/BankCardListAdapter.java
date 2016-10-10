package com.pcjinrong.pcjr.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pcjinrong.pcjr.R;
import com.pcjinrong.pcjr.bean.BankCard;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 银行卡适配器
 * Created by Mario on 2016/8/1.
 */
public class BankCardListAdapter extends RecyclerView.Adapter<BankCardListAdapter.ViewHolder>{

    private List<BankCard> list;
    private String realname;

    private OnDeleteClickListener listener;

    public BankCardListAdapter() {
        list = new ArrayList<>();
    }

    public void setData(List<BankCard> list, String realname) {
        this.list.clear();
        this.list = list;
        this.realname = realname;
        notifyDataSetChanged();
    }

    public void delete(BankCard data) {
        this.list.remove(data);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_bank_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindTo((list.get(position)), realname);
        holder.mIvDelete.setOnClickListener(v->{
            listener.onDeleteClick(v,list.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.bank) TextView mIvBank;
        @BindView(R.id.card_no) TextView mIvCardNo;
        @BindView(R.id.status) TextView mIvStatus;
        @BindView(R.id.delete) TextView mIvDelete;
        @BindView(R.id.realname) TextView mIvRealname;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindTo(BankCard object, String realname) {
            mIvBank.setText(object.getBank());
            mIvCardNo.setText(object.getCard_no());
            mIvRealname.setText(realname);

        }
    }

    public void setOnDeleteClickListener(OnDeleteClickListener listener){
        this.listener = listener;
    }

    public interface OnDeleteClickListener{
        void onDeleteClick(View view , BankCard data);
    }

}
