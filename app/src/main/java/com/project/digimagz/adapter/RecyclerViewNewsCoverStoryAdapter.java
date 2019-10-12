package com.project.digimagz.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.project.digimagz.Constant;
import com.project.digimagz.R;
import com.project.digimagz.model.NewsCoverStoryModel;
import com.project.digimagz.model.NewsModel;
import com.project.digimagz.view.activity.DetailNewsActivity;
import com.project.digimagz.view.activity.DetailNewsCoverStoryActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class RecyclerViewNewsCoverStoryAdapter extends RecyclerView.Adapter<RecyclerViewNewsCoverStoryAdapter.ViewHolder> {

    private ArrayList<NewsCoverStoryModel> newsCoverStoryModelArrayList;
    private SimpleDateFormat simpleDateFormat;
    private Date date;
    private String newsImage;

    public static final String INTENT_PARAM_KEY_NEWS_COVER_STORY_DATA = "INTENT_PARAM_KEY_NEWS_DATA";

    public RecyclerViewNewsCoverStoryAdapter(ArrayList<NewsCoverStoryModel> newsCoverStoryModelArrayList) {
        this.newsCoverStoryModelArrayList = newsCoverStoryModelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_news, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final NewsCoverStoryModel newsCoverStoryModel = newsCoverStoryModelArrayList.get(position);

        newsImage = Constant.URL_IMAGE_NEWS + newsCoverStoryModel.getNewsImage();

        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date = simpleDateFormat.parse(newsCoverStoryModel.getDateNews());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.textViewTitle.setText(newsCoverStoryModel.getTitleNews());
        holder.textViewLike.setText(String.valueOf(newsCoverStoryModel.getLikes()));
        holder.textViewComment.setText(String.valueOf(newsCoverStoryModel.getComments()));
        holder.textViewDate.setText(DateFormat.getDateInstance(DateFormat.SHORT, new Locale("in", "ID")).format(date));
        Glide.with(holder.itemView.getContext())
                .load(newsImage)
                .into(holder.imageViewNews);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DetailNewsCoverStoryActivity.class);
                intent.putExtra(INTENT_PARAM_KEY_NEWS_COVER_STORY_DATA, newsCoverStoryModel);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsCoverStoryModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewTitle, textViewDate, textViewComment, textViewLike;
        private ImageView imageViewNews;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewComment = itemView.findViewById(R.id.textViewComment);
            textViewLike = itemView.findViewById(R.id.textViewLike);
            imageViewNews = itemView.findViewById(R.id.imageViewNews);
        }
    }
}
