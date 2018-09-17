package com.ap.stephen.videodrawerplayer.content;

import android.graphics.Bitmap;

public class VideoItem {
    private final String name;
    private final String path;
    private final Bitmap bitmap;

    public VideoItem(String name, String path, Bitmap bitmap) {
        this.name = name;
        this.path = path;
        this.bitmap = bitmap;
    }

    @Override
    public String toString() {
        return path;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }
}
