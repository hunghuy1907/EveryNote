package com.hungth.everynote.dto;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v4.widget.DrawerLayout;
import android.widget.Button;


/**
 * Created by Admin on 3/2/2018.
 */

public class AcountDto {
    private String imgIconAcount;
    private String nameAcount;
    private String passWord;

    public AcountDto(String imgIconAcount, String nameAcount, String passWord) {
        this.imgIconAcount = imgIconAcount;
        this.nameAcount = nameAcount;
        this.passWord = passWord;
    }

    public AcountDto(String nameAcount, String passWord) {
        this.nameAcount = nameAcount;
        this.passWord = passWord;
    }

    public String getImgIconAcount() {
        return imgIconAcount;
    }

    public String getNameAcount() {
        return nameAcount;
    }

    public void setNameAcount(String nameAcount) {
        this.nameAcount = nameAcount;
    }

    public String getPassWord() {
        return passWord;
    }
}
