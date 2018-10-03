
package com.swipe.shrinkcom.pojo.pojoSinup;

import com.google.gson.annotations.SerializedName;


public class UserDatum {

    @SerializedName("address")
    private String mAddress;
    @SerializedName("created_on")
    private String mCreatedOn;
    @SerializedName("dob")
    private Object mDob;
    @SerializedName("email")
    private String mEmail;
    @SerializedName("gender")
    private Object mGender;
    @SerializedName("image")
    private String mImage;
    @SerializedName("login_by")
    private String mLoginBy;
    @SerializedName("mobile")
    private Object mMobile;
    @SerializedName("name")
    private String mName;
    @SerializedName("password")
    private String mPassword;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("swipe_id")
    private Object mSwipeId;
    @SerializedName("tokan")
    private Object mTokan;
    @SerializedName("user_id")
    private String mUserId;
    @SerializedName("username")
    private String mUsername;

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public String getCreatedOn() {
        return mCreatedOn;
    }

    public void setCreatedOn(String createdOn) {
        mCreatedOn = createdOn;
    }

    public Object getDob() {
        return mDob;
    }

    public void setDob(Object dob) {
        mDob = dob;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public Object getGender() {
        return mGender;
    }

    public void setGender(Object gender) {
        mGender = gender;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public String getLoginBy() {
        return mLoginBy;
    }

    public void setLoginBy(String loginBy) {
        mLoginBy = loginBy;
    }

    public Object getMobile() {
        return mMobile;
    }

    public void setMobile(Object mobile) {
        mMobile = mobile;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public Object getSwipeId() {
        return mSwipeId;
    }

    public void setSwipeId(Object swipeId) {
        mSwipeId = swipeId;
    }

    public Object getTokan() {
        return mTokan;
    }

    public void setTokan(Object tokan) {
        mTokan = tokan;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

}
