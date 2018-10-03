package com.swipe.shrinkcom.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.swipe.shrinkcom.R;
import com.swipe.shrinkcom.adapter.HideFriendAdaptor;
import com.swipe.shrinkcom.database.SessionManager;
import com.swipe.shrinkcom.fragment.FriendsFragment;
import com.swipe.shrinkcom.network.ApiCall;
import com.swipe.shrinkcom.network.ApiClient;
import com.swipe.shrinkcom.other.Utils;
import com.swipe.shrinkcom.pojo.HideFriend.HideFriend;
import com.swipe.shrinkcom.pojo.hidefriendlist.HideFriendList;
import com.swipe.shrinkcom.utils.CommonUtils;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;

public class ActionFriendActivity extends AppCompatActivity {
    private static final String TAG = ActionFriendActivity.class.getSimpleName();
    LinearLayout llayout;
    RecyclerView frcyclefriend;
    Context context;
    SessionManager session;
    String strtitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_friend);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageView imgback = (ImageView)toolbar.findViewById(R.id.idback);
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        context = this;
        session = new SessionManager(context);
        TextView tvtitle = (TextView)toolbar.findViewById(R.id.idcationtype);
        strtitle = getIntent().getStringExtra("type");//Hide

        tvtitle.setText(strtitle);
        llayout = (LinearLayout)findViewById(R.id.idlinearlayout);
        frcyclefriend = (RecyclerView)findViewById(R.id.recycleviewfriendlist);


        if (strtitle.equals("Hide")){
            HashMap<String, String> data = new HashMap<>();
            data.put("action","showhide_friend");
            data.put("user_id",""+session.getUserId());
            HideFriends(data);
        }else {
            HashMap<String, String> data = new HashMap<>();
            data.put("action","showblock_friend");
            data.put("user_id",""+session.getUserId());
            HideFriends(data);
        }
    }


    private void HideFriends(HashMap<String, String> data){
        final ProgressDialog progressDialog= CommonUtils.showProgressDialog(context,"loading...");
        ApiCall apiCall=  ApiClient.getClient().create(ApiCall.class);
        apiCall.HideFriendList(data).enqueue(new Callback<HideFriendList>() {
            @Override
            public void onResponse(Call<HideFriendList> call, retrofit2.Response<HideFriendList> response) {
                int code = response.code();
                Log.e(TAG, " CODEEEEE:   " + code);
                if (code == 200) {
                    progressDialog.dismiss();
                    HideFriendList s = response.body();
//                  sessionManager.setUserId(s.getUserData().get(0).getUserId());
                    Log.e("BODYYYYY", "---->" + response.body());
                    if (s.getResult() == 1) {
                        // TODO: 9/12/2018  setAdaptorhere
                        Log.e("sizeeee", "---->" + s.getUserData().size());
                       if (s.getUserData().size()>0){
                           llayout.setVisibility(View.GONE);
                           LinearLayoutManager layoutManager1 = new LinearLayoutManager(context);
                           layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
                           frcyclefriend.setLayoutManager(layoutManager1);
                           frcyclefriend.setItemAnimator(new DefaultItemAnimator());
                           HideFriendAdaptor hideadaptor = new HideFriendAdaptor(context,s,strtitle);
                           frcyclefriend.setAdapter(hideadaptor);
                           hideadaptor.notifyDataSetChanged();

                       }else {
                           llayout.setVisibility(View.VISIBLE);
                       }
                    }
                }
            }

            @Override
            public void onFailure(Call<HideFriendList> call, Throwable t) {
            }

        });
    }

}
