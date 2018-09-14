package com.ap.stephen.videodrawerplayer;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import com.ap.stephen.videodrawerplayer.content.VideoContent;

/**
 * A fragment representing a single Video detail screen.
 * This fragment is either contained in a {@link VideoListActivity}
 * in two-pane mode (on tablets) or a {@link VideoDetailActivity}
 * on handsets.
 */
public class VideoDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
    public static final String ARG_ITEM_PATH = "item_path";

    /**
     * The dummy content this fragment is presenting.
     */
    private VideoContent.VideoItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public VideoDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_PATH)) {
            mItem = VideoContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_PATH));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.name);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.video_detail, container, false);

        if (mItem != null) {
            VideoView video = rootView.findViewById(R.id.current_video);
            String path = mItem.path;
            PlayLocalVideo(video, path);
        }

        return rootView;
    }
    public void PlayLocalVideo(VideoView video, String path)
    {
        MediaController mediaController = new MediaController(this.getActivity());
        mediaController.setAnchorView(video);
        video.setMediaController(mediaController);
        video.setKeepScreenOn(true);
        video.setVideoPath(path);
        video.start();
        video.requestFocus();
    }
}
