package com.pcjinrong.pcjr.ui.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pcjinrong.pcjr.R;
import com.pcjinrong.pcjr.bean.InterestTicket;
import com.pcjinrong.pcjr.bean.Product;
import com.pcjinrong.pcjr.utils.DateUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 加息券适配器
 * Created by Mario on 2016/12/5.
 */
public class InterestListAdapter extends RecyclerView.Adapter<InterestListAdapter.ViewHolder> implements View.OnClickListener {

    private List<InterestTicket> list;
    private Product product;
    private int type;

    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public InterestListAdapter() {
        list = new ArrayList<>();
        this.type = type;
    }

    public void setData(List<InterestTicket> list,Product product) {
        this.list.clear();
        this.list = list;
        this.product = product;
        notifyDataSetChanged();
    }

    public void addAll(List<InterestTicket> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_interest, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindTo((list.get(position)),product);
        holder.itemView.setTag(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            mOnItemClickListener.onItemClick(v, (InterestTicket) v.getTag());
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.interest)
        TextView mIvInterest;
        @BindView(R.id.description)
        TextView mIvDescription;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindTo(InterestTicket object, Product product) {
            int investDays = (int)((product.getDeadline() - product.getValue_date()) / 86400);
            String series;
            switch (object.getSeries()) {
                case 1:
                    series = "仅限大城小爱系列";
                    break;
                case 2:
                    series = "仅限国泰民安系列";
                    break;
                case 3:
                    series = "仅限珠联璧合系列";
                    break;
                default:
                    series = "全部产品通用";
                    break;
            }
            String limit = "期限:";
            if(object.getEnd_day() == 0){
                limit += "≥"+object.getStart_day()+"天；";
            }else{
                limit += object.getStart_day()+"-"+object.getEnd_day()+"天；";
            }
            mIvInterest.setText(object.getRate() + "%");
            mIvDescription.setText("单笔投资:" + object.getStart_amount().replace(".00","") + "-"
                    + object.getEnd_amount().replace(".00","") + "元；有效期至"
                    + DateUtils.dateTimeToStr(new Date(object.getEnd_time() * 1000), "yyyy-MM-dd")
                    + limit + series);

            if(investDays<object.getStart_day() || (object.getEnd_day() != 0 && investDays>object.getEnd_day())
                    || (object.getSeries() != 0 && product.getSeries() != object.getSeries())
                    || System.currentTimeMillis()/1000 < object.getStart_time()
                    || System.currentTimeMillis()/1000 > object.getEnd_time()){
                mIvInterest.setTextColor(Color.parseColor("#cacaca"));
                mIvDescription.setTextColor(Color.parseColor("#cacaca"));
            }
        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, InterestTicket data);
    }

}
