package com.ap.stephen.videodrawerplayer.content;

import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.provider.MediaStore;

import java.util.HashMap;

public class VideoItem {
    private final String name;
    private final String path;
    private final static HashMap<String, Bitmap> BitmapCache = new HashMap<>();

    public VideoItem(String name, String path) {
        this.name = name;
        this.path = path;
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
        return buildBitmap();
    }

    private Bitmap buildBitmap() {
        String path = getPath();
        if (!BitmapCache.containsKey(path)) {
            Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(getPath(),
                    MediaStore.Video.Thumbnails.MICRO_KIND);
            BitmapCache.put(path, bitmap);
        }
        return BitmapCache.get(path);
    }
}
