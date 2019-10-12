package com.project.digimagz.adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.digimagz.R;
import com.project.digimagz.model.CommentModel;
import com.project.digimagz.model.NewsModel;
import com.project.digimagz.view.activity.DetailNewsActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewCommentAdapter extends RecyclerView.Adapter<RecyclerViewCommentAdapter.ViewHolder> {

    private ArrayList<CommentModel> commentModelArrayList;
    SimpleDateFormat simpleDateFormat;
    Date date;

    public static final String INTENT_PARAM_KEY_NEWS_DATA = "INTENT_PARAM_KEY_NEWS_DATA";

    public RecyclerViewCommentAdapter(ArrayList<CommentModel> commentModelArrayList) {
        this.commentModelArrayList = commentModelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_comment, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final CommentModel commentModel = commentModelArrayList.get(position);

        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            date = simpleDateFormat.parse(commentModel.getDateComment());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        holder.textViewCommentUser.setText(commentModel.getEmail());
        holder.textViewCommentContent.setText(commentModel.getCommentText());
        holder.textViewCommentDate.setText(DateFormat.getDateInstance(DateFormat.LONG, new Locale("in", "ID")).format(date));

    }

    @Override
    public int getItemCount() {
        return commentModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView circleImageViewComment;
        private TextView textViewCommentUser, textViewCommentContent, textViewCommentDate;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageViewComment = itemView.findViewById(R.id.circleImageViewComment);
            textViewCommentUser = itemView.findViewById(R.id.textViewCommentUser);
            textViewCommentContent = itemView.findViewById(R.id.textViewCommentContent);
            textViewCommentDate = itemView.findViewById(R.id.textViewCommentDate);
        }
    }
}
