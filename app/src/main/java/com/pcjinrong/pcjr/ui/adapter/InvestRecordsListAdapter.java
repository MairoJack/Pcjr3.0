package com.pcjinrong.pcjr.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.pcjinrong.pcjr.R;
import com.pcjinrong.pcjr.bean.InvestRecords;
import com.pcjinrong.pcjr.utils.DateUtils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 投资记录适配器
 * Created by Mario on 2016/8/1.
 */
public class InvestRecordsListAdapter extends RecyclerView.Adapter<InvestRecordsListAdapter.ViewHolder> {

    private List<InvestRecords> list;

    public InvestRecordsListAdapter() {
        list = new ArrayList<>();
    }

    public void setData(List<InvestRecords> list) {
        this.list.clear();
        this.list = list;
        notifyDataSetChanged();
    }

    public void addAll(List<InvestRecords> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_invest_records, parent, false);
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
        @BindView(R.id.product_name) TextView mIvProductName;
        @BindView(R.id.repayment) TextView mIvRepayment;
        @BindView(R.id.amount) TextView mIvAmount;
        @BindView(R.id.year_income) TextView mIvYearIncome;
        @BindView(R.id.income) TextView mIvIncome;
        @BindView(R.id.date) TextView mIvDate;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindTo(InvestRecords object) {
            mIvProductName.setText(object.getProduct_name());
            int repayment = object.getRepayment();
            switch (repayment) {
                case 0:
                    mIvRepayment.setText("一次还本付息");
                    break;
                case 1:
                    mIvRepayment.setText("先息后本(按月付息)");
                    break;
                case 2:
                    mIvRepayment.setText("等额本息");
                    break;
                case 3:
                    mIvRepayment.setText("先息后本(按季付息)");
                    break;
            }

            String joinDate = DateUtils.dateTimeToStr(new Date(object.getJoin_date()*1000),"yyyy年MM月dd日");
            String deadline = DateUtils.dateTimeToStr(new Date(object.getDeadline()*1000),"yyyy年MM月dd日");

            String amount = object.getAmount();
            String interestTotal = object.getInterest_total();
            String extraInterestTotal = object.getExtra_interest_total();

            mIvAmount.setText(amount);
            if(object.getInterest_year_income().equals("0.00")){
                mIvYearIncome.setText(object.getYear_income());
            }else {
                mIvYearIncome.setText(object.getYear_income() + "+" + object.getInterest_year_income());
            }
            BigDecimal bd_amount = new BigDecimal(amount);
            BigDecimal bd_interestTotal = new BigDecimal(interestTotal);
            BigDecimal bd_extraInterestTotal = new BigDecimal(extraInterestTotal);
            mIvIncome.setText(String.valueOf(bd_amount.add(bd_interestTotal).add(bd_extraInterestTotal)));
            mIvDate.setText(joinDate+"起投-"+deadline+"到期");
        }
    }

}
