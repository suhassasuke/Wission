package com.example.wission.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.wission.PlayerActivity;
import com.example.wission.R;
import com.example.wission.connector.VideoItem;
import com.google.firebase.firestore.GeoPoint;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class YouTubeAdapter extends RecyclerView.Adapter<YouTubeAdapter.MyViewHolder> {

    private Context mContext;
    private List<VideoItem> mVideoList;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class MyViewHolder extends RecyclerView.ViewHolder{

        public ImageView thumbnail;
        public TextView videotitle, videoviews ;
        public LinearLayout youtubelayout;

        public MyViewHolder(View view) {

            super(view);

            //the video_item.xml file is now associated as view object
            //so the view can be called from view's object
            thumbnail = (ImageView) view.findViewById(R.id.videoimage);
            videotitle = (TextView) view.findViewById(R.id.videotitle);
            videoviews = (TextView) view.findViewById(R.id.videoviews);
            youtubelayout = (LinearLayout) view.findViewById(R.id.youtubelayout);
        }
    }

    //Parameterised Constructor to save the Activity context and video list
    //helps in initializing a oject for this class
    public YouTubeAdapter(Context mContext, List<VideoItem> mVideoList) {
        this.mContext = mContext;
        this.mVideoList = mVideoList;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        // create a new view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_youtube_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    // Replace the contents of a view (invoked by the layout manager)
    //filling every item of view with respective text and image
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final VideoItem singleVideo = mVideoList.get(position);

        //replace the default text with id, title and description with setText method
        holder.videotitle.setText(singleVideo.getTitle());
        holder.videoviews.setText(singleVideo.getViews());

        //Picasso library allows for hassle-free image loading
        // in your application—often in one line of code!
        //Features :
        //-Handling ImageView recycling and download cancelation in an adapter
        //-Complex image transformations with minimal memory use
        //-Automatic memory and disk caching

        //placing the thumbnail with picasso library
        //by resizing it to the size of thumbnail

        //with method gives access to the global default Picasso instance
        //load method starts an image request using the specified path may be a remote URL, file resource, etc.
        //resize method resizes the image to the specified size in pixels wrt width and height
        //centerCrop crops an image inside of the bounds specified by resize(int, int) rather than distorting the aspect ratio
        //into method asynchronously fulfills the request into the specified Target
        Picasso.with(mContext)
                .load(singleVideo.getThumbnailURL())
                .resize(480,270)
                .centerCrop()
                .into(holder.thumbnail);

        //setting on click listener for each video_item to launch clicked video in new activity
        holder.youtubelayout.setOnClickListener(new View.OnClickListener() {

            //onClick method called when the view is clicked
            @Override
            public void onClick(View view) {

                //creating a intent for PlayerActivity class from this Activity
                //arguments needed are package context and the new Activity class
                Intent intent = new Intent(mContext, PlayerActivity.class);

                //putExtra method helps to add extra/extended data to the intent
                //which can then be used by the new activity to get initial data from older activity
                //arguments is a name used to identify the data and other is the data itself
                intent.putExtra("VIDEO_ID", singleVideo.getId());
                intent.putExtra("VIDEO_TITLE",singleVideo.getTitle());
                intent.putExtra("VIDEO_DESC",singleVideo.getDescription());

                //Flags define hot the activity should behave when launched
                //FLAG_ACTIVITY_NEW_TASK flag if set, the activity will become the start of a new task on this history stack.
                //adding flag as it is required for YoutubePlayerView Activity
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                //launching the activity by startActivity method
                //use mContext as this class is not the original context
                mContext.startActivity(intent);
            }
        });
    }

    // Return the size of your dataset (invoked by the layout manager)
    //here the dataset is mVideoList
    @Override
    public int getItemCount() {
        return mVideoList.size();
    }
}