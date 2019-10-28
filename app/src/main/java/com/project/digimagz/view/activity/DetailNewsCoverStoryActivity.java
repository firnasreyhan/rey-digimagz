package com.project.digimagz.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.project.digimagz.Constant;
import com.project.digimagz.R;
import com.project.digimagz.adapter.RecyclerViewCommentAdapter;
import com.project.digimagz.adapter.RecyclerViewNewsAdapter;
import com.project.digimagz.adapter.RecyclerViewNewsCoverStoryAdapter;
import com.project.digimagz.api.InitRetrofit;
import com.project.digimagz.model.CommentModel;
import com.project.digimagz.model.NewsCoverStoryModel;
import com.project.digimagz.model.NewsModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class DetailNewsCoverStoryActivity extends AppCompatActivity {

    private FirebaseUser firebaseUser;

    private ImageView imageViewCover;
    private TextView textViewTitle, textViewDate, textViewContent, textViewCountComment, textViewLike, textViewCategory, textViewEditor, textViewVerificator;
    private ImageButton imageButtonSendComment, imageButtonLike, imageButtonDislike;
    private WebView webViewDetailNews;
    private TextInputEditText textInputEditTextComment;
    private RecyclerView recyclerViewComment, recyclerViewNews;
    private MaterialToolbar materialToolbar;

    private LinearLayout linearLayoutShare;

    private NewsCoverStoryModel newsCoverStoryModel;
    private Object object;

    private SimpleDateFormat simpleDateFormat;
    private Date date;
    private String newsImage, dataHtml;

    private InitRetrofit initRetrofit, initRetrofitComment, initRetrofitNews, initRetrofitLike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_news);

        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        object = getIntent().getSerializableExtra(RecyclerViewNewsCoverStoryAdapter.INTENT_PARAM_KEY_NEWS_COVER_STORY_DATA);
        newsCoverStoryModel = (NewsCoverStoryModel) object;

        materialToolbar = findViewById(R.id.materialToolbar);
        setSupportActionBar(materialToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("");

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        initRetrofit = new InitRetrofit();
        initRetrofitComment = new InitRetrofit();
        initRetrofitNews = new InitRetrofit();
        initRetrofitLike = new InitRetrofit();

        textViewTitle = findViewById(R.id.textViewTitle);
        textViewDate = findViewById(R.id.textViewDate);
        textViewContent = findViewById(R.id.textViewContent);
        imageViewCover = findViewById(R.id.imageViewCover);
        textViewCountComment = findViewById(R.id.textViewCountComment);
        textViewLike = findViewById(R.id.textViewLike);
        textViewCategory = findViewById(R.id.textViewCategory);
        textViewEditor = findViewById(R.id.textViewEditor);
        textViewVerificator = findViewById(R.id.textViewVerificator);
        imageButtonSendComment = findViewById(R.id.imageButtonSendComment);
        imageButtonLike = findViewById(R.id.imageButtonLike);
        imageButtonDislike = findViewById(R.id.imageButtonDislike);
        textInputEditTextComment = findViewById(R.id.textInputEditTextComment);
        recyclerViewComment = findViewById(R.id.recyclerViewComment);
        recyclerViewNews = findViewById(R.id.recyclerViewNews);
        linearLayoutShare = findViewById(R.id.linearLayoutShare);
        webViewDetailNews = findViewById(R.id.webViewDetailNews);
        //webViewDetailNews.setBackgroundColor(Color.TRANSPARENT);

        dataHtml = "<html><head><meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"></head>";
        dataHtml = dataHtml + "<body width=\"100%\" height=\"auto\">" + newsCoverStoryModel.getContentNews()  + "</body></html>";

        webViewDetailNews.getSettings().setJavaScriptEnabled(true);
        webViewDetailNews.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webViewDetailNews.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webViewDetailNews.getSettings().setPluginState(WebSettings.PluginState.ON);
        webViewDetailNews.getSettings().setSupportMultipleWindows(true);
        webViewDetailNews.setWebChromeClient(new WebChromeClient());
        webViewDetailNews.setHorizontalScrollBarEnabled(false);
        webViewDetailNews.getSettings().setLoadWithOverviewMode(true);
        webViewDetailNews.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webViewDetailNews.getSettings().setBuiltInZoomControls(false);

        try {
            date = simpleDateFormat.parse(newsCoverStoryModel.getDateNews());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        newsImage = Constant.URL_IMAGE_NEWS + newsCoverStoryModel.getNewsImage();

        textViewTitle.setText(newsCoverStoryModel.getTitleNews());
        textViewDate.setText(DateFormat.getDateInstance(DateFormat.LONG, new Locale("in", "ID")).format(date));
        textViewCategory.setText(newsCoverStoryModel.getNameCategory());
        textViewContent.setText(Html.fromHtml(Html.fromHtml(newsCoverStoryModel.getContentNews()).toString()));
        textViewLike.setText(String.valueOf(newsCoverStoryModel.getLikes()));
        Glide.with(DetailNewsCoverStoryActivity.this)
                .load(newsImage)
                .into(imageViewCover);
        webViewDetailNews.loadDataWithBaseURL(null, dataHtml,
                "text/html", "UTF-8", null);
        webViewDetailNews.setWebViewClient(new WebViewClient());

        if (newsCoverStoryModel.getEditor() != null) {
            textViewEditor.setText(newsCoverStoryModel.getEditor());
        }
        if (newsCoverStoryModel.getVerificator() != null) {
            textViewVerificator.setText(newsCoverStoryModel.getVerificator());
        }

        if (firebaseUser != null) {
            imageButtonLike.setEnabled(true);
            imageButtonDislike.setEnabled(true);
            imageButtonSendComment.setEnabled(true);
            textInputEditTextComment.setEnabled(true);

            initRetrofitLike.getLikeFromApi(newsCoverStoryModel.getIdNews(), firebaseUser.getEmail());
            initRetrofitLike.setOnRetrofitSuccess(new InitRetrofit.OnRetrofitSuccess() {
                @Override
                public void onSuccessGetData(ArrayList arrayList) {
                    if (arrayList.get(0).equals("Yes")) {
                        imageButtonDislike.setVisibility(View.VISIBLE);
                        imageButtonLike.setVisibility(View.GONE);
                    } else if (arrayList.get(0).equals("No")){
                        imageButtonDislike.setVisibility(View.GONE);
                        imageButtonLike.setVisibility(View.VISIBLE);
                    }
                }
            });

            imageButtonLike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    imageButtonLike.setVisibility(View.GONE);
                    imageButtonDislike.setVisibility(View.VISIBLE);
                    initRetrofit.postLikeToApi(newsCoverStoryModel.getIdNews(), firebaseUser.getEmail());
                    newsCoverStoryModel.setLikes(newsCoverStoryModel.getLikes() + 1);
                    textViewLike.setText(String.valueOf(newsCoverStoryModel.getLikes()));
                }
            });

            imageButtonDislike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    imageButtonLike.setVisibility(View.VISIBLE);
                    imageButtonDislike.setVisibility(View.GONE);
                    initRetrofit.deleteLikeFromApi(newsCoverStoryModel.getIdNews(), firebaseUser.getEmail());
                    newsCoverStoryModel.setLikes(newsCoverStoryModel.getLikes() - 1);
                    textViewLike.setText(String.valueOf(newsCoverStoryModel.getLikes()));
                }
            });

            imageButtonSendComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(DetailNewsCoverStoryActivity.this, "Terima Kasih Atas Komentar Anda Dan Sedang Kami Moderasi", Toast.LENGTH_LONG).show();
                    textInputEditTextComment.setText("");
                    initRetrofit.postCommentToApi(newsCoverStoryModel.getIdNews(), firebaseUser.getEmail(), textInputEditTextComment.getText().toString());
                }
            });

            initRetrofitComment.getCommentFromApi(newsCoverStoryModel.getIdNews());
            initRetrofitComment.setOnRetrofitSuccess(new InitRetrofit.OnRetrofitSuccess() {
                @Override
                public void onSuccessGetData(ArrayList arrayList) {
                    if (!arrayList.isEmpty()) {
                        Log.i("Size", String.valueOf(arrayList.size()));
                        textViewCountComment.setText(String.valueOf(arrayList.size()));
                        showRecyclerListViewComment(arrayList);
                    } else {
                        Log.i("Size", String.valueOf(arrayList.size()));
                    }
                }
            });

            initRetrofitNews.getNewsRelatedFromApi(newsCoverStoryModel.getIdNews());
            initRetrofitNews.setOnRetrofitSuccess(new InitRetrofit.OnRetrofitSuccess() {
                @Override
                public void onSuccessGetData(ArrayList arrayList) {
                    if (!arrayList.isEmpty()) {
                        Log.i("Size", String.valueOf(arrayList.size()));
                        showRecyclerListViewNews(arrayList);
                    } else {
                        Log.i("Size", String.valueOf(arrayList.size()));
                    }
                }
            });
        } else {
            imageButtonLike.setEnabled(false);
            imageButtonDislike.setEnabled(false);
            imageButtonSendComment.setEnabled(false);
            textInputEditTextComment.setEnabled(false);
        }

        linearLayoutShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openShare(newsCoverStoryModel);
            }
        });
    }

    private void showRecyclerListViewComment(ArrayList<CommentModel> commentModelArrayList) {
        recyclerViewComment.setHasFixedSize(true);
        recyclerViewComment.setLayoutManager(new LinearLayoutManager(this));
        RecyclerViewCommentAdapter recyclerViewCommentAdapter = new RecyclerViewCommentAdapter(commentModelArrayList);
        recyclerViewComment.setAdapter(recyclerViewCommentAdapter);
    }

    private void showRecyclerListViewNews(ArrayList<NewsModel> newsModelArrayList) {
        recyclerViewNews.setHasFixedSize(true);
        recyclerViewNews.setLayoutManager(new LinearLayoutManager(this));
        RecyclerViewNewsAdapter recyclerViewNewsAdapter = new RecyclerViewNewsAdapter(newsModelArrayList, newsModelArrayList.size());
        recyclerViewNews.setAdapter(recyclerViewNewsAdapter);
    }

    private void openShare(NewsCoverStoryModel model) {
        Intent myIntent = new Intent(Intent.ACTION_SEND);
        myIntent.setType("text/plain");
        String shareBody = model.getTitleNews() + "\n" + "http://digimagz.kristomoyo.com/news/view/" + model.getIdNews();
        String shareSub = "Digimagz";
        myIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
        myIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(myIntent, "Share \"Digimagz\" via"));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
