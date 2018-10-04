package com.swipe.shrinkcom.swipe.database;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
/*
* Developer : Aajesh Choudhary
*
* */
public class SessionManager  {
    SharedPreferences pref;

    // Editor for Shared preferences
    Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "ajju";
    public static final String KEY_NAME = "name";

    // Email address (make variable public to access from outside)
    public static final String KEY_EMAIL = "email";

    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }
    public void setGeneratedSwipeID(String swipeid){
        editor.putString("GeneratedSwipeID", swipeid);
        editor.commit();
    }

    public String getGeneratedSwipeID(){
        return pref.getString("GeneratedSwipeID","");
    }

    public void setUserId(String swipeid){
        editor.putString("USER_ID", swipeid);
        editor.commit();
    }
    public String getUserId(){
        return pref.getString("USER_ID","");
    }



    public void setGender(String gender){
        editor.putString("GENDER", gender);
        editor.commit();
    }
    public String getGender(){
        return pref.getString("GENDER","");
    }




    public void setUserName(String UserName){
        editor.putString("UserName", UserName);
        editor.commit();
    }
    public String getUserName(){
        return pref.getString("UserName","");
    }


    public void setLocation(String Location){
        editor.putString("Location", Location);
        editor.commit();
    }
    public String getLocation(){
        return pref.getString("Location","");
    }


    public void setImage(String Image){
        editor.putString("Image", Image);
        editor.commit();
    }
    public String getImage(){
        return pref.getString("Image","");
    }

    public void setTotalGems(int gemstotal){
        editor.putInt("TotalGems",gemstotal);
        editor.commit();
    }
    public int getTotalGems(){
        return pref.getInt("TotalGems",0);
    }



    public void setSelectedGender(String gemstotal){
        editor.putString("SelectedGender",gemstotal);
        editor.commit();
    }
    public String getSelectedGender(){
        return pref.getString("SelectedGender","");
    }


    public void setSelectedreason(String gemstotal){
        editor.putString("Selectedreason",gemstotal);
        editor.commit();
    }
    public String getSelectedreason(){
        return pref.getString("Selectedreason","");
    }


    public void setCheckedValue(int gemstotal){
        editor.putInt("CheckedValue",gemstotal);
        editor.commit();
    }
    public int getCheckedValue(){
        return pref.getInt("CheckedValue",0);
    }



    public void setFriendId(String gemstotal){
        editor.putString("FriendId",gemstotal);
        editor.commit();
    }
    public String getFriendId(){
        return pref.getString("FriendId","");
    }





    public void SetLogout() {
        editor.clear();
        editor.commit();
    }

    public void setClear() {
        editor.clear();
    }
}//end of the SessionManger Class
