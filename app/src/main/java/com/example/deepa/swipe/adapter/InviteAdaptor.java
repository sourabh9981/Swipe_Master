package com.swipe.shrinkcom.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.swipe.shrinkcom.R;
import com.swipe.shrinkcom.beans.Invite;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.swipe.shrinkcom.activity.InviteFriendActivity.selected;


public class InviteAdaptor extends BaseAdapter {

    public List<Invite> _data;
    private ArrayList<Invite> arraylist;
    Context _c;
    //RoundImage roundedImage;
    ViewHolder v;
    public InviteAdaptor(List<Invite> selectUsers, Context context) {
        _data = selectUsers;
        _c = context;
        this.arraylist = new ArrayList<Invite>();
        this.arraylist.addAll(_data);
    }



    @Override
    public int getCount() {
        return _data.size();
    }

    @Override
    public Object getItem(int i) {
        return _data.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(int i, final View convertView, ViewGroup viewGroup) {
        View view = convertView;
        if (view == null) {
            LayoutInflater li = (LayoutInflater) _c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = li.inflate(R.layout.list_layout_invite_contact, null);
            Log.e("Inside", "here--------------------------- In view1");
        } else {
            view = convertView;
            Log.e("Inside", "here--------------------------- In view2");
        }

        v = new ViewHolder();

        v.title = (TextView) view.findViewById(R.id.name);
        v.check = (CheckBox) view.findViewById(R.id.check);
        v.phone = (TextView) view.findViewById(R.id.no);
        v.imageView = (ImageView) view.findViewById(R.id.pic);

        final Invite data = (Invite) _data.get(i);
        v.title.setText(data.getName());
        v.check.setChecked(data.getCheckedBox());
        v.phone.setText(data.getPhone());

        // Set image if exists
        try {

            if (data.getThumb() != null) {
                v.imageView.setImageBitmap(data.getThumb());
            } else {
                v.imageView.setImageResource(R.drawable.ic_person_black);
            }
            // Seting round image
            Bitmap bm = BitmapFactory.decodeResource(view.getResources(), R.drawable.ic_person_black); // Load default image
            //roundedImage = new RoundImage(bm);
           // v.imageView.setImageDrawable(roundedImage);
        } catch (OutOfMemoryError e) {
            // Add default picture
            v.imageView.setImageDrawable(this._c.getDrawable(R.drawable.ic_person_black));
            e.printStackTrace();
        }

        Log.e("Image Thumb", "--------------" + data.getThumb());




        /*v.title.setOnClickListener( new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox cb = (CheckBox) v ;
                Invite country = (Invite) cb.getTag();
                Toast.makeText(_c,
                        "Clicked on Checkbox: " + cb.getText() +
                                " is " + cb.isChecked(),
                        Toast.LENGTH_LONG).show();
                country.setCheckedBox(cb.isChecked());
            }
        });


*/


        // Set check box listener android
        v.check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckBox checkBox = (CheckBox) view;
                if (checkBox.isChecked()) {
                    data.setCheckedBox(true);
                  } else {
                    data.setCheckedBox(false);
                }
            }
        });

        view.setTag(data);

        try {
            selected.add((Invite) view.getTag());
            Log.e("ffffffffff","---->"+selected);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    // Filter Class
    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        _data.clear();
        if (charText.length() == 0) {
            _data.addAll(arraylist);
        } else {
            for (Invite wp : arraylist) {
                if (wp.getName().toLowerCase(Locale.getDefault())
                        .contains(charText)) {
                    _data.add(wp);
                }
            }
        }
        notifyDataSetChanged();
    }
    static class ViewHolder {
        ImageView imageView;
        TextView title, phone;
        CheckBox check;
    }
}
