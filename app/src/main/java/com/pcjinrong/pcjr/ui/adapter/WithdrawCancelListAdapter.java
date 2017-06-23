package com.pcjinrong.pcjr.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.pcjinrong.pcjr.R;
import com.pcjinrong.pcjr.bean.BankCard;
import com.pcjinrong.pcjr.bean.WithdrawCancel;
import com.pcjinrong.pcjr.utils.BankUtils;
import com.pcjinrong.pcjr.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *  提现取消适配器
 *  Created by Mario on 2017/6/21上午9:32
 */
public class WithdrawCancelListAdapter extends RecyclerView.Adapter<WithdrawCancelListAdapter.ViewHolder> implements View.OnClickListener{

    private List<WithdrawCancel> list;

    private OnCancelClickListener listener;

    public WithdrawCancelListAdapter() {
        list = new ArrayList<>();
    }

    public void setData(List<WithdrawCancel> list) {
        this.list.clear();
        this.list = list;
        notifyDataSetChanged();
    }

    public void addAll(List<WithdrawCancel> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_withdraw_cancel, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.mIvBtnCancel.setOnClickListener(v-> listener.onCancelClick(v,list.get(position)));
        holder.bindTo((list.get(position)),getItemCount());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onClick(View v) {
        if (listener != null) {
            listener.onCancelClick(v, (WithdrawCancel) v.getTag());
        }
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.txt_date) TextView mIvTxtDate;
        @BindView(R.id.txt_bank) TextView mIvTxtBank;
        @BindView(R.id.btn_cancel) Button mIvBtnCancel;
        @BindView(R.id.txt_withdraw_amount) TextView mIvTxtWithdrawAmount;
        @BindView(R.id.txt_fee) TextView mIvTxtFee;
        @BindView(R.id.txt_tips) TextView mIvTxtTips;
        @BindView(R.id.img_bank) ImageView mIvImgBank;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindTo(WithdrawCancel object,int size) {

            if(getAdapterPosition() == size - 1){
                mIvTxtTips.setVisibility(View.VISIBLE);
            }else{
                mIvTxtTips.setVisibility(View.GONE);
            }
            BankCard bankCard = object.getBank_card();
            mIvTxtDate.setText(DateUtils.dateTimeToStr(new Date(object.getJoin_date() * 1000), "yyyy-MM-dd HH:mm:ss"));
            mIvTxtBank.setText(bankCard.getBank()+"(尾号"+ bankCard.getCard_last()+")");
            mIvTxtWithdrawAmount.setText(object.getActual_amount());
            mIvTxtFee.setText(object.getFee());
            mIvImgBank.setImageResource(BankUtils.getBankById(Integer.parseInt(bankCard.getBank_code())).getIcon());
        }
    }

    public void setOnCancelClickListener(OnCancelClickListener listener){
        this.listener = listener;
    }

    public interface OnCancelClickListener{
        void onCancelClick(View view , WithdrawCancel data);
    }
}
