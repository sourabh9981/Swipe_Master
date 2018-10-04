package com.swipe.shrinkcom.swipe.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.swipe.shrinkcom.R;
import com.swipe.shrinkcom.swipe.activity.Profile_newActivity;
import com.swipe.shrinkcom.swipe.beans.Invite;
import com.swipe.shrinkcom.swipe.pojo.allfriendlist.Frienlist;
import com.swipe.shrinkcom.swipe.pojo.allfriendlist.UserDatum;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class FriendListAdaptor extends RecyclerView.Adapter<FriendListAdaptor.MyViewHolder>{

    Context context;
    List<UserDatum> list;
    public FriendListAdaptor(Context context, Frienlist list) {
        this.context = context;
        this.list = list.getUserData();
        Log.e("LISTTTTTT","--->"+this.list);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_layoutfriedlist, parent, false);

        return new FriendListAdaptor.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

       holder.tvname.setText(list.get(position).getUsername());
       holder.tvcountry.setText(list.get(position).getAddress());



        holder.rootlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("listfriendid","-->"+list.get(position).getFriendId());
                // TODO: 9/1/2018
                Intent in = new Intent(context, Profile_newActivity.class);
                in.putExtra("username",list.get(position).getUsername());
                in.putExtra("friendid",list.get(position).getFriendId());
                in.putExtra("swipeid",list.get(position).getSwipeId());
                in.putExtra("image",list.get(position).getImage());
                in.putExtra("address",list.get(position).getAddress());
                in.putExtra("mobile",list.get(position).getMobile());
                in.putExtra("email",list.get(position).getEmail());
                in.putExtra("gender",list.get(position).getGender());
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

        TextView tvname,tvcountry,tvrequesttime;
        CircleImageView imageuesr;
        LinearLayout rootlayout;
        public MyViewHolder(View itemView) {
            super(itemView);
            tvname = (TextView)itemView.findViewById(R.id.idusername);
            tvcountry = (TextView)itemView.findViewById(R.id.idusercountry);
            tvrequesttime = (TextView)itemView.findViewById(R.id.iduserreqtime);
            imageuesr = (CircleImageView)itemView.findViewById(R.id.idimageuser);
            rootlayout = (LinearLayout) itemView.findViewById(R.id.linearMess);
        }
    }
}
