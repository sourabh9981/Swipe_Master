package com.swipe.shrinkcom.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.swipe.shrinkcom.R;
import com.swipe.shrinkcom.pojo.receivethumbsuppojo.ReceiveThumbsupPojo;
import com.swipe.shrinkcom.pojo.receivethumbsuppojo.UserDatum;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SendThumbsupAdaptor extends RecyclerView.Adapter<SendThumbsupAdaptor.MyViewHolder>{

    Context context;
    List<UserDatum> list;
    public SendThumbsupAdaptor(Context context,ReceiveThumbsupPojo list) {
        this.context = context;
        this.list = list.getUserData();
    }

    @NonNull
    @Override
    public SendThumbsupAdaptor.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.receive_thumbsup_list_layout, parent, false);

        return new SendThumbsupAdaptor.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SendThumbsupAdaptor.MyViewHolder holder, int position) {
        holder.sendthumbs.setVisibility(View.GONE);
        holder.tvusername.setText(list.get(position).getUsername());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        CircleImageView userimage,sendthumbs;
        TextView tvusername;
        public MyViewHolder(View itemView) {
            super(itemView);
            userimage = (CircleImageView)itemView.findViewById(R.id.iduserimage);
            sendthumbs = (CircleImageView)itemView.findViewById(R.id.sendreceive);
            tvusername = (TextView)itemView.findViewById(R.id.idusername);
        }
    }

}
