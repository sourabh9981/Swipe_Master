
package com.swipe.shrinkcom.swipe.pojo.interestpojo;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserDatum implements Serializable
{

    @SerializedName("interest_id")
    @Expose
    private String interestId;
    @SerializedName("interest_name")
    @Expose
    private String interestName;
    @SerializedName("interest_user_id")
    @Expose
    private String interestUserId;
    private final static long serialVersionUID = 5986016919188779061L;

    public String getInterestId() {
        return interestId;
    }

    public void setInterestId(String interestId) {
        this.interestId = interestId;
    }

    public String getInterestName() {
        return interestName;
    }

    public void setInterestName(String interestName) {
        this.interestName = interestName;
    }

    public String getInterestUserId() {
        return interestUserId;
    }

    public void setInterestUserId(String interestUserId) {
        this.interestUserId = interestUserId;
    }

}
