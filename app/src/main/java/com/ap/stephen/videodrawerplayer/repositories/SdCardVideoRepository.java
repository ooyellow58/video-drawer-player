package com.ap.stephen.videodrawerplayer.repositories;

import android.os.Environment;

import com.ap.stephen.videodrawerplayer.content.VideoItem;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SdCardVideoRepository {
    public static SdCardVideoRepository getInstance() {
        return new SdCardVideoRepository();
    }
    private List<VideoItem> items;
    private Map<String, VideoItem> itemMap;

    private Object itemsLoadLock = new Object();
    public List<VideoItem> getItems() {
        if (items == null) {
            synchronized (itemsLoadLock)
            {
                if (items == null) {
                    setItemsAndMapFrom(loadItemsFromMoviesFolder());
                }
            }
        }
        return items;
    }
    private Object itemMapLoadLock = new Object();
    public Map<String, VideoItem> getItemMap() {
        if (itemMap == null) {
            synchronized (itemMapLoadLock) {
                if (itemMap == null) {
                    setItemsAndMapFrom(loadItemsFromMoviesFolder());
                }
            }
        }
        return itemMap;
    }

    protected List<VideoItem> loadItemsFromMoviesFolder() {
        List<VideoItem> itemsLoaded = new ArrayList<>();

        String[] externalStorageDirectories = new String[]{Environment.getExternalStorageDirectory().toString(), "/mnt/sdcard2"};
        for (String externalStorageDirectory : externalStorageDirectories) {
            String pathName = externalStorageDirectory + "/movies";
            File file = new File(pathName);
            File list[] = file.listFiles();
            if (list == null) continue;
            for (int i = 0; i < list.length; i++) {
                VideoItem item = buildItem(list[i].getName(), pathName + "/" + list[i].getName());
                itemsLoaded.add(item);
            }
        }
        return itemsLoaded;
    }

    public void randomizeItems() {
        Collections.shuffle(getItems());
    }

    private static VideoItem buildItem(String name, String videoFilePath) {
        name = name.replace(".mp4", "");
        return new VideoItem(name, videoFilePath);
    }


    private void setItemsAndMapFrom(List<VideoItem> items) {
        this.items = items;
        itemMap = new HashMap<>();
        for (VideoItem item : items) {
            itemMap.put(item.getPath(), item);
        }
    }

    protected SdCardVideoRepository() {
        loadItemsFromMoviesFolder();
    }
}
