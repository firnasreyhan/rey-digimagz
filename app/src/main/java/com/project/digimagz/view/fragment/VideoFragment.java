package com.project.digimagz.view.fragment;

import android.os.Bundle;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.project.digimagz.R;
import com.project.digimagz.adapter.RecyclerViewVideoAdapter;
import com.project.digimagz.model.YoutubeDataModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class VideoFragment extends Fragment {

    private final String GOOGLE_YOUTUBE_API_KEY = "AIzaSyBXmYa9XW8pUIVu3_jfZRH1GuloT8d1tgo";
    private final String CHANNEL_ID = "UC1QoJ6wmGGalOLcsWFiMN_w";
    private final String CHANNEL_GET_URL = "https://www.googleapis.com/youtube/v3/search?part=snippet&order=date&channelId="+ CHANNEL_ID +"&maxResults=25&key=" + GOOGLE_YOUTUBE_API_KEY + "";

    private ArrayList<YoutubeDataModel> mListData = new ArrayList<>();
    private RecyclerView recyclerViewVideo;
    private RecyclerViewVideoAdapter recyclerViewVideoAdapter;
    private ShimmerFrameLayout mShimmerViewContainer;
    private NestedScrollView nestedScrollViewVideo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_video, container, false);
        recyclerViewVideo = view.findViewById(R.id.recyclerViewVideo);
        mShimmerViewContainer = view.findViewById(R.id.shimmer_view_container);
        nestedScrollViewVideo = view.findViewById(R.id.nestedScrollViewVideo);

        displayVideo();
        return view;
    }

    private void showRecyclerListViewVideo() {
        recyclerViewVideo.setHasFixedSize(true);
        recyclerViewVideo.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewVideoAdapter = new RecyclerViewVideoAdapter(mListData, getContext());
        recyclerViewVideo.setAdapter(recyclerViewVideoAdapter);
    }

    private void displayVideo() {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, CHANNEL_GET_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("items");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        Log.e("Size", String.valueOf(i));
                        JSONObject object = jsonArray.getJSONObject(i);
                        JSONObject objectVideoId = object.getJSONObject("id");
                        JSONObject objectSnippet = object.getJSONObject("snippet");
                        JSONObject objectDefault = objectSnippet.getJSONObject("thumbnails").getJSONObject("medium");

                        String kind = objectVideoId.getString("kind");

                        if (kind.equalsIgnoreCase("youtube#video")) {
                            YoutubeDataModel dataModel = new YoutubeDataModel();
                            Log.e("Id", object.getString("id"));
                            dataModel.setVideoId(objectVideoId.getString("videoId"));
                            dataModel.setTitle(objectSnippet.getString("title"));
                            dataModel.setDescription(objectSnippet.getString("description"));
                            dataModel.setPublishedAt(objectSnippet.getString("publishedAt"));
                            dataModel.setThumbnail(objectDefault.getString("url"));
                            mListData.add(dataModel);
                        }
                    }
                    // stop animating Shimmer and hide the layout
                    mShimmerViewContainer.stopShimmer();
                    mShimmerViewContainer.setVisibility(View.GONE);
                    showRecyclerListViewVideo();
                } catch (JSONException e) {
                    Log.e("Youtube", e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        requestQueue.add(stringRequest);
    }

    public void scrollUp(){
        nestedScrollViewVideo.smoothScrollTo(0,0);
    }

    @Override
    public void onResume() {
        super.onResume();
        mShimmerViewContainer.startShimmer();
    }

    @Override
    public void onPause() {
        mShimmerViewContainer.stopShimmer();
        super.onPause();
    }
}
