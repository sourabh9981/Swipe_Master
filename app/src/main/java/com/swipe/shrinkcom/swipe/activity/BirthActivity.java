package com.swipe.shrinkcom.swipe.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.swipe.shrinkcom.R;
import com.swipe.shrinkcom.swipe.database.SessionManager;
import com.swipe.shrinkcom.swipe.enum1.Gender;
import com.swipe.shrinkcom.swipe.logicall.UitlLogic;
import com.swipe.shrinkcom.swipe.network.ApiCall;
import com.swipe.shrinkcom.swipe.network.ApiClient;
import com.swipe.shrinkcom.swipe.other.GPSTracker;
import com.swipe.shrinkcom.swipe.pojo.pojoSinup.SignupP;
import com.swipe.shrinkcom.swipe.utils.CommonUtils;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;

import static com.swipe.shrinkcom.swipe.logicall.UitlLogic.calender;

public class BirthActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = BirthActivity.class.getSimpleName();
    private TextView textView_signup;
    RadioButton radioButton_male, radioButton_female;
    EditText edit_text_birthyear;
    LinearLayout birthlayout;
    Context context;
     Gender  gender;
     SessionManager sessionManager;
    String str_userid;
    GPSTracker tracker;
    String straddgress;
    Set<Integer> set_year;
     List<Integer> list;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context=this;
        sessionManager=new SessionManager(this);
        setContentView(R.layout.activity_birth);
        ByViewID();
         tracker = new GPSTracker(this);
        gender  = Gender.MALE;
        textView_signup.setOnClickListener(this);
        radioButton_male.setOnClickListener(this);
        radioButton_female.setOnClickListener(this);
         birthlayout.setOnClickListener(this);
         set_year=calender();
         list = new ArrayList<Integer>(set_year);
           str_userid= getIntent().getStringExtra("userid");

         Log.e(TAG, "onCreate: str_userid "+str_userid );


         straddgress = CommonUtils.completeAddress(this,tracker.getLatitude(),tracker.getLongitude());
    }
   /*=================end onCreate() method=======================================================*/
   private void ByViewID() {
        textView_signup=findViewById(R.id.textView_signup);
        radioButton_male=findViewById(R.id.radioButton_male);
        radioButton_female=findViewById(R.id.radioButton_female);
        edit_text_birthyear=findViewById(R.id.edit_text_birthyear);
        birthlayout = (LinearLayout)findViewById(R.id.idbirthlayout);
    }
    /*=================end ByViewID() method======================================================*/
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.textView_signup:
                // TODO: 5/9/18 action=update_basicinfo, gender, dob
                    HashMap<String, String> params=new HashMap<>();
                    params.put("action","update_basicinfo");
                    params.put("user_id",""+str_userid);
                    params.put("gender",""+gender);
                    params.put("dob","");
                    params.put("address",""+straddgress);
                    birthYearupdate(params);

                return;
            case R.id.radioButton_male:
                gender  = Gender.MALE;
                return;
            case R.id.radioButton_female:
                gender= Gender.FEMALE;
                return;
            case R.id.idbirthlayout:
                dialogYear();
                return;
        }
    }
/*=================end onClick() method=======================================================*/
  private void dialogYear() {
        final Dialog dialog=new Dialog(context);
        dialog.setContentView(R.layout.year_layout);
        dialog.show();
        ListView listView_year=dialog.findViewById(R.id.list_year);





        Collections.sort(list, Collections.reverseOrder());


        ArrayAdapter<Integer> arrayAdapter=new ArrayAdapter<Integer>(this, android.R.layout.simple_list_item_1, list);
        listView_year.setAdapter(arrayAdapter);
        listView_year.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e(TAG, "onItemClick: ==position:  :  "+position );
                Log.e(TAG, "onItemClick: ==list:  :  "+list.get(position) );
                String str= String.valueOf(list.get(position));
                edit_text_birthyear.setText(str);
                dialog.dismiss();
//                dialog.cancel();
            }
        });
    }
    /*=================end dialogYear() method=======================================================*/
    private void birthYearupdate(HashMap<String, String> data){
        final ProgressDialog progressDialog= CommonUtils.showProgressDialog(context,"loading...");
        ApiCall apiCall=  ApiClient.getClient().create(ApiCall.class);
        apiCall.Signup(data).enqueue(new Callback<SignupP>() {
            @Override
            public void onResponse(Call<SignupP> call, retrofit2.Response<SignupP> response) {
                int  code=response.code();
                Log.e(TAG, "onResponse:===code :   " +code);
                SignupP s= response.body();
                Log.e(TAG, "onResponse: == :  "+s.getResult() );
                if(code==200){
                    progressDialog.dismiss();

                    Log.e(TAG, "onResponse: useid   "+s.getUserData().get(0).getUserId());


                    sessionManager.setUserId(s.getUserData().get(0).getUserId());
                    sessionManager.setUserName(s.getUserData().get(0).getUsername());
                    sessionManager.setLocation(s.getUserData().get(0).getAddress());
                    sessionManager.setImage(s.getUserData().get(0).getImage());
                    // session

                     startActivity(new Intent(BirthActivity.this, HomeActivity.class));
                     /*action=update_tokan,user_id, tokan*/
                    HashMap<String, String > params=new HashMap<>();
                    params.put("action","update_tokan");
                    params.put("user_id",s.getUserData().get(0).getUserId());
                    params.put("tokan","");
                    Log.e(TAG, "onResponse:params:   "+params );
                    tokenUpdateApi(params);
                }
            }
            @Override
            public void onFailure(Call<SignupP> call, Throwable t) {
            }
        });
}//end of the LoginActivity class

/*=================end dialogYear() method=======================================================*/
    private void tokenUpdateApi(HashMap<String, String> data){
        final ProgressDialog progressDialog= CommonUtils.showProgressDialog(context,"loading...");
        ApiCall apiCall=  ApiClient.getClient().create(ApiCall.class);
        apiCall.Signup(data).enqueue(new Callback<SignupP>() {
            @Override
            public void onResponse(Call<SignupP> call, retrofit2.Response<SignupP> response) {
                int  code=response.code();
                Log.e(TAG, "onResponse:===code :   " +code);
                SignupP s= response.body();
                Log.e(TAG, "onResponse: == :  "+s.getResult() );
                if(code==200){
                    progressDialog.dismiss();
                    sessionManager.setUserId(s.getUserData().get(0).getUserId());
                    startActivity(new Intent(BirthActivity.this, HomeActivity.class));

                    /*action=generate_swipeid, user_id, swipe_id*/
                    HashMap<String, String > params=new HashMap<>();
                    params.put("action","generate_swipeid");
                    params.put("user_id",s.getUserData().get(0).getUserId());
                    params.put("swipe_id", UitlLogic.generateString());
                    Log.e(TAG, " Genrate swipe id :   "+params );
                    swipeGenerateIdApi(params);
                }
            }
            @Override
            public void onFailure(Call<SignupP> call, Throwable t) {

            }
        });

    }//end of the LoginActivity class
    /*=================end tokenUpdateApi() method=======================================================*/
    private void swipeGenerateIdApi(HashMap<String, String> data){
        Log.e(TAG, "swipeGenerateIdApi: " );
        final ProgressDialog progressDialog= CommonUtils.showProgressDialog(context,"loading...");
        ApiCall apiCall=  ApiClient.getClient().create(ApiCall.class);
        apiCall.Signup(data).enqueue(new Callback<SignupP>() {
            @Override
            public void onResponse(Call<SignupP> call, retrofit2.Response<SignupP> response) {
                int  code=response.code();
                Log.e(TAG, "onResponse:===code :   " +code);
                SignupP s= response.body();
                Log.e(TAG, "onResponse: == :  "+s.getResult() );
                if(code==200){
                    progressDialog.dismiss();


                }

            }
            @Override
            public void onFailure(Call<SignupP> call, Throwable t) {

            }
        });

    }//end of the LoginActivity class

    /*=================end swipeGenerateIdApi() method=======================================================*/


            }//end of the BirthActivity
