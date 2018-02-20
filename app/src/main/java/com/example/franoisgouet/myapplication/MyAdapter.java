package com.example.franoisgouet.myapplication;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import java.util.ArrayList;
import java.util.List;

/**
 * @author françois GOUET
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
    public static List<ItemData> itemsData = new ArrayList<>();

    public MyAdapter(List<ItemData> itemsData) {
        MyAdapter.itemsData = itemsData;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        @SuppressLint("InflateParams") View itemLayoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.add_view,null);
        // create ViewHolder
        return new ViewHolder(itemLayoutView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder holder, int position){
        // - get data from your itemsData at this position
        // - replace the contents of the view with that itemsData

        holder.title.setText(itemsData.get(position).getTitle());
        holder.youtubeThumbnailView.setTag(itemsData.get(position).getVideoID());
        Log.d("list", holder.youtubeThumbnailView.getTag().toString());
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        protected YouTubeThumbnailView youtubeThumbnailView;
        private YouTubeThumbnailLoader loader;
        private Button buttonAdd;


        public ViewHolder(final View itemLayoutView) {
            super(itemLayoutView);
            title = (TextView) itemLayoutView.findViewById(R.id.Textv);
            youtubeThumbnailView = (YouTubeThumbnailView) itemLayoutView.findViewById(R.id.youtubethumbnailView);
            buttonAdd = (Button) itemLayoutView.findViewById(R.id.ButtonAdd);
            initialize();
            buttonAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Toast.makeText(itemLayoutView.getContext(), "Item click nr: " + getLayoutPosition(), Toast.LENGTH_SHORT).show();
                    Intent mIntent = new Intent(v.getContext(),MainActivity.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putSerializable("DATA", itemsData.get(getLayoutPosition()));
                    mIntent.putExtras(mBundle);
                    //v.getContext().StartActivity(mIntent);
                    v.getContext().startService(mIntent);
                    //LocalBroadcastManager.getInstance(v.getContext()).sendBroadcast(mIntent);
                }
            });
        }

        private void initialize() {
            youtubeThumbnailView.initialize(CONSTANT.YOUTUBE_KEY, new YouTubeThumbnailView.OnInitializedListener() {
                @Override
                public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {
                    loader = youTubeThumbnailLoader;
                    loader.setVideo((String) youTubeThumbnailView.getTag());
                    youTubeThumbnailLoader.setOnThumbnailLoadedListener(new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
                        @Override
                        public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                        }

                        @Override
                        public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {
                        }

                    });
                }

                @Override
                public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {

                }
            });
        }
        }

        // Return the size of your itemsData (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return itemsData.size();
        }
}