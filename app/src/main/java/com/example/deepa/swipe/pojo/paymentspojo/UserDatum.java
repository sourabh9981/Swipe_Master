
package com.swipe.shrinkcom.pojo.paymentspojo;

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
    private final static long serialVersionUID = 7775114311669975091L;

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

}
