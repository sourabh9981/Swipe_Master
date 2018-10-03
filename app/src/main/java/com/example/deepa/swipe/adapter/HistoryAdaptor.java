package com.swipe.shrinkcom.adapter;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import com.swipe.shrinkcom.activity.MessageActivity;
import com.swipe.shrinkcom.database.SessionManager;

import com.swipe.shrinkcom.pojo.historypojo.HistoryPojo;
import com.swipe.shrinkcom.pojo.historypojo.UserDatum;


import java.util.List;
import de.hdodenhof.circleimageview.CircleImageView;


public class HistoryAdaptor  extends RecyclerView.Adapter<HistoryAdaptor.MyViewholder> {


    Context context;
    List<UserDatum> list;
    String type;
    SessionManager session;
    public HistoryAdaptor(Context context, HistoryPojo list) {
        this.context = context;
        this.list = list.getUserData();

        Log.e("HHHHHHHHH","--->"+this.list.get(0).getUsername());
    }

    @NonNull
    @Override
    public HistoryAdaptor.MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_layout_hidefriend, parent, false);

        return new HistoryAdaptor.MyViewholder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryAdaptor.MyViewholder holder, final int position) {
        session = new SessionManager(context);
        holder.tvname.setText(list.get(position).getUsername());

        holder.linearlayout.setVisibility(View.GONE);
        Log.e("STATUSSSSS","--->"+this.list.get(position).getConStatus());
       holder.rootlayout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               Intent intent = new Intent(context, MessageActivity.class);
               intent.putExtra("userid",list.get(position).getUserId());
               intent.putExtra("username",list.get(position).getUsername());
               intent.putExtra("userimage",list.get(position).getImage());
               intent.putExtra("friendid",list.get(position).getConFriendId());
               intent.putExtra("status",list.get(position).getConStatus());
               context.startActivity(intent);
           }
       });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewholder extends RecyclerView.ViewHolder{
        CircleImageView imageView;
        TextView tvname,tvmanage;
        LinearLayout linearlayout;
        LinearLayout rootlayout;
        public MyViewholder(View itemView) {
            super(itemView);
            imageView = (CircleImageView)itemView.findViewById(R.id.idimages);
            tvname = (TextView) itemView.findViewById(R.id.idtvname);
            tvmanage = (TextView) itemView.findViewById(R.id.idmanage);
            linearlayout = (LinearLayout) itemView.findViewById(R.id.manegelayout);
            rootlayout = (LinearLayout)itemView.findViewById(R.id.rootlayout);
        }
    }



}
