
package com.swipe.shrinkcom.pojo.packagelist;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserDatum implements Serializable
{

    @SerializedName("package_id")
    @Expose
    private String packageId;
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
    private final static long serialVersionUID = 8484019964674744882L;

    public String getPackageId() {
        return packageId;
    }

    public void setPackageId(String packageId) {
        this.packageId = packageId;
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
