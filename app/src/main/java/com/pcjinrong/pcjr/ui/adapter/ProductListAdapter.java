package com.pcjinrong.pcjr.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.pcjinrong.pcjr.R;
import com.pcjinrong.pcjr.bean.Product;
import com.pcjinrong.pcjr.constant.Constant;
import com.pcjinrong.pcjr.utils.DateUtils;
import com.pcjinrong.pcjr.widget.ProgressWheel;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.iwgang.countdownview.CountdownView;

/**
 * 产品列表适配器
 * Created by Mario on 2016/7/22.
 */
public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> implements View.OnClickListener {

    private List<Product> list;
    private long server_time;
    private long sys_time;

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public ProductListAdapter() {
        list = new ArrayList<>();
    }

    public void setData(List<Product> list, long server_time, long sys_time) {
        this.list.clear();
        this.list = list;
        this.server_time = server_time * 1000;
        this.sys_time = sys_time;
        notifyDataSetChanged();
    }

    public void addAll(List<Product> list, long server_time, long sys_time) {
        this.list.addAll(list);
        this.server_time = server_time * 1000;
        this.sys_time = sys_time;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        ViewHolder viewHolder;
        if(viewType == Constant.TYPE_ONGOING){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
            viewHolder = new ViewHolderFirst(view);
        }else if(viewType == Constant.TYPE_SUCCESS){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_success, parent, false);
            viewHolder = new ViewHolderFirst(view);
        }else if (viewType == Constant.TYPE_COMING){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_coming, parent, false);
            viewHolder = new ViewHolderSecond(view);
        }else{
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product_countdown, parent, false);
            viewHolder = new ViewHolderThird(view);
        }

        view.setOnClickListener(this);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(holder instanceof ViewHolderThird) {
            ((ViewHolderThird)holder).bindTo(list.get(position),server_time,sys_time);
        }else{
            holder.bindTo(list.get(position));
        }
        holder.itemView.setTag(list.get(position).getId());
    }

    @Override
    public int getItemViewType(int position) {
        Product product = list.get(position);
        int type;
        if (product.getStatus() == 2 || product.getStatus() == 3 || product.getStatus() == 4) {
            type = Constant.TYPE_SUCCESS;
        } else {
            long current_time = server_time + System.currentTimeMillis() - sys_time;
            Date date = new Date(current_time);
            Date pub_date = new Date(product.getPub_date() * 1000);
            if (DateUtils.isStartDateBeforeEndDate(date, pub_date)) {
                if (DateUtils.getHoursOfTowDiffDate(date, pub_date) > 1) {
                    type = Constant.TYPE_COMING;
                } else {
                    type = Constant.TYPE_COUNTDOWN;
                }
            } else {
                type = Constant.TYPE_ONGOING;
            }
        }
        return type;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, (String) v.getTag());
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name) TextView mIvName;
        @BindView(R.id.repayment) TextView mIvRepayment;
        @BindView(R.id.income) TextView mIvIncome;
        @BindView(R.id.amount) TextView mIvAmount;
        @BindView(R.id.month) TextView mIvMonth;

        @BindView(R.id.tqhk) ImageView mIvTqhk;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindTo(Product object) {
            mIvName.setText(object.getName());
            int repayment = object.getRepayment();
            int preview_repayment = object.getIs_preview_repayment();
            switch (repayment) {
                case 0:mIvRepayment.setText("一次还本付息");break;
                case 1:mIvRepayment.setText("先息后本(按月付息)");break;
                case 2:mIvRepayment.setText("等额本息");break;
                case 3:mIvRepayment.setText("先息后本(按季付息)");break;
            }
            if (object.getFinish_preview_repayment() == 1) {
                mIvTqhk.setImageResource(R.mipmap.ic_tqhk);
            } else {
                if (preview_repayment == 0) {
                    mIvTqhk.setVisibility(View.GONE);
                } else if (preview_repayment == 1) {
                    if (object.getStatus() == 1) {
                        mIvTqhk.setImageResource(R.mipmap.ic_kntqhk);
                    } else {
                        mIvTqhk.setImageResource(R.mipmap.ic_kntqhk_gray);
                    }
                }
            }
            mIvIncome.setText(String.valueOf(object.getYear_income()));
            BigDecimal amount = new BigDecimal(object.getAmount());
            BigDecimal tenThousand = new BigDecimal(10000);
            mIvAmount.setText(String.format("%.2f", amount.divide(tenThousand)));
            mIvMonth.setText(object.getMonth());

        }
    }

    public static class ViewHolderFirst extends ViewHolder {
        @BindView(R.id.rate) TextView mIvRate;
        @BindView(R.id.pw_spinner) ProgressWheel mIvProgressWheel;

        public ViewHolderFirst(View itemView) {
            super(itemView);
        }

        public void bindTo(Product object) {
            super.bindTo(object);
            if (object.getStatus() == 2 || object.getStatus() == 3) {
                mIvRate.setText("募集成功");
            } else if (object.getStatus() == 4) {
                mIvRate.setText("项目结束");
            } else {
                mIvRate.setText(object.getRate() + "%");
                mIvProgressWheel.setProgress(18 * object.getRate() / 5);
            }
        }
    }

    public static class ViewHolderSecond extends ViewHolder {
        @BindView(R.id.time) TextView mIvTime;

        public ViewHolderSecond(View itemView) {
            super(itemView);
        }

        public void bindTo(Product object) {
            super.bindTo(object);
            mIvTime.setText(DateUtils.dateTimeToStr(new Date(object.getPub_date() * 1000), "MM-dd HH:mm"));
        }
    }

    public static class ViewHolderThird extends ViewHolder {
        @BindView(R.id.cv_countdown)
        CountdownView mIvCountdown;

        public ViewHolderThird(View itemView) {
            super(itemView);
        }

        public void bindTo(Product object, long server_time, long sys_time) {
            super.bindTo(object);
            long current_time = server_time + System.currentTimeMillis() - sys_time;
            mIvCountdown.start(object.getPub_date() * 1000 - current_time);
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, String id);
    }
}
