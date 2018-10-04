package com.swipe.shrinkcom.swipe.fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.swipe.shrinkcom.R;
import com.swipe.shrinkcom.swipe.activity.AddFriendActivity;
import com.swipe.shrinkcom.swipe.activity.MessageActivity;
import com.swipe.shrinkcom.swipe.adapter.FriendListAdaptor;
import com.swipe.shrinkcom.swipe.database.SessionManager;
import com.swipe.shrinkcom.swipe.network.ApiCall;
import com.swipe.shrinkcom.swipe.network.ApiClient;
import com.swipe.shrinkcom.swipe.pojo.allfriendlist.Frienlist;
import com.swipe.shrinkcom.swipe.pojo.sendfriendlist.Sendfriendlist;
import com.swipe.shrinkcom.swipe.utils.CommonUtils;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends Fragment {
    TextView linearmessage;

    public MessageFragment() {
        // Required empty public constructor
    }


    RecyclerView recycleviewmessaege;
    TextView tvnodatafound;
    SessionManager session;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_message, container, false);
        view.findViewById(R.id.layoutMesssage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), MessageActivity.class));
            }
        });
        session = new SessionManager(getActivity());
        recycleviewmessaege = (RecyclerView) view.findViewById(R.id.idrecycleview);
        linearmessage = view.findViewById(R.id.linearmessage);
        tvnodatafound = (TextView)view.findViewById(R.id.idnodatafoundd);
        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "billabong.ttf");

        // Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/" + font);

        linearmessage.setTypeface(font);
        view.findViewById(R.id.layoutMesssage1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), MessageActivity.class));
            }
        });
        view.findViewById(R.id.linearAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AddFriendActivity.class));
            }
        });
        //friendrequest_list&user_id=10
        HashMap<String, String> data = new HashMap<>();
        data.put("action","friendrequest_list");
        data.put("user_id",""+session.getUserId());
        CallFriendlist(data);//+session.getUserId()
        return view;
    }


    // TODO: 9/25/2018  new all messagelist here



    private void CallFriendlist(HashMap<String, String> data){
        final ProgressDialog progressDialog= CommonUtils.showProgressDialog(getActivity(),"loading...");
        ApiCall apiCall=  ApiClient.getClient().create(ApiCall.class);
        apiCall.SenderLIst(data).enqueue(new Callback<Sendfriendlist>() {
            @Override
            public void onResponse(Call<Sendfriendlist> call, retrofit2.Response<Sendfriendlist> response) {
                int code = response.code();
                Log.e("Messagefragment", " CODEEEEE:   " + code);
                if (code == 200) {
                    progressDialog.dismiss();
                    Sendfriendlist s = response.body();

                    Log.e("RESPONSEE", "---->" + s.getResult());

//                  sessionManager.setUserId(s.getUserData().get(0).getUserId());
                    Log.e("BODYYYYYResult", "---->" + s.getUserData().size());
                    if (s.getUserData().size()>0){
                        // TODO: 9/12/2018  setAdaptorhere
                        tvnodatafound.setVisibility(View.GONE);
                        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getActivity());
                        layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
                        recycleviewmessaege.setLayoutManager(layoutManager1);
                        recycleviewmessaege.setItemAnimator(new DefaultItemAnimator());
                        FriendRequestAdaptor friendlistadaptor = new FriendRequestAdaptor(getActivity(),s);
                        recycleviewmessaege.setAdapter(friendlistadaptor);
                    }else {
                        tvnodatafound.setVisibility(View.VISIBLE);
                    }

                }
            }
            @Override
            public void onFailure(Call<Sendfriendlist> call, Throwable t) {

            }
        });
    }


}
