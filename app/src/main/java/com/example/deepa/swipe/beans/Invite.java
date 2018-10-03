package com.swipe.shrinkcom.beans;

import android.graphics.Bitmap;

import java.io.Serializable;

public class Invite implements Serializable {
    String name;
    Bitmap thumb;
    String phone;
    Boolean checkedBox = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getThumb() {
        return thumb;
    }

    public void setThumb(Bitmap thumb) {
        this.thumb = thumb;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getCheckedBox() {
        return checkedBox;
    }

    public void setCheckedBox(Boolean checkedBox) {
        this.checkedBox = checkedBox;
    }

    @Override
    public String toString() {
        return "Invite{" +
                "name='" + name + '\'' +
                ", thumb=" + thumb +
                ", phone='" + phone + '\'' +
                ", checkedBox=" + checkedBox +
                '}';
    }
}
