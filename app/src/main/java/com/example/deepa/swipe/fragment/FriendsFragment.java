package com.swipe.shrinkcom.fragment;


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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.swipe.shrinkcom.R;
import com.swipe.shrinkcom.activity.AddFriendActivity;
import com.swipe.shrinkcom.activity.BirthActivity;
import com.swipe.shrinkcom.activity.HomeActivity;
import com.swipe.shrinkcom.activity.LoginActivity;
import com.swipe.shrinkcom.activity.ProfileActivity;
import com.swipe.shrinkcom.activity.Profile_newActivity;
import com.swipe.shrinkcom.activity.RegisterIdActivity;
import com.swipe.shrinkcom.activity.SearchActivity;
import com.swipe.shrinkcom.activity.SettingsActivity;
import com.swipe.shrinkcom.adapter.FriendListAdaptor;
import com.swipe.shrinkcom.beans.Invite;
import com.swipe.shrinkcom.database.SessionManager;
import com.swipe.shrinkcom.network.ApiCall;
import com.swipe.shrinkcom.network.ApiClient;
import com.swipe.shrinkcom.pojo.allfriendlist.Frienlist;
import com.swipe.shrinkcom.pojo.pojoSinup.SignupP;
import com.swipe.shrinkcom.utils.CommonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;

import static com.swipe.shrinkcom.api.UtilApi.IMAGELINK;

/**
 * A simple {@link Fragment} subclass.
 */
public class FriendsFragment extends Fragment implements View.OnClickListener{
    private static final String TAG = FriendsFragment.class.getSimpleName();
    LinearLayout linearSwipeId;
    TextView tcSwipe;
    private String A_BOLD = "billabong.ttf";

    public FriendsFragment() {
        // Required empty public constructor
    }

    RecyclerView recyclerViewrecentfriend;
    RecyclerView recyclerViewfriendlist;
    List<Invite> list;

    ImageView profileimage;
    SessionManager session;
    TextView tvusername,tvlocation;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         view = inflater.inflate(R.layout.fragment_friends, container, false);

        session = new SessionManager(getActivity());
         ByViewId();


        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "billabong.ttf");
        tcSwipe.setTypeface(font);




        /*action=all_friendlist , user_id*/





        // Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/" + font);





        // tcSwipe.setTypeface(Typeface.createFromAsset(getContext().getAssets(), A_BOLD));
        view.findViewById(R.id.linearAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AddFriendActivity.class));
            }
        });
        view.findViewById(R.id.imageSettings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SettingsActivity.class));
            }
        });
        view.findViewById(R.id.searchVieww).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), SearchActivity.class));
            }
        });
        view.findViewById(R.id.linearSwipeId).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), RegisterIdActivity.class));
            }
        });
        view.findViewById(R.id.linearUserProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ProfileActivity.class));
            }
        });
        view.findViewById(R.id.linearUserProfile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), ProfileActivity.class));
            }
        });
        list = new ArrayList<>();
        // TODO: 9/1/2018 here business code for friends list
        recyclerViewrecentfriend = (RecyclerView)view.findViewById(R.id.idrecentfriend);
        recyclerViewfriendlist = (RecyclerView)view.findViewById(R.id.idfriendlist);

        // TODO: 9/1/2018 for new friend

        HashMap<String, String> datarecent = new HashMap<>();
        datarecent.put("action","recent_friendlist");
        datarecent.put("user_id",""+session.getUserId());
        Log.e("SENDVALUEEE","--->"+datarecent);
        CallRecentFriendlist(datarecent);


        // TODO: 9/1/2018 friendlist
        HashMap<String, String> data = new HashMap<>();
        data.put("action","all_friendlist");
        data.put("user_id",""+session.getUserId());
        Log.e("SENDVALUEEE","--->"+data);
        CallFriendlist(data);


        return view;
        // linearAdd
    }

    private void ByViewId() {
        tcSwipe = view.findViewById(R.id.tcSwipe);
        view.findViewById(R.id.linearAdd);
        view.findViewById(R.id.imageSettings);
        view.findViewById(R.id.searchVieww);

        profileimage = (CircleImageView)view.findViewById(R.id.profile_image);
        tvusername = (TextView)view.findViewById(R.id.idusername);
        tvlocation = (TextView)view.findViewById(R.id.idlocation);


        if (session.getImage().equals("images")){
            if (session.getGender().equals("MALE")) {
                profileimage.setImageResource(R.drawable.profile);
            }else {
                profileimage.setImageResource(R.drawable.profile_f);
            }
        }else {
            Glide.with(getActivity())
                    .load(IMAGELINK+session.getImage().replaceAll(" ","%20"))
                    .into(profileimage);
        }
        tvusername.setText(session.getUserName());
        tvlocation.setText(session.getLocation());



    }
    /*==============================end of ByViewId==================================*/

    @Override
    public void onClick(View v) {






    }
    /*==============================end of onClick()==================================*/


    // TODO: 9/12/2018  getAllfriend list nad recent friend list;
    private void CallFriendlist(HashMap<String, String> data){
        final ProgressDialog progressDialog= CommonUtils.showProgressDialog(getActivity(),"loading...");
        ApiCall apiCall=  ApiClient.getClient().create(ApiCall.class);
        apiCall.AllFriendList(data).enqueue(new Callback<Frienlist>() {
            @Override
            public void onResponse(Call<Frienlist> call, retrofit2.Response<Frienlist> response) {
                int code = response.code();
                Log.e(TAG, " CODEEEEE:   " + code);
                if (code == 200) {
                    progressDialog.dismiss();
                    Frienlist s = response.body();

                    Log.e("RESPONSEE", "---->" + response.body());

//                  sessionManager.setUserId(s.getUserData().get(0).getUserId());
                    Log.e("BODYYYYY", "---->" + s.getResult());
                    if (s.getResult()==1){
                        // TODO: 9/12/2018  setAdaptorhere

                        LinearLayoutManager layoutManager1 = new LinearLayoutManager(getActivity());
                        layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
                        recyclerViewfriendlist.setLayoutManager(layoutManager1);
                        recyclerViewfriendlist.setItemAnimator(new DefaultItemAnimator());

                        FriendListAdaptor friendlistadaptor = new FriendListAdaptor(getActivity(),s);
                        recyclerViewfriendlist.setAdapter(friendlistadaptor);


                    }

                }
            }
            @Override
            public void onFailure(Call<Frienlist> call, Throwable t) {

            }
        });
    }


    // TODO: 9/12/2018 here call recent friendlist

    private void CallRecentFriendlist(HashMap<String, String> data){
        final ProgressDialog progressDialog= CommonUtils.showProgressDialog(getActivity(),"loading...");
        ApiCall apiCall=  ApiClient.getClient().create(ApiCall.class);
        apiCall.AllFriendList(data).enqueue(new Callback<Frienlist>() {
            @Override
            public void onResponse(Call<Frienlist> call, retrofit2.Response<Frienlist> response) {
                int code = response.code();
                Log.e(TAG, " CODEEEEE:   " + code);
                if (code == 200) {
                    progressDialog.dismiss();
                    Frienlist s = response.body();
//                  sessionManager.setUserId(s.getUserData().get(0).getUserId());
                    Log.e("BODYYYYY", "---->" + s.getResult());
                    if (s.getResult()==1){
                        // TODO: 9/12/2018  setAdaptorhere

                        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        recyclerViewrecentfriend.setLayoutManager(layoutManager);
                        recyclerViewrecentfriend.setItemAnimator(new DefaultItemAnimator());
                        FriendListAdaptor  recentfriendadaptor = new FriendListAdaptor(getActivity(),s);
                        recyclerViewrecentfriend.setAdapter(recentfriendadaptor);


                    }

                }
            }
            @Override
            public void onFailure(Call<Frienlist> call, Throwable t) {

            }
        });
    }






}
