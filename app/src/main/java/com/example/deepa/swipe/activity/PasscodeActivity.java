package com.swipe.shrinkcom.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.swipe.shrinkcom.R;
import com.swipe.shrinkcom.other.CheckForSDCard;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class PasscodeActivity extends AppCompatActivity implements View.OnClickListener{


    LinearLayout  passcodelayout,changepasscodelayout;
    SwitchCompat passswitch;
    TextView tvpasscode;
    private static final int REQUEST_CODE_ENABLE = 11;
    File apkStorage = null;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_passcode);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;
        passcodelayout = (LinearLayout)findViewById(R.id.idlayoutpasscode);
        changepasscodelayout = (LinearLayout)findViewById(R.id.idlayoutchangepassword);
        passswitch = (SwitchCompat)findViewById(R.id.idswitch);
        tvpasscode = (TextView)findViewById(R.id.idtvchangepasscode);

        passcodelayout.setOnClickListener(this);
        changepasscodelayout.setOnClickListener(this);


        passswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked==true){
                    
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
      //  Intent intent = new Intent(PasscodeActivity.this, CustomPinActivity.class);
      switch (v.getId()){
          case R.id.idlayoutpasscode:

            /*  intent.putExtra(AppLock.EXTRA_TYPE, AppLock.ENABLE_PINLOCK);
              startActivityForResult(intent, REQUEST_CODE_ENABLE);*/
              break;
          case R.id.idlayoutchangepassword:
           /*   intent.putExtra(AppLock.EXTRA_TYPE, AppLock.CHANGE_PIN);
              startActivity(intent);*/
              break;
      }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case REQUEST_CODE_ENABLE:
                passswitch.setChecked(true);
                tvpasscode.setFocusable(true);
                tvpasscode.setEnabled(true);
                tvpasscode.setTextColor(getResources().getColor(R.color.black));
                changepasscodelayout.setClickable(true);
                Toast.makeText(this, "PinCode enabled", Toast.LENGTH_SHORT).show();


                try {
                    if (new CheckForSDCard().isSDCardPresent()) {
                        apkStorage = new File(
                                Environment.getExternalStorageDirectory()+ "/"+ "swipe");
                    } else
                        Toast.makeText(context, "Oops!! There is no SD Card.", Toast.LENGTH_SHORT).show();

                    //If File is not present create directory
                    if (!apkStorage.exists()) {
                        apkStorage.mkdir();
                        Log.e("Passcode", "Directory Created.");
                    }

                    String  strpdffilename = SystemClock.currentThreadTimeMillis()+"swipecode.txt";
                    File outputFile = new File(apkStorage, strpdffilename);

                    if (!outputFile.exists()) {
                        outputFile.createNewFile();
                        Log.e("passcode", "File Created");
                    }

                    FileOutputStream fos = new FileOutputStream(outputFile);//Get OutputStream for NewFile Location

                    //Close all connection after doing task
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
        }
    }
}
