package com.swipe.shrinkcom.swipe.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.swipe.shrinkcom.R;
import com.swipe.shrinkcom.swipe.database.SessionManager;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import javax.security.auth.login.LoginException;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;

import static com.swipe.shrinkcom.swipe.api.UtilApi.IMAGELINK;

public class DisplayFilteruserActivity extends AppCompatActivity {
    private static final String TAG = "DisplayFilteruser";
    CircleImageView useriamge;
    TextView tvusername,tvcountry;
    private InterstitialAd mInterstitialAd;
    SessionManager session;


    private static final int CONNECTION_REQUEST = 1;
    private static boolean commandLineRun = false;
    private SharedPreferences sharedPref;
    private String keyprefResolution;
    private String keyprefFps;
    private String keyprefVideoBitrateType;
    private String keyprefVideoBitrateValue;
    private String keyprefAudioBitrateType;
    private String keyprefAudioBitrateValue;
    private String keyprefRoomServerUrl;
    private String keyprefRoom;
    private String keyprefRoomList;

      private  String roomid = "jksdhgisdgkl";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        keyprefResolution = getString(R.string.pref_resolution_key);
        keyprefFps = getString(R.string.pref_fps_key);
        keyprefVideoBitrateType = getString(R.string.pref_maxvideobitrate_key);
        keyprefVideoBitrateValue = getString(R.string.pref_maxvideobitratevalue_key);
        keyprefAudioBitrateType = getString(R.string.pref_startaudiobitrate_key);
        keyprefAudioBitrateValue = getString(R.string.pref_startaudiobitratevalue_key);
        keyprefRoomServerUrl = getString(R.string.pref_room_server_url_key);
        keyprefRoom = getString(R.string.pref_room_key);
        keyprefRoomList = getString(R.string.pref_room_list_key);

        setContentView(R.layout.activity_display_filteruser);
        MobileAds.initialize(this,"ca-app-pub-3940256099942544~3347511713");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        session = new SessionManager(this);
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");

        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        /*if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
            Toast.makeText(this, "Aids not Show", Toast.LENGTH_SHORT).show();
        }*/
       // showInterstitial();

        useriamge = (CircleImageView)findViewById(R.id.iduserimage);
        tvusername = (TextView)findViewById(R.id.idusername);
        tvcountry = (TextView)findViewById(R.id.idcountry);
        HashMap<String, String> data = new HashMap<>();



        // TODO: 9/25/2018  add webrtc here




    }

    private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();

        }else {
            Log.d("TAGnotload", "The interstitial wasn't loaded yet.");
          // Toast.makeText(this, "Aids not Show", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        String room = roomid;
      //  String roomListJson = new JSONArray(roomList).toString();
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(keyprefRoom, room);
     //   editor.putString(keyprefRoomList, roomListJson);
        editor.commit();
    }



    @Override
    public void onResume() {
        super.onResume();
        String room = sharedPref.getString(keyprefRoom, "");
    }

    @Override
    protected void onRestart() {
        super.onRestart();

       // connectToRoom(roomid, false, false, false, 0);



    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CONNECTION_REQUEST && commandLineRun) {
            Log.d(TAG, "Return: " + resultCode);
            setResult(resultCode);
            commandLineRun = false;
            finish();
        }
    }


    /**
     * Get a value from the shared preference or from the intent, if it does not
     * exist the default is used.
     */
    private String sharedPrefGetString(
            int attributeId, String intentName, int defaultId, boolean useFromIntent) {
        String defaultValue = getString(defaultId);
        if (useFromIntent) {
            String value = getIntent().getStringExtra(intentName);
            if (value != null) {
                return value;
            }
            return defaultValue;
        } else {
            String attributeName = getString(attributeId);
            return sharedPref.getString(attributeName, defaultValue);
        }
    }
    /**
     * Get a value from the shared preference or from the intent, if it does not
     * exist the default is used.
     */
    private boolean sharedPrefGetBoolean(
            int attributeId, String intentName, int defaultId, boolean useFromIntent) {
        boolean defaultValue = Boolean.valueOf(getString(defaultId));
        if (useFromIntent) {
            return getIntent().getBooleanExtra(intentName, defaultValue);
        } else {
            String attributeName = getString(attributeId);
            return sharedPref.getBoolean(attributeName, defaultValue);
        }
    }

    /**
     * Get a value from the shared preference or from the intent, if it does not
     * exist the default is used.
     */




    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }


    // TODO: 9/27/2018  reducegems here





}
