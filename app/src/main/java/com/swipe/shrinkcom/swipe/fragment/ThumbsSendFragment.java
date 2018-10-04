package com.swipe.shrinkcom.swipe.fragment;

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
import com.swipe.shrinkcom.swipe.adapter.ReceiveThumbsupAdaptor;
import com.swipe.shrinkcom.swipe.adapter.SendThumbsupAdaptor;
import com.swipe.shrinkcom.swipe.database.SessionManager;
import com.swipe.shrinkcom.swipe.network.ApiCall;
import com.swipe.shrinkcom.swipe.network.ApiClient;
import com.swipe.shrinkcom.swipe.pojo.receivethumbsuppojo.ReceiveThumbsupPojo;
import com.swipe.shrinkcom.swipe.utils.CommonUtils;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;


public class ThumbsSendFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public ThumbsSendFragment() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

private static final String TAG = "ThumbsSendFragment";
    RecyclerView recycleviewsend;
    SessionManager session;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_thumbs_send, container, false);

        recycleviewsend = (RecyclerView)v.findViewById(R.id.idsendrecyclevew);
        session = new SessionManager(getActivity());
        //action=showmythumbsup, user_id
        HashMap<String, String> data = new HashMap<>();
        data.put("action","showmythumbsup");
        data.put("user_id",""+session.getUserId());
        SendThumbsup(data);
        return  v;
    }


    // TODO: 9/20/2018 send thumbsupfragment

    private void SendThumbsup(HashMap<String, String> data){
        final ProgressDialog progressDialog= CommonUtils.showProgressDialog(getActivity(),"loading...");
        ApiCall apiCall=  ApiClient.getClient().create(ApiCall.class);
        apiCall.SendThumbsupList(data).enqueue(new Callback<ReceiveThumbsupPojo>() {
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
                        recycleviewsend.setLayoutManager(layoutManager);
                        recycleviewsend.setItemAnimator(new DefaultItemAnimator());
                        SendThumbsupAdaptor recentfriendadaptor = new SendThumbsupAdaptor(getActivity(),s);
                        recycleviewsend.setAdapter(recentfriendadaptor);

                    }

                }
            }
            @Override
            public void onFailure(Call<ReceiveThumbsupPojo> call, Throwable t) {

            }
        });
    }

}
