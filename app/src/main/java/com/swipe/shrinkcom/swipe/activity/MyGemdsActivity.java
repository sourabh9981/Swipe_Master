package com.swipe.shrinkcom.swipe.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.swipe.shrinkcom.R;
import com.swipe.shrinkcom.swipe.adapter.HideFriendAdaptor;
import com.swipe.shrinkcom.swipe.adapter.PackageAdaptor;
import com.swipe.shrinkcom.swipe.database.SessionManager;
import com.swipe.shrinkcom.swipe.network.ApiCall;
import com.swipe.shrinkcom.swipe.network.ApiClient;
import com.swipe.shrinkcom.swipe.pojo.hidefriendlist.HideFriendList;
import com.swipe.shrinkcom.swipe.pojo.packagelist.PackageList;
import com.swipe.shrinkcom.swipe.pojo.totalpojo.TotalGems;
import com.swipe.shrinkcom.swipe.utils.CommonUtils;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;

public class MyGemdsActivity extends AppCompatActivity {

    private static final String TAG = MyGemdsActivity.class.getSimpleName();
    RecyclerView recyclepackage;
    Context context;
    TextView tvtotalgems;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_gemds);
        context = this;
        session = new SessionManager(this);

        ImageView imgback = (ImageView)findViewById(R.id.fab);
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        recyclepackage = (RecyclerView)findViewById(R.id.idpackagelist);

        tvtotalgems = (TextView)findViewById(R.id.idtotalgems);
        HashMap<String, String> data = new HashMap<>();
        data.put("action","package_list");

        Packages(data);

        HashMap<String, String> datagems = new HashMap<>();
        datagems.put("action","get_total_gems");
        datagems.put("user_id",""+session.getUserId());
        Totalgems(datagems);

    }
    

    private void Packages(HashMap<String, String> data){
        final ProgressDialog progressDialog= CommonUtils.showProgressDialog(context,"loading...");
        ApiCall apiCall=  ApiClient.getClient().create(ApiCall.class);
        apiCall.Packeges(data).enqueue(new Callback<PackageList>() {
            @Override
            public void onResponse(Call<PackageList> call, retrofit2.Response<PackageList> response) {
                int code = response.code();
                Log.e(TAG, " CODEEEEE:   " + code);
                if (code == 200) {
                    progressDialog.dismiss();
                    PackageList s = response.body();
//                  sessionManager.setUserId(s.getUserData().get(0).getUserId());
                    Log.e("BODYYYYY", "---->" + response.body());
                    if (s.getResult() == 1) {
                        // TODO: 9/12/2018  setAdaptorhere
                        Log.e("sizeeee", "---->" + s.getUserData().size());
                        if (s.getUserData().size()>0){

                            GridLayoutManager layoutManager1 = new GridLayoutManager(context,3);
                            recyclepackage.setLayoutManager(layoutManager1);
                            recyclepackage.setItemAnimator(new DefaultItemAnimator());
                            PackageAdaptor hideadaptor = new PackageAdaptor(context,s);
                            recyclepackage.setAdapter(hideadaptor);
                            hideadaptor.notifyDataSetChanged();

                        }else {

                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<PackageList> call, Throwable t) {
            }

        });
    }



    private void Totalgems(HashMap<String, String> data){
        final ProgressDialog progressDialog= CommonUtils.showProgressDialog(context,"loading...");
        ApiCall apiCall=  ApiClient.getClient().create(ApiCall.class);
        apiCall.TotalGems(data).enqueue(new Callback<TotalGems>() {
            @Override
            public void onResponse(Call<TotalGems> call, retrofit2.Response<TotalGems> response) {
                int code = response.code();
                Log.e(TAG, " CODEEEEE:   " + code);
                if (code == 200) {
                    progressDialog.dismiss();
                    TotalGems s = response.body();
//                  sessionManager.setUserId(s.getUserData().get(0).getUserId());
                    Log.e("BODYYYYY", "---->" + response.body());
                    if (s.getResult() == 1) {
                        // TODO: 9/12/2018  setAdaptorhere
                        Log.e("sizeeee", "---->" + s.getUserData());
                      String strtotalgems =   s.getUserData();

                        session.setTotalGems(Integer.parseInt(s.getUserData()));

                        tvtotalgems.setText(strtotalgems);
                    }
                }
            }

            @Override
            public void onFailure(Call<TotalGems> call, Throwable t) {
            }

        });
    }



}
