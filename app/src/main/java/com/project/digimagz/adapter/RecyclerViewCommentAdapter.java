package com.project.digimagz.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
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
    private Context context;
    SimpleDateFormat simpleDateFormat;
    Date date;

    public static final String INTENT_PARAM_KEY_NEWS_DATA = "INTENT_PARAM_KEY_NEWS_DATA";

    public RecyclerViewCommentAdapter(ArrayList<CommentModel> commentModelArrayList, Context context) {
        this.commentModelArrayList = commentModelArrayList;
        this.context = context;
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

        if (commentModel.getProfilpicUrl() != null) {
            Glide.with(context)
                    .asBitmap()
                    .load(commentModel.getProfilpicUrl())
                    .placeholder(R.color.chef)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .into(holder.circleImageViewComment);
        }

        holder.textViewCommentUser.setText(commentModel.getEmail());
        holder.textViewCommentContent.setText(commentModel.getCommentText());
        holder.textViewCommentDate.setText(DateFormat.getDateInstance(DateFormat.LONG, new Locale("in", "ID")).format(date));

        if (commentModel.getAdminReply() != null) {
            holder.linearLayoutReply.setVisibility(View.VISIBLE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                holder.textViewCommentContentAdmin.setText(Html.fromHtml(commentModel.getAdminReply(), Html.FROM_HTML_MODE_COMPACT));
            } else {
                holder.textViewCommentContentAdmin.setText(Html.fromHtml(commentModel.getAdminReply()));
            }
        }

    }

    public void add(CommentModel newComment){
        commentModelArrayList.add(newComment);
        notifyItemInserted(commentModelArrayList.size()-1);
        notifyItemRangeChanged(0, commentModelArrayList.size()-1);
    }

    @Override
    public int getItemCount() {
        return commentModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView circleImageViewComment;
        private TextView textViewCommentUser, textViewCommentContent, textViewCommentDate, textViewCommentContentAdmin;
        private LinearLayout linearLayoutReply;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageViewComment = itemView.findViewById(R.id.circleImageViewComment);
            textViewCommentUser = itemView.findViewById(R.id.textViewCommentUser);
            textViewCommentContent = itemView.findViewById(R.id.textViewCommentContent);
            textViewCommentDate = itemView.findViewById(R.id.textViewCommentDate);
            textViewCommentContentAdmin = itemView.findViewById(R.id.textViewCommentContentAdmin);
            linearLayoutReply = itemView.findViewById(R.id.linearLayoutReply);
        }
    }
}
