package com.example.franoisgouet.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Thumbnail;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class Main2Activity extends AppCompatActivity {

    public VideoListFragment mFragment;
    //The query I want to pass to the MainActivity
    private String query;
    static final String test = "test";
    private int mTest;
    private FragmentManager mFragmentManager;

    public Main2Activity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.i("here","OnCreaet");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        // toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //fab
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        //Log.i("request",intent.getStringExtra("request"));

        // Restore preferences
        SharedPreferences settings = getSharedPreferences("PREFS_NAME", 0);
        mTest = settings.getInt("test",mTest);

        //mTest = mIntent.getIntExtra("mes",0);
        /*TextView textView = new TextView(this);
        layout.addView(textView);
        textView.setTextSize(40);
        textView.setText(message);*/
        //Log.i("here",message);
        //mTest = savedInstanceState.getInt(test,mTest);

        // find the retained fragment on activity restarts
        mFragmentManager = getSupportFragmentManager();
        mFragment = (VideoListFragment) mFragmentManager.findFragmentById(R.id.sample_content_fragment);
        //mFragment = (VideoListFragment) mFragmentManager.findFragmentByTag("mFragment");
        if (savedInstanceState != null) {
            mTest = savedInstanceState.getInt(test,mTest);
            Log.i("here","SaveInstance not null");
          }
        else {
            Log.i("here","SaveInstance null");
            mFragment = (VideoListFragment) mFragmentManager.getFragments().get(0);
        }

        // creates the fragment and the data first time
        if (mFragment == null) {
            // add the fragment
            mFragment = new VideoListFragment();
            mFragmentManager.beginTransaction().add(mFragment,"data").addToBackStack(null).commit();
            Log.i("here", "fragment null");
        }
        else {
            Log.i("here","mFragment not null");
        }
            //Log.i("ee", Integer.toString(mTest));

        Toast.makeText(getApplicationContext(),Integer.toString(mTest),Toast.LENGTH_SHORT).show();

    }

    @Override
    public void finish(){
        overridePendingTransition(R.anim.activity_in,R.anim.activity_out);
        super.finish();

        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences settings = getSharedPreferences("PREFS_NAME", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("test", mTest);
        // Commit the edits!
        editor.apply();
        Log.i("here", "finish");

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(test, mTest);
        Log.i("here", "onSavaInstanceState");
        // Save dataList ? Currently, save just a number
        //VideoListFragment savedInstance = (VideoListFragment) outState.getSerializable("mContent");
        mFragmentManager.putFragment(outState, "fragment", mFragment);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mTest = savedInstanceState.getInt(test,mTest);
        Log.i("here", "onRestoreInstanceState");
        //Toast.makeText(getApplicationContext(),Integer.toString(mTest),Toast.LENGTH_SHORT).show();
        //savedInstanceState.putSerializable("mContent", (Serializable) mFragment);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("here", "Onpauseact2");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("here", "OnResumeAct2");
        mTest += 1;
        Intent intent = getIntent();
        String message = intent.getStringExtra("request");
        Log.i("requetes",message);
        startSearch(message);
        // Set the old data to the new ones
        overridePendingTransition(R.anim.activity_in,R.anim.activity_out);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i("here", "OnrestartAct2");
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed(){
        overridePendingTransition(R.anim.activity_in,R.anim.activity_out);
        if(query != null) {
            Intent intent = new Intent();
            intent.putExtra("query",query);
            setResult(RESULT_OK, intent);
        }

        super.onBackPressed();
    }

    public void youTubeSearch(View view) {
        // Prompt the user to enter a query term.
        EditText editText = (EditText) findViewById(R.id.edit_text);
        query = editText.getText().toString();
        startSearch(query);
    }

    private void startSearch(String query) {
        Log.v("quetes",query);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        try {
            // This object is used to make YouTube Data API requests. The last
            // argument is required, but since we don't need anything
            // initialized when the HttpRequest is initialized, we override
            // the interface and provide a no-op function.

            YouTube youtube1 = new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), new HttpRequestInitializer() {
                public void initialize(HttpRequest request) throws IOException {
                }
            }).setApplicationName("youtube-cmdline-search-sample").build();

            // Define the API request for retrieving search results.
            YouTube.Search.List search = youtube1.search().list("id,snippet");

            // Set your developer key from the Google Developers Console for
            // non-authenticated requests. See:
            // https://console.developers.google.com/

            search.setKey(CONSTANT.YOUTUBE_KEY);
            search.setQ(query);

            //v.getContext().startService(mIntent);

            // Restrict the search results to only include videos. See:
            // https://developers.google.com/youtube/v3/docs/search/list#type
            search.setType("video");

            // To increase efficiency, only retrieve the fields that the
            // application uses.
            search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
            search.setMaxResults(CONSTANT.NUMBER_OF_VIDEOS_RETURNED);

            // Call the API and print results.
            SearchListResponse searchResponse = search.execute();
            List<SearchResult> searchResultList = searchResponse.getItems();
            if (searchResultList != null) {
                prettyPrint(searchResultList.iterator());
            }
        } catch (GoogleJsonResponseException e) {
            Log.e("error", e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
        } catch (IOException e) {
            Log.e("There was an IO error: ", e.getCause() + " : " + e.getMessage());
        } catch (Throwable t) {
            Log.v("error", String.valueOf(t));
        }
    }

    /**
             * Prints out all results in the Iterator. For each result, print the
             * title, video ID, and thumbnail.
             *
             * @param iteratorSearchResults Iterator of SearchResults to print
             *
        **/
    private void prettyPrint(Iterator<SearchResult> iteratorSearchResults) {

        getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        TextView txt = (TextView) findViewById(R.id.result);

        String titre;
        String res = "";

        if (!iteratorSearchResults.hasNext()) {
            res += " There aren't any results for your query.";
        }

        while (iteratorSearchResults.hasNext()) {
            SearchResult singleVideo = iteratorSearchResults.next();
            ResourceId rId = singleVideo.getId();

            // Confirm that the result represents a video. Otherwise, the
            // item will not contain a video ID.
            if (rId.getKind().equals("youtube#video")) {
                //final View newView = inflater.inflate(R.layout.add_view,null);
                //myLayout.addView(newView);
                //TextView result1= (TextView) newView.findViewById(R.id.Textv);
                Thumbnail thumbnail = singleVideo.getSnippet().getThumbnails().getDefault();
                final String videoID =(" Video Id" + rId.getVideoId());
                final String ID = rId.getVideoId();
                titre = singleVideo.getSnippet().getTitle();
                String title =" Title: " + titre;
                res+=videoID+title;
                res+=(" Thumbnail: " + thumbnail.getUrl());
                res+=("\n-------------------------------------------------------------\n");
                mFragment.add(new ItemData(titre,R.drawable.zoom,ID));
            }
        }
        Log.println(Log.INFO, "resultat", res);
        txt.setText(res);
    }

}
