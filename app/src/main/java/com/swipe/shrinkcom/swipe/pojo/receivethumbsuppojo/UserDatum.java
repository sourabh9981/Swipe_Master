
package com.swipe.shrinkcom.swipe.pojo.receivethumbsuppojo;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserDatum implements Serializable
{

    @SerializedName("like_id")
    @Expose
    private String likeId;
    @SerializedName("profile_id")
    @Expose
    private String profileId;
    @SerializedName("like_userid")
    @Expose
    private String likeUserid;
    @SerializedName("like_status")
    @Expose
    private String likeStatus;
    @SerializedName("like_created_on")
    @Expose
    private String likeCreatedOn;
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
    private Object swipeId;
    @SerializedName("mobile")
    @Expose
    private Object mobile;
    @SerializedName("image")
    @Expose
    private Object image;
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
    private final static long serialVersionUID = -3828548896159663562L;

    public String getLikeId() {
        return likeId;
    }

    public void setLikeId(String likeId) {
        this.likeId = likeId;
    }

    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getLikeUserid() {
        return likeUserid;
    }

    public void setLikeUserid(String likeUserid) {
        this.likeUserid = likeUserid;
    }

    public String getLikeStatus() {
        return likeStatus;
    }

    public void setLikeStatus(String likeStatus) {
        this.likeStatus = likeStatus;
    }

    public String getLikeCreatedOn() {
        return likeCreatedOn;
    }

    public void setLikeCreatedOn(String likeCreatedOn) {
        this.likeCreatedOn = likeCreatedOn;
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

    public Object getSwipeId() {
        return swipeId;
    }

    public void setSwipeId(Object swipeId) {
        this.swipeId = swipeId;
    }

    public Object getMobile() {
        return mobile;
    }

    public void setMobile(Object mobile) {
        this.mobile = mobile;
    }

    public Object getImage() {
        return image;
    }

    public void setImage(Object image) {
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
