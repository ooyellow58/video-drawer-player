package com.ap.stephen.videodrawerplayer.repositories;

import com.ap.stephen.videodrawerplayer.content.VideoItem;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SdCardVideoRepositoryTests {

    @Test
    public void Should_contain_items_and_map_on_lazy_load(){
        // Arrange
        MockSdCardVideoRepository classUnderTest = new MockSdCardVideoRepository();

        // Act
        classUnderTest.randomizeItems();
        List<VideoItem> items = classUnderTest.getItems();
        Map<String, VideoItem> itemMap = classUnderTest.getItemMap();

        // Assert
        Assert.assertNotNull(items);
        Assert.assertNotNull(itemMap);
        Assert.assertEquals(4, items.size());
        Assert.assertEquals(4, itemMap.size());
    }

    @Test
    public void Should_contain_same_items_after_randomize(){
        // Arrange
        MockSdCardVideoRepository classUnderTest = new MockSdCardVideoRepository();

        // Act
        classUnderTest.randomizeItems();
        List<VideoItem> items = classUnderTest.getItems();
        Map<String, VideoItem> itemMap = classUnderTest.getItemMap();

        // Assert
        Assert.assertEquals(4, items.size());
        Assert.assertEquals(4, itemMap.size());
        assertItemsContainsPath(items, "path1");
        assertItemsContainsPath(items, "path2");
        assertItemsContainsPath(items, "path3");
        assertItemsContainsPath(items, "path4");
        Assert.assertTrue(itemMap.containsKey("path1"));
        Assert.assertTrue(itemMap.containsKey("path2"));
        Assert.assertTrue(itemMap.containsKey("path3"));
        Assert.assertTrue(itemMap.containsKey("path4"));
    }

    private void assertItemsContainsPath(List<VideoItem> items, String expectedPath) {
        for (VideoItem item : items) {
            if (expectedPath.equals(item.getPath())) {
                return;
            }
        }
        Assert.fail("did not contain path " + expectedPath);
    }

    @Test
    public void Should_eventually_randomize_first_item(){
        // Arrange
        MockSdCardVideoRepository classUnderTest = new MockSdCardVideoRepository();

        // Act & Assert
        // Try 100 times means .25 ^ 100 = small possibility
        for (int i = 0; i < 100; i++) {
            classUnderTest.randomizeItems();
            VideoItem videoItem = classUnderTest.getItems().get(0);
            if (!"path1".equals(videoItem.getPath())) {
                return;
            }
        }
        Assert.fail("failed to change path from the first path after lots of tries");
    }

    private class MockSdCardVideoRepository extends SdCardVideoRepository {
        public MockSdCardVideoRepository() {
            super();
        }

        @Override
        protected List<VideoItem> loadItemsFromMoviesFolder() {
            ArrayList<VideoItem> items = new ArrayList<>();
            items.add(new VideoItem("name1", "path1", null));
            items.add(new VideoItem("name2", "path2", null));
            items.add(new VideoItem("name3", "path3", null));
            items.add(new VideoItem("name4", "path4", null));
            return items;
        }
    }
}
