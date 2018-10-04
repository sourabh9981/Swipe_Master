package com.swipe.shrinkcom.swipe.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.swipe.shrinkcom.R;
import com.swipe.shrinkcom.swipe.adapter.ItemInventoryAdaptor;
import com.swipe.shrinkcom.swipe.adapter.PackageAdaptor;
import com.swipe.shrinkcom.swipe.database.SessionManager;
import com.swipe.shrinkcom.swipe.network.ApiCall;
import com.swipe.shrinkcom.swipe.network.ApiClient;
import com.swipe.shrinkcom.swipe.pojo.purchaseitemPojo.PurchaseItemPojo;
import com.swipe.shrinkcom.swipe.pojo.randomnumberpojo.RandomNumberPojo;
import com.swipe.shrinkcom.swipe.utils.CommonUtils;
import com.swipe.shrinkcom.swipe.webrtc.ConnectActivity;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;

public class ItemInventory extends AppCompatActivity {

    private static final String TAG = "ItemInventory";
    RecyclerView recycleinventory;
    Context context;
    SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_inventory);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ImageView imgback = (ImageView)toolbar.findViewById(R.id.idback);
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        TextView tvtitle = (TextView)toolbar.findViewById(R.id.idcationtype);
        tvtitle.setText("Inventory");

         session = new SessionManager(this);
        recycleinventory = (RecyclerView)findViewById(R.id.idrecycleviewinventory);
        HashMap<String, String> data = new HashMap<>();
        data.put("action","get_purchased_package");
        data.put("user_id",""+session.getUserId());
        CaiiInventory(data);
    }

    private void CaiiInventory(HashMap<String, String> data){
        final ProgressDialog progressDialog= CommonUtils.showProgressDialog(ItemInventory.this,"loading...");
        ApiCall apiCall=  ApiClient.getClient().create(ApiCall.class);
        apiCall.PurchaseItem(data).enqueue(new Callback<PurchaseItemPojo>() {
            @Override
            public void onResponse(Call<PurchaseItemPojo> call, retrofit2.Response<PurchaseItemPojo> response) {
                int code = response.code();
                Log.e(TAG, " CODEEEEE:   " + code);
                if (code == 200) {
                    progressDialog.dismiss();
                    PurchaseItemPojo s = response.body();

                    Log.e("BODYYYYY", "---->" + response.body());
                    if (s.getResult() == 1) {
                        // TODO: 9/12/2018  setAdaptorhere
                        Log.e("sizeeee", "---->" + s.getUserData().size());
                        if (s.getUserData().size()>0){

                            GridLayoutManager layoutManager1 = new GridLayoutManager(context,3);
                            recycleinventory.setLayoutManager(layoutManager1);
                            recycleinventory.setItemAnimator(new DefaultItemAnimator());
                            ItemInventoryAdaptor hideadaptor = new ItemInventoryAdaptor(context,s);
                            recycleinventory.setAdapter(hideadaptor);
                            hideadaptor.notifyDataSetChanged();



                        }else {

                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<PurchaseItemPojo> call, Throwable t) {
            }

        });
    }

}
