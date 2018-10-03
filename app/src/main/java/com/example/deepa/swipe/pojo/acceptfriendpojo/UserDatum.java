
package com.swipe.shrinkcom.pojo.acceptfriendpojo;

import java.io.Serializable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserDatum implements Serializable
{

    @SerializedName("friendlist_id")
    @Expose
    private String friendlistId;
    @SerializedName("user_id")
    @Expose
    private String userId;
    @SerializedName("friend_id")
    @Expose
    private String friendId;
    @SerializedName("friend_status")
    @Expose
    private String friendStatus;
    @SerializedName("created_on")
    @Expose
    private String createdOn;
    private final static long serialVersionUID = -7123352396832323707L;

    public String getFriendlistId() {
        return friendlistId;
    }

    public void setFriendlistId(String friendlistId) {
        this.friendlistId = friendlistId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

    public String getFriendStatus() {
        return friendStatus;
    }

    public void setFriendStatus(String friendStatus) {
        this.friendStatus = friendStatus;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

}
