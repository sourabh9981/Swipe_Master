package com.swipe.shrinkcom.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.swipe.shrinkcom.R;
import com.swipe.shrinkcom.database.SessionManager;

public class RegisterSwipeIdActivity extends AppCompatActivity implements View.OnClickListener{

    EditText editTextid;
    ImageView imgcancel;
    Button btnregister;
    Context context;
    SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_swipe_id);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageView imgback = (ImageView)toolbar.findViewById(R.id.idimageback);
        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        session = new SessionManager(this);
        context = this;
        init();

    }//end  of the RegisterSwipeId
    void init(){
        editTextid = (EditText)findViewById(R.id.idedtswipeid);
        imgcancel = (ImageView) findViewById(R.id.idcancel);
        btnregister = (Button) findViewById(R.id.idregister);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.idcancel:
                editTextid.setText("");
                break;
            case R.id.idregister:
                if (editTextid.getText().toString().isEmpty()){
                    editTextid.setError("Enter unique Swipe ID");
                }else {
                    editTextid.setError(null);
                    // TODO: 9/1/2018  call api to to submit swipe uniqueid
                    session.setGeneratedSwipeID(editTextid.getText().toString().trim());
                    Intent intent = new Intent(context,AddSwipeFriend.class);
                    finish();
                    startActivity(intent);
                }
                break;
        }
    }
}//end of the method
