package com.swipe.shrinkcom.swipe.adapter;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.swipe.shrinkcom.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class SlidingImage_Adapter extends PagerAdapter {

    private List<Album> IMAGES;
    private LayoutInflater inflater;
    private Context context;


    public SlidingImage_Adapter(Context oneFragment, List<Album> IMAGES) {
        this.context = oneFragment;
        this.IMAGES = IMAGES;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return IMAGES.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.slidingimages_layout, view, false);

        assert imageLayout != null;
        final CircleImageView imageView =  imageLayout
                .findViewById(R.id.image);
        final TextView tvtext = imageLayout
                .findViewById(R.id.tvtext);
        final TextView tvtext1 = imageLayout
                .findViewById(R.id.tvtext1);

        tvtext.setText(IMAGES.get(position).getName());
        tvtext1.setText(IMAGES.get(position).getNumOfSongs());
        imageView.setImageResource(IMAGES.get(position).getThumbnail());

        view.addView(imageLayout, 0);

        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }


}
