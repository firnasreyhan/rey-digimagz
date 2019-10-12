package com.project.digimagz.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.MaterialToolbar;
import com.project.digimagz.Constant;
import com.project.digimagz.R;
import com.project.digimagz.adapter.RecyclerViewNewsAdapter;
import com.project.digimagz.adapter.RecyclerViewNewsCoverStoryAdapter;
import com.project.digimagz.adapter.RecyclerViewStoryAdapter;
import com.project.digimagz.api.InitRetrofit;
import com.project.digimagz.model.NewsCoverStoryModel;
import com.project.digimagz.model.NewsModel;
import com.project.digimagz.model.StoryModel;

import java.util.ArrayList;

public class DetailStoryActivity extends AppCompatActivity {

    private InitRetrofit initRetrofit;

    private MaterialToolbar materialToolbar;
    private TextView textViewTitle, textViewContent, textViewCountNews;
    private ImageView imageViewCover;
    private RecyclerView recyclerViewNews;

    private StoryModel storyModel;
    private Object object;

    private String newsImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_story);

        object = getIntent().getSerializableExtra(RecyclerViewStoryAdapter.INTENT_PARAM_KEY_STORY_DATA);
        storyModel = (StoryModel) object;

        materialToolbar = findViewById(R.id.materialToolbar);
        setSupportActionBar(materialToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("");

        initRetrofit = new InitRetrofit();

        textViewTitle = findViewById(R.id.textViewTitle);
        textViewContent = findViewById(R.id.textViewContent);
        textViewCountNews = findViewById(R.id.textViewCountNews);

        imageViewCover = findViewById(R.id.imageViewCover);

        recyclerViewNews = findViewById(R.id.recyclerViewNews);

        textViewTitle.setText(storyModel.getTitleCoverStory());
        textViewContent.setText(Html.fromHtml(Html.fromHtml(storyModel.getSummary()).toString()));

        newsImage = Constant.URL_IMAGE_STORY + storyModel.getImageCoverStory();
        Glide.with(this)
                .load(newsImage)
                .into(imageViewCover);

        initRetrofit.getNewsCoverStoryFromApi(storyModel.getIdCoverStory());
        initRetrofit.setOnRetrofitSuccess(new InitRetrofit.OnRetrofitSuccess() {
            @Override
            public void onSuccessGetData(ArrayList arrayList) {
                if (!arrayList.isEmpty()) {
                    textViewCountNews.setText(String.valueOf(arrayList.size()) + " Story Pilihan");
                    showRecyclerListViewNews(arrayList);
                } else {
                    Log.e("Size", "Empty");
                }
            }
        });
    }

    private void showRecyclerListViewNews(ArrayList<NewsCoverStoryModel> newsModelArrayList) {
        recyclerViewNews.setHasFixedSize(true);
        recyclerViewNews.setLayoutManager(new LinearLayoutManager(this));
        RecyclerViewNewsCoverStoryAdapter recyclerViewNewsAdapter = new RecyclerViewNewsCoverStoryAdapter(newsModelArrayList);
        recyclerViewNews.setAdapter(recyclerViewNewsAdapter);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
