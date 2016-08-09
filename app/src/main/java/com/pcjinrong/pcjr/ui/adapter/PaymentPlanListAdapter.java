package com.pcjinrong.pcjr.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.pcjinrong.pcjr.R;
import com.pcjinrong.pcjr.bean.PaymentPlan;
import com.pcjinrong.pcjr.utils.DateUtils;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 回款计划适配器
 * Created by Mario on 2016/8/2.
 */
public class PaymentPlanListAdapter extends RecyclerView.Adapter<PaymentPlanListAdapter.ViewHolder> {

    private List<PaymentPlan> list;

    public PaymentPlanListAdapter() {
        list = new ArrayList<>();
    }

    public void setData(List<PaymentPlan> lists) {
        this.list.clear();
        this.list.addAll(lists);
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_payment_plan, parent, false);
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
        @BindView(R.id.status) TextView mIvStatus;
        @BindView(R.id.amount) TextView mIvAmount;
        @BindView(R.id.date) TextView mIvDate;
        @BindView(R.id.icon) ImageView mIvIcon;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindTo(PaymentPlan object) {
            int status = object.getStatus();
            if (status == 0) {
                mIvDate.setText(DateUtils.dateTimeToStr(new Date(object.getEstimated_time() * 1000), "yyyy-MM-dd"));
                mIvStatus.setText("未回款");
                mIvIcon.setImageResource(R.mipmap.item_error);
            } else if (status == 1) {
                mIvDate.setText(DateUtils.dateTimeToStr(new Date(object.getActual_time() * 1000), "yyyy-MM-dd"));
                mIvStatus.setText("已回款");
                mIvIcon.setImageResource(R.mipmap.item_right);
            }
            mIvProductName.setText(object.getProduct_name());
            BigDecimal estimated_interest = new BigDecimal(object.getEstimated_interest());
            BigDecimal estimated_capital = new BigDecimal(object.getEstimated_capital());
            mIvAmount.setText(String.format("%.2f", estimated_interest.add(estimated_capital)) + "元");

        }
    }

}
