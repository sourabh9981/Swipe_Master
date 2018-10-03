package com.swipe.shrinkcom.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.swipe.shrinkcom.R;
import com.swipe.shrinkcom.adapter.FriendListAdaptor;
import com.swipe.shrinkcom.adapter.ReceiveThumbsupAdaptor;
import com.swipe.shrinkcom.database.SessionManager;
import com.swipe.shrinkcom.network.ApiCall;
import com.swipe.shrinkcom.network.ApiClient;
import com.swipe.shrinkcom.pojo.allfriendlist.Frienlist;
import com.swipe.shrinkcom.pojo.receivethumbsuppojo.ReceiveThumbsupPojo;
import com.swipe.shrinkcom.utils.CommonUtils;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;


public class ThumbsReceivedFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    // TODO: Rename and change types of parameters


    public ThumbsReceivedFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ThumbsReceivedFragment newInstance(String param1, String param2) {
        ThumbsReceivedFragment fragment = new ThumbsReceivedFragment();



        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    private static final String TAG = "ThumbsReceivedFragment";
    
    RecyclerView recyclethumbsuplist;
    SessionManager session;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_thumbs_received, container, false);

      recyclethumbsuplist = (RecyclerView)v.findViewById(R.id.idreceivedthumbsuplidt);
      session = new SessionManager(getActivity());
// action=showthumbsupbyfriends, user_id
           HashMap<String, String> data = new HashMap<>();
        data.put("action","showthumbsupbyfriends");
        data.put("user_id",session.getUserId());
        ReceiveThumbsup(data);
        return v;
    }

    // TODO: 9/20/2018  call api to receivethumbsuplist

    private void ReceiveThumbsup(HashMap<String, String> data){
        final ProgressDialog progressDialog= CommonUtils.showProgressDialog(getActivity(),"loading...");
        ApiCall apiCall=  ApiClient.getClient().create(ApiCall.class);
        apiCall.ReceivethumbsupList(data).enqueue(new Callback<ReceiveThumbsupPojo>() {
            @Override
            public void onResponse(Call<ReceiveThumbsupPojo> call, retrofit2.Response<ReceiveThumbsupPojo> response) {
                int code = response.code();
                Log.e(TAG, " CODEEEEE:   " + code);
                if (code == 200) {
                    progressDialog.dismiss();
                    ReceiveThumbsupPojo s = response.body();
//                  sessionManager.setUserId(s.getUserData().get(0).getUserId());
                    Log.e("BODYYYYY", "---->" + s.getResult());
                    if (s.getResult()==1){
                        // TODO: 9/12/2018  setAdaptorhere
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        recyclethumbsuplist.setLayoutManager(layoutManager);
                        recyclethumbsuplist.setItemAnimator(new DefaultItemAnimator());
                        ReceiveThumbsupAdaptor recentfriendadaptor = new ReceiveThumbsupAdaptor(getActivity(),s);
                        recyclethumbsuplist.setAdapter(recentfriendadaptor);

                    }

                }
            }
            @Override
            public void onFailure(Call<ReceiveThumbsupPojo> call, Throwable t) {

            }
        });
    }

}
