package com.swipe.shrinkcom.swipe.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.swipe.shrinkcom.R;
import com.swipe.shrinkcom.swipe.adapter.InterestAdaptor;
import com.swipe.shrinkcom.swipe.database.SessionManager;
import com.swipe.shrinkcom.swipe.network.ApiCall;
import com.swipe.shrinkcom.swipe.network.ApiClient;
import com.swipe.shrinkcom.swipe.pojo.interestpojo.InterestPojo;
import com.swipe.shrinkcom.swipe.utils.CommonUtils;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;

public class SelectInterestActivity extends AppCompatActivity implements View.OnClickListener{

    ImageView imddance,imgcoffey,imgparty,imgdog,imgcat,imgmusic,imgmobie;
    ImageView imgtrvel,imgsoccet,imgfood,imgshooping,imgfation,imgaimation;

     private static   int intdance = 0, intcoffey =0,intparty =0,intdog=0,intcat=0,intmusic=0,intmvie=0;
     private static   int inttravel=0,intsoccet =0,intfood=0,intshooping=0,intfation=0,intanimation=0;

     Context context;
     LinearLayout linearLayoutaddinterest;
     SessionManager session;
     String strinterestname;
    StringBuilder sb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_interest);

        session = new SessionManager(this);
        sb= new StringBuilder();
        context = this;
         init();

    }

    void  init(){

        linearLayoutaddinterest = (LinearLayout)findViewById(R.id.linearBottom);
        imddance = (ImageView)findViewById(R.id.idimagedance);
        imgcoffey = (ImageView)findViewById(R.id.idimagecoffey);
        imgparty = (ImageView)findViewById(R.id.idimageparty);
        imgdog = (ImageView)findViewById(R.id.idimagedog);
        imgcat = (ImageView)findViewById(R.id.idimagecat);
        imgmusic = (ImageView)findViewById(R.id.idimagemusic);
        imgmobie = (ImageView)findViewById(R.id.idimagmovie);
        imgtrvel = (ImageView)findViewById(R.id.idimagetravel);
        imgsoccet = (ImageView)findViewById(R.id.idimagesoccar);
        imgfood = (ImageView)findViewById(R.id.idimagefood);
        imgshooping = (ImageView)findViewById(R.id.idimageshopping);
        imgfation = (ImageView)findViewById(R.id.idimagefation);
        imgaimation = (ImageView)findViewById(R.id.idimageanimation);

        imddance.setOnClickListener(this);
        imgcoffey.setOnClickListener(this);
        imgparty.setOnClickListener(this);
        imgdog.setOnClickListener(this);
        imgcat.setOnClickListener(this);
        imgmusic.setOnClickListener(this);
        imgmobie.setOnClickListener(this);
        imgtrvel.setOnClickListener(this);
        imgsoccet.setOnClickListener(this);
        imgfood.setOnClickListener(this);
        imgshooping.setOnClickListener(this);
        imgfation.setOnClickListener(this);
        imgaimation.setOnClickListener(this);

        linearLayoutaddinterest.setOnClickListener(this);


        if (intdance==0){
            imddance.setBackgroundResource(R.drawable.circle_fill_colo_pinkr);
        }else {
            imddance.setBackgroundResource(R.drawable.circle_unfil_colo_pinkr);
        }

        if (intcoffey==0){
            imgcoffey.setBackgroundResource(R.drawable.circle_fill_colo_pinkr);
        }else {
            imgcoffey.setBackgroundResource(R.drawable.circle_unfil_colo_pinkr);
        }
        if (intparty==0){
            imgparty.setBackgroundResource(R.drawable.circle_fill_colo_pinkr);
        }else {
            imgparty.setBackgroundResource(R.drawable.circle_unfil_colo_pinkr);

        }
        if (intdog==0){

            imgdog.setBackgroundResource(R.drawable.circle_fill_colo_pinkr);
        }else {
            imgdog.setBackgroundResource(R.drawable.circle_unfil_colo_pinkr);
        }

        if (intcat==0){
            imgcat.setBackgroundResource(R.drawable.circle_fill_colo_pinkr);
        }else {
            imgcat.setBackgroundResource(R.drawable.circle_unfil_colo_pinkr);
        }
        if (intmusic==0){
            imgmusic.setBackgroundResource(R.drawable.circle_fill_colo_pinkr);
        }else {
            imgmusic.setBackgroundResource(R.drawable.circle_unfil_colo_pinkr);
        }
        if (intmvie==0){
           imgmobie.setBackgroundResource(R.drawable.circle_fill_colo_pinkr);
        }else {
            imgmobie.setBackgroundResource(R.drawable.circle_unfil_colo_pinkr);
        }
        if (inttravel==0){
            imgtrvel.setBackgroundResource(R.drawable.circle_fill_colo_pinkr);
        }else {
            imgtrvel.setBackgroundResource(R.drawable.circle_unfil_colo_pinkr);
        }

        if (intsoccet==0){
            imgsoccet.setBackgroundResource(R.drawable.circle_fill_colo_pinkr);
        }else {
            imgsoccet.setBackgroundResource(R.drawable.circle_unfil_colo_pinkr);
        }
        if (intfood==0){
            imgfood.setBackgroundResource(R.drawable.circle_fill_colo_pinkr);
        }else {
            imgfood.setBackgroundResource(R.drawable.circle_unfil_colo_pinkr);
        }


        if (intshooping==0){
            imgshooping.setBackgroundResource(R.drawable.circle_fill_colo_pinkr);
        }else {
            imgshooping.setBackgroundResource(R.drawable.circle_fill_colo_pinkr);
        }

        if (intfation==0){
            imgfation.setBackgroundResource(R.drawable.circle_fill_colo_pinkr);
        }else {
            imgfation.setBackgroundResource(R.drawable.circle_fill_colo_pinkr);
        }


        if (intanimation==0){
            imgaimation.setBackgroundResource(R.drawable.circle_fill_colo_pinkr);
        }else {
            imgaimation.setBackgroundResource(R.drawable.circle_fill_colo_pinkr);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.idimagedance:
                strinterestname = "Dance";
                sb.append("Dance").append(",");
                if (intdance==0){
                    intdance=1;
                    imddance.setBackgroundResource(R.drawable.circle_unfil_colo_pinkr);
                }else {
                    imddance.setBackgroundResource(R.drawable.circle_fill_colo_pinkr);
                    intdance=0;
                }

                break;
            case R.id.idimagecoffey:
                strinterestname = "Coffey";
                sb.append("Coffey").append(",");
                if (intcoffey==0){
                    intcoffey=1;
                    imgcoffey.setBackgroundResource(R.drawable.circle_unfil_colo_pinkr);
                }else {
                    imgcoffey.setBackgroundResource(R.drawable.circle_fill_colo_pinkr);
                    intcoffey=0;

                }

                break;
            case R.id.idimageparty:
                strinterestname = "Party"; sb.append("Party").append(",");

                if (intparty==0){
                    intparty=1;
                    imgparty.setBackgroundResource(R.drawable.circle_unfil_colo_pinkr);
                }else {
                    imgparty.setBackgroundResource(R.drawable.circle_fill_colo_pinkr);
                    intparty=0;

                }
                break;
            case R.id.idimagedog:
                strinterestname = "Dog";sb.append("Dog").append(",");

                if (intdog==0){
                    intdog=1;
                    imgdog.setBackgroundResource(R.drawable.circle_unfil_colo_pinkr);
                }else {
                    imgdog.setBackgroundResource(R.drawable.circle_fill_colo_pinkr);
                    intdog=0;

                }
                break;
            case R.id.idimagecat:
                strinterestname = "Cat";
                sb.append("Cat").append(",");
                if (intcat==0){
                    intcat=1;
                    imgcat.setBackgroundResource(R.drawable.circle_unfil_colo_pinkr);
                }else {
                    imgcat.setBackgroundResource(R.drawable.circle_fill_colo_pinkr);
                    intcat=0;

                }
                break;
            case R.id.idimagemusic:
                sb.append("Music").append(",");
                strinterestname = "Music";
                if (intmusic==0){
                    intmusic=1;
                    imgmusic.setBackgroundResource(R.drawable.circle_unfil_colo_pinkr);
                }else {
                    imgmusic.setBackgroundResource(R.drawable.circle_fill_colo_pinkr);
                    intmusic = 0;
                }
                break;
            case R.id.idimagmovie:
                strinterestname = "Movie";
                sb.append("Movie").append(",");
                if (intmvie==0){
                    intmvie=1;
                    imgmobie.setBackgroundResource(R.drawable.circle_unfil_colo_pinkr);
                }else {
                    imgmobie.setBackgroundResource(R.drawable.circle_fill_colo_pinkr);
                    intmvie=0;
                }
                break;
            case R.id.idimagetravel:
                strinterestname = "Travel";
                sb.append("Travel").append(",");
                if (inttravel==0){
                    inttravel=1;
                    imgtrvel.setBackgroundResource(R.drawable.circle_unfil_colo_pinkr);
                }else {
                    imgtrvel.setBackgroundResource(R.drawable.circle_fill_colo_pinkr);
                    inttravel=0;
                }

                break;
            case R.id.idimagesoccar:
                strinterestname = "Soccer";
                sb.append("Soccer").append(",");
                if (intsoccet==0){
                    intsoccet=1;
                    imgsoccet.setBackgroundResource(R.drawable.circle_unfil_colo_pinkr);
                }else {
                    imgsoccet.setBackgroundResource(R.drawable.circle_fill_colo_pinkr);
                    intsoccet=0;
                }

                break;
            case R.id.idimagefood:
                strinterestname = "Food";
                sb.append("Food").append(",");
                if (intfood==0){
                    intfood=1;
                    imgfood.setBackgroundResource(R.drawable.circle_unfil_colo_pinkr);
                }else {
                    imgfood.setBackgroundResource(R.drawable.circle_fill_colo_pinkr);
                    intfood=0;
                }
                break;
            case R.id.idimageshopping:
                strinterestname = "Shopping";
                sb.append("Shopping").append(",");
                if (intshooping==0){
                    intshooping=1;
                    imgshooping.setBackgroundResource(R.drawable.circle_unfil_colo_pinkr);
                }else {
                    imgshooping.setBackgroundResource(R.drawable.circle_fill_colo_pinkr);
                    intshooping=0;
                }
                break;
            case R.id.idimagefation:
                strinterestname = "fashion";
                sb.append("fashion").append(",");
                if (intfation==0){
                    intfation=1;
                    imgfation.setBackgroundResource(R.drawable.circle_unfil_colo_pinkr);
                }else {
                    imgfation.setBackgroundResource(R.drawable.circle_fill_colo_pinkr);
                    intfation=0;
                }
                break;
            case R.id.idimageanimation:
                strinterestname = "Animation";
                sb.append("Animation");
                if (intanimation==0){
                    intanimation=1;
                    imgaimation.setBackgroundResource(R.drawable.circle_unfil_colo_pinkr);
                }else {
                    imgaimation.setBackgroundResource(R.drawable.circle_fill_colo_pinkr);
                    intanimation=0;
                }
                break;


            case R.id.linearBottom:
                //action=add_interest&user_id=1&interest=playing
                HashMap<String, String> data = new HashMap<>();
                data.put("action","add_interest");
                data.put("user_id",session.getUserId());
                data.put("interest",""+sb);
                AddInterest(data);
                Log.e("SndValuue","---->"+data);
                break;
        }
    }



    private void AddInterest(HashMap<String, String> data){
        final ProgressDialog progressDialog= CommonUtils.showProgressDialog(context,"loading...");
        ApiCall apiCall=  ApiClient.getClient().create(ApiCall.class);
        apiCall.AddInterest(data).enqueue(new Callback<InterestPojo>() {
            @Override
            public void onResponse(Call<InterestPojo> call, retrofit2.Response<InterestPojo> response) {
                int code = response.code();
                Log.e("ADDINTEREST", " CODEEEEE:   " + code);
                if (code == 200) {
                    progressDialog.dismiss();
                    InterestPojo s = response.body();
//                  sessionManager.setUserId(s.getUserData().get(0).getUserId());
                    Log.e("BODYYYYY", "---->" + response.body());
                    if (s.getResult() == 1) {
                        // TODO: 9/12/2018  setAdaptorhere

                        startActivity(new Intent(context,ProfileActivity.class));
                        finish();

                    }
                }
            }

            @Override
            public void onFailure(Call<InterestPojo> call, Throwable t) {
            }

        });
    }

}
