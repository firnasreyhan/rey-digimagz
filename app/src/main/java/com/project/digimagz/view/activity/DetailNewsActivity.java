package com.project.digimagz.view.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.project.digimagz.Constant;
import com.google.android.material.textfield.TextInputEditText;
import com.project.digimagz.R;
import com.project.digimagz.adapter.ImageSliderGalleryAdapater;
import com.project.digimagz.adapter.ImageSliderNewsAdapater;
import com.project.digimagz.adapter.RecyclerViewCommentAdapter;
import com.project.digimagz.adapter.RecyclerViewNewsAdapter;
import com.project.digimagz.api.InitRetrofit;
import com.project.digimagz.model.CommentModel;
import com.project.digimagz.model.NewsModel;
import com.project.digimagz.model.UserModel;
import com.viewpagerindicator.CirclePageIndicator;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DetailNewsActivity extends AppCompatActivity {

    private FirebaseUser firebaseUser;

    private ImageView imageViewCover;
    private TextView textViewTitle, textViewDate, textViewContent, textViewCountComment, textViewLike, textViewCategory, textViewEditor, textViewVerificator;
    private ImageButton imageButtonSendComment, imageButtonLike, imageButtonDislike;
    private WebView webViewDetailNews;
    private TextInputEditText textInputEditTextComment;
    private RecyclerView recyclerViewComment, recyclerViewNews;
    private MaterialToolbar materialToolbar;

    private LinearLayout linearLayoutShare;

    private NewsModel newsModel;
    private Object object;

    private SimpleDateFormat simpleDateFormat;
    private Date date;
    private String newsImage, dataHtml;

    private InitRetrofit initRetrofit, initRetrofitComment, initRetrofitNews, initRetrofitLike, initRetrofitView, initRetrofitShare, initRetrofitUser;
    private RecyclerViewCommentAdapter recyclerViewCommentAdapter;
    private ArrayList<UserModel> userModels = new ArrayList<>();

    private RelativeLayout relativeLayoutSlider;
    private ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;

    private CirclePageIndicator indicator;

    private Handler swiper = new Handler();
    private Runnable swiperRunnable = new Runnable() {
        public void run() {
            if (currentPage == NUM_PAGES) {
                currentPage = 0;
            }
            mPager.setCurrentItem(currentPage++, true);

            swiper.postDelayed(this, 3000);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_news);

        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        object = getIntent().getSerializableExtra(RecyclerViewNewsAdapter.INTENT_PARAM_KEY_NEWS_DATA);
        newsModel = (NewsModel) object;

        materialToolbar = findViewById(R.id.materialToolbar);
        setSupportActionBar(materialToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("");

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        initRetrofit = new InitRetrofit();
        initRetrofitComment = new InitRetrofit();
        initRetrofitNews = new InitRetrofit();
        initRetrofitLike = new InitRetrofit();
        initRetrofitView = new InitRetrofit();
        initRetrofitShare = new InitRetrofit();
        initRetrofitUser = new InitRetrofit();

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
        relativeLayoutSlider = findViewById(R.id.relativeLayoutSlider);
        mPager = findViewById(R.id.pagerSlider);
        indicator = findViewById(R.id.indicatorSlider);
        //webViewDetailNews.setBackgroundColor(Color.TRANSPARENT);

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
            date = simpleDateFormat.parse(newsModel.getDateNews());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (newsModel.getNewsImage() != null) {
            if (!newsModel.getNewsImage().isEmpty()) {
                if (newsModel.getNameCategory().equalsIgnoreCase("Berita")) {
                    newsImage = Constant.URL_IMAGE_NEWS + newsModel.getNewsImage().get(0);
                } else if (newsModel.getNameCategory().equalsIgnoreCase("Artikel")) {
                    newsImage = Constant.URL_IMAGE_NEWS + newsModel.getNewsImage().get(0);
                } else if (newsModel.getNameCategory().equalsIgnoreCase("Galeri")) {
                    newsImage = Constant.URL_IMAGE_GALLERY + newsModel.getIdNews() + "/" + newsModel.getNewsImage().get(0);
                    relativeLayoutSlider.setVisibility(View.VISIBLE);
                    imageViewCover.setVisibility(View.GONE);
                    showSlider(newsModel.getNewsImage(), newsModel.getIdNews());
                }
                Glide.with(DetailNewsActivity.this)
                        .load(newsImage)
                        .into(imageViewCover);
            }
        }

        if (newsModel.getTitleNews() != null) {
            textViewTitle.setText(newsModel.getTitleNews());
        }

        if (date != null) {
            textViewDate.setText(DateFormat.getDateInstance(DateFormat.LONG, new Locale("in", "ID")).format(date));
        }

        if (newsModel.getContentNews() != null) {
            dataHtml = "<html><head><meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"></head>";
            dataHtml = dataHtml + "<body width=\"100%\" height=\"auto\">" + newsModel.getContentNews()  + "</body></html>";

            webViewDetailNews.loadDataWithBaseURL(null, dataHtml, "text/html", "UTF-8", null);
            webViewDetailNews.setWebViewClient(new WebViewClient());

            textViewContent.setText(Html.fromHtml(Html.fromHtml(newsModel.getContentNews()).toString()));
        }

        if (newsModel.getNameCategory() != null) {
            textViewCategory.setText(newsModel.getNameCategory());
        }

        textViewLike.setText(String.valueOf(newsModel.getLikes()));

        if (newsModel.getEditor() != null) {
            textViewEditor.setText(newsModel.getEditor());
        }
        if (newsModel.getVerificator() != null) {
            textViewVerificator.setText(newsModel.getVerificator());
        }

        setRecyclerView();

        if (firebaseUser != null) {
            imageButtonLike.setEnabled(true);
            imageButtonDislike.setEnabled(true);
            imageButtonSendComment.setEnabled(true);
            textInputEditTextComment.setEnabled(true);

            initRetrofitUser.getUserFromApi(firebaseUser.getEmail());
            initRetrofitUser.setOnRetrofitSuccess(new InitRetrofit.OnRetrofitSuccess() {
                @Override
                public void onSuccessGetData(ArrayList arrayList) {
                    if (!arrayList.isEmpty()) {
                        userModels.addAll(arrayList);
                    }
                }
            });

            initRetrofitView.postViewToApi(newsModel.getIdNews(), firebaseUser.getEmail());

            initRetrofitLike.getLikeFromApi(newsModel.getIdNews(), firebaseUser.getEmail());
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
                    initRetrofit.postLikeToApi(newsModel.getIdNews(), firebaseUser.getEmail());
                    newsModel.setLikes(newsModel.getLikes() + 1);
                    newsModel.setCheckLike(1);
                    textViewLike.setText(String.valueOf(newsModel.getLikes()));
                }
            });

            imageButtonDislike.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    imageButtonLike.setVisibility(View.VISIBLE);
                    imageButtonDislike.setVisibility(View.GONE);
                    initRetrofit.deleteLikeFromApi(newsModel.getIdNews(), firebaseUser.getEmail());
                    newsModel.setLikes(newsModel.getLikes() - 1);
                    newsModel.setCheckLike(2);
                    textViewLike.setText(String.valueOf(newsModel.getLikes()));
                }
            });

            imageButtonSendComment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (textInputEditTextComment.length() > 0) {
                        Toast.makeText(DetailNewsActivity.this, "Terima Kasih Atas Komentar Anda", Toast.LENGTH_LONG).show();
                        initRetrofit.postCommentToApi(newsModel.getIdNews(), firebaseUser.getEmail(), textInputEditTextComment.getText().toString());

//                        Calendar calendar = Calendar.getInstance();
//                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

//                        recyclerViewCommentAdapter.add(new CommentModel(null, null,
//                                firebaseUser.getEmail(), textInputEditTextComment.getText().toString(),
//                                null, dateFormat.format(calendar.getTime()), userModels.get(0).getUserName(), userModels.get(0).getUrlPic()));
//
//                        textViewCountComment.setText(String.valueOf(Integer.parseInt(textViewCountComment.getText().toString()) + 1));

                        textInputEditTextComment.setText("");
                    } else {
                        Toast.makeText(DetailNewsActivity.this, "Harap isi komentar dengan benar", Toast.LENGTH_SHORT).show();
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
                openShare(newsModel);
            }
        });
    }

    private void setRecyclerView() {
        //swiper.removeCallbacks(swiperRunnable);

        initRetrofitComment.getCommentFromApi(newsModel.getIdNews());
        initRetrofitComment.setOnRetrofitSuccess(new InitRetrofit.OnRetrofitSuccess() {
            @Override
            public void onSuccessGetData(ArrayList arrayList) {
                Log.i("Size", String.valueOf(arrayList.size()));
                textViewCountComment.setText(String.valueOf(arrayList.size()));

                ArrayList commentModels = new ArrayList<CommentModel>();
                for (int i = arrayList.size()-1; i >= 0; i--) {
                    commentModels.add(arrayList.get(i));
                }

                showRecyclerListViewComment(commentModels);
            }
        });

        initRetrofitNews.getNewsRelatedFromApi(newsModel.getIdNews());
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
    }

    private void showRecyclerListViewComment(ArrayList<CommentModel> commentModelArrayList) {
        //recyclerViewComment.setHasFixedSize(true);
        recyclerViewComment.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, true));
        recyclerViewCommentAdapter = new RecyclerViewCommentAdapter(commentModelArrayList, getApplicationContext());
        recyclerViewComment.setAdapter(recyclerViewCommentAdapter);
    }

    private void showRecyclerListViewNews(ArrayList<NewsModel> newsModelArrayList) {
        recyclerViewNews.setHasFixedSize(true);
        recyclerViewNews.setLayoutManager(new LinearLayoutManager(this));
        RecyclerViewNewsAdapter recyclerViewNewsAdapter;
        if (newsModelArrayList.size() >= 3) {
            recyclerViewNewsAdapter = new RecyclerViewNewsAdapter(newsModelArrayList, 3);
        } else {
            recyclerViewNewsAdapter = new RecyclerViewNewsAdapter(newsModelArrayList, newsModelArrayList.size());
        }
        recyclerViewNews.setAdapter(recyclerViewNewsAdapter);
    }

    private void openShare(NewsModel model) {
        Intent myIntent = new Intent(Intent.ACTION_SEND);
        myIntent.setType("text/plain");
        String shareBody = model.getTitleNews() + "\n" + "http://pn10mobprd.ptpn10.co.id:8598/news/view/" + model.getIdNews();
        String shareSub = "Digimagz";
        myIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
        myIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(myIntent, "Share \"Digimagz\" via"));
    }

    private void showSlider(ArrayList<String> newsModelArrayList, String idNews) {
        if (newsModelArrayList.size() > 0) {
            mPager.setAdapter(new ImageSliderGalleryAdapater(newsModelArrayList, idNews));
            indicator.setViewPager(mPager);

            final float density = getResources().getDisplayMetrics().density;

            //Set circle indicator radius
            indicator.setRadius(5 * density);

            NUM_PAGES = newsModelArrayList.size();

            // Auto start of viewpager
            swiper.postDelayed(swiperRunnable, 3000);

            /**Timer swipeTimer = new Timer();
             swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
            handler.post(Update);
            }
            }, 3000, 3000);*/

            indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    currentPage = position;
                    Log.e("pos", String.valueOf(currentPage));
                }

                @Override
                public void onPageSelected(int position) {

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setRecyclerView();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (firebaseUser != null) {
                initRetrofitShare.postShareToApi(newsModel.getIdNews(), firebaseUser.getEmail());
            }
        }
    }

}
