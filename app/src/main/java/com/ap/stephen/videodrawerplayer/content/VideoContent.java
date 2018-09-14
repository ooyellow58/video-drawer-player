package com.ap.stephen.videodrawerplayer.content;

import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.os.Environment;
import android.provider.MediaStore;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VideoContent {

    public static final List<VideoItem> ITEMS = new ArrayList<>();

    public static final Map<String, VideoItem> ITEM_MAP = new HashMap<>();

    private static void addItem(String name, String videoFilePath) {
        Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(videoFilePath,
                MediaStore.Video.Thumbnails.MINI_KIND);
        VideoItem item = new VideoItem(name, videoFilePath, bitmap);
        addItem(item);
    }

    private static void addItem(VideoItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.path, item);
    }

    public static void loadItemsFromMoviesFolder() {
        String[] externalStorageDirectories = new String[] { Environment.getExternalStorageDirectory().toString(), "/mnt/sdcard2"};
        for (String externalStorageDirectory : externalStorageDirectories) {
            String pathName = externalStorageDirectory + "/movies";
            File file = new File(pathName);
            File list[] = file.listFiles();
            if (list == null) continue;
            for( int i=0; i< list.length; i++)
            {
                addItem(list[i].getName(), pathName + "/" + list[i].getName());
            }
        }
    }

    public static void randomizeItems() {
        List keys = new ArrayList(ITEM_MAP.keySet());
        Collections.shuffle(keys);
        for (Object o : keys) {
            ITEM_MAP.get(o);
        }
    }

    public static class VideoItem {
        public final String name;
        public final String path;
        public final Bitmap bitmap;

        public VideoItem(String name, String path, Bitmap bitmap) {
            this.name = name;
            this.path = path;
            this.bitmap = bitmap;
        }

        @Override
        public String toString() {
            return path;
        }
    }
}
