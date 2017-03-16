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
import java.util.Collections;
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
    private long server_time;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public InterestListAdapter() {
        list = new ArrayList<>();

    }

    public void setData(List<InterestTicket> list,Product product,long server_time) {
        this.list.clear();
        this.list = list;
        this.product = product;
        this.server_time = server_time;
        int investDays = (int)((product.getDeadline() - product.getValue_date()) / 86400);
        for(InterestTicket object : list){
            if(!object.getId().equals("00")) {
                if (investDays < object.getStart_day() || (object.getEnd_day() != 0 && investDays > object.getEnd_day())
                        || (object.getSeries() != 0 && product.getSeries() != object.getSeries())
                        || server_time / 1000 < object.getStart_time()
                        || server_time / 1000 > object.getEnd_time()) {
                    object.setSelectable(false);
                }else{
                    object.setSelectable(true);
                }
            }else{
                object.setSelectable(true);
            }
        }
        Collections.sort(this.list);
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

        public void bindTo(InterestTicket object) {
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
                case 4:
                    series = "仅限商通保盈系列";
                    break;
                default:
                    series = "全部产品通用";
                    break;
            }
            String limit = "期限:";
            limit += object.getStart_day()+"-"+object.getEnd_day()+"天；";
            mIvInterest.setText(object.getRate() + "%");
            mIvDescription.setText("单笔投资:" + object.getStart_amount().replace(".00","") + "-"
                    + object.getEnd_amount().replace(".00","") + "元；有效期至"
                    + DateUtils.dateTimeToStr(new Date(object.getEnd_time() * 1000), "yyyy-MM-dd；")
                    + limit + series);

            if(!object.getSelectable()){
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
