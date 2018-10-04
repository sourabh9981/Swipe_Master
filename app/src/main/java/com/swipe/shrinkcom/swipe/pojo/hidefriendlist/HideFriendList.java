
package com.swipe.shrinkcom.swipe.pojo.hidefriendlist;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class HideFriendList implements Serializable
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
    private final static long serialVersionUID = -3422212159076377154L;

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
