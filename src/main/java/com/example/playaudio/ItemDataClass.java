package com.example.playaudio;

import android.widget.SeekBar;

public class ItemDataClass {
    private String timer;
    private int imageId;
    private int seekBar;

    public ItemDataClass(String timer, int imageId,int seekBar){
        this.timer = timer;
        this.imageId = imageId;
        this.seekBar = seekBar;
    }   

    public int getSeekBar() {
        return seekBar;
    }

    public void setSeekBar(int seekBar) {
        this.seekBar = seekBar;
    }

    public String getTimer() {
        return timer;
    }

    public void setTimer(String timer) {
        this.timer = timer;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

}
