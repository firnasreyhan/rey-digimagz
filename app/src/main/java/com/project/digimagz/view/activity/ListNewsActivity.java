package com.project.digimagz.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.project.digimagz.R;
import com.project.digimagz.adapter.RecyclerViewNewsAdapter;
import com.project.digimagz.api.InitRetrofit;
import com.project.digimagz.model.NewsModel;

import java.util.ArrayList;

public class ListNewsActivity extends AppCompatActivity {

    private InitRetrofit initRetrofit;

    private MaterialToolbar materialToolbar;

    private Intent intent;
    private RecyclerView recyclerViewNews;
    private FrameLayout frameLayoutEmpty;
    private ShimmerFrameLayout shimmerFrameLayoutNews;

    private String params;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_news);

        intent = getIntent();
        params = intent.getExtras().getString("params");

        materialToolbar = findViewById(R.id.materialToolbar);
        setSupportActionBar(materialToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("");

        initRetrofit = new InitRetrofit();
        recyclerViewNews = findViewById(R.id.recyclerViewNews);
        shimmerFrameLayoutNews = findViewById(R.id.shimmerFrameLayoutNews);
        frameLayoutEmpty = findViewById(R.id.frameLayoutEmpty);

        setRecylerView();



    }

    private void setRecylerView() {
        if (!params.equalsIgnoreCase("trending")) {
            initRetrofit.getNewsFromApiWithParams(params);
            initRetrofit.setOnRetrofitSuccess(new InitRetrofit.OnRetrofitSuccess() {
                @Override
                public void onSuccessGetData(ArrayList arrayList) {
                    if (!arrayList.isEmpty()) {
                        Log.i("Size", String.valueOf(arrayList.size()));
                        showRecyclerListViewNews(arrayList);
                    } else {
                        frameLayoutEmpty.setVisibility(View.VISIBLE);
                        recyclerViewNews.setVisibility(View.GONE);
                        shimmerFrameLayoutNews.stopShimmer();
                        shimmerFrameLayoutNews.setVisibility(View.GONE);
                        Log.i("Size", String.valueOf(arrayList.size()));
                    }
                }
            });
        } else {
            initRetrofit.getNewsTrendingFromApi();
            initRetrofit.setOnRetrofitSuccess(new InitRetrofit.OnRetrofitSuccess() {
                @Override
                public void onSuccessGetData(ArrayList arrayList) {
                    if (!arrayList.isEmpty()) {
                        Log.i("Size", String.valueOf(arrayList.size()));
                        showRecyclerListViewNews(arrayList);
                    } else {
                        frameLayoutEmpty.setVisibility(View.VISIBLE);
                        recyclerViewNews.setVisibility(View.GONE);
                        shimmerFrameLayoutNews.stopShimmer();
                        shimmerFrameLayoutNews.setVisibility(View.GONE);
                        Log.i("Size", String.valueOf(arrayList.size()));
                    }
                }
            });
        }
    }

    private void showRecyclerListViewNews(ArrayList<NewsModel> newsModelArrayList) {
        recyclerViewNews.setHasFixedSize(true);
        recyclerViewNews.setLayoutManager(new LinearLayoutManager(this));
        RecyclerViewNewsAdapter recyclerViewNewsAdapter = new RecyclerViewNewsAdapter(newsModelArrayList, newsModelArrayList.size());
        recyclerViewNews.setAdapter(recyclerViewNewsAdapter);
        recyclerViewNewsAdapter.notifyDataSetChanged();
        shimmerFrameLayoutNews.stopShimmer();
        shimmerFrameLayoutNews.setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();
        setRecylerView();
        shimmerFrameLayoutNews.startShimmer();
    }

    @Override
    public void onPause() {
        shimmerFrameLayoutNews.stopShimmer();
        super.onPause();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
