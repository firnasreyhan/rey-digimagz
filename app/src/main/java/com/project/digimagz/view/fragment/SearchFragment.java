package com.project.digimagz.view.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.project.digimagz.R;
import com.project.digimagz.adapter.RecyclerViewNewsAdapter;
import com.project.digimagz.api.InitRetrofit;
import com.project.digimagz.model.NewsModel;
import com.project.digimagz.view.activity.ListNewsActivity;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    private InitRetrofit initRetrofit;

    private TextInputEditText textInputEditTextSearch;
    private MaterialButton materialButtonSearch;

    private RecyclerView recyclerViewNews;
    private ShimmerFrameLayout shimmerFrameLayoutNews;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        initRetrofit = new InitRetrofit();

        textInputEditTextSearch = view.findViewById(R.id.textInputEditTextSearch);
        materialButtonSearch = view.findViewById(R.id.materialButtonSearch);

        recyclerViewNews = view.findViewById(R.id.recyclerViewNews);
        shimmerFrameLayoutNews = view.findViewById(R.id.shimmerFrameLayoutNews);

        setRecyclerView();

        materialButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ListNewsActivity.class);
                intent.putExtra("params", textInputEditTextSearch.getText().toString());
                startActivity(intent);
            }
        });

        return view;
    }

    private void setRecyclerView() {
        initRetrofit.getNewsFromApi();
        initRetrofit.setOnRetrofitSuccess(new InitRetrofit.OnRetrofitSuccess() {
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

    private void showRecyclerListViewNews(ArrayList<NewsModel> newsModelArrayList) {
        recyclerViewNews.setHasFixedSize(true);
        recyclerViewNews.setLayoutManager(new LinearLayoutManager(getContext()));
        RecyclerViewNewsAdapter recyclerViewNewsAdapter = new RecyclerViewNewsAdapter(newsModelArrayList, newsModelArrayList.size());
        recyclerViewNews.setAdapter(recyclerViewNewsAdapter);
        recyclerViewNewsAdapter.notifyDataSetChanged();
        shimmerFrameLayoutNews.stopShimmer();
        shimmerFrameLayoutNews.setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        super.onResume();
        setRecyclerView();
        shimmerFrameLayoutNews.startShimmer();
    }

    @Override
    public void onPause() {
        shimmerFrameLayoutNews.stopShimmer();
        super.onPause();
    }
}
