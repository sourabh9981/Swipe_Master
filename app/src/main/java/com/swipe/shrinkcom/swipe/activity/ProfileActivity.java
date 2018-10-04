package com.swipe.shrinkcom.swipe.activity;

import android.Manifest;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.swipe.shrinkcom.R;
import com.swipe.shrinkcom.swipe.adapter.Album;
import com.swipe.shrinkcom.swipe.adapter.AlbumsAdapter;
import com.swipe.shrinkcom.swipe.adapter.InterestAdaptor;
import com.swipe.shrinkcom.swipe.adapter.SlidingImage_Adapter;
import com.swipe.shrinkcom.swipe.database.SessionManager;
import com.swipe.shrinkcom.swipe.network.ApiCall;
import com.swipe.shrinkcom.swipe.network.ApiClient;
import com.swipe.shrinkcom.swipe.other.DialodUtilsImg;
import com.swipe.shrinkcom.swipe.other.Utility;
import com.swipe.shrinkcom.swipe.permissionhelper.PermissionHelper;
import com.swipe.shrinkcom.swipe.pojo.interestpojo.InterestPojo;
import com.swipe.shrinkcom.swipe.pojo.profilepojo.ProfilePojo;
import com.swipe.shrinkcom.swipe.pojo.totalpojo.TotalGems;
import com.swipe.shrinkcom.swipe.utils.CommonUtils;
import com.viewpagerindicator.CirclePageIndicator;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

import static com.swipe.shrinkcom.swipe.api.UtilApi.IMAGELINK;

public class ProfileActivity extends AppCompatActivity {
    private static final String TAG = "ProfileActivity";
    private static ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    private static final Integer[] IMAGES = {R.drawable.placeholder_q, R.drawable.place, R.drawable.place, R.drawable.place};
    private ArrayList<Integer> ImagesArray = new ArrayList<Integer>();
    private AlbumsAdapter adapter;
    private List<Album> albumList;
    Context context;

    ///=======================================\\
    ImageView imageViewprofrofile;
    TextView tvusername,tvlocation,tvswipeid;
    LinearLayout linearpickimage;
    SessionManager session;
    //========================================\\
//======ad image from camera and galary =====//
    String userChoosenTask;
    private int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    byte[] inputData;
    String systemtime;
    File finalFile;
//==========================//


    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        session = new SessionManager(this);
        context = this;

        imageViewprofrofile = (ImageView)findViewById(R.id.idimageviewprofilr);
        tvusername= (TextView)findViewById(R.id.tvusername);
        tvlocation = (TextView)findViewById(R.id.idlocation);
        tvswipeid = (TextView)findViewById(R.id.idswipeid);

        recyclerView = (RecyclerView)findViewById(R.id.idinterestlist);
        linearpickimage = (LinearLayout)findViewById(R.id.idpickimagelayout);


        init();
        findViewById(R.id.fabThumps).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, ThumpsUpActivity.class));
            }
        });
        findViewById(R.id.tvusername).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, EditNickActivity.class).putExtra("username",tvusername.getText().toString()));
                finish();
            }
        });
        findViewById(R.id.fabClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        findViewById(R.id.linearAdd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, SelectInterestActivity.class));
                finish();
            }
        });


        // TODO: 9/18/2018  call api here
        HashMap<String, String> data = new HashMap<>();
        data.put("action","get_profile");
        data.put("user_id",""+session.getUserId());
        Log.e("UPDAREEYE","===>"+data);
        getProfile(data);


        HashMap<String, String> dataint = new HashMap<>();
        dataint.put("action","get_interest");
        dataint.put("user_id",session.getUserId());
        getInterest(dataint);




        linearpickimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(ProfileActivity.this, new String[]{android.Manifest.permission.CAMERA}, 1);
                ActivityCompat.requestPermissions(ProfileActivity.this, new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, 2);

            }
        });

    }

    private void prepareAlbums() {
        int[] covers = new int[]{
                R.drawable.place,
                R.drawable.gps,
                R.drawable.document,
                R.drawable.photo_camera,
                R.drawable.vip,
                R.drawable.billboard,
        };

        Album a = new Album("Unique Location", "Try out unique and fun locations", covers[0]);
        albumList.add(a);

        a = new Album("Regional preference", "Meet people from all over the world", covers[1]);
        albumList.add(a);

        a = new Album("Change Nickname", "Change your nickname any time", covers[2]);
        albumList.add(a);

        a = new Album("Rear camera", "Enjoy your matches using the rear camera", covers[3]);
        albumList.add(a);

        a = new Album("VIP badge", "let your profile photo shine among others", covers[4]);
        albumList.add(a);

        a = new Album("Remove Ads", "Enjoy Swipe withour Ads", covers[5]);
        albumList.add(a);


        adapter.notifyDataSetChanged();
    }

    private void init() {
        for (int i = 0; i < IMAGES.length; i++)
            ImagesArray.add(IMAGES[i]);

        mPager = (ViewPager) findViewById(R.id.pager);
        albumList = new ArrayList<>();
        adapter = new AlbumsAdapter(ProfileActivity.this, albumList);

        prepareAlbums();
        mPager.setAdapter(new SlidingImage_Adapter(ProfileActivity.this, albumList));


        CirclePageIndicator indicator = (CirclePageIndicator)
                findViewById(R.id.indicator);

        indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;

//Set circle indicator radius
        indicator.setRadius(5 * density);

        NUM_PAGES = IMAGES.length;

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);

        //Pager listener over indicator

        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;

            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });

    }

    @Override
    protected void onRestart() {
        super.onRestart();
       /* HashMap<String, String> data = new HashMap<>();
        data.put("action","get_profile");
        data.put("user_id",""+session.getUserId());
        Log.e(TAG, " sendvalueee:   " + data);
        getProfile(data);*/

    }

    private void getProfile(HashMap<String, String> data){
        final ProgressDialog progressDialog= CommonUtils.showProgressDialog(context,"loading...");
        ApiCall apiCall=  ApiClient.getClient().create(ApiCall.class);
        apiCall.Profile(data).enqueue(new Callback<ProfilePojo>() {
            @Override
            public void onResponse(Call<ProfilePojo> call, retrofit2.Response<ProfilePojo> response) {
                int code = response.code();
                Log.e(TAG, " CODEEEEE:   " + code);
                if (code == 200) {
                    progressDialog.dismiss();
                    ProfilePojo s = response.body();
//                  sessionManager.setUserId(s.getUserData().get(0).getUserId());
                    Log.e("BODYYYYY", "---->" + response.body());
                    if (s.getResult() == 1) {
                        // TODO: 9/12/2018  setAdaptorhere

                        String strimage = s.getUserData().get(0).getImage();
                        Log.e("IMAGELINKKK", "---->" +strimage);
                        try {
                            if (strimage.equals("images/")){
                                if (s.getUserData().get(0).getGender().equals("MALE"))
                                {
                                imageViewprofrofile.setImageResource(R.drawable.profile);
                                }else {
                                    imageViewprofrofile.setImageResource(R.drawable.profile_f);
                                }
                            }else {
                                Glide.with(context)
                                        .load(IMAGELINK+strimage.replaceAll(" ","%20"))
                                        .into(imageViewprofrofile);
                                session.setImage(strimage);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        Log.e("username","----->"+s.getUserData().get(0).getUsername());
                        tvlocation.setText(s.getUserData().get(0).getAddress());
                        tvusername.setText(s.getUserData().get(0).getUsername());
                        tvswipeid.setText(s.getUserData().get(0).getSwipeId());

                        session.setLocation(s.getUserData().get(0).getAddress());
                        session.setUserName(s.getUserData().get(0).getUsername());
                        session.setGeneratedSwipeID(s.getUserData().get(0).getSwipeId());

                    }
                }
            }

            @Override
            public void onFailure(Call<ProfilePojo> call, Throwable t) {
            }

        });
    }


    // TODO: 9/18/2018 get image from camera and galary here


    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    DialodUtilsImg.openDialogpickimg(ProfileActivity.this);
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    ActivityCompat.requestPermissions(ProfileActivity.this, new String[]{android.Manifest.permission.CAMERA}, 1);
                    ActivityCompat.requestPermissions(ProfileActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2);
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case DialodUtilsImg.REQUEST_CAMERA:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();
                    onCaptureImageResult(data);

                    Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                    Log.e("bbbbbbb", "----" + thumbnail);

                 /*   ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    thumbnail.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    inputData = stream.toByteArray();*/


                }

                break;
            case DialodUtilsImg.SELECT_FILE:
                if (resultCode == RESULT_OK) {
                    Uri selectedImage = data.getData();

                    try {
                        imageViewprofrofile.setImageURI(selectedImage);
                        imageViewprofrofile.invalidate();
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                        Log.e("fffffffff", "---" + bitmap);

                        Uri tempUri = getImageUri(getApplicationContext(), bitmap);
                        finalFile = new File(getRealPathFromURI(tempUri));
                        Log.e("FILEEEE", "---" + finalFile);
                        postImage();

                    } catch (IOException e) {
                        e.printStackTrace();Log.e("ERRORRRR","---->"+e);
                    }


                }
                break;
        }
    }



    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



        imageViewprofrofile.setImageBitmap(Bitmap.createScaledBitmap(thumbnail, 120, 120, false));

        imageViewprofrofile.invalidate();

        Uri tempUri = getImageUri(getApplicationContext(), thumbnail);
        finalFile = new File(getRealPathFromURI(tempUri));

        postImage();

    }






    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, System.currentTimeMillis()+"Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        String path = "";
        if (getContentResolver() != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }






    private void postImage(){

       // File file = new File(finalFile);
        Log.e("FILEEEEsend", "---" + finalFile);
        RequestBody reqFile = RequestBody.create(MediaType.parse("multipart/form-data"), finalFile);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image",finalFile.getName(), reqFile);
        RequestBody action = RequestBody.create(MediaType.parse("multipart/form-data"), "updateprofile_pic");
        RequestBody user_id = RequestBody.create(MediaType.parse("multipart/form-data"), session.getUserId());


        final ProgressDialog progressDialog= CommonUtils.showProgressDialog(context,"loading...");
        ApiCall apiCall=  ApiClient.getClient().create(ApiCall.class);
        apiCall.postImage(body,action,user_id).enqueue(new Callback<ProfilePojo>() {
            @Override
            public void onResponse(Call<ProfilePojo> call, retrofit2.Response<ProfilePojo> response) {
                int code = response.code();
                Log.e(TAG, " CODEEEEE:   " + code);
                if (code == 200) {
                    progressDialog.dismiss();
                    ProfilePojo s = response.body();
//                  sessionManager.setUserId(s.getUserData().get(0).getUserId());
                    Log.e("BODYYYYY", "---->" + response.body());
                    if (s.getResult() == 1) {
                        // TODO: 9/12/2018  setAdaptorhere

                        String strimage = s.getUserData().get(0).getImage();
                        Log.e("IMAGESSS","--->"+s.getUserData().get(0).getImage());
                        session.setImage(strimage);
                        if (strimage.equals("images/")){
                            if (s.getUserData().get(0).getGender().equals("MALE")) {
                                imageViewprofrofile.setImageResource(R.drawable.profile);
                            }else {
                                imageViewprofrofile.setImageResource(R.drawable.profile_f);
                            }
                        }else {
                            Glide.with(context)
                                    .load(IMAGELINK+strimage.replaceAll(" ","%20"))
                                    .into(imageViewprofrofile);
                            session.setImage(strimage);
                        }

                        tvlocation.setText(s.getUserData().get(0).getAddress());
                        tvusername.setText(s.getUserData().get(0).getUsername());
                        tvswipeid.setText(s.getUserData().get(0).getSwipeId());

                        session.setLocation(s.getUserData().get(0).getAddress());
                        session.setUserName(s.getUserData().get(0).getUsername());
                        session.setGeneratedSwipeID(s.getUserData().get(0).getSwipeId());

                    }
                }
            }

            @Override
            public void onFailure(Call<ProfilePojo> call, Throwable t) {
            }

        });
    }


    // TODO: 9/21/2018  getInterest here

    private void getInterest(HashMap<String, String> data){
        final ProgressDialog progressDialog= CommonUtils.showProgressDialog(context,"loading...");
        ApiCall apiCall=  ApiClient.getClient().create(ApiCall.class);
        apiCall.getInterest(data).enqueue(new Callback<InterestPojo>() {
            @Override
            public void onResponse(Call<InterestPojo> call, retrofit2.Response<InterestPojo> response) {
                int code = response.code();
                Log.e(TAG, " CODEEEEE:   " + code);
                if (code == 200) {
                    progressDialog.dismiss();
                    InterestPojo s = response.body();
//                  sessionManager.setUserId(s.getUserData().get(0).getUserId());
                    Log.e("BODYYYYY", "---->" + response.body());
                    if (s.getResult() == 1) {
                        // TODO: 9/12/2018  setAdaptorhere
                        recyclerView.setLayoutManager(new GridLayoutManager(context, 4));
                        InterestAdaptor  interestAdaptor = new InterestAdaptor(context,s);
                        recyclerView.setAdapter(interestAdaptor);
                        adapter.notifyDataSetChanged();


                    }
                }
            }

            @Override
            public void onFailure(Call<InterestPojo> call, Throwable t) {
            }

        });
    }

}
