
package com.swipe.shrinkcom.swipe.pojo.profilepojo;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProfilePojo implements Serializable
{

    @SerializedName("result")
    @Expose
    private Integer result;
    @SerializedName("userData")
    @Expose
    private List<UserDatum> userData = null;
    private final static long serialVersionUID = -5758238416654248351L;

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

}
