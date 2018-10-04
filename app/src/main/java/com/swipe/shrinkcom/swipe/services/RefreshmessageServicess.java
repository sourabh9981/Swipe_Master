package com.swipe.shrinkcom.swipe.services;

import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.util.Log;

import com.swipe.shrinkcom.swipe.activity.MessageActivity;
import com.swipe.shrinkcom.swipe.adapter.MessageAdapter;
import com.swipe.shrinkcom.swipe.network.ApiCall;
import com.swipe.shrinkcom.swipe.network.ApiClient;
import com.swipe.shrinkcom.swipe.pojo.allmessagepojo.GetMessageList;
import com.swipe.shrinkcom.swipe.utils.CommonUtils;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;



public class RefreshmessageServicess extends Service {

    private MessageAdapter messageAdapter;
    Context context;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //getting systems default ringtone

        context = this;
        // TODO: 9/26/2018  call api here

       MessageActivity.callApi();

        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //stopping the player when service is destroyed

    }


    // TODO: 9/26/2018

/*
    private void getMessage(HashMap<String, String> data){
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
*/

    // TODO: 9/26/2018  add data to messagelist
/*
    void addmessagetolist( GetMessageList  message) {
        try {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    messageAdapter.add(message);
                    messagerecycel.setSelection(messagerecycel.getCount() - 1);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("ERRORRRR","====>"+e);
        }
    }
*/

}
