
package com.swipe.shrinkcom.swipe.pojo.sendfriend;

import com.google.gson.annotations.SerializedName;

public class UserDatum {

    @SerializedName("created_on")
    private String mCreatedOn;
    @SerializedName("friend_id")
    private String mFriendId;
    @SerializedName("friend_status")
    private String mFriendStatus;
    @SerializedName("friendlist_id")
    private String mFriendlistId;
    @SerializedName("user_id")
    private String mUserId;

    public String getCreatedOn() {
        return mCreatedOn;
    }

    public void setCreatedOn(String createdOn) {
        mCreatedOn = createdOn;
    }

    public String getFriendId() {
        return mFriendId;
    }

    public void setFriendId(String friendId) {
        mFriendId = friendId;
    }

    public String getFriendStatus() {
        return mFriendStatus;
    }

    public void setFriendStatus(String friendStatus) {
        mFriendStatus = friendStatus;
    }

    public String getFriendlistId() {
        return mFriendlistId;
    }

    public void setFriendlistId(String friendlistId) {
        mFriendlistId = friendlistId;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }

}
