package com.swipe.shrinkcom.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.swipe.shrinkcom.R;

public class ThumpsUpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thumps_up);
        findViewById(R.id.linearThumps).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ThumpsUpActivity.this, ThumpsUpListActivity.class));
            }
        });
    }
}
