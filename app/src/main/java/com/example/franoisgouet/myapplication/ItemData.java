package com.example.franoisgouet.myapplication;

import java.io.Serializable;

/**
 * @author françois GOUET
 */

public class ItemData implements Serializable {

    private String videoID;
    private String title;
    private int imageUrl;

    public ItemData(String title,int imageUrl,String videoID){
        this.title = title;
        this.imageUrl = imageUrl;
        this.videoID = videoID;
    }

    public ItemData() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImageUrl(int imageUrl){
        this.imageUrl = imageUrl;
    }

    public String getVideoID() {
        return videoID;
    }

    public void setVideoID(String videoID) {
        this.videoID = videoID;
    }
}