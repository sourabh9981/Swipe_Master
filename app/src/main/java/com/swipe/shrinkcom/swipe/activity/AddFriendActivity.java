package com.swipe.shrinkcom.swipe.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

import com.swipe.shrinkcom.R;


public class AddFriendActivity extends AppCompatActivity implements View.OnClickListener {

    LinearLayout addfriend,invitefriend,inviteapps;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        context = this;
        addfriend = (LinearLayout)findViewById(R.id.idaddfriendswipe);
        invitefriend = (LinearLayout)findViewById(R.id.idinvitefriend);
        inviteapps = (LinearLayout)findViewById(R.id.invitefriendsbyapp);
        addfriend.setOnClickListener(this);
        invitefriend.setOnClickListener(this);
        inviteapps.setOnClickListener(this);

    }//===========end of the AddFriendActivity==========================================

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.idaddfriendswipe:
                startActivity(new Intent(context,AddSwipeFriend.class));
                break;
            case R.id.idinvitefriend:
                startActivity(new Intent(context,InviteFriendActivity.class));
                break;
            case R.id.invitefriendsbyapp:
                share();
                break;
        }
    }

    void share(){
        Intent myIntent = new Intent(Intent.ACTION_SEND);
        myIntent.setType("text/plain");
        String shareBody = "Meet milion of people from around the world!by using Swipe \n https://www.shrinkcom.com/";
        myIntent.putExtra(Intent.EXTRA_SUBJECT, shareBody);
        myIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(myIntent, "Share using"));
    }

}//end of the AddFriendActivity
