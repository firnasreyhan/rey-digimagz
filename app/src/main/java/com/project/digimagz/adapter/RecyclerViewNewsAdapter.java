package com.project.digimagz.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.project.digimagz.Constant;
import com.project.digimagz.R;
import com.project.digimagz.api.InitRetrofit;
import com.project.digimagz.model.NewsModel;
import com.project.digimagz.view.activity.DetailNewsActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class RecyclerViewNewsAdapter extends RecyclerView.Adapter<RecyclerViewNewsAdapter.ViewHolder> {

    private ArrayList<NewsModel> newsModelArrayList;
    private SimpleDateFormat simpleDateFormat;
    private Date date;
    private String newsImage;
    private FirebaseUser firebaseUser;

    public static final String INTENT_PARAM_KEY_NEWS_DATA = "INTENT_PARAM_KEY_NEWS_DATA";

    private int size;

    public static int checkLike;
    private InitRetrofit initRetrofitLike;

    public RecyclerViewNewsAdapter(ArrayList<NewsModel> newsModelArrayList, int size) {
        this.newsModelArrayList = newsModelArrayList;
        this.size = size;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_news, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final NewsModel newsModel = newsModelArrayList.get(position);

        newsImage = Constant.URL_IMAGE_NEWS + newsModel.getNewsImage();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        initRetrofitLike = new InitRetrofit();

        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date = simpleDateFormat.parse(newsModel.getDateNews());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (firebaseUser != null) {
            initRetrofitLike.getLikeFromApi(newsModel.getIdNews(), firebaseUser.getEmail());
            initRetrofitLike.setOnRetrofitSuccess(new InitRetrofit.OnRetrofitSuccess() {
                @Override
                public void onSuccessGetData(ArrayList arrayList) {
                    if (arrayList.get(0).equals("Yes")) {
                        holder.ivLiked.setVisibility(View.VISIBLE);
                        holder.ivNotLike.setVisibility(View.GONE);
                    }else if (arrayList.get(0).equals("No")){
                        holder.ivLiked.setVisibility(View.GONE);
                        holder.ivNotLike.setVisibility(View.VISIBLE);
                    }
                }
            });
        }

        holder.textViewTitle.setText(newsModel.getTitleNews());
        holder.textViewLike.setText(String.valueOf(newsModel.getLikes()));
        holder.textViewComment.setText(String.valueOf(newsModel.getComments()));
        holder.textViewDate.setText(DateFormat.getDateInstance(DateFormat.SHORT, new Locale("in", "ID")).format(date));
        Glide.with(holder.itemView.getContext())
                .load(newsImage)
                .into(holder.imageViewNews);

        if (newsModel.getCheckLike() == 1){
            holder.ivLiked.setVisibility(View.VISIBLE);
            holder.ivNotLike.setVisibility(View.GONE);
        }else{
            holder.ivLiked.setVisibility(View.GONE);
            holder.ivNotLike.setVisibility(View.VISIBLE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DetailNewsActivity.class);
                intent.putExtra(INTENT_PARAM_KEY_NEWS_DATA, newsModel);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return size;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView textViewTitle, textViewDate, textViewComment, textViewLike;
        private ImageView imageViewNews, ivNotLike, ivLiked;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.textViewTitle);
            textViewDate = itemView.findViewById(R.id.textViewDate);
            textViewComment = itemView.findViewById(R.id.textViewComment);
            textViewLike = itemView.findViewById(R.id.textViewLike);
            imageViewNews = itemView.findViewById(R.id.imageViewNews);
            ivNotLike = itemView.findViewById(R.id.ivNotLike);
            ivLiked = itemView.findViewById(R.id.ivLiked);
        }
    }
}
