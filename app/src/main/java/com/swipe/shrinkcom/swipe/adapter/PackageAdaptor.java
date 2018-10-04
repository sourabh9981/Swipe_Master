package com.swipe.shrinkcom.swipe.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.swipe.shrinkcom.R;
import com.swipe.shrinkcom.swipe.activity.PaymentsAppActivity;
import com.swipe.shrinkcom.swipe.pojo.packagelist.PackageList;
import com.swipe.shrinkcom.swipe.pojo.packagelist.UserDatum;

import java.util.List;

import static com.swipe.shrinkcom.swipe.api.UtilApi.IMAGELINK;

public class PackageAdaptor extends RecyclerView.Adapter<PackageAdaptor.MyViewHolder> {
    Context context;
    List<UserDatum> list;
    public PackageAdaptor(Context context, PackageList list) {
        this.context = context;
        this.list = list.getUserData();
    }

    @NonNull
    @Override
    public PackageAdaptor.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_paxckage_layout, parent, false);

        return new PackageAdaptor.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PackageAdaptor.MyViewHolder holder, final int position) {

        String strimages = IMAGELINK+list.get(position).getPackageImage();
        Glide.with(context)
                .load(strimages)
                .into(holder.imageView);

        holder.tvprice.setText(list.get(position).getGems());
        holder.tvamount.setText(list.get(position).getPackagePrice());

        holder.rootlyout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,PaymentsAppActivity.class);
                intent.putExtra("amount",list.get(position).getPackagePrice());
                intent.putExtra("id",list.get(position).getPackageId());
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

    ImageView imageView;
    TextView tvprice,tvamount;
    LinearLayout rootlyout;
        public MyViewHolder(View itemView) {
            super(itemView);
            imageView = (ImageView)itemView.findViewById(R.id.idimageview);
            tvprice = (TextView) itemView.findViewById(R.id.idtextprice);
            tvamount = (TextView) itemView.findViewById(R.id.idtextamount);
            rootlyout = (LinearLayout)itemView.findViewById(R.id.idrootlayout);
        }
    }
}
