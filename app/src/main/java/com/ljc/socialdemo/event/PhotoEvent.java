package com.ljc.socialdemo.event;

/**
 * Created by 18075 on 2018/11/9.
 */

public class PhotoEvent {
    private String photoPath;

    public String getPhotoPath() {
        return photoPath;
    }

    public PhotoEvent(String photoPath) {
        this.photoPath = photoPath;
    }

}
