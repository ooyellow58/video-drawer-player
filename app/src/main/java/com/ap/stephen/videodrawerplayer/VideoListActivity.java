package com.ap.stephen.videodrawerplayer;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ap.stephen.videodrawerplayer.content.VideoContent;

import java.util.List;

/**
 * An activity representing a list of Videos. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link VideoDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class VideoListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.red)));


        if (findViewById(R.id.video_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }

        VideoContent.loadItemsFromMoviesFolder();
        setupRecyclerView();
    }

    private void setupRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.video_list);
        assert recyclerView != null;
        //VideoContent.randomizeItems();
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, VideoContent.getItems(), mTwoPane));
    }

    private static boolean isVideoLoading;

    public static class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final VideoListActivity mParentActivity;
        private final List<VideoContent.VideoItem> mValues;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isVideoLoading) {
                    return;
                }
                isVideoLoading = true;
                //mParentActivity.setupRecyclerView();
                VideoContent.VideoItem item = (VideoContent.VideoItem) view.getTag();
                if (mTwoPane) {
                    Bundle arguments = new Bundle();
                    arguments.putString(VideoDetailFragment.ARG_ITEM_PATH, item.path);
                    VideoDetailFragment fragment = new VideoDetailFragment();
                    fragment.setArguments(arguments);
                    mParentActivity.getSupportFragmentManager().beginTransaction()
                            .replace(R.id.video_detail_container, fragment)
                            .commit();
                    isVideoLoading = false;
                } else {
                    Context context = view.getContext();
                    Intent intent = new Intent(context, VideoDetailActivity.class);
                    intent.putExtra(VideoDetailFragment.ARG_ITEM_PATH, item.path);

                    context.startActivity(intent);
                    isVideoLoading = false;
                }
            }
        };

        SimpleItemRecyclerViewAdapter(VideoListActivity parent,
                                      List<VideoContent.VideoItem> items,
                                      boolean twoPane) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.video_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            VideoContent.VideoItem item = mValues.get(position);
            // TODO make the below an option
//            holder.mIdView.setText(item.name);
            holder.mContentView.setImageBitmap(item.bitmap);

            holder.itemView.setTag(item);
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mIdView;
            final ImageView mContentView;

            ViewHolder(View view) {
                super(view);
                mIdView = view.findViewById(R.id.id_text);
                mContentView = view.findViewById(R.id.content);
            }
        }
    }
}
