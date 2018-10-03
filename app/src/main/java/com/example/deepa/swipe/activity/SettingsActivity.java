package com.swipe.shrinkcom.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.swipe.shrinkcom.R;
import com.swipe.shrinkcom.database.SessionManager;

public class SettingsActivity extends AppCompatActivity implements View.OnClickListener{

    LinearLayout layoutmygains,layoutperchasegains,hidefriend,layoutpasscode,blockfriend;
    LinearLayout layoutiteminventory;
    Context context;
    LinearLayout linear_signout;
    SessionManager sessionManager;
    LinearLayout receivedthumbs,sendthumbsup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        sessionManager=new SessionManager(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageView imgback = (ImageView)toolbar.findViewById(R.id.idback);
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        context = this;
        init();

    }
    void init (){
        layoutmygains = (LinearLayout)findViewById(R.id.idmygains);
        layoutperchasegains = (LinearLayout)findViewById(R.id.idpurchasegains);
        hidefriend = (LinearLayout)findViewById(R.id.idlayouthidefriends);
        blockfriend = (LinearLayout)findViewById(R.id.idblockfriends);
        layoutpasscode = (LinearLayout)findViewById(R.id.idlayoutpasscode);
        linear_signout = (LinearLayout)findViewById(R.id.linear_signout);
        layoutiteminventory = (LinearLayout)findViewById(R.id.iditeminventroy);
        receivedthumbs = (LinearLayout)findViewById(R.id.receivedthumbsup);
        sendthumbsup = (LinearLayout)findViewById(R.id.idsendthumbsup);


        layoutmygains.setOnClickListener(this);
        layoutperchasegains.setOnClickListener(this);
        hidefriend.setOnClickListener(this);
        blockfriend.setOnClickListener(this);
        layoutpasscode.setOnClickListener(this);
        linear_signout.setOnClickListener(this);
        layoutiteminventory.setOnClickListener(this);
        receivedthumbs.setOnClickListener(this);
        sendthumbsup.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.idmygains:
                startActivity(new Intent(context,MyGemdsActivity.class));
                break;
            case R.id.idpurchasegains:
                startActivity(new Intent(context,MyGemdsActivity.class));
                break;
                case R.id.idlayouthidefriends:
                startActivity(new Intent(context,ActionFriendActivity.class).putExtra("type","Hide"));
                break;
                case R.id.idblockfriends:
                startActivity(new Intent(context,ActionFriendActivity.class).putExtra("type","Block"));
                break;
                case R.id.idlayoutpasscode:
                startActivity(new Intent(context,PasscodeActivity.class));
                break;
                case R.id.iditeminventroy:
                startActivity(new Intent(context,ItemInventory.class));
                break;
            case R.id.receivedthumbsup:
                startActivity(new Intent(context,ThumbsupListActivity.class).putExtra("typesend","0"));
                break;
            case R.id.idsendthumbsup:
                startActivity(new Intent(context,ThumbsupListActivity.class).putExtra("typesend","1"));
                break;

                case R.id.linear_signout:

                     //LoginManager.getInstance().logOut();
                     sessionManager.SetLogout();
                     Intent intent=new Intent(SettingsActivity.this, LoginActivity.class);
                     intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                     intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                     intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                     startActivity(intent);
                     finish();
                break;

        }
    }






}
