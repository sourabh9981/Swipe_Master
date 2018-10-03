package com.swipe.shrinkcom.fragment;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.hardware.Camera;
import android.media.effect.Effect;
import android.media.effect.EffectContext;
import android.media.effect.EffectFactory;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.GLUtils;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.swipe.shrinkcom.R;
import com.swipe.shrinkcom.Utilities.GLToolbox;
import com.swipe.shrinkcom.Utilities.RecyclerItemClickListener;
import com.swipe.shrinkcom.Utilities.TextureRenderer;
import com.swipe.shrinkcom.activity.DisplayFilteruserActivity;
import com.swipe.shrinkcom.activity.FilterActivity;
import com.swipe.shrinkcom.activity.MyGemdsActivity;
import com.swipe.shrinkcom.activity.PaymentsAppActivity;
import com.swipe.shrinkcom.adapter.FilterAdapterFactory;
import com.swipe.shrinkcom.adapter.SendThumbsupAdaptor;
import com.swipe.shrinkcom.database.SessionManager;
import com.swipe.shrinkcom.network.ApiCall;
import com.swipe.shrinkcom.network.ApiClient;
import com.swipe.shrinkcom.other.OnSwipeTouchListener;
import com.swipe.shrinkcom.pojo.receivethumbsuppojo.ReceiveThumbsupPojo;
import com.swipe.shrinkcom.pojo.reducepojo.ReduceGemsPojo;
import com.swipe.shrinkcom.pojo.totalpojo.TotalGems;
import com.swipe.shrinkcom.utils.CommonUtils;
import com.swipe.shrinkcom.webrtc.ConnectActivity;

import java.util.HashMap;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import retrofit2.Call;
import retrofit2.Callback;


public class DiscoverFragment extends Fragment implements SurfaceHolder.Callback, View.OnClickListener {
    private static final String TAG = "DiscoverFragment";
    Context context;
    SurfaceView surfaceView;
    SurfaceHolder surfaceHolder;
    Camera camera;
    LinearLayout linearGender, linearGenderBo, linearRegionBo, linearRegion, linearGems;
    FloatingActionButton favChange;

    public DiscoverFragment() {
        // Required empty public constructor
    }



    //====================================\\

    private RecyclerView recList;
    int mCurrentEffect;
    private GLSurfaceView mEffectView;
    private int[] mTextures = new int[2];
    private EffectContext mEffectContext;
    private Effect mEffect;
    private TextureRenderer mTexRenderer = new TextureRenderer();
    private int mImageWidth;
    private int mImageHeight;
    private boolean mInitialized = false;
    private volatile boolean saveFrame;
    LinearLayout layoutfilter;
      SessionManager session;
      TextView tvtotalshowgems;
    public void setCurrentEffect(int effect) {
        mCurrentEffect = effect;
    }
    //====================================\\


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        context = getContext();
        View view = inflater.inflate(R.layout.fragment_discover, container, false);
        // Inflate the layout for this fragment
        session = new SessionManager(getActivity());
        setUpUI(view);



        showToast(view);

        surfaceView.setOnTouchListener(new OnSwipeTouchListener(getActivity()) {
            public void onSwipeTop() {
                //Toast.makeText(getActivity(), "top", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(),DisplayFilteruserActivity.class);
                startActivity(intent);
            }
            public void onSwipeRight() {
                //Toast.makeText(getActivity(), "right", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeLeft() {
                // Toast.makeText(getActivity(), "left", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(),DisplayFilteruserActivity.class);
                startActivity(intent);

            }
            public void onSwipeBottom() {
                //Toast.makeText(getActivity(), "bottom", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity(),DisplayFilteruserActivity.class);
                startActivity(intent);
            }

        });






        //=================end imageiffect=====================





        return view;
    }

    private void setUpUI(View view) {
        linearRegionBo = view.findViewById(R.id.linearRegionBo);
        linearGenderBo = view.findViewById(R.id.linearGenderBo);
        linearGender = view.findViewById(R.id.linearGender);
        linearRegion = view.findViewById(R.id.linearRegion);
        linearGems = view.findViewById(R.id.linearGems);
        favChange = view.findViewById(R.id.favChange);
        layoutfilter = view.findViewById(R.id.idfiltercamera);
        linearGender.setOnClickListener(this);
        linearRegion.setOnClickListener(this);
        linearGems.setOnClickListener(this);
        linearRegionBo.setOnClickListener(this);
        linearGenderBo.setOnClickListener(this);
        favChange.setOnClickListener(this);
        layoutfilter.setOnClickListener(this);


        surfaceView = (SurfaceView) view.findViewById(R.id.surfaceView);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        tvtotalshowgems = (TextView)view.findViewById(R.id.idtotalshowgems);
        tvtotalshowgems.setText(""+session.getTotalGems());

    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        // Now that the size is known, set up the camera parameters and begin
        // the preview.
        refreshCamera();
    }

    public void surfaceCreated(SurfaceHolder holder) {
        try {
            // open the camera
            camera = Camera.open(1);
        } catch (RuntimeException e) {
            // check for exceptions
            System.err.println(e);
            return;
        }
        Camera.Parameters param;
        param = camera.getParameters();

        // modify parameter
        param.setPreviewSize(352, 288);
        camera.setParameters(param);
        try {
            // The Surface has been created, now tell the camera where to draw
            // the preview.
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
            camera.setDisplayOrientation(90);
        } catch (Exception e) {
            // check for exceptions
            System.err.println(e);
            return;
        }
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        // stop preview and release camera
        try {
            camera.stopPreview();
            camera.release();
            camera = null;
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void refreshCamera() {
        if (surfaceHolder.getSurface() == null) {
            // preview surface does not exist
            return;
        }
        // stop preview before making changes
        try {
            camera.stopPreview();
        } catch (Exception e) {
            // ignore: tried to stop a non-existent preview
        }

        // set preview size and make any resize, rotate or
        // reformatting changes here
        // start preview with new settings
        try {
            camera.setPreviewDisplay(surfaceHolder);
            camera.startPreview();
        } catch (Exception e) {

        }
    }

    private Camera openFrontFacingCamera() {
        int cameraCount = 0;
        Camera cam = null;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        cameraCount = Camera.getNumberOfCameras();
        for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
            Camera.getCameraInfo(camIdx, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                try {
                    cam = Camera.open(camIdx);
                } catch (RuntimeException e) {
                    Log.e(TAG, "Camera failed to open: " + e.getLocalizedMessage());
                }
            }
        }

        return cam;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.linearGender:
                GenderDialogue();
                break;
            case R.id.linearRegion:
                RegionDialige();
                break;
            case R.id.linearGems:
                startActivity(new Intent(context, MyGemdsActivity.class));
                break;
            case R.id.linearGenderBo:
                GenderDialogue();
                break;
            case R.id.linearRegionBo:
                RegionDialige();
                break;
            case R.id.favChange:
                CHangeDialige();
                break;
                case R.id.idfiltercamera:

                    startActivity(new Intent(getActivity(), FilterActivity.class));
                break;
        }
    }

    private void CHangeDialige() {
        final Dialog dialog = new Dialog(context);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.dialoge_vip);
        // dialog.setTitle("Title...");,
        // set the custom dialog components - text, image and button
       /* TextView text = (TextView) dialog.findViewById(R.id.text);
        text.setText("Android custom dialog example!");
        ImageView image = (ImageView) dialog.findViewById(R.id.image);
        image.setImageResource(R.drawable.ic_launcher);*/

        ImageView imagecancel = dialog.findViewById(R.id.imagecancel);
        TextView aloowewr = dialog.findViewById(R.id.aloowewr);
      //  LinearLayout linearpayment = dialog.findViewById(R.id.idpaymentt);
      /*  LinearLayout linearIndia = dialog.findViewById(R.id.linearIndia);
        LinearLayout linearBalanced = dialog.findViewById(R.id.linearBalanced);
        RadioButton radioButtonBalanced = dialog.findViewById(R.id.radioButtonBalanced);
*/
        imagecancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        aloowewr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context, PaymentsAppActivity.class).putExtra("amount","999").putExtra("id",session.getUserId()));
                dialog.dismiss();
            }
        });

        dialog.show();

    }


    private void RegionDialige() {

        final Dialog dialog = new Dialog(context);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.dialoge_regional);
        // dialog.setTitle("Title...");,

        final RadioGroup radioGroup1 = dialog.findViewById(R.id.radioGroup);

        selectFirstVisibleRadioButton(radioGroup1);

        TextView dialogButton = dialog.findViewById(R.id.dialogButtonOK);

        RadioButton radioButtonBalanced = dialog.findViewById(R.id.radioButtonBalanced);

        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroup1.getCheckedRadioButtonId();
                final RadioButton  radioBoth = (RadioButton) dialog.findViewById(selectedId);

                session.setCheckedValue(selectedId);
                String strgendertype =  radioBoth.getText().toString();

                session.setSelectedreason(strgendertype);
                ShowDiloage(strgendertype,"reason");

                dialog.dismiss();
            }
        });

        dialog.show();
    }


    void selectFirstVisibleRadioButton(RadioGroup radioGroup) {

        int childCount = radioGroup.getChildCount();

        for (int i = 0; i < childCount; i++) {
            RadioButton rButton = (RadioButton) radioGroup.getChildAt(i);

            if (session.getCheckedValue()==rButton.getId()) {
                rButton.setChecked(true);
                return;
            }

        }

    }

    private void GenderDialogue() {
        final Dialog dialog = new Dialog(context);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.dialoge_gender);
        dialog.setTitle("Title...");

        final RadioGroup radioGroup1 = dialog.findViewById(R.id.radioGroup1);


        selectFirstVisibleRadioButton(radioGroup1);

        TextView dialogButton = dialog.findViewById(R.id.dialogButtonOKG);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroup1.getCheckedRadioButtonId();
                // find the radiobutton by returned id
                final RadioButton  radioBoth = (RadioButton) dialog.findViewById(selectedId);

                session.setCheckedValue(selectedId);

               String strgendertype =  radioBoth.getText().toString();
               session.setSelectedGender(strgendertype);
               if (strgendertype.equals("Both")){
                   dialog.dismiss();
               }else {
                   ShowDiloage(strgendertype,"gender");
                   dialog.dismiss();
               }
            }
        });

        dialog.show();


    }

    private void ShowDiloage(String genderType,String showtype) {
        final Dialog dialog = new Dialog(context);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        dialog.setContentView(R.layout.dialoge_purchase_gems);
        dialog.setTitle("Title...");

        TextView textViewgemsmessage = dialog.findViewById(R.id.idgemsmessage);
        TextView dialogButton = dialog.findViewById(R.id.tvPurchase);
        TextView tvCancel = dialog.findViewById(R.id.tvCancel);

        if (showtype.equals("gender")){
            if (session.getTotalGems()>9){
                dialogButton.setText("Continue");
            }else {
                dialogButton.setText("Purchase");
            }
            textViewgemsmessage.setText("9 "+getResources().getString(R.string.gemspurchasemessage)+" gender filter is turned on.");
        }
        if (showtype.equals("reason")){
            if (session.getTotalGems()>20){
                dialogButton.setText("Continue");
            }else {
                dialogButton.setText("Purchase");
            }
            textViewgemsmessage.setText("20 "+getResources().getString(R.string.gemspurchasemessage)+" Country filter is turned on.");
        }







        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (showtype.equals("gender")){
                    if (session.getTotalGems()>9){
                        // TODO: 9/20/2018  call api here diduction api
                        HashMap<String, String> data = new HashMap<>();
                        data.put("action","deduct_gems");
                        data.put("user_id",""+session.getUserId());
                        data.put("gems","9");

                        Log.e("SENDVALUSEE","----->"+data);
                        Reducegems(data);

                    }else {
                        startActivity(new Intent(context,MyGemdsActivity.class));
                    }
                }
                if (showtype.equals("reason")) {
                        if (session.getTotalGems() > 20) {
                            // TODO: 9/20/2018  call api here diduction api
                            HashMap<String, String> data = new HashMap<>();
                            data.put("action","deduct_gems");
                            data.put("user_id",""+session.getUserId());
                            data.put("gems","20");
                            Log.e("SENDVALUSEE","----->"+data);
                            Reducegems(data);
                        } else {
                            startActivity(new Intent(context,MyGemdsActivity.class));
                        }
                }

                dialog.dismiss();
            }
        });
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();

    }

    // TODO: 9/20/2018   call method to diduct gems 
    private void Reducegems(HashMap<String, String> data){
        final ProgressDialog progressDialog= CommonUtils.showProgressDialog(getActivity(),"loading...");
        ApiCall apiCall=  ApiClient.getClient().create(ApiCall.class);
        apiCall.ReduceGems(data).enqueue(new Callback<ReduceGemsPojo>() {
            @Override
            public void onResponse(Call<ReduceGemsPojo> call, retrofit2.Response<ReduceGemsPojo> response) {
                int code = response.code();
                Log.e(TAG, " CODEEEEE:   " + code);
                if (code == 200) {
                    progressDialog.dismiss();
                    ReduceGemsPojo s = response.body();
//                  sessionManager.setUserId(s.getUserData().get(0).getUserId());
                    Log.e("BODYYYYY", "---->" + s.getResult());
                    if (s.getResult()==1){
                        // TODO: 9/12/2018  setAdaptorhere
                        HashMap<String, String> datagems = new HashMap<>();
                        datagems.put("action","get_total_gems");
                        datagems.put("user_id",""+session.getUserId());
                        Totalgems(datagems);


                    }

                }
            }
            @Override
            public void onFailure(Call<ReduceGemsPojo> call, Throwable t) {

            }
        });
    }


    private void Totalgems(HashMap<String, String> data){
        final ProgressDialog progressDialog= CommonUtils.showProgressDialog(context,"loading...");
        ApiCall apiCall=  ApiClient.getClient().create(ApiCall.class);
        apiCall.TotalGems(data).enqueue(new Callback<TotalGems>() {
            @Override
            public void onResponse(Call<TotalGems> call, retrofit2.Response<TotalGems> response) {
                int code = response.code();

                if (code == 200) {
                       progressDialog.dismiss();
                    TotalGems s = response.body();
//                  sessionManager.setUserId(s.getUserData().get(0).getUserId());
                    Log.e("BODYYYYY", "---->" + response.body());
                    if (s.getResult() == 1) {
                        // TODO: 9/12/2018  setAdaptorhere
                        Log.e("sizeeee", "---->" + s.getUserData());
                        String strtotalgems =   s.getUserData();
                        Log.e("Totalgems","---->"+strtotalgems);
                        session.setTotalGems(Integer.parseInt(s.getUserData()));
                        tvtotalshowgems.setText(""+session.getTotalGems());

                    }
                }
            }

            @Override
            public void onFailure(Call<TotalGems> call, Throwable t) {
            }

        });
    }


    // TODO: 9/25/2018  set duration on toast
    public  void showToast(View view) {
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
