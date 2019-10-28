package com.project.digimagz.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.project.digimagz.R;
import com.project.digimagz.model.NewsModel;
import com.project.digimagz.view.activity.DetailNewsActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class ImageSliderAdapater extends PagerAdapter {

    public static final String INTENT_PARAM_KEY_NEWS_DATA = "INTENT_PARAM_KEY_NEWS_DATA";

    private ArrayList<NewsModel> newsModelArrayList;
    private LayoutInflater inflater;
    private Context context;

    private SimpleDateFormat simpleDateFormat;
    private Date date;

    public ImageSliderAdapater(Context context, ArrayList<NewsModel> newsModelArrayList){
        this.context = context;
        this.newsModelArrayList = newsModelArrayList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return newsModelArrayList.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup view, final int position) {
        View imageLayout = inflater.inflate(R.layout.list_image_slider, view, false);
        final NewsModel newsModel = newsModelArrayList.get(position);

        assert imageLayout != null;
        final ImageView imageView = imageLayout.findViewById(R.id.imageSlider);
        final TextView textViewTitle = imageLayout.findViewById(R.id.textViewTitle);
        final TextView textViewDate = imageLayout.findViewById(R.id.textViewDate);

        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date = simpleDateFormat.parse(newsModel.getDateNews());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Glide.with(context)
                .load("http://digimon.kristomoyo.com/images/news/" + newsModel.getNewsImage())
                .into(imageView);

        textViewTitle.setText(newsModel.getTitleNews());
        textViewDate.setText(DateFormat.getDateInstance(DateFormat.LONG, new Locale("in", "ID")).format(date));

        imageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DetailNewsActivity.class);
                intent.putExtra(INTENT_PARAM_KEY_NEWS_DATA, newsModel);
                view.getContext().startActivity(intent);
            }
        });

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
