package com.swipe.shrinkcom.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.swipe.shrinkcom.R;
import com.swipe.shrinkcom.pojo.interestpojo.InterestPojo;
import com.swipe.shrinkcom.pojo.interestpojo.UserDatum;

import java.util.List;

public class InterestAdaptor extends RecyclerView.Adapter<InterestAdaptor.MyViewHolder> {

    Context context;
    List<UserDatum> list;
    public InterestAdaptor(Context context, InterestPojo list) {
        this.context = context;
        this.list = list.getUserData();
    }

    @NonNull
    @Override
    public InterestAdaptor.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_layout_allinterest, parent, false);

        return new InterestAdaptor.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InterestAdaptor.MyViewHolder holder, int position) {

        holder.tvinterest.setText("#"+list.get(position).getInterestName());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvinterest;
        public MyViewHolder(View itemView) {
            super(itemView);
            tvinterest = (TextView)itemView.findViewById(R.id.idinterest);
        }
    }
}
