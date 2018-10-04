package com.swipe.shrinkcom.swipe.fragment;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.swipe.shrinkcom.R;
import com.swipe.shrinkcom.swipe.activity.AddFriendActivity;
import com.swipe.shrinkcom.swipe.activity.DisplayFilteruserActivity;
import com.swipe.shrinkcom.swipe.adapter.HistoryAdaptor;
import com.swipe.shrinkcom.swipe.database.SessionManager;
import com.swipe.shrinkcom.swipe.network.ApiCall;
import com.swipe.shrinkcom.swipe.network.ApiClient;
import com.swipe.shrinkcom.swipe.pojo.historypojo.HistoryPojo;
import com.swipe.shrinkcom.swipe.pojo.randomnumberpojo.RandomNumberPojo;
import com.swipe.shrinkcom.swipe.utils.CommonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

import static com.swipe.shrinkcom.swipe.api.UtilApi.IMAGELINK;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryFragment extends Fragment {
    TextView linearHist;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    private int[] tabIcons = {
            R.drawable.girllllll,
            R.drawable.profile_f,
            R.drawable.profile
    };

    public HistoryFragment() {
        // Required empty public constructor
    }


    RecyclerView recycleHistory;
    SessionManager session;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        view.findViewById(R.id.linearAddd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AddFriendActivity.class));
            }
        });

        session = new SessionManager(getActivity());
        recycleHistory = (RecyclerView)view.findViewById(R.id.idhistorylist);
// totalData,user_id
        HashMap<String, String> data = new HashMap<>();
        data.put("action","connection_list");
        data.put("user_id",session.getUserId());
        Log.e("DATAAAAAAA","--->"+data);
        History(data);
        viewPager = (ViewPager) view.findViewById(R.id.viewpagerr);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) view.findViewById(R.id.tabss);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        linearHist = view.findViewById(R.id.linearHist);

        Typeface font = Typeface.createFromAsset(getContext().getAssets(), "billabong.ttf");

        // Typeface typeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/" + font);

        linearHist.setTypeface(font);
        return view;
        //
    }

    private void setupTabIcons() {
        TextView tabOne = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        tabOne.setText("");
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.boy, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        tabTwo.setText("");
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.boy, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

      /*  TextView tabThree = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.custom_tab, null);
        tabThree.setText("THREE");
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.boy, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);*/

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager());
        adapter.addFragment(new BlankFragment(), "ONE");
        adapter.addFragment(new BlankFragment(), "ONE");
        //adapter.addFragment(new BlankFragment(), "ONE");

        viewPager.setAdapter(adapter);


    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
           return mFragmentTitleList.get(position);
           //return null;
        }
    }


    // TODO: 9/24/2018  call api here
    private void History(HashMap<String, String> data){
        final ProgressDialog progressDialog= CommonUtils.showProgressDialog(getActivity(),"loading...");
        ApiCall apiCall=  ApiClient.getClient().create(ApiCall.class);
        apiCall.History(data).enqueue(new Callback<HistoryPojo>() {
            @Override
            public void onResponse(Call<HistoryPojo> call, retrofit2.Response<HistoryPojo> response) {
                int code = response.code();
                Log.e("History", " CODEEEEE:   " + code);
                if (code == 200) {
                    progressDialog.dismiss();
                    HistoryPojo s = response.body();
//                  sessionManager.setUserId(s.getUserData().get(0).getUserId());
                    Log.e("BODYYYYY", "---->" + s.getMessage());
                    if (s.getResult() == 1) {
                        // TODO: 9/12/2018  setAdaptorhere
                        Log.e("sizeeeeHistory", "---->" + s.getUserData().size());
                        if (s.getUserData().size() > 0) {

                            LinearLayoutManager layoutManager1 = new LinearLayoutManager(getActivity());
                            layoutManager1.setOrientation(LinearLayoutManager.VERTICAL);
                            recycleHistory.setLayoutManager(layoutManager1);
                            recycleHistory.setItemAnimator(new DefaultItemAnimator());
                            HistoryAdaptor adaptor = new HistoryAdaptor(getActivity(),s);
                            recycleHistory.setAdapter(adaptor);
                            adaptor.notifyDataSetChanged();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<HistoryPojo> call, Throwable t) {
            }

        });
    }


}
