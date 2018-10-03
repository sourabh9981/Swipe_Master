package com.swipe.shrinkcom.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.swipe.shrinkcom.R;
import com.swipe.shrinkcom.database.SessionManager;
import com.swipe.shrinkcom.fragment.DiscoverFragment;
import com.swipe.shrinkcom.fragment.FriendsFragment;
import com.swipe.shrinkcom.fragment.HistoryFragment;
import com.swipe.shrinkcom.fragment.MessageFragment;
import com.swipe.shrinkcom.network.ApiCall;
import com.swipe.shrinkcom.network.ApiClient;
import com.swipe.shrinkcom.pojo.totalpojo.TotalGems;
import com.swipe.shrinkcom.utils.CommonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class HomeActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
   /* private int[] tabIcons = {
            R.drawable.tab_ico_friends,
            R.drawable.ic_tab_call,
            R.drawable.ic_tab_call,
            R.drawable.ic_tab_contacts
    };*/

   SessionManager session;
   Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/
        session = new SessionManager(this);

        HashMap<String, String> datagems = new HashMap<>();
        datagems.put("action","get_total_gems");
        datagems.put("user_id",""+session.getUserId());
        Totalgems(datagems);


        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        setupTabIcons();
        permissionDialoge();
    }

    private void permissionDialoge() {
        final Dialog dialog = new Dialog(HomeActivity.this);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.dialoge_permission);
        dialog.setTitle("Title...");

        TextView dialogButton = dialog.findViewById(R.id.aloow);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }
    @SuppressLint("ResourceAsColor")
    private void setupTabIcons() {
        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText("Friends");
        //  tabOne.setTextColor(R.color.colorPrimaryDark);
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.tab_ico_friends, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText("Message");
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.tab_ico_message, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabThree.setText("History");
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.tab_ico_history, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);

        TextView tabFour = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabFour.setText("Discover");
        tabFour.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.tab_ico_discover, 0, 0);
        tabLayout.getTabAt(3).setCustomView(tabFour);

        if (tabLayout.getSelectedTabPosition()==3){
            showToast() ;
        }

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new FriendsFragment(), "Friends");
        adapter.addFragment(new MessageFragment(), "Message");
        adapter.addFragment(new HistoryFragment(), "History");
        adapter.addFragment(new DiscoverFragment(), "Discover");
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
        }
    }



    private void Totalgems(HashMap<String, String> data){
        //final ProgressDialog progressDialog= CommonUtils.showProgressDialog(context,"loading...");
        ApiCall apiCall=  ApiClient.getClient().create(ApiCall.class);
        apiCall.TotalGems(data).enqueue(new Callback<TotalGems>() {
            @Override
            public void onResponse(Call<TotalGems> call, retrofit2.Response<TotalGems> response) {
                int code = response.code();

                if (code == 200) {
                 //   progressDialog.dismiss();
                    TotalGems s = response.body();
//                  sessionManager.setUserId(s.getUserData().get(0).getUserId());
                    Log.e("BODYYYYY", "---->" + response.body());
                    if (s.getResult() == 1) {
                        // TODO: 9/12/2018  setAdaptorhere
                        Log.e("sizeeee", "---->" + s.getUserData());
                        String strtotalgems =   s.getUserData();

                        session.setTotalGems(Integer.parseInt(s.getUserData()));


                    }
                }
            }

            @Override
            public void onFailure(Call<TotalGems> call, Throwable t) {
            }

        });
    }

    public  void showToast() {
        // Set the toast and duration
        // Toast.makeText(context, "Please Swipe Top to match other Users", Toast.LENGTH_SHORT).show();
        Toast mToastToShow;
        int toastDurationInMilliSeconds = 10000;
        mToastToShow = Toast.makeText(context, "Please Swipe Up to match other Users", Toast.LENGTH_LONG);

        // Set the countdown to display the toast
        CountDownTimer toastCountDown;
        toastCountDown = new CountDownTimer(toastDurationInMilliSeconds, 1000 /*Tick duration*/) {
            public void onTick(long millisUntilFinished) {
                mToastToShow.setGravity(Gravity.TOP, 0, 0);
                mToastToShow.show();
            }
            public void onFinish() {
                mToastToShow.cancel();
            }
        };

        // Show the toast and starts the countdown
        mToastToShow.show();
        toastCountDown.start();
    }


}
