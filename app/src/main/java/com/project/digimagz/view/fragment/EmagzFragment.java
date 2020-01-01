package com.project.digimagz.view.fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.project.digimagz.R;
import com.project.digimagz.adapter.RecyclerViewEmagzAdapter;
import com.project.digimagz.api.InitRetrofit;
import com.project.digimagz.model.EmagzModel;

import java.util.ArrayList;

public class EmagzFragment extends Fragment {

    private SwipeRefreshLayout swipeRefreshLayout;
    private NestedScrollView nestedScrollViewEmagz;
    private RecyclerView recyclerViewEmagz;
    private ShimmerFrameLayout shimmerFrameLayout;
    private RecyclerViewEmagzAdapter recyclerViewEmagzAdapter;

    private InitRetrofit initRetrofit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_emagz, container, false);

        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        nestedScrollViewEmagz = view.findViewById(R.id.nestedScrollViewEmagz);
        recyclerViewEmagz = view.findViewById(R.id.recyclerViewEmagz);
        shimmerFrameLayout = view.findViewById(R.id.shimmer_view_container);

        initRetrofit = new InitRetrofit();

        setRecyclerView();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setRecyclerView();
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2500);
            }
        });

        return view;
    }

    private void setRecyclerView() {
        initRetrofit.getEmagzFromApi();
        initRetrofit.setOnRetrofitSuccess(new InitRetrofit.OnRetrofitSuccess() {
            @Override
            public void onSuccessGetData(ArrayList arrayList) {
                if (!arrayList.isEmpty()) {
                    Log.i("Size", String.valueOf(arrayList.size()));
                    showRecyclerListViewEmagz(arrayList);
                } else {
                    Log.i("Size", String.valueOf(arrayList.size()));
                }
            }
        });
    }

    private void showRecyclerListViewEmagz(ArrayList<EmagzModel> list) {
        recyclerViewEmagz.setHasFixedSize(true);
        recyclerViewEmagz.setLayoutManager(new GridLayoutManager(getContext(), 2));
        recyclerViewEmagzAdapter = new RecyclerViewEmagzAdapter(list, getContext());
        recyclerViewEmagz.setAdapter(recyclerViewEmagzAdapter);
        shimmerFrameLayout.stopShimmer();
        shimmerFrameLayout.setVisibility(View.GONE);
    }

    public void scrollUp(){
        nestedScrollViewEmagz.smoothScrollTo(0,0);
    }

    @Override
    public void onResume() {
        super.onResume();
        shimmerFrameLayout.startShimmer();
    }

    @Override
    public void onPause() {
        shimmerFrameLayout.stopShimmer();
        super.onPause();
    }

}
