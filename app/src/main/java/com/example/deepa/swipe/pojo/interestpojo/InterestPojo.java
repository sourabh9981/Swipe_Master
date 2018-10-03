
package com.swipe.shrinkcom.pojo.interestpojo;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InterestPojo implements Serializable
{

    @SerializedName("result")
    @Expose
    private Integer result;
    @SerializedName("userData")
    @Expose
    private List<UserDatum> userData = null;
    @SerializedName("message")
    @Expose
    private String message;
    private final static long serialVersionUID = 986123564173622389L;

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public List<UserDatum> getUserData() {
        return userData;
    }

    public void setUserData(List<UserDatum> userData) {
        this.userData = userData;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
