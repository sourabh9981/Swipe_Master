
package com.swipe.shrinkcom.pojo.sendfriendlist;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserDatum implements Serializable
{

    @SerializedName("friendlist_id")
    @Expose
    private String friendlistId;
    @SerializedName("frenduserid")
    @Expose
    private String frenduserid;
    @SerializedName("friend_id")
    @Expose
    private String friendId;
    @SerializedName("friend_status")
    @Expose
    private String friendStatus;
    @SerializedName("created_on")
    @Expose
    private String createdOn;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("dob")
    @Expose
    private String dob;
    @SerializedName("gender")
    @Expose
    private String gender;
    @SerializedName("swipe_id")
    @Expose
    private String swipeId;
    @SerializedName("mobile")
    @Expose
    private String mobile;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("login_by")
    @Expose
    private String loginBy;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("password")
    @Expose
    private String password;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("tokan")
    @Expose
    private String tokan;
    private final static long serialVersionUID = -2735598054552516614L;

    public String getFriendlistId() {
        return friendlistId;
    }

    public void setFriendlistId(String friendlistId) {
        this.friendlistId = friendlistId;
    }

    public String getFrenduserid() {
        return frenduserid;
    }

    public void setFrenduserid(String frenduserid) {
        this.frenduserid = frenduserid;
    }

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

    public String getFriendStatus() {
        return friendStatus;
    }

    public void setFriendStatus(String friendStatus) {
        this.friendStatus = friendStatus;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSwipeId() {
        return swipeId;
    }

    public void setSwipeId(String swipeId) {
        this.swipeId = swipeId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getLoginBy() {
        return loginBy;
    }

    public void setLoginBy(String loginBy) {
        this.loginBy = loginBy;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTokan() {
        return tokan;
    }

    public void setTokan(String tokan) {
        this.tokan = tokan;
    }

}
