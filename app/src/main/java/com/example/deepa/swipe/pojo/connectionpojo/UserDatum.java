
package com.swipe.shrinkcom.pojo.connectionpojo;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserDatum implements Serializable
{

    @SerializedName("con_id")
    @Expose
    private String conId;
    @SerializedName("con_user_id")
    @Expose
    private String conUserId;
    @SerializedName("con_friend_id")
    @Expose
    private String conFriendId;
    @SerializedName("con_status")
    @Expose
    private String conStatus;
    private final static long serialVersionUID = 2918492040341815826L;

    public String getConId() {
        return conId;
    }

    public void setConId(String conId) {
        this.conId = conId;
    }

    public String getConUserId() {
        return conUserId;
    }

    public void setConUserId(String conUserId) {
        this.conUserId = conUserId;
    }

    public String getConFriendId() {
        return conFriendId;
    }

    public void setConFriendId(String conFriendId) {
        this.conFriendId = conFriendId;
    }

    public String getConStatus() {
        return conStatus;
    }

    public void setConStatus(String conStatus) {
        this.conStatus = conStatus;
    }

}
