package com.pcjinrong.pcjr.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.pcjinrong.pcjr.R;
import com.pcjinrong.pcjr.bean.Product;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 产品列表适配器
 * Created by Mario on 2016/7/22.
 */
public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {

    private List<Product> list;
    private long current_time;

    public ProductListAdapter() {
        list = new ArrayList<>();
    }

    public void setData(List<Product> list, long current_time) {
        this.list.clear();
        this.list = list;
        this.current_time = current_time;
        notifyItemChanged(list.size() - 1);
    }

    public void addAll(List<Product> list, long current_time) {
        this.list.addAll(list);
        this.current_time = current_time;
        notifyItemChanged(list.size() - 1);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
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
        @BindView(R.id.name) TextView mIvName;
        @BindView(R.id.repayment) TextView mIvRepayment;
        @BindView(R.id.income) TextView mIvIncome;
        @BindView(R.id.amount) TextView mIvAmount;
        @BindView(R.id.month) TextView mIvMonth;
        @BindView(R.id.rate) TextView mIvRate;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v -> {

            });
        }

        public void bindTo(Product product) {
            mIvName.setText(product.getName());
            int repayment = product.getRepayment();
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
            mIvIncome.setText(String.valueOf(product.getYear_income()));
            BigDecimal amount = new BigDecimal(product.getAmount());
            BigDecimal tenThousand = new BigDecimal(10000);
            mIvAmount.setText(String.format("%.2f", amount.divide(tenThousand)));
            mIvMonth.setText(product.getMonth());
            mIvRate.setText(product.getRate() + "%");
        }
    }

}
