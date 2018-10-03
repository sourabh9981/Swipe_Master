
package com.swipe.shrinkcom.pojo.totalpojo;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TotalGems implements Serializable
{

    @SerializedName("result")
    @Expose
    private Integer result;
    @SerializedName("userData")
    @Expose
    private String userData;
    @SerializedName("message")
    @Expose
    private String message;
    private final static long serialVersionUID = 638337171461853268L;

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getUserData() {
        return userData;
    }

    public void setUserData(String userData) {
        this.userData = userData;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
