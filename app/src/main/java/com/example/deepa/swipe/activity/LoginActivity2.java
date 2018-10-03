package com.swipe.shrinkcom.activity;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.VideoView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.swipe.shrinkcom.R;
import com.swipe.shrinkcom.pojo.pojoSinup.SignupP;
import com.swipe.shrinkcom.utils.AppLogger;
import com.swipe.shrinkcom.utils.CommonUtils;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

import static com.swipe.shrinkcom.api.UtilApi.BASE_URL;
import static com.facebook.FacebookSdk.getApplicationContext;

public class LoginActivity2 extends FragmentActivity implements SmartLoginCallbacks, GoogleApiClient.OnConnectionFailedListener{
    private static final String TAG = LoginActivity2.class.getSimpleName();
    SmartLogin smartLogin;
    SmartLoginConfig config;

    private static final int RC_SIGN_IN = 007;
    private GoogleApiClient mGoogleApiClient;
    private ProgressDialog mProgressDialog;
    String loginby;
    Context context;
    public LoginActivity2(){
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(context);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        config = new SmartLoginConfig(this, this);
        config.setFacebookAppId(getString(R.string.facebook_app_id));
        config.setFacebookPermissions(null);
        config.setGoogleApiClient(mGoogleApiClient);

     }
    public  void facebook(){
        VolleyRequestFacebook volleyRequestFacebook = new VolleyRequestFacebook();
        volleyRequestFacebook.setUrl(BASE_URL);
        loginby = "FACEBOOK";
        smartLogin = SmartLoginFactory.build(LoginType.Facebook);
        smartLogin.facebook(config, volleyRequestFacebook);
    }
    public void google(){
        VolleyRequestGoogle volleyRequestGoogle = new VolleyRequestGoogle();
        volleyRequestGoogle.setUrl(BASE_URL);
        loginby = "GOOGLE";
        smartLogin = SmartLoginFactory.build(LoginType.Google);
        smartLogin.google(config, volleyRequestGoogle);
    }


    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            String personName = acct.getDisplayName();
            String email = acct.getEmail();
            Log.e(TAG, "Name: " + personName + ", email: " + email);
            Toast.makeText(this, ""+acct.getDisplayName(), Toast.LENGTH_SHORT).show();
            context.startActivity(new Intent(LoginActivity2.this, HomeActivity.class));
        } else {
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (smartLogin != null) {
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
            showProgressDialog();

            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    hideProgressDialog();
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
            mProgressDialog.setMessage("Loading");
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
        Toast.makeText(this, ""+s, Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onSignupSuccess(String s) {

    }
    @Override
    public void onLoginFailure(SmartLoginException e) {

    }

    @Override
    public Map<String, String> doCustomLogin() {
        return null;
    }

    @Override
    public Map<String, String> doCustomSignup() {
        return null;
    }
    @Override
    public Map<String, String> onFacebookLoginSuccess(SmartFacebookUser smartFacebookUser) {
        /*https://github.com/MindorksOpenSource/android-mvp-architecture*/

        Log.e(TAG, "onFacebookLoginSuccess: email   == :  "+smartFacebookUser.getEmail() );
        HashMap<String, String> params = new HashMap<>();
        params.put("action", "user_sociallogin");
//        params.put("name", ""+smartFacebookUser.getFirstName()+""+smartFacebookUser.getLastName());
        params.put("username", smartFacebookUser.getProfileName());
        params.put("email", smartFacebookUser.getUserId() + "@facebook.com");
        params.put("loginby", "FACEBOOK");
        socialLoginApi(params);
        AppLogger.d(TAG, "onFacebookLoginSuccess: PARAM :  "+params);

        return params;
    }

    @Override
    public Map<String, String> onGoogleLoginSuccess(SmartGoogleUser smartGoogleUser) {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {

                    }
                });
        HashMap<String, String> params = new HashMap<>();
        params.put("action", "user_sociallogin");
//        params.put("name", ""+smartGoogleUser.getFirstName()+""+smartGoogleUser.getLastName());
        params.put("username", smartGoogleUser.getDisplayName());
        params.put("email", smartGoogleUser.getEmail());
        params.put("loginby", "GOOGLE");
        AppLogger.d(TAG, "onGoogleLoginSuccess: PARAM: "+params );

        socialLoginApi(params);
        return params;
    }
    public void socialLoginApi(final HashMap<String, String> params){
        final ProgressDialog progressDialog= CommonUtils.showProgressDialog(context,"loading...");
       StringRequest stringRequest = new StringRequest(Request.Method.POST,BASE_URL ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        AppLogger.d(TAG, "onResponse:  check the " +response);
                        GsonBuilder builder = new GsonBuilder();
                        Gson gson = builder.create();
                        SignupP signupP=gson.fromJson(response, SignupP.class);
                        if(signupP.getResult()==1&&signupP!=null){
                            startActivity(new Intent(context, BirthActivity.class));

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        AppLogger.d("ERROR :  "+error);
                    }
                    }) {
                        @Override
                        protected Map<String, String> getParams() {

                        return params;
                        }
                    };
             stringRequest.setRetryPolicy(new DefaultRetryPolicy(60000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
             RequestQueue requestQueue = Volley.newRequestQueue(this);
             requestQueue.add(stringRequest);
             }
}//end of the LoginActivity class
