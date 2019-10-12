package com.project.digimagz.view.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.button.MaterialButton;
import com.project.digimagz.R;
import com.project.digimagz.adapter.ImageSliderAdapater;
import com.project.digimagz.adapter.RecyclerViewNewsAdapter;
import com.project.digimagz.adapter.RecyclerViewStoryAdapter;
import com.project.digimagz.api.InitRetrofit;
import com.project.digimagz.model.NewsModel;
import com.project.digimagz.model.StoryModel;
import com.project.digimagz.view.activity.ListNewsActivity;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFragment extends Fragment {

    private InitRetrofit initRetrofitTrending, initRetrofitNews, initRetrofitStory, initRetrofitSlider;

    private RecyclerView recyclerViewTranding, recylcerViewCoverStory, recylcerViewNews;
    private ShimmerFrameLayout shimmerFrameLayoutTranding, shimmerFrameLayoutCoverStory, shimmerFrameLayoutNews, shimmerFrameLayoutSlider;
    private MaterialButton materialButtonMoreTrending;
    private NestedScrollView nestedScrollViewHome;

    private RelativeLayout relativeLayoutSlider;
    private ViewPager mPager;
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;

    private CirclePageIndicator indicator;

   // private String[] urls = new String[] {"https://upload.wikimedia.org/wikipedia/commons/3/32/Jesus_und_Ehebrecherin.jpg", "https://upload.wikimedia.org/wikipedia/commons/3/32/Jesus_und_Ehebrecherin.jpg", "https://upload.wikimedia.org/wikipedia/commons/3/32/Jesus_und_Ehebrecherin.jpg"};


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        initRetrofitSlider = new InitRetrofit();
        initRetrofitTrending = new InitRetrofit();
        initRetrofitStory = new InitRetrofit();
        initRetrofitNews = new InitRetrofit();

        recyclerViewTranding = view.findViewById(R.id.recylerViewTranding);
        recylcerViewCoverStory = view.findViewById(R.id.recylcerViewCoverStory);
        recylcerViewNews = view.findViewById(R.id.recylcerViewNews);
        nestedScrollViewHome = view.findViewById(R.id.nestedScrollViewHome);
        relativeLayoutSlider = view.findViewById(R.id.relativeLayoutSlider);

        shimmerFrameLayoutTranding = view.findViewById(R.id.shimmerFrameLayoutTranding);
        shimmerFrameLayoutCoverStory = view.findViewById(R.id.shimmerFrameLayoutCoverStory);
        shimmerFrameLayoutNews = view.findViewById(R.id.shimmerFrameLayoutNews);
        shimmerFrameLayoutSlider = view.findViewById(R.id.shimmerFrameLayoutSlider);

        materialButtonMoreTrending = view.findViewById(R.id.materialButtonMoreTrending);

        mPager = view.findViewById(R.id.pagerSlider);
        indicator = view.findViewById(R.id.indicatorSlider);

        initRetrofitSlider.getSliderFromApi();
        initRetrofitSlider.setOnRetrofitSuccess(new InitRetrofit.OnRetrofitSuccess() {
            @Override
            public void onSuccessGetData(ArrayList arrayList) {
                if (!arrayList.isEmpty()) {
                    Log.i("Size", String.valueOf(arrayList.size()));
                    showSlider(arrayList);
                } else {
                    Log.i("Size", String.valueOf(arrayList.size()));
                }
            }
        });

        setRecyclerView();

        materialButtonMoreTrending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ListNewsActivity.class);
                intent.putExtra("params", "trending");
                startActivity(intent);
            }
        });

        return view;
    }

    private void setRecyclerView() {
        initRetrofitTrending.getNewsTrendingFromApi();
        initRetrofitTrending.setOnRetrofitSuccess(new InitRetrofit.OnRetrofitSuccess() {
            @Override
            public void onSuccessGetData(ArrayList arrayList) {
                if (!arrayList.isEmpty()) {
                    Log.i("Size", String.valueOf(arrayList.size()));
                    showRecyclerListViewTrending(arrayList);
                    materialButtonMoreTrending.setVisibility(View.VISIBLE);
                } else {
                    Log.i("Size", String.valueOf(arrayList.size()));
                }
            }
        });

        initRetrofitStory.getStoryFromApi();
        initRetrofitStory.setOnRetrofitSuccess(new InitRetrofit.OnRetrofitSuccess() {
            @Override
            public void onSuccessGetData(ArrayList arrayList) {
                if (!arrayList.isEmpty()) {
                    Log.i("Size", String.valueOf(arrayList.size()));
                    showRecyclerListViewCoverStory(arrayList);
                } else {
                    Log.i("Size", String.valueOf(arrayList.size()));
                }
            }
        });

        initRetrofitNews.getNewsFromApi();
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

    private void showSlider(ArrayList<NewsModel> newsModelArrayList) {
        mPager.setAdapter(new ImageSliderAdapater(getContext(), newsModelArrayList));
        indicator.setViewPager(mPager);

        final float density = getResources().getDisplayMetrics().density;

        //Set circle indicator radius
        indicator.setRadius(5 * density);

        NUM_PAGES = newsModelArrayList.size();

        // Auto start of viewpager
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (currentPage == NUM_PAGES) {
                    currentPage = 0;
                }
                mPager.setCurrentItem(currentPage++, true);
            }
        };

        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);

        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                currentPage = position;
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        relativeLayoutSlider.setVisibility(View.VISIBLE);
        shimmerFrameLayoutSlider.stopShimmer();
        shimmerFrameLayoutSlider.setVisibility(View.GONE);
    }

    private void showRecyclerListViewTrending(ArrayList<NewsModel> newsModelArrayList) {
        recyclerViewTranding.setHasFixedSize(true);
        recyclerViewTranding.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerViewNewsAdapter recyclerViewNewsAdapter = new RecyclerViewNewsAdapter(newsModelArrayList, 3);
        recyclerViewTranding.setAdapter(recyclerViewNewsAdapter);
        recyclerViewNewsAdapter.notifyDataSetChanged();
        shimmerFrameLayoutTranding.stopShimmer();
        shimmerFrameLayoutTranding.setVisibility(View.GONE);
    }

    private void showRecyclerListViewCoverStory(ArrayList<StoryModel> newsModelArrayList) {
        recylcerViewCoverStory.setHasFixedSize(true);
        recylcerViewCoverStory.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        RecyclerViewStoryAdapter recyclerViewStoryAdapter = new RecyclerViewStoryAdapter(newsModelArrayList);
        recylcerViewCoverStory.setAdapter(recyclerViewStoryAdapter);
        recyclerViewStoryAdapter.notifyDataSetChanged();
        shimmerFrameLayoutCoverStory.stopShimmer();
        shimmerFrameLayoutCoverStory.setVisibility(View.GONE);
    }

    private void showRecyclerListViewNews(ArrayList<NewsModel> newsModelArrayList) {
        recylcerViewNews.setHasFixedSize(true);
        recylcerViewNews.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerViewNewsAdapter recyclerViewNewsAdapter = new RecyclerViewNewsAdapter(newsModelArrayList, newsModelArrayList.size());
        recylcerViewNews.setAdapter(recyclerViewNewsAdapter);
        recyclerViewNewsAdapter.notifyDataSetChanged();
        shimmerFrameLayoutNews.stopShimmer();
        shimmerFrameLayoutNews.setVisibility(View.GONE);
    }

    public void scrollUp(){
        nestedScrollViewHome.smoothScrollTo(0,0);
    }

    @Override
    public void onResume() {
        super.onResume();
        setRecyclerView();
        shimmerFrameLayoutTranding.startShimmer();
        shimmerFrameLayoutCoverStory.startShimmer();
        shimmerFrameLayoutNews.startShimmer();
        shimmerFrameLayoutSlider.startShimmer();
    }

    @Override
    public void onPause() {
        shimmerFrameLayoutTranding.stopShimmer();
        shimmerFrameLayoutCoverStory.stopShimmer();
        shimmerFrameLayoutNews.stopShimmer();
        shimmerFrameLayoutSlider.stopShimmer();
        super.onPause();
    }
}
