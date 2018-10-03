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
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.swipe.shrinkcom.R;

import com.swipe.shrinkcom.activity.HomeActivity;
import com.swipe.shrinkcom.activity.Profile_newActivity;
import com.swipe.shrinkcom.database.SessionManager;
import com.swipe.shrinkcom.network.ApiCall;
import com.swipe.shrinkcom.network.ApiClient;
import com.swipe.shrinkcom.other.Utils;
import com.swipe.shrinkcom.pojo.HideFriend.HideFriend;
import com.swipe.shrinkcom.pojo.hidefriendlist.HideFriendList;
import com.swipe.shrinkcom.pojo.hidefriendlist.UserDatum;
import com.swipe.shrinkcom.utils.CommonUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;

public class HideFriendAdaptor extends RecyclerView.Adapter<HideFriendAdaptor.MyViewholder> {
    private static final String TAG = HideFriendAdaptor.class.getSimpleName();
    String[] value = new String[]{
            "Add back to friend list",
            "Block",
    };

    String[] valueunblock = new String[]{
            "Un - Block",
    };
    Context context;
    List<UserDatum> list;
    String type;
    SessionManager session;
    public HideFriendAdaptor(Context context,HideFriendList list,String type) {
        this.context = context;
        this.list = list.getUserData();
        this.type = type;

        Log.e("HHHHHHHHH","--->"+this.list.get(0).getUsername());
    }

    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_layout_hidefriend, parent, false);

        return new MyViewholder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholder holder, final int position) {
        session = new SessionManager(context);
        if (type.equals("Hide")){
            holder.tvmanage.setText("Manage");
        }else {
            holder.tvmanage.setText("Unblock");
        }
        holder.tvname.setText(list.get(position).getUsername());

        holder.linearlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 9/13/2018 here manage adaptor
                if (type.equals("Hide")) {
                    moreAlert(list.get(position).getFriendId(), list.get(position).getUsername());
                }else {
                    // TODO: 9/14/2018  actipn for block friend
                    UnblockAlert(list.get(position).getFriendId(), list.get(position).getUsername());
                }
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
        public MyViewholder(View itemView) {
            super(itemView);
            imageView = (CircleImageView)itemView.findViewById(R.id.idimages);
            tvname = (TextView) itemView.findViewById(R.id.idtvname);
            tvmanage = (TextView) itemView.findViewById(R.id.idmanage);
            linearlayout = (LinearLayout) itemView.findViewById(R.id.manegelayout);

        }
    }


    // TODO: 9/14/2018 alert unblok

    void UnblockAlert(final String friendid, String usernme){


        AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(context);
        alertdialogbuilder.setTitle(usernme);
        alertdialogbuilder.setItems(valueunblock, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String selectedText = Arrays.asList(value).get(which);
                // action=hide_friend, user_id, friend_id
                // TODO: 9/1/2018  hide or block user here
                    // TODO: 9/12/2018  unhide api call
                    HashMap<String, String> data = new HashMap<>();
                    data.put("action","unblock_friend");
                    data.put("user_id",""+session.getUserId());
                    data.put("friend_id",""+friendid);
                    Log.e("SENDDDD","--->"+data);
                    HideFriends(data);
            }
        });

        AlertDialog dialog = alertdialogbuilder.create();

        dialog.show();
    }



    void moreAlert(final String friendid, String usernme){


        AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(context);


        alertdialogbuilder.setTitle(usernme);

        alertdialogbuilder.setItems(value, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String selectedText = Arrays.asList(value).get(which);
                // action=hide_friend, user_id, friend_id
                // TODO: 9/1/2018  hide or block user here
                if (selectedText.equals("Add back to friend list")){
                    // TODO: 9/12/2018  unhide api call
                    HashMap<String, String> data = new HashMap<>();
                    data.put("action","unhide_friend");
                    data.put("user_id",""+session.getUserId());
                    data.put("friend_id",""+friendid);
                    Log.e("SENDDDD","--->"+data);
                    HideFriends(data);
                }else {
                    // TODO: 9/12/2018  Block api call
                    HashMap<String, String> data = new HashMap<>();
                    data.put("action","block_friend");
                    data.put("user_id",""+session.getUserId());
                    data.put("friend_id",""+friendid);
                    Log.e("BLOCKFRIEND","--->"+data);
                    BlockFriends(data);

                }

            }
        });

        AlertDialog dialog = alertdialogbuilder.create();

        dialog.show();
    }


    private void HideFriends(HashMap<String, String> data){
        final ProgressDialog progressDialog= CommonUtils.showProgressDialog(context,"loading...");
        ApiCall apiCall=  ApiClient.getClient().create(ApiCall.class);
        apiCall.HideFriend(data).enqueue(new Callback<HideFriend>() {
            @Override
            public void onResponse(Call<HideFriend> call, retrofit2.Response<HideFriend> response) {
                int code = response.code();
                Log.e(TAG, " CODEEEEE:   " + code);
                if (code == 200) {
                    progressDialog.dismiss();
                    HideFriend s = response.body();
//                  sessionManager.setUserId(s.getUserData().get(0).getUserId());
                    Log.e("BODYYYYY", "---->" + s.getMessage());
                    if (s.getResult() == 1) {
                        // TODO: 9/12/2018  setAdaptorhere
                        Utils.showToast(context,s.getMessage());
                        Intent intent = new Intent(context,HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(intent);
                    }
                }
            }

            @Override
            public void onFailure(Call<HideFriend> call, Throwable t) {
            }

        });
    }


    private void BlockFriends(HashMap<String, String> data){
        final ProgressDialog progressDialog= CommonUtils.showProgressDialog(context,"loading...");
        ApiCall apiCall=  ApiClient.getClient().create(ApiCall.class);
        apiCall.HideFriend(data).enqueue(new Callback<HideFriend>() {
            @Override
            public void onResponse(Call<HideFriend> call, retrofit2.Response<HideFriend> response) {
                int code = response.code();
                Log.e(TAG, " CODEEEEE:   " + code);
                if (code == 200) {
                    progressDialog.dismiss();
                    HideFriend s = response.body();
//                  sessionManager.setUserId(s.getUserData().get(0).getUserId());
                    Log.e("BODYYYYY", "---->" + s.getResult());
                    if (s.getResult() == 1) {
                        // TODO: 9/12/2018  setAdaptorhere
                        Utils.showToast(context,"Block Friend Success");
                        Intent intent = new Intent(context,HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(intent);
                    }
                }
            }

            @Override
            public void onFailure(Call<HideFriend> call, Throwable t) {
            }

        });
    }

}
