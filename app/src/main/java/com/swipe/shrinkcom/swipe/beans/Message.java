package com.swipe.shrinkcom.swipe.beans;

import java.io.Serializable;

public class Message implements Serializable {

    int friendid;
    int userid;
    String usermessage;
    String  videourl;
String username;
String userimage;
    public Message() {
    }

    public int getFriendid() {
        return friendid;
    }

    public void setFriendid(int friendid) {
        this.friendid = friendid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getUsermessage() {
        return usermessage;
    }

    public void setUsermessage(String usermessage) {
        this.usermessage = usermessage;
    }

    public String getVideourl() {
        return videourl;
    }

    public void setVideourl(String videourl) {
        this.videourl = videourl;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserimage() {
        return userimage;
    }

    public void setUserimage(String userimage) {
        this.userimage = userimage;
    }

    @Override
    public String toString() {
        return "Message{" +
                "friendid=" + friendid +
                ", userid=" + userid +
                ", usermessage='" + usermessage + '\'' +
                ", videourl='" + videourl + '\'' +
                ", username='" + username + '\'' +
                ", userimage='" + userimage + '\'' +
                '}';
    }
}
