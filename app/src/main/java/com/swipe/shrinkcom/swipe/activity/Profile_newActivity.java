package com.swipe.shrinkcom.swipe.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.swipe.shrinkcom.R;
import com.swipe.shrinkcom.swipe.adapter.FriendListAdaptor;
import com.swipe.shrinkcom.swipe.beans.Message;
import com.swipe.shrinkcom.swipe.database.SessionManager;
import com.swipe.shrinkcom.swipe.fragment.FriendsFragment;
import com.swipe.shrinkcom.swipe.network.ApiCall;
import com.swipe.shrinkcom.swipe.network.ApiClient;
import com.swipe.shrinkcom.swipe.other.Utils;
import com.swipe.shrinkcom.swipe.pojo.HideFriend.HideFriend;
import com.swipe.shrinkcom.swipe.pojo.allfriendlist.Frienlist;
import com.swipe.shrinkcom.swipe.pojo.receivethumbsuppojo.ReceiveThumbsupPojo;
import com.swipe.shrinkcom.swipe.utils.CommonUtils;


import java.util.Arrays;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;

public class Profile_newActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = Profile_newActivity.class.getSimpleName();
    Context context;
    ImageView userimage,imgclose,imgstar,imgviewmore,idimagelike;
    TextView tv_username,tv_country;
    LinearLayout layoutmessage,layoutvideocall,layoutlike;
SessionManager session;
    String[] value = new String[]{
            "Hide",
            "Block",
    };



    String username,friendid,swipeid,image,address,mobile,email,gender,strstatus;
    void getdatafromIntent(){
        Intent intent = getIntent();
        username= intent.getStringExtra("username");
        friendid= intent.getStringExtra("friendid");
        swipeid= intent.getStringExtra("swipeid");
        image= intent.getStringExtra("image");
        address= intent.getStringExtra("address");
        mobile= intent.getStringExtra("mobile");
        email= intent.getStringExtra("email");
        gender= intent.getStringExtra("gender");
        strstatus= intent.getStringExtra("status");

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_profile_new);
        session = new SessionManager(this);
        context = this;
        init();
        getdatafromIntent();

        tv_username.setText(username);
    }

    void  init(){
        userimage  = (ImageView)findViewById(R.id.idimageuser);
        imgclose  = (ImageView)findViewById(R.id.fabClose);
        imgstar  = (ImageView)findViewById(R.id.idstar);
        imgviewmore  = (ImageView)findViewById(R.id.idviewmore);
        idimagelike  = (ImageView)findViewById(R.id.idimagelike);

        tv_username = (TextView)findViewById(R.id.tvusername);
        tv_country = (TextView)findViewById(R.id.idtvcountry);

        layoutmessage = (LinearLayout) findViewById(R.id.idmessage);
        layoutvideocall = (LinearLayout) findViewById(R.id.idvideocall);
        layoutlike = (LinearLayout) findViewById(R.id.idlike);

        imgclose.setOnClickListener(this);
        imgstar.setOnClickListener(this);
        imgviewmore.setOnClickListener(this);
        layoutmessage.setOnClickListener(this);
        layoutvideocall.setOnClickListener(this);
        layoutlike.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fabClose:
                finish();
                break;
            case R.id.idstar:
                // TODO: 9/1/2018 give rating here
                break;
            case R.id.idviewmore:
                moreAlert(username);
                break;
            case R.id.idmessage:
                // TODO: 9/1/2018  send message here
                Intent intent = new Intent(context, MessageActivity.class);
                intent.putExtra("username",username);
                intent.putExtra("friendid",friendid);
                intent.putExtra("userid",session.getUserId());
                intent.putExtra("image",image);
                intent.putExtra("status",strstatus);
                startActivity(intent);
                break;
            case R.id.idvideocall:
                // TODO: 9/1/2018  make video call here

                startActivity(new Intent(context,LoginActivity.class));
                break;
            case R.id.idlike:
                // TODO: 9/1/2018  call Like api here

                //action=add_thumbsup, user_id, friend_id
                HashMap<String, String> data = new HashMap<>();
                data.put("action","add_thumbsup");
                data.put("user_id",""+session.getUserId());
                data.put("friend_id",""+friendid);
                AddthumbsUp(data);
                break;
        }
    }


    void moreAlert(String username){
        AlertDialog.Builder alertdialogbuilder = new AlertDialog.Builder(Profile_newActivity.this);


        alertdialogbuilder.setTitle(username);

        alertdialogbuilder.setItems(value, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String selectedText = Arrays.asList(value).get(which);
               // action=hide_friend, user_id, friend_id
                // TODO: 9/1/2018  hide or block user here
                if (selectedText.equals("Hide")){


                    // TODO: 9/12/2018  hide api call
                    alert("are you sure you want to Hide this friend","Hide");

                }else {
                    // TODO: 9/12/2018  Block api call

                    alert("are you sure you want to Block this friend","Block");

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
                    Log.e("BODYYYYY", "---->" + s.getResult());
                    if (s.getResult() == 1) {
                        // TODO: 9/12/2018  setAdaptorhere
                        Utils.showToast(context,"Hide Success");
                        Intent intent = new Intent(context,HomeActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
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
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onFailure(Call<HideFriend> call, Throwable t) {
            }

        });
    }


    // TODO: 9/13/2018  success alert dialog
    void  alert(String message, final String type) {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Profile_newActivity.this);

        // Setting Dialog Title
        alertDialog.setTitle("Confirm");

        // Setting Dialog Message
        alertDialog.setMessage(message);


        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                if (type.equals("Hide")) {
                    HashMap<String, String> data = new HashMap<>();
                    data.put("action", "hide_friend");
                    data.put("user_id", "" + session.getUserId());
                    data.put("friend_id", "" + friendid);
                    Log.e("HIDEFRIENDSS","--->"+data);
                    HideFriends(data);
                }else {
                    HashMap<String, String> data = new HashMap<>();
                    data.put("action","block_friend");
                    data.put("user_id",""+session.getUserId());
                    data.put("friend_id",""+friendid);
                    Log.e("BLOCKFRIEND","--->"+data);
                    BlockFriends(data);
                }
            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke NO event
                Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }


    // TODO: 9/26/2018  cal,l api for like here


    private void AddthumbsUp(HashMap<String, String> data){
        final ProgressDialog progressDialog= CommonUtils.showProgressDialog(context,"loading...");
        ApiCall apiCall=  ApiClient.getClient().create(ApiCall.class);
        apiCall.AddThumbsup(data).enqueue(new Callback<ReceiveThumbsupPojo>() {
            @Override
            public void onResponse(Call<ReceiveThumbsupPojo> call, retrofit2.Response<ReceiveThumbsupPojo> response) {
                int code = response.code();
                Log.e(TAG, " CODEEEEE:   " + code);
                if (code == 200) {
                    progressDialog.dismiss();
                    ReceiveThumbsupPojo s = response.body();
//                  sessionManager.setUserId(s.getUserData().get(0).getUserId());
                    Log.e("BODYYYYY", "---->" + s.getResult());
                    if (s.getResult() == 1) {
                        // TODO: 9/12/2018  setAdaptorhere
                        Utils.showToast(context,"Thumbs up Success");

                    }
                }
            }

            @Override
            public void onFailure(Call<ReceiveThumbsupPojo> call, Throwable t) {
            }

        });
    }

}
