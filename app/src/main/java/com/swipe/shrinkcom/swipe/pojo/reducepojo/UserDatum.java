
package com.swipe.shrinkcom.swipe.pojo.reducepojo;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserDatum implements Serializable
{

    @SerializedName("gems_id")
    @Expose
    private String gemsId;
    @SerializedName("no_gems")
    @Expose
    private String noGems;
    @SerializedName("gems_user_id")
    @Expose
    private String gemsUserId;
    @SerializedName("gems_type")
    @Expose
    private String gemsType;
    @SerializedName("payment_id")
    @Expose
    private String paymentId;
    private final static long serialVersionUID = 5424894953679253663L;

    public String getGemsId() {
        return gemsId;
    }

    public void setGemsId(String gemsId) {
        this.gemsId = gemsId;
    }

    public String getNoGems() {
        return noGems;
    }

    public void setNoGems(String noGems) {
        this.noGems = noGems;
    }

    public String getGemsUserId() {
        return gemsUserId;
    }

    public void setGemsUserId(String gemsUserId) {
        this.gemsUserId = gemsUserId;
    }

    public String getGemsType() {
        return gemsType;
    }

    public void setGemsType(String gemsType) {
        this.gemsType = gemsType;
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

}
