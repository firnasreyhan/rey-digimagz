package com.project.digimagz.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.project.digimagz.Constant;
import com.project.digimagz.R;
import com.project.digimagz.model.StoryModel;
import com.project.digimagz.view.activity.DetailNewsActivity;
import com.project.digimagz.view.activity.DetailStoryActivity;

import java.util.ArrayList;

public class RecyclerViewStoryAdapter extends RecyclerView.Adapter<RecyclerViewStoryAdapter.ViewHolder> {

    public static final String INTENT_PARAM_KEY_STORY_DATA = "INTENT_PARAM_KEY_STORY_DATA";

    private ArrayList<StoryModel> storyModelArrayList;

    private String newsImage;

    public RecyclerViewStoryAdapter(ArrayList<StoryModel> storyModelArrayList) {
        this.storyModelArrayList = storyModelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_cover_story, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final StoryModel storyModel = storyModelArrayList.get(position);

        newsImage = Constant.URL_IMAGE_STORY + storyModel.getImageCoverStory();

        Glide.with(holder.itemView.getContext())
                .load(newsImage)
                .into(holder.imageViewStory);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), DetailStoryActivity.class);
                intent.putExtra(INTENT_PARAM_KEY_STORY_DATA, storyModel);
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return storyModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageViewStory;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewStory = itemView.findViewById(R.id.imageViewStory);
        }
    }
}
