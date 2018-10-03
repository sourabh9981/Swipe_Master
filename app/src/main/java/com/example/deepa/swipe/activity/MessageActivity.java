package com.swipe.shrinkcom.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.swipe.shrinkcom.R;
import com.swipe.shrinkcom.adapter.MessageAdapter;
import com.swipe.shrinkcom.beans.Message;
import com.swipe.shrinkcom.database.SessionManager;
import com.swipe.shrinkcom.fragment.FriendRequestAdaptor;
import com.swipe.shrinkcom.network.ApiCall;
import com.swipe.shrinkcom.network.ApiClient;
import com.swipe.shrinkcom.pojo.acceptfriendpojo.AcceptFriendPojo;
import com.swipe.shrinkcom.pojo.allmessagepojo.GetMessageList;
import com.swipe.shrinkcom.pojo.sendfriendlist.Sendfriendlist;
import com.swipe.shrinkcom.services.RefreshmessageServicess;
import com.swipe.shrinkcom.utils.CommonUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import javax.security.auth.login.LoginException;

import retrofit2.Call;
import retrofit2.Callback;

public class MessageActivity extends AppCompatActivity {


    String struserid;
    static String strfriendid;
    static String strusername;
    String strimages;
    String strstatus;
    static Context context;
    static SessionManager session;
    
    LinearLayout accepylayout;
    TextView tvtextmessage;
    Button btnaccept,btncancel;

    static ListView messagerecycel;
    EditText edtmessage;
    ImageView ivrecording,imgtextsend;

    private static MessageAdapter messageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_message);
        session = new SessionManager(this);
        context = this;

        final Handler handler = new Handler();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startService(new Intent(context, RefreshmessageServicess.class));
                handler.postDelayed(this, 5000);
            }
        }, 5000);


        ImageView imageViewback = (ImageView)findViewById(R.id.idback);
        imageViewback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                strfriendid ="";
                stopService(new Intent(context, RefreshmessageServicess.class));
                finish();
            }
        });

        accepylayout = (LinearLayout)findViewById(R.id.acceptlayout);
        tvtextmessage = (TextView) findViewById(R.id.idtextmessage);
        btnaccept = (Button) findViewById(R.id.idbtnaccept);
        btncancel = (Button) findViewById(R.id.idbtncancel); 
        messagerecycel = (ListView)findViewById(R.id.idmessagrecycleview);
        edtmessage = (EditText)findViewById(R.id.idittextmess);
        ivrecording = (ImageView)findViewById(R.id.recorded);
        imgtextsend = (ImageView)findViewById(R.id.icsendtext);
        // TODO: 9/25/2018  getData from intent

        Intent intent = getIntent();

        strfriendid = intent.getStringExtra("friendid");
        struserid = intent.getStringExtra("userid");
        strusername = intent.getStringExtra("username");
        strimages = intent.getStringExtra("image");
        strstatus = intent.getStringExtra("status");

       Log.e("STATUSSSS","---->"+strstatus);
        tvtextmessage.setText(strusername+" "+getResources().getString(R.string.send_to_friend_frquest));


        if (session.getUserId().equals(strfriendid)){
            accepylayout.setVisibility(View.GONE);  
        }else {
            if (strstatus.equals("0")){
                accepylayout.setVisibility(View.GONE);
            } else {
                accepylayout.setVisibility(View.GONE);  
            }
        }

        btnaccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //action=accept_friend, user_id, friend_id
                HashMap<String, String> data = new HashMap<>();
                data.put("action","accept_friend");
                data.put("user_id",""+session.getUserId());
                data.put("friend_id",""+strfriendid);
                Log.e("SENDVALUEEE","00000--->"+data);
                AcceptFriend(data);
            }
        });

        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //action=accept_friend, user_id, friend_id
                strfriendid ="";
                stopService(new Intent(context, RefreshmessageServicess.class));
               finish();

            }
        });


        edtmessage.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (i1>0){
                    imgtextsend.setVisibility(View.VISIBLE);
                    ivrecording.setVisibility(View.GONE);
                }else {
                    imgtextsend.setVisibility(View.GONE);
                    ivrecording.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        Log.e("FRIENDIDDD","--->"+strfriendid);
        // TODO: 9/26/2018  send message here
        imgtextsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //action=send_message&sendfrom=16&sendto=1&message=hello&type=text
                String strmessage = edtmessage.getText().toString().trim();

                HashMap<String, String> data  = new HashMap<>();
                //action=get_message&sendfrom=16&sendto=1
                data.put("action","send_message");
                data.put("sendfrom",""+session.getUserId());//sessionid
                data.put("sendto",""+strfriendid); //friendid
                data.put("message",""+strmessage);
                data.put("type","text");

                Log.e("SENDMESSAGEDATA","--->"+data);
                Sendmessage(data);

                edtmessage.setText("");
            }
        });


        callApi();


    }


    private void AcceptFriend(HashMap<String, String> data){
        final ProgressDialog progressDialog= CommonUtils.showProgressDialog(context,"loading...");
        ApiCall apiCall=  ApiClient.getClient().create(ApiCall.class);
        apiCall.AcceptFriend(data).enqueue(new Callback<AcceptFriendPojo>() {
            @Override
            public void onResponse(Call<AcceptFriendPojo> call, retrofit2.Response<AcceptFriendPojo> response) {
                int code = response.code();
                Log.e("Messagefragment", " CODEEEEE:   " + code);
                if (code == 200) {
                    progressDialog.dismiss();
                    AcceptFriendPojo s = response.body();

                    Log.e("RESPONSEE", "---->" + s.getResult());

//                  sessionManager.setUserId(s.getUserData().get(0).getUserId());
                  //  Log.e("BODYYYYYResult", "---->" + s.getUserData().size());
                    if (s.getResult()==1) {
                            // TODO: 9/12/2018  setAdaptorhere
                            accepylayout.setVisibility(View.GONE);
                            Toast.makeText(context, "Requested Accepted", Toast.LENGTH_SHORT).show();
                        }else {
                        accepylayout.setVisibility(View.GONE);
                    }
                }
            }
            @Override
            public void onFailure(Call<AcceptFriendPojo> call, Throwable t) {

            }
        });
    }


    // TODO: 9/26/2018 getMessageList

    private static void getMessage(HashMap<String, String> data){

        ApiCall apiCall=  ApiClient.getClient().create(ApiCall.class);
        apiCall.getMessageChat(data).enqueue(new Callback<GetMessageList>() {
            @Override
            public void onResponse(Call<GetMessageList> call, retrofit2.Response<GetMessageList> response) {
                int code = response.code();
                Log.e("Messagefragment", " CODEEEEE:   " + code);
                if (code == 200) {
                    GetMessageList s = response.body();
                    Log.e("RESPONSEEOBJECT", "---->" + s.getMessage());

                    messageAdapter = new MessageAdapter(context,strusername,s);
                    messagerecycel.setAdapter(messageAdapter);
                    addmessagetolist(s);

                }
            }
            @Override
            public void onFailure(Call<GetMessageList> call, Throwable t) {

                Log.e("ERRORRRR","--->"+call.toString());
            }
        });
    }

    // TODO: 9/26/2018  add data to messagelist
    static void addmessagetolist(GetMessageList message) {
        messageAdapter.add(message);
        messagerecycel.setSelection(messagerecycel.getCount() - 1);

    }


    // TODO: 9/26/2018  here send message from friend ;

    private void Sendmessage(HashMap<String, String> data){
        final ProgressDialog progressDialog= CommonUtils.showProgressDialog(context,"loading...");
        ApiCall apiCall=  ApiClient.getClient().create(ApiCall.class);
        apiCall.getMessageChat(data).enqueue(new Callback<GetMessageList>() {
            @Override
            public void onResponse(Call<GetMessageList> call, retrofit2.Response<GetMessageList> response) {
                int code = response.code();
                Log.e("Messagefragment", " CODEEEEE:   " + code);
                if (code == 200) {
                    progressDialog.dismiss();
                    GetMessageList s = response.body();
                    Log.e("RESPONSEEOBJECT", "---->" + s.getMessage());
                    HashMap<String, String> data  = new HashMap<>();
                    //action=get_message&sendfrom=16&sendto=1
                    data.put("action","get_message");
                    data.put("sendfrom",""+session.getUserId());//sessionid
                    data.put("sendto",""+strfriendid); //friendid
                    getMessage(data);

                }
            }
            @Override
            public void onFailure(Call<GetMessageList> call, Throwable t) {

                Log.e("ERRORRRR","--->"+call.toString());
            }
        });
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        strfriendid ="";
        stopService(new Intent(context, RefreshmessageServicess.class));
    }

    public static void callApi(){
        try {
            HashMap<String, String> data  = new HashMap<>();
            //action=get_message&sendfrom=16&sendto=1
            data.put("action","get_message");
            data.put("sendfrom",""+session.getUserId());//sessionid
            data.put("sendto",""+strfriendid); //friendid
            Log.e("SENDINGVALUEEE","--->"+data);
            getMessage(data);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
