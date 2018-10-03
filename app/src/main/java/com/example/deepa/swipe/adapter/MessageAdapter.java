package com.swipe.shrinkcom.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.swipe.shrinkcom.R;
import com.swipe.shrinkcom.beans.Message;
import com.swipe.shrinkcom.database.SessionManager;
import com.swipe.shrinkcom.pojo.allmessagepojo.GetMessageList;
import com.swipe.shrinkcom.pojo.allmessagepojo.UserDatum;

import java.util.ArrayList;
import java.util.List;


public class MessageAdapter extends BaseAdapter {

    List<UserDatum> messages ;//= new ArrayList<Message>();
    Context context;
SessionManager session;
String friendname;
    public MessageAdapter(Context context,String friendname, GetMessageList messages) {
        this.context = context;
        this.friendname = friendname;
        this.messages = messages.getUserData();
    }


    public void add(GetMessageList  message) {
        this.messages = message.getUserData();
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return messages.size();
    }

    @Override
    public Object getItem(int i) {
        return messages.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        MessageViewHolder holder = new MessageViewHolder();
        session = new SessionManager(context);
        LayoutInflater messageInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        Log.e("DATAAFAA","====>"+messages.get(i).getMessage());

         // int sessionuserid = Integer.parseInt(session.getUserId());
        if (messages.get(i).getSendfrom().equals(session.getUserId())) {
            convertView = messageInflater.inflate(R.layout.my_message, null);
            holder.messageBody = (TextView) convertView.findViewById(R.id.message_body);
            convertView.setTag(holder);
            holder.messageBody.setText(messages.get(i).getMessage());
        } else {
            convertView = messageInflater.inflate(R.layout.their_message, null);
            holder.avatar = (View) convertView.findViewById(R.id.avatar);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.messageBody = (TextView) convertView.findViewById(R.id.message_body);
            convertView.setTag(holder);

            holder.name.setText(friendname);
            holder.messageBody.setText(messages.get(i).getMessage());
            GradientDrawable drawable = (GradientDrawable) holder.avatar.getBackground();
            drawable.setColor(Color.parseColor("#48b3ff"));
        }

        return convertView;
    }

}

class MessageViewHolder {
    public View avatar;
    public TextView name;
    public TextView messageBody;
}