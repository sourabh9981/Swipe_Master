
package com.swipe.shrinkcom.pojo.historypojo;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserDatum implements Serializable
{

    @SerializedName("con_id")
    @Expose
    private String conId;
    @SerializedName("con_user_id")
    @Expose
    private String conUserId;
    @SerializedName("con_friend_id")
    @Expose
    private String conFriendId;
    @SerializedName("con_status")
    @Expose
    private String conStatus;
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
    private Object dob;
    @SerializedName("gender")
    @Expose
    private Object gender;
    @SerializedName("swipe_id")
    @Expose
    private String swipeId;
    @SerializedName("mobile")
    @Expose
    private Object mobile;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("login_by")
    @Expose
    private String loginBy;
    @SerializedName("address")
    @Expose
    private Object address;
    @SerializedName("created_on")
    @Expose
    private String createdOn;
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
    private Object tokan;
    private final static long serialVersionUID = -8343364124168268049L;

    public String getConId() {
        return conId;
    }

    public void setConId(String conId) {
        this.conId = conId;
    }

    public String getConUserId() {
        return conUserId;
    }

    public void setConUserId(String conUserId) {
        this.conUserId = conUserId;
    }

    public String getConFriendId() {
        return conFriendId;
    }

    public void setConFriendId(String conFriendId) {
        this.conFriendId = conFriendId;
    }

    public String getConStatus() {
        return conStatus;
    }

    public void setConStatus(String conStatus) {
        this.conStatus = conStatus;
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

    public Object getDob() {
        return dob;
    }

    public void setDob(Object dob) {
        this.dob = dob;
    }

    public Object getGender() {
        return gender;
    }

    public void setGender(Object gender) {
        this.gender = gender;
    }

    public String getSwipeId() {
        return swipeId;
    }

    public void setSwipeId(String swipeId) {
        this.swipeId = swipeId;
    }

    public Object getMobile() {
        return mobile;
    }

    public void setMobile(Object mobile) {
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

    public Object getAddress() {
        return address;
    }

    public void setAddress(Object address) {
        this.address = address;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
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

    public Object getTokan() {
        return tokan;
    }

    public void setTokan(Object tokan) {
        this.tokan = tokan;
    }

}
