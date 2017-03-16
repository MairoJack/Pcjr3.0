package com.pcjinrong.pcjr.ui.adapter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
public class InterestListSelectAdapter extends RecyclerView.Adapter<InterestListSelectAdapter.ViewHolder> implements View.OnClickListener {

    private List<InterestTicket> list;
    private int type;
    private InterestTicket interest;
    private Product product;
    private String check_id;
    private long server_time;
    private OnRecyclerViewItemClickListener mOnItemClickListener = null;

    public InterestListSelectAdapter() {
        list = new ArrayList<>();
        this.type = type;
    }

    public void setData(List<InterestTicket> list,Product product,long server_time) {
        this.list.clear();
        this.list = list;
        this.product = product;
        this.server_time = server_time;
        Collections.sort(this.list);
        notifyDataSetChanged();
    }

    public void addAll(List<InterestTicket> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void update(List<InterestTicket> list,String check_id){
        this.list.clear();
        this.check_id = check_id;
        this.list.addAll(list);
        Collections.sort(this.list);
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_interest_select, parent, false);
        view.setOnClickListener(this);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindTo((list.get(position)),product,check_id,server_time);
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
            interest = (InterestTicket) v.getTag();
            check_id = interest.getId();
            if(interest.getSelectable())
                notifyDataSetChanged();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.left)
        LinearLayout mIvLeft;
        @BindView(R.id.center)
        LinearLayout mIvCenter;
        @BindView(R.id.interest)
        TextView mIvInterest;
        @BindView(R.id.description)
        TextView mIvDescription;
        @BindView(R.id.check)
        ImageView mIvCheck;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindTo(InterestTicket object,Product product,String check_id,long server_time) {
            if(object.getId().equals("00")){
                mIvLeft.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 0.6f));
                mIvCenter.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, 1.5f));
                mIvLeft.setGravity(Gravity.CENTER_VERTICAL);
                mIvInterest.setTextSize(18);
                mIvInterest.setText("  不使用加息劵");
                mIvDescription.setText("");
            }else {
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
                mIvDescription.setText("单笔投资:" + object.getStart_amount().replace(".00", "") + "-"
                        + object.getEnd_amount().replace(".00", "") + "元；有效期至"
                        + DateUtils.dateTimeToStr(new Date(object.getEnd_time() * 1000), "yyyy-MM-dd；")
                        + limit + series);

                if(!object.getSelectable() || investDays<object.getStart_day() || (object.getEnd_day() != 0 && investDays>object.getEnd_day())
                        || (object.getSeries() != 0 && product.getSeries() != object.getSeries())
                        || server_time/1000 < object.getStart_time()
                        || server_time/1000 > object.getEnd_time()){
                    mIvInterest.setTextColor(Color.parseColor("#cacaca"));
                    mIvDescription.setTextColor(Color.parseColor("#cacaca"));
                }else{
                    mIvInterest.setTextColor(Color.parseColor("#333333"));
                    mIvDescription.setTextColor(Color.parseColor("#666666"));
                }
            }
            if(check_id == null){
                mIvCheck.setVisibility(View.INVISIBLE);
            }else{
                if(object.getId().equals(check_id)){
                    mIvCheck.setVisibility(View.VISIBLE);
                }else{
                    mIvCheck.setVisibility(View.INVISIBLE);
                }
            }
            /*if(interest == null){
                mIvCheck.setVisibility(View.INVISIBLE);
            }else{
                if(object.getId().equals(interest.getId())){
                    mIvCheck.setVisibility(View.VISIBLE);
                }else{
                    mIvCheck.setVisibility(View.INVISIBLE);
                }
            }*/

        }
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, InterestTicket data);
    }

}
