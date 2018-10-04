package com.swipe.shrinkcom.swipe.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;

import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.swipe.shrinkcom.R;
import com.swipe.shrinkcom.swipe.database.SessionManager;
import com.swipe.shrinkcom.swipe.network.ApiCall;
import com.swipe.shrinkcom.swipe.network.ApiClient;
import com.swipe.shrinkcom.swipe.pojo.pojoSinup.SignupP;
import com.swipe.shrinkcom.swipe.utils.CommonUtils;


import java.util.HashMap;

import java.util.Map;

import irshad.sheikh.loginlibrary.LoginType;
import irshad.sheikh.loginlibrary.SmartLogin;
import irshad.sheikh.loginlibrary.SmartLoginCallbacks;
import irshad.sheikh.loginlibrary.SmartLoginConfig;
import irshad.sheikh.loginlibrary.SmartLoginFactory;
import irshad.sheikh.loginlibrary.users.SmartFacebookUser;
import irshad.sheikh.loginlibrary.users.SmartGoogleUser;
import irshad.sheikh.loginlibrary.util.SmartLoginException;
import irshad.sheikh.loginlibrary.vollyrequest.VolleyRequestFacebook;
import irshad.sheikh.loginlibrary.vollyrequest.VolleyRequestGoogle;
import retrofit2.Call;
import retrofit2.Callback;

import static com.swipe.shrinkcom.swipe.api.UtilApi.BASE_URL;


public class LoginActivity extends AppCompatActivity implements SmartLoginCallbacks, GoogleApiClient.OnConnectionFailedListener ,View.OnClickListener{
    private static final String TAG = LoginActivity.class.getSimpleName();

    SmartLogin smartLogin;
    SmartLoginConfig config;
    LinearLayout linearGoogle, linearFacebbok, linearMore, linearLogin;

    VideoView video;
    VideoView mVideoView;

    private static final int RC_SIGN_IN = 007;
    private GoogleApiClient mGoogleApiClient;
    private ProgressDialog mProgressDialog;
    String loginby;
    Context context;


    SessionManager sessionManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        context=this;
        sessionManager=new SessionManager(this);
        Log.d(TAG,"======Oncreate Called  == :  ");
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        // TODO: 4/9/18

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

//        String token = FirebaseInstanceId.getInstance().getToken();
//        Log.d("MyRefreshedTokan: ", token);


        ByViewId();
        oncallVedio();

        linearLogin.setOnClickListener(this);
        linearFacebbok.setOnClickListener(this);
        linearGoogle.setOnClickListener(this);
        linearMore.setOnClickListener(this);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        config = new SmartLoginConfig(this, this);
        config.setFacebookAppId(getString(R.string.facebook_app_id));
        config.setFacebookPermissions(null);
        config.setGoogleApiClient(mGoogleApiClient);
    }
    private void ByViewId() {
        mVideoView = (VideoView) findViewById(R.id.mVideoView);
        linearGoogle = findViewById(R.id.linearGoogle);
        linearMore = findViewById(R.id.linearMore);
        linearFacebbok = findViewById(R.id.linearFb);
        linearLogin=findViewById(R.id.linearLogin);
    }
    /*=================================end of the OnCreate() method ===============================*/

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.linearLogin:
                startActivity(new Intent(LoginActivity.this, LoginSecondActivity.class));
                return;
            case R.id.linearFb:
                VolleyRequestFacebook volleyRequestFacebook = new VolleyRequestFacebook();
                volleyRequestFacebook.setUrl(BASE_URL);
                loginby = "FACEBOOK";
                smartLogin = SmartLoginFactory.build(LoginType.Facebook);
                smartLogin.facebook(config, volleyRequestFacebook);

                return;
            case R.id.linearGoogle:
                VolleyRequestGoogle volleyRequestGoogle = new VolleyRequestGoogle();
                volleyRequestGoogle.setUrl(BASE_URL);
                loginby = "GOOGLE";
                smartLogin = SmartLoginFactory.build(LoginType.Google);
                smartLogin.google(config, volleyRequestGoogle);
                showProgressDialog();
                return;
            case R.id.linearMore:
                linearGoogle.setVisibility(View.VISIBLE);
                linearMore.setVisibility(View.GONE);
                return;
        }
    }
    private void handleSignInResult(GoogleSignInResult result) {

        Log.e(TAG, "handleSignInResult Check: "+result );
        hideProgressDialog();

        if (result.isSuccess()) {
            Log.d(TAG, "handleSignInResult: ==:  "+result);

            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            String personName = acct.getDisplayName();
            String email = acct.getEmail();
            Log.d(TAG, "Name: " + personName + ", email: " + email);
//            Toast.makeText(this, ""+acct.getDisplayName(), Toast.LENGTH_SHORT).show();
           // startActivity(new Intent(LoginActivity.this, HomeActivity.class));
        } else {

        }
    }
    /*==========================end of the handleSignInResult()=======================================*/
    private void oncallVedio() {
        DisplayMetrics metrics = new DisplayMetrics();
        float yInches = metrics.heightPixels / metrics.ydpi;
        float xInches = metrics.widthPixels / metrics.xdpi;
        double diagonalInches = Math.sqrt(xInches * xInches + yInches * yInches);
        Uri uri;
        uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.video_new);
        //uri = Uri.parse("https://www.youtube.com/watch?v=KWzYD37QKtI&feature=youtu.be");
        mVideoView.setVideoURI(uri);
        mVideoView.start();
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                mediaPlayer.setVolume(0f, 0f);
//                mVideoView.start();
                mediaPlayer.setLooping(true);
            }
        });
    }
    /*==========================end of the oncallVedio()=======================================*/
    @Override
    protected void onRestart() {
        oncallVedio();
        super.onRestart();
    }
    @Override
    protected void onDestroy() {
        oncallVedio();
        super.onDestroy();
    }

    Boolean doubleBackToExitPressedOnce=false;
    @Override
    public void onBackPressed() {
        hideProgressDialog();
        oncallVedio();
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (smartLogin != null) {
            Log.d(TAG, "onActivityResult: check the smartlogin" );
            hideProgressDialog();
            smartLogin.onActivityResult(requestCode, resultCode, data, config);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d(TAG, "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
//            showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    Log.d(TAG, "onResult: check the data " );

//                    hideProgressDialog();
//                    showProgressDialog();
                    handleSignInResult(googleSignInResult);

                }
            });
        }
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
    }
    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Loading Google.........");
            mProgressDialog.setIndeterminate(true);
        }
        mProgressDialog.show();
    }
    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }
    @Override
    public void onLoginSuccess(String s) {
        Log.d(TAG, "onLoginSuccess: === :  " );
        Toast.makeText(this, ""+s, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onSignupSuccess(String s) {
        Log.d(TAG, "onSignupSuccess: === :  " );
    }
    @Override
    public void onLoginFailure(SmartLoginException e) {
    }
    @Override
    public Map<String, String> doCustomLogin() {
        Log.d(TAG, "doCustomLogin: ===" );
        return null;
    }
    @Override
    public Map<String, String> doCustomSignup() {
        return null;
    }
    @Override
    public Map<String, String> onFacebookLoginSuccess(SmartFacebookUser smartFacebookUser) {
        /*https://github.com/MindorksOpenSource/android-mvp-architecture*/

        hideProgressDialog();
        Log.d(TAG, "onFacebookLoginSuccess: email   == :  "+smartFacebookUser.getEmail() );
        HashMap<String, String> params = new HashMap<>();
        params.put("action", "user_sociallogin");
//        params.put("name", ""+smartFacebookUser.getFirstName()+""+smartFacebookUser.getLastName());
        params.put("username", smartFacebookUser.getProfileName());
        params.put("email", smartFacebookUser.getUserId() + "@facebook.com");
        params.put("image", "https://graph.facebook.com/"+smartFacebookUser.getUserId()+"/picture?type=large");
        params.put("loginby", "FACEBOOK");
        CallApiSignup(params);
//        socialLoginApi(params);
        Log.d(TAG, "onFacebookLoginSuccess: PARAM :  "+params);
        return params;
    }
    @Override
    public Map<String, String> onGoogleLoginSuccess(SmartGoogleUser smartGoogleUser) {
        Log.d(TAG, "onGoogleLoginSuccess: success the  " );
        hideProgressDialog();

        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        Log.d(TAG, "onResult:check status== :   " );

                    }
                });
        HashMap<String, String> params = new HashMap<>();
        params.put("action", "user_sociallogin");
//        params.put("name", ""+smartGoogleUser.getFirstName()+""+smartGoogleUser.getLastName());
        params.put("username", smartGoogleUser.getDisplayName());
        params.put("email", smartGoogleUser.getEmail());
        params.put("image", "https://graph.facebook.com/"+"/picture?type=large");
        params.put("loginby", "GOOGLE");
        Log.d(TAG, "onGoogleLoginSuccess: PARAM: "+params );
        //socialLoginApi(params);
        CallApiSignup(params);
        return params;
    }
    /*====================end of the onGoogleLoginSuccess()==================================== */
    private void CallApiSignup(HashMap<String, String> data){
        final ProgressDialog progressDialog= CommonUtils.showProgressDialog(context,"loading...");
        ApiCall apiCall=  ApiClient.getClient().create(ApiCall.class);
        apiCall.Signup(data).enqueue(new Callback<SignupP>() {
            @Override
            public void onResponse(Call<SignupP> call, retrofit2.Response<SignupP> response) {
                int  code=response.code();
                Log.d(TAG, "onResponse:===code :   " +code);
                if(code==200){
                    progressDialog.dismiss();
                    SignupP s= response.body();
//                  sessionManager.setUserId(s.getUserData().get(0).getUserId());


                    String str_messge=s.getMessage();
                    Log.e(TAG, "onResponse: ajesh "+str_messge );






                    if(str_messge.contains("This email already exist")){

                        LoginManager.getInstance().logOut();
                        sessionManager.setUserId(s.getUserData().get(0).getUserId());
                        sessionManager.setUserName(s.getUserData().get(0).getUsername());
                        sessionManager.setLocation(s.getUserData().get(0).getAddress());
                        sessionManager.setImage(s.getUserData().get(0).getImage());

                        startActivity(new Intent(context, HomeActivity.class));
                    }else {
                        LoginManager.getInstance().logOut();
                        startActivity(new Intent(context, BirthActivity.class).putExtra("userid",s.getUserData().get(0).getUserId()));
                    }



//                 sessionManager.setGeneratedSwipeID(s.getUserData().get(0).getUserId());
//                 Log.d(TAG, "onResponse: == :  "+s.getUserData().get(0).getUserId());
                }
            }
            @Override
            public void onFailure(Call<SignupP> call, Throwable t) {

            }
        });
    }/*==========================CallApiSignup()===================================================*/


}//======================end of the LoginActivity