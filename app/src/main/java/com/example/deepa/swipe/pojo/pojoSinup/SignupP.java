
package com.swipe.shrinkcom.pojo.pojoSinup;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class SignupP {

    @SerializedName("message")
    private String mMessage;
    @SerializedName("result")
    private Long mResult;
    @SerializedName("userData")
    private List<UserDatum> mUserData;

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public Long getResult() {
        return mResult;
    }

    public void setResult(Long result) {
        mResult = result;
    }

    public List<UserDatum> getUserData() {
        return mUserData;
    }

    public void setUserData(List<UserDatum> userData) {
        mUserData = userData;
    }

}
