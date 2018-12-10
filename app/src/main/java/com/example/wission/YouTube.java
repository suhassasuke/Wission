package com.example.wission;

import android.app.ProgressDialog;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.wission.adapter.YouTubeAdapter;
import com.example.wission.connector.VideoItem;
import com.example.wission.connector.YouTubeConnector;

import java.util.List;

public class YouTube extends AppCompatActivity {

    private static final String TAG = "YouTube";

    YouTubeAdapter youTubeAdapter;
    private RecyclerView mRecyclerView;
    private List<VideoItem> searchResults;
    private ProgressDialog mProgressDialog;
    private Handler handler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube);

        //initailising
        mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setTitle("Searching...");
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        //displaying the progress dialog
        mProgressDialog.show();

        handler = new Handler();
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclelayout);
        searchOnYoutube("stand-up comedy");
    }

    //custom search method which takes argument as the keyword for which videos is to be searched
    private void searchOnYoutube(final String keywords){

        //A thread that will execute the searching and inflating the RecyclerView as and when
        //results are found
        new Thread(){

            //implementing run method
            public void run(){

                //create our YoutubeConnector class's object with Activity context as argument
                YouTubeConnector yc = new YouTubeConnector(YouTube.this);

                //calling the YoutubeConnector's search method by entered keyword
                //and saving the results in list of type VideoItem class
                searchResults = yc.search(keywords);

                //handler's method used for doing changes in the UI
                handler.post(new Runnable(){

                    //implementing run method of Runnable
                    public void run(){

                        //call method to create Adapter for RecyclerView and filling the list
                        //with thumbnail, title, id and description
                        fillYoutubeVideos();

                        //after the above has been done hiding the ProgressDialog
                        mProgressDialog.dismiss();
                    }
                });
            }
            //starting the thread
        }.start();
    }

    //method for creating adapter and setting it to recycler view
    private void fillYoutubeVideos(){
        Log.d(TAG, "fillYoutubeVideos: " + searchResults);

        //object of YoutubeAdapter which will fill the RecyclerView
        youTubeAdapter = new YouTubeAdapter(getApplicationContext(),searchResults);

        LinearLayoutManager llm = new LinearLayoutManager(this);

        //setAdapter to RecyclerView
        mRecyclerView.setAdapter(youTubeAdapter);
        mRecyclerView.setLayoutManager(llm);
        //notify the Adapter that the data has been downloaded so that list can be updapted
        youTubeAdapter.notifyDataSetChanged();
    }
}
