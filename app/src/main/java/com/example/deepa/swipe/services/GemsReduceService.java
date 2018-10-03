package com.swipe.shrinkcom.services;

import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.swipe.shrinkcom.activity.MessageActivity;
import com.swipe.shrinkcom.adapter.MessageAdapter;
import com.swipe.shrinkcom.database.SessionManager;
import com.swipe.shrinkcom.network.ApiCall;
import com.swipe.shrinkcom.network.ApiClient;
import com.swipe.shrinkcom.pojo.reducepojo.ReduceGemsPojo;
import com.swipe.shrinkcom.pojo.totalpojo.TotalGems;
import com.swipe.shrinkcom.utils.CommonUtils;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;

public class GemsReduceService extends Service{


    Context context;
SessionManager session;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //getting systems default ringtone

        context = this;
        session = new SessionManager(context);
        // TODO: 9/26/2018  call api here

        HashMap<String, String> data = new HashMap<>();
        data.put("action","deduct_gems");
        data.put("user_id",""+session.getUserId());
        data.put("gems","9");
        Log.e("SENDVALUSEE","----->"+data);
        Reducegems(data);

        return START_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        //stopping the player when service is destroyed

    }


    private void Reducegems(HashMap<String, String> data){

        ApiCall apiCall=  ApiClient.getClient().create(ApiCall.class);
        apiCall.ReduceGems(data).enqueue(new Callback<ReduceGemsPojo>() {
            @Override
            public void onResponse(Call<ReduceGemsPojo> call, retrofit2.Response<ReduceGemsPojo> response) {
                int code = response.code();
                Log.e("Reducegemsservices", " CODEEEEE:   " + code);
                if (code == 200) {

                    ReduceGemsPojo s = response.body();
//                  sessionManager.setUserId(s.getUserData().get(0).getUserId());
                    Log.e("BODYYYYY", "---->" + s.getResult());
                    if (s.getResult()==1){
                        // TODO: 9/12/2018  setAdaptorhere
                        HashMap<String, String> datagems = new HashMap<>();
                        datagems.put("action","get_total_gems");
                        datagems.put("user_id",""+session.getUserId());
                        Totalgems(datagems);

                    }

                }
            }
            @Override
            public void onFailure(Call<ReduceGemsPojo> call, Throwable t) {

            }
        });
    }


    private void Totalgems(HashMap<String, String> data){

        ApiCall apiCall=  ApiClient.getClient().create(ApiCall.class);
        apiCall.TotalGems(data).enqueue(new Callback<TotalGems>() {
            @Override
            public void onResponse(Call<TotalGems> call, retrofit2.Response<TotalGems> response) {
                int code = response.code();

                if (code == 200) {

                    TotalGems s = response.body();
//                  sessionManager.setUserId(s.getUserData().get(0).getUserId());
                    Log.e("BODYYYYY", "---->" + response.body());
                    if (s.getResult() == 1) {
                        // TODO: 9/12/2018  setAdaptorhere
                        Log.e("sizeeee", "---->" + s.getUserData());
                        String strtotalgems =   s.getUserData();
                        Log.e("Totalgems","---->"+strtotalgems);
                        session.setTotalGems(Integer.parseInt(s.getUserData()));


                    }
                }
            }

            @Override
            public void onFailure(Call<TotalGems> call, Throwable t) {
            }

        });
    }

}
