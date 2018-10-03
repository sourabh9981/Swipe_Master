package com.swipe.shrinkcom;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;


/**
 * Created by oliviergoutay on 1/14/15.
 */
public class CustomApplication extends MultiDexApplication {

    @SuppressWarnings("unchecked")
    @Override
    public void onCreate() {
        MultiDex.install(this);
        super.onCreate();

       // LockManager<CustomPinActivity> lockManager = LockManager.getInstance();
      //  lockManager.enableAppLock(this, CustomPinActivity.class);
      //  lockManager.getAppLock().setLogoId(R.drawable.security_lock);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //include this method
        MultiDex.install(this);
    }
}
