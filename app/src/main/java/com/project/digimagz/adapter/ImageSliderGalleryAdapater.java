package com.project.digimagz.adapter;

import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.project.digimagz.Constant;
import com.project.digimagz.R;
import com.project.digimagz.model.NewsModel;
import com.project.digimagz.view.activity.DetailNewsActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ImageSliderGalleryAdapater extends PagerAdapter {

    public static final String INTENT_PARAM_KEY_NEWS_DATA = "INTENT_PARAM_KEY_NEWS_DATA";

    private ArrayList<String> stringArrayList;
    private String newsImage, idNews;

    public ImageSliderGalleryAdapater(ArrayList<String> stringArrayList, String idNews) {
        this.stringArrayList = stringArrayList;
        this.idNews = idNews;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return stringArrayList.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup view, final int position) {
        View imageLayout = LayoutInflater.from(view.getContext()).inflate(R.layout.list_image, view, false);

        assert imageLayout != null;
        final ImageView imageView = imageLayout.findViewById(R.id.imageViewImage);

        if (stringArrayList.get(position) != null) {
            if (URLUtil.isValidUrl(stringArrayList.get(position))) {
                newsImage = stringArrayList.get(position);
            } else {
                newsImage = Constant.URL_IMAGE_GALLERY + idNews + "/" + stringArrayList.get(position);
            }
            Glide.with(view.getContext())
                    .load(newsImage)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .placeholder(R.drawable.mqdefault)
                    .into(imageView);
        }

        view.addView(imageLayout, 0);

        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }

}