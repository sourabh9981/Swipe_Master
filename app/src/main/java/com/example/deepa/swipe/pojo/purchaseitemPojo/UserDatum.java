
package com.swipe.shrinkcom.pojo.purchaseitemPojo;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserDatum implements Serializable
{

    @SerializedName("payment_id")
    @Expose
    private String paymentId;
    @SerializedName("amount")
    @Expose
    private String amount;
    @SerializedName("package_id")
    @Expose
    private String packageId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("transaction_id")
    @Expose
    private String transactionId;
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
    @SerializedName("package_name")
    @Expose
    private String packageName;
    @SerializedName("package_price")
    @Expose
    private String packagePrice;
    @SerializedName("gems")
    @Expose
    private String gems;
    @SerializedName("package_status")
    @Expose
    private String packageStatus;
    @SerializedName("package_created_on")
    @Expose
    private String packageCreatedOn;
    @SerializedName("package_image")
    @Expose
    private String packageImage;
    private final static long serialVersionUID = -633033266466630223L;

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
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

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getPackagePrice() {
        return packagePrice;
    }

    public void setPackagePrice(String packagePrice) {
        this.packagePrice = packagePrice;
    }

    public String getGems() {
        return gems;
    }

    public void setGems(String gems) {
        this.gems = gems;
    }

    public String getPackageStatus() {
        return packageStatus;
    }

    public void setPackageStatus(String packageStatus) {
        this.packageStatus = packageStatus;
    }

    public String getPackageCreatedOn() {
        return packageCreatedOn;
    }

    public void setPackageCreatedOn(String packageCreatedOn) {
        this.packageCreatedOn = packageCreatedOn;
    }

    public String getPackageImage() {
        return packageImage;
    }

    public void setPackageImage(String packageImage) {
        this.packageImage = packageImage;
    }

}
