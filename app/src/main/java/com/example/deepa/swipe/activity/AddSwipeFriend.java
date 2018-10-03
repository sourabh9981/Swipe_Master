package com.swipe.shrinkcom.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.swipe.shrinkcom.R;
import com.swipe.shrinkcom.database.SessionManager;
import com.swipe.shrinkcom.network.ApiCall;
import com.swipe.shrinkcom.network.ApiClient;
import com.swipe.shrinkcom.other.Utils;
import com.swipe.shrinkcom.pojo.pojoSinup.SignupP;
import com.swipe.shrinkcom.pojo.sendfriend.SendFriendRequest;
import com.swipe.shrinkcom.utils.CommonUtils;

import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;

public class AddSwipeFriend extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = AddSwipeFriend.class.getSimpleName();
    EditText edtsearch;
    TextView tvnodata;
    LinearLayout registerlayout,resultlayout;
    TextView registerbyid;
    CircleImageView useriame;
    TextView tvusername;
    LinearLayout layoutaddfrienttolist;
    Context context;
    SessionManager session;
    LinearLayout layoutviewswipid;
    TextView tvsetswipeid;

      LinearLayout idlayoutnoid_reponse;
    LinearLayout idlayoutnoid;
    String friend_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_swipe_friend);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageView imgback = (ImageView)toolbar.findViewById(R.id.idimageback);
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        context = this;
        session = new SessionManager(this);
        init();
        edtsearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    // TODO: 9/1/2018  search api here
                    //  action=searchbyswipeid, swipe_id
                    if(edtsearch.getText().toString().length()<4){
                        tvnodata.setVisibility(View.VISIBLE);
                        tvnodata.setText(getResources().getString(R.string.title_name_atlistfour_digit));

                    }else {
                        HashMap<String, String> params = new HashMap<>();
                        params.put("action", "searchbyswipeid");
                        params.put("swipe_id", edtsearch.getText().toString());
                        CallSwipeApi(params);

                    }
                    return true;
                }
                return false;
            }
        });
        edtsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                tvnodata.setVisibility(View.GONE);idlayoutnoid_reponse.setVisibility(View.GONE);

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvnodata.setVisibility(View.GONE);idlayoutnoid_reponse.setVisibility(View.GONE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        if(session.getGeneratedSwipeID().equals("")){
           // registerlayout.setVisibility(View.VISIBLE);
           // resultlayout.setVisibility(View.GONE);
            layoutviewswipid.setVisibility(View.GONE);
        }else {
          //  registerlayout.setVisibility(View.GONE);
          //  resultlayout.setVisibility(View.GONE);
            layoutviewswipid.setVisibility(View.VISIBLE);
            tvsetswipeid.setText(session.getGeneratedSwipeID());
        }
    }
    void init() {
        idlayoutnoid_reponse=findViewById(R.id.idlayoutnoid_reponse);
        idlayoutnoid=findViewById(R.id.idlayoutnoid);
        edtsearch = (EditText) findViewById(R.id.idsearch);
        tvnodata = (TextView) findViewById(R.id.idnoresult);
        registerbyid = (TextView) findViewById(R.id.registerswipeid);
        tvusername = (TextView) findViewById(R.id.idusername);
        registerlayout = (LinearLayout)findViewById(R.id.idlayoutnoid);
        resultlayout = (LinearLayout)findViewById(R.id.idlayoutnoid);
        layoutaddfrienttolist = (LinearLayout)findViewById(R.id.idaddfriendlist);
        layoutviewswipid = (LinearLayout)findViewById(R.id.viewswipeid);
        tvsetswipeid = (TextView) findViewById(R.id.setswipeid);
        useriame = (CircleImageView)findViewById(R.id.iduserimage);
        registerbyid.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        layoutaddfrienttolist.setOnClickListener(this);
        registerbyid.setOnClickListener(this);

    }//end of the init()
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.registerswipeid:
                // TODO: 9/1/2018  register friend here
                startActivity(new Intent(context,RegisterSwipeIdActivity.class));
                break;
            case R.id.idaddfriendlist:

//                layoutaddfrienttolist.setBackground(getResources().getString(R.drawable.button_background_gray));

                // TODO: 10/9/18  action=friend_request, user_id,friend_id
                 HashMap<String , String> params=new HashMap<String,String>();
                params.put("action","friend_request");
                params.put("user_id",""+session.getUserId());
                params.put("friend_id",""+friend_id);
                CallAddFreindListApi(params);


                // TODO: 9/1/2018  add friend to list here
                break;
        }
    }// ===============end=========================================




    /*String friend_id*/
    private void CallSwipeApi(HashMap<String, String> data){
        final ProgressDialog progressDialog= CommonUtils.showProgressDialog(context,"loading...");
        ApiCall apiCall=  ApiClient.getClient().create(ApiCall.class);
        apiCall.Signup(data).enqueue(new Callback<SignupP>() {
            @Override
            public void onResponse(Call<SignupP> call, retrofit2.Response<SignupP> response) {
                Log.d(TAG, "onResponse:===code :   " +response.code());
                if(response.code()==200){
                    progressDialog.dismiss();
                    long result=response.body().getResult();
                     Log.e(TAG, "onResponse: check result  :   "+result );

                     if(response.body().getResult()==1){
                         SignupP signupP= response.body();

                         if(signupP.getUserData().size()>0){

                             idlayoutnoid.setVisibility(View.GONE);
                             idlayoutnoid_reponse.setVisibility(View.VISIBLE);

                             Log.e(TAG, "onResponse:=== :     "+signupP.getUserData().get(0).getUserId() );
                             Log.e(TAG, "onResponse:=== swipe id  :   "+signupP.getUserData().get(0).getSwipeId() );
                             Log.e(TAG, "onResponse: user Name ;     "+signupP.getUserData().get(0).getUsername());
                             tvusername.setText(signupP.getUserData().get(0).getUsername());


                             Log.e(TAG, "onResponse: ==friend id  :  "+signupP.getUserData().get(0).getUserId());

                              friend_id=signupP.getUserData().get(0).getUserId();

                         }else{
                             idlayoutnoid.setVisibility(View.VISIBLE);
                             idlayoutnoid_reponse.setVisibility(View.GONE);
                             tvnodata.setVisibility(View.VISIBLE);
                             tvnodata.setText(getResources().getString(R.string.title_name_notfound));
                         }
                     }
                     }
            }
            @Override
            public void onFailure(Call<SignupP> call, Throwable t) {

            }
        });
    }/*==========================CallSwipeApi()===================================================*/

    private void CallAddFreindListApi(HashMap<String, String> data){
        final ProgressDialog progressDialog= CommonUtils.showProgressDialog(context,"loading...");
        ApiCall apiCall=  ApiClient.getClient().create(ApiCall.class);
        apiCall.sendfriendlist(data).enqueue(new Callback<SendFriendRequest>() {
            @Override
            public void onResponse(Call<SendFriendRequest> call, retrofit2.Response<SendFriendRequest> response) {
                Log.d(TAG, "onResponse:===code :   " +response.code());
                if(response.code()==200){
                    progressDialog.dismiss();
                    long result=response.body().getResult();
                    Log.e(TAG, "onResponse: check result  :   "+result );
                    if(response.body().getResult()==1){
                        SendFriendRequest signupP= response.body();
                        if(signupP.getUserData().size()>0){
                            layoutaddfrienttolist.setBackground(ContextCompat.getDrawable(context, R.drawable.button_background_gray));
                            Log.e(TAG, "onResponse: freind id  =:  "+signupP.getUserData().get(0).getFriendId() );
                            Log.e(TAG, "onResponse: user id  id  =:  "+signupP.getUserData().get(0).getUserId());
                            Utils.showToast(context,signupP.getMessage());

                        }else{
                        }
                    }
                }
            }
            @Override
            public void onFailure(Call<SendFriendRequest> call, Throwable t) {

            }
        });
    }/*==========================CallAddFreindListApi()===================================================*/



}/*========================end of the Class=====================================================*/

