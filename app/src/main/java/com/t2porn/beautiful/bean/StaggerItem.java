package com.t2porn.beautiful.bean;

/**
 * Created by Kay on 15/4/19.
 */
public class StaggerItem {
    private int imageSource;
    private String description;
    private int collectNum;
    private int thumbImage;
    private String userName;
    private String collectPlace;

    private long ID;

    private String strurl;

    public StaggerItem() {
    }

    public StaggerItem(String strurl, long ID) {
        //item
        this.strurl = strurl;
        this.ID = ID;
    }

    public StaggerItem(int imageSource, String destription, int collectNum, int thumbImage, String userName, String collectPlace) {
        this.imageSource = imageSource;
        this.description = destription;
        this.collectNum = collectNum;
        this.thumbImage = thumbImage;
        this.userName = userName;
        this.collectPlace = collectPlace;
    }

    public StaggerItem(String strurl, String destription, int collectNum, int thumbImage, String userName, String collectPlace) {
        this.strurl = strurl;
        this.description = destription;
        this.collectNum = collectNum;
        this.thumbImage = thumbImage;
        this.userName = userName;
        this.collectPlace = collectPlace;
    }

    public long getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getStrurl() {
        return strurl;
    }

    public void setStrurl(String strurl) {
        this.strurl = strurl;
    }
    public int getImageSource() {
        return imageSource;
    }

    public void setImageSource(int imageSource) {
        this.imageSource = imageSource;
    }

    public String getDestription() {
        return description;
    }

    public void setDestription(String destription) {
        this.description = destription;
    }

    public int getCollectNum() {
        return collectNum;
    }

    public void setCollectNum(int collectNum) {
        this.collectNum = collectNum;
    }

    public int getThumbImage() {
        return thumbImage;
    }

    public void setThumbImage(int thumbImage) {
        this.thumbImage = thumbImage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCollectPlace() {
        return collectPlace;
    }

    public void setCollectPlace(String collectPlace) {
        this.collectPlace = collectPlace;
    }
}
