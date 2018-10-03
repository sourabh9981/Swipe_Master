package com.swipe.shrinkcom.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.swipe.shrinkcom.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AlbumsAdapter extends RecyclerView.Adapter<AlbumsAdapter.MyViewHolder> {

    private Context mContext;
    private List<Album> albumList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvtext, tvtext1;
        public CircleImageView thumbnail, overflow;

        public MyViewHolder(View view) {
            super(view);
            tvtext = (TextView) view.findViewById(R.id.tvtext);
            tvtext1 = (TextView) view.findViewById(R.id.tvtext1);
            thumbnail =  view.findViewById(R.id.image);
        }
    }


    public AlbumsAdapter(Context mContext, List<Album> albumList) {
        this.mContext = mContext;
        this.albumList = albumList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.slidingimages_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        Album album = albumList.get(position);
        holder.tvtext.setText(album.getName());
        holder.tvtext1.setText(album.getNumOfSongs());

        // loading album cover using Glide library
        Glide.with(mContext).load(album.getThumbnail()).into(holder.thumbnail);

    }


    @Override
    public int getItemCount() {
        return albumList.size();
    }
}

