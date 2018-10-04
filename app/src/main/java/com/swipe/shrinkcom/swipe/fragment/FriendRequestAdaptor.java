package com.swipe.shrinkcom.swipe.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.swipe.shrinkcom.R;
import com.swipe.shrinkcom.swipe.activity.MessageActivity;
import com.swipe.shrinkcom.swipe.activity.Profile_newActivity;

import com.swipe.shrinkcom.swipe.pojo.sendfriendlist.Sendfriendlist;
import com.swipe.shrinkcom.swipe.pojo.sendfriendlist.UserDatum;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FriendRequestAdaptor extends RecyclerView.Adapter<FriendRequestAdaptor.MyViewHolder>{

    Context context;
    List<UserDatum> list;
    public FriendRequestAdaptor(Context context, Sendfriendlist list) {
        this.context = context;
        this.list = list.getUserData();
        Log.e("LISTTTTTT","--->"+this.list);
    }

    @NonNull
    @Override
    public FriendRequestAdaptor.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listlayoutfriendrequest, parent, false);

        return new FriendRequestAdaptor.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FriendRequestAdaptor.MyViewHolder holder, final int position) {

        holder.tvname.setText(list.get(position).getUsername());
        holder.tvcountry.setText(list.get(position).getAddress());

        holder.tvmessage.setVisibility(View.GONE);

        holder.rootlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("listfriendid","-->"+list.get(position).getFriendId());
                // TODO: 9/1/2018
                Intent in = new Intent(context, MessageActivity.class);
                in.putExtra("username",list.get(position).getUsername());
                in.putExtra("friendid",list.get(position).getFriendId());
                in.putExtra("image",list.get(position).getImage());
                in.putExtra("userid",list.get(position).getUserId());
                in.putExtra("status",list.get(position).getFriendStatus());


                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(in);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvname,tvcountry,tvmessage;
        CircleImageView imageuesr;
        LinearLayout rootlayout;
        public MyViewHolder(View itemView) {
            super(itemView);
            tvname = (TextView)itemView.findViewById(R.id.idusername);
            tvcountry = (TextView)itemView.findViewById(R.id.idcountry);
            tvmessage = (TextView)itemView.findViewById(R.id.idmessage);
            imageuesr = (CircleImageView)itemView.findViewById(R.id.idimageview);
            rootlayout = (LinearLayout) itemView.findViewById(R.id.idrootlayout);
        }
    }
}
