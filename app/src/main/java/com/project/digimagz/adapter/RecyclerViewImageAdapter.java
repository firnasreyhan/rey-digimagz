package com.project.digimagz.adapter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.project.digimagz.Constant;
import com.project.digimagz.R;
import com.project.digimagz.model.VideoModel;
import com.project.digimagz.view.activity.DetailVideoActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class RecyclerViewImageAdapter extends RecyclerView.Adapter<RecyclerViewImageAdapter.ViewHolder> {

    private ArrayList<String> stringArrayList;
    private String newsImage, typeNews, idNews;

    public RecyclerViewImageAdapter(ArrayList<String> stringArrayList, String typeNews, String idNews) {
        this.stringArrayList = stringArrayList;
        this.typeNews = typeNews;
        this.idNews = idNews;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_image, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        if (typeNews.equalsIgnoreCase("Berita")) {
            newsImage = Constant.URL_IMAGE_NEWS + stringArrayList.get(0);
        } else if (typeNews.equalsIgnoreCase("Artikel")) {
            newsImage = Constant.URL_IMAGE_NEWS + stringArrayList.get(0);
        } else if (typeNews.equalsIgnoreCase("Galeri")) {
            newsImage = Constant.URL_IMAGE_GALLERY + idNews + "/" + stringArrayList.get(0);
        }

        Glide.with(holder.itemView.getContext())
                .load(newsImage)
                .into(holder.imageViewImage);
    }

    @Override
    public int getItemCount() {
        return stringArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageViewImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewImage = itemView.findViewById(R.id.imageViewImage);
        }
    }
}
