package com.swipe.shrinkcom.swipe.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import android.support.v7.widget.AppCompatEditText;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.swipe.shrinkcom.R;
import com.swipe.shrinkcom.swipe.network.ApiCall;
import com.swipe.shrinkcom.swipe.network.ApiClient;
import com.swipe.shrinkcom.swipe.pojo.pojoSinup.SignupP;
import com.swipe.shrinkcom.swipe.utils.CommonUtils;
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


/**
 *  Developer : Aajesh Choudhary
 *
**/

public class LoginSecondActivity extends AppCompatActivity implements View.OnClickListener,SmartLoginCallbacks, GoogleApiClient.OnConnectionFailedListener{
    private static final String TAG = LoginSecondActivity.class.getSimpleName();
    private Button btn_login;
    private LinearLayout linear_google_login, linear_facebook_login;
    private TextView textView_forgot_pwd;
    private Context context;

    SmartLoginConfig config;
    private static final int RC_SIGN_IN = 007;
    private GoogleApiClient mGoogleApiClient;
    private ProgressDialog mProgressDialog;
    String loginby;
    SmartLogin smartLogin;
    AppCompatEditText appcompat_edit_password, appcompat_edit_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_second);

        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        context=this;
        ViewByID();
        textView_forgot_pwd.setOnClickListener(this);
        btn_login.setOnClickListener(this);
        linear_google_login.setOnClickListener(this);
        linear_facebook_login.setOnClickListener(this);

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
    /*==============================end of the onCreate()==============================================*/
    private void ViewByID() {
          textView_forgot_pwd=findViewById(R.id.textView_forgot_pwd);
          btn_login= findViewById(R.id.btn_login);
          linear_google_login= findViewById(R.id.linear_google_login);
          linear_facebook_login= findViewById(R.id.linear_facebook_login);
          appcompat_edit_password= findViewById(R.id.appcompat_edit_password);
          appcompat_edit_email= findViewById(R.id.appcompat_edit_email);
    }
 /*==============================end of the ViewByID()==============================================*/
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.textView_forgot_pwd:
                forgotPAsswordDialoge();
                return;
            case R.id.btn_login:

                if(appcompat_edit_email.getText().toString().isEmpty()){
                    appcompat_edit_email.setError("This is field is required");
                }else if(appcompat_edit_password.getText().toString().isEmpty()){
                    appcompat_edit_password.setError("This is field is required");
                }else{
//                  startActivity(new Intent(LoginSecondActivity.this, HomeActivity.class));
                }
                return;
            case R.id.linear_facebook_login:
                VolleyRequestFacebook volleyRequestFacebook = new VolleyRequestFacebook();
                volleyRequestFacebook.setUrl(BASE_URL);
                loginby = "FACEBOOK";
                smartLogin = SmartLoginFactory.build(LoginType.Facebook);
                smartLogin.facebook(config, volleyRequestFacebook);
                return;
            case R.id.linear_google_login:
                VolleyRequestGoogle volleyRequestGoogle = new VolleyRequestGoogle();
                volleyRequestGoogle.setUrl(BASE_URL);
                loginby = "GOOGLE";
                smartLogin = SmartLoginFactory.build(LoginType.Google);
                smartLogin.google(config, volleyRequestGoogle);
                showProgressDialog();
                return;
        }
    }
/*==============================end of the OnClick()==============================================*/

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (smartLogin != null) {
            Log.d(TAG, "onActivityResult: check the smartlogin" );
            hideProgressDialog();
            smartLogin.onActivityResult(requestCode, resultCode, data, config);
        }
    }
      private void forgotPAsswordDialoge() {
            final Dialog dialog = new Dialog(LoginSecondActivity.this);
            dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
            dialog.setContentView(R.layout.dialoge_forgot);
            dialog.setTitle("Title...");
            TextView dialogButton = dialog.findViewById(R.id.dialogButtonOKGgg);
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
                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }
    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            String personName = acct.getDisplayName();
            String email = acct.getEmail();
            Log.e(TAG, "Name: " + personName + ", email: " + email);
            Toast.makeText(this, ""+acct.getDisplayName(), Toast.LENGTH_SHORT).show();
            startActivity(new Intent(LoginSecondActivity.this, com.swipe.shrinkcom.swipe.activity.HomeActivity.class));
        } else {
        }
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
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    @Override
    public void onLoginSuccess(String s) {
        Toast.makeText(this, ""+s, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSignupSuccess(String s) {

    }
    @Override
    public Map<String, String> onFacebookLoginSuccess(SmartFacebookUser smartFacebookUser) {
        hideProgressDialog();

        /*https://github.com/MindorksOpenSource/android-mvp-architecture*/
        Log.e(TAG, "onFacebookLoginSuccess: email   == :  "+smartFacebookUser.getEmail() );
        HashMap<String, String> params = new HashMap<>();
        params.put("action", "user_sociallogin");
//        params.put("name", ""+smartFacebookUser.getFirstName()+""+smartFacebookUser.getLastName());
        params.put("username", smartFacebookUser.getProfileName());
        params.put("email", smartFacebookUser.getUserId() + "@facebook.com");
        params.put("loginby", "FACEBOOK");
        CallApiSignup(params);
//        socialLoginApi(params);
        Log.d(TAG, "onFacebookLoginSuccess: PARAM :  "+params);
        return params;
    }
    @Override
    public Map<String, String> onGoogleLoginSuccess(SmartGoogleUser smartGoogleUser) {
        hideProgressDialog();
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
        Log.d(TAG, "onGoogleLoginSuccess: PARAM: "+params );

        //socialLoginApi(params);
         CallApiSignup(params);
        return params;
    }
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
                    startActivity(new Intent(context, com.swipe.shrinkcom.swipe.activity.BirthActivity.class).putExtra("userid",s.getUserData().get(0).getUserId()));
                }
            }
            @Override
            public void onFailure(Call<SignupP> call, Throwable t) {

            }
        });
    }/*==========================CallApiSignup()===================================================*/
    @Override
    public void onLoginFailure(SmartLoginException e) {
        Log.e(TAG, "onLoginFailure: smartlogin== :    "+e );

    }
    @Override
    public Map<String, String> doCustomLogin() {
        return null;
    }

    @Override
    public Map<String, String> doCustomSignup() {
        return null;
    }
 /*======end ===================forgotPAsswordDialoge=====================================================*/
}
/*==============================end of the Class LoginSecondActivity================================*/
