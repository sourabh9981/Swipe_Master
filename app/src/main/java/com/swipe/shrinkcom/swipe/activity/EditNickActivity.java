package com.swipe.shrinkcom.swipe.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import com.swipe.shrinkcom.R;
import com.swipe.shrinkcom.swipe.database.SessionManager;
import com.swipe.shrinkcom.swipe.network.ApiCall;
import com.swipe.shrinkcom.swipe.network.ApiClient;
import com.swipe.shrinkcom.swipe.pojo.profilepojo.ProfilePojo;
import com.swipe.shrinkcom.swipe.utils.CommonUtils;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;

public class EditNickActivity extends AppCompatActivity {

     private static final String TAG = "EditNickActivity";
    EditText edtchangenam;
    LinearLayout layoutsubmit;
    Context context;
    SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_nick);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        session = new SessionManager(this);
        context = this;
        String username = getIntent().getStringExtra("username");

        edtchangenam = (EditText)findViewById(R.id.etNick);
        edtchangenam.setText(username);


        layoutsubmit = (LinearLayout)findViewById(R.id.lineraebutt);

        layoutsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HashMap<String, String> data = new HashMap<>();
                data.put("action","update_username");
                data.put("user_id",""+session.getUserId());
                data.put("username",""+edtchangenam.getText().toString().trim());
                updateProfile(data);
            }
        });
    }



    private void updateProfile(HashMap<String, String> data){
        final ProgressDialog progressDialog= CommonUtils.showProgressDialog(context,"loading...");
        ApiCall apiCall=  ApiClient.getClient().create(ApiCall.class);
        apiCall.UpdetProfileName(data).enqueue(new Callback<ProfilePojo>() {
            @Override
            public void onResponse(Call<ProfilePojo> call, retrofit2.Response<ProfilePojo> response) {
                int code = response.code();
                Log.e(TAG, " CODEEEEE:   " + code);
                if (code == 200) {
                    progressDialog.dismiss();
                    ProfilePojo s = response.body();
//                  sessionManager.setUserId(s.getUserData().get(0).getUserId());
                    Log.e("BODYYYYY", "---->" + response.body());
                    if (s.getResult() == 1) {
                        // TODO: 9/12/2018  setAdaptorhere
                        startActivity(new Intent(context,ProfileActivity.class));
                        finish();

                    }
                }
            }

            @Override
            public void onFailure(Call<ProfilePojo> call, Throwable t) {
            }

        });
    }

}
