package com.project.digimagz.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.project.digimagz.Constant;
import com.project.digimagz.R;
import com.project.digimagz.api.InitRetrofit;
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
    private FirebaseUser firebaseUser;

    public static final String INTENT_PARAM_KEY_NEWS_COVER_STORY_DATA = "INTENT_PARAM_KEY_NEWS_DATA";

    private InitRetrofit initRetrofitLike;

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
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final NewsCoverStoryModel newsCoverStoryModel = newsCoverStoryModelArrayList.get(position);

        if (newsCoverStoryModel.getNewsImage() != null) {
            if (URLUtil.isValidUrl(newsCoverStoryModel.getNewsImage().get(0))) {
                newsImage = newsCoverStoryModel.getNewsImage().get(0);
            } else {
                if (newsCoverStoryModel.getNameCategory().equalsIgnoreCase("Berita")) {
                    newsImage = Constant.URL_IMAGE_NEWS + newsCoverStoryModel.getNewsImage().get(0);
                } else if (newsCoverStoryModel.getNameCategory().equalsIgnoreCase("Artikel")) {
                    newsImage = Constant.URL_IMAGE_NEWS + newsCoverStoryModel.getNewsImage().get(0);
                } else if (newsCoverStoryModel.getNameCategory().equalsIgnoreCase("Galeri")) {
                    newsImage = Constant.URL_IMAGE_GALLERY + newsCoverStoryModel.getIdNews() + "/" + newsCoverStoryModel.getNewsImage().get(0);
                }
            }
            Glide.with(holder.itemView.getContext())
                    .load(newsImage)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .placeholder(R.drawable.mqdefault)
                    .into(holder.imageViewNews);
        }

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        initRetrofitLike = new InitRetrofit();

        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date = simpleDateFormat.parse(newsCoverStoryModel.getDateNews());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (firebaseUser != null) {
            initRetrofitLike.getLikeFromApi(newsCoverStoryModel.getIdNews(), firebaseUser.getEmail());
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

        if (newsCoverStoryModel.getTitleNews() != null) {
            holder.textViewTitle.setText(newsCoverStoryModel.getTitleNews());
        }
        holder.textViewLike.setText(String.valueOf(newsCoverStoryModel.getLikes()));
        holder.textViewComment.setText(String.valueOf(newsCoverStoryModel.getComments()));
        holder.textViewDate.setText(DateFormat.getDateInstance(DateFormat.SHORT, new Locale("in", "ID")).format(date));

        if (newsCoverStoryModel.getCheckLike() == 1){
            holder.ivLiked.setVisibility(View.VISIBLE);
            holder.ivNotLike.setVisibility(View.GONE);
        }else{
            holder.ivLiked.setVisibility(View.GONE);
            holder.ivNotLike.setVisibility(View.VISIBLE);
        }
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
