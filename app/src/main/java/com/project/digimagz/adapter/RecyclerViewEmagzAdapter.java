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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.project.digimagz.Constant;
import com.project.digimagz.R;
import com.project.digimagz.model.EmagzModel;
import com.project.digimagz.view.activity.DetailVideoActivity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class RecyclerViewEmagzAdapter extends RecyclerView.Adapter<RecyclerViewEmagzAdapter.ViewHolder> {

    private ArrayList<EmagzModel> emagzModelArrayList;
    private Context context;
    private SimpleDateFormat simpleDateFormat;
    private Date date;

//    private boolean isPaused = false;


    public RecyclerViewEmagzAdapter(ArrayList<EmagzModel> emagzModelArrayList, Context context) {
        this.emagzModelArrayList = emagzModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_emagz, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        final EmagzModel dataModel = emagzModelArrayList.get(position);

        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = simpleDateFormat.parse(dataModel.getDateUploaded().substring(0, 10));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Log.e("date", dataModel.getDateUploaded());

        String imageUrl = Constant.URL_IMAGE_EMAGZ + dataModel.getThumbnail();

        Glide.with(context)
                .load(imageUrl)
                .into(holder.imageViewThumbnailEmagz);

        holder.materialButtonDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Downloading...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return emagzModelArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageViewThumbnailEmagz;
        private MaterialButton materialButtonDownload;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageViewThumbnailEmagz = itemView.findViewById(R.id.imageViewEmagz);
            materialButtonDownload = itemView.findViewById(R.id.materialButtonDownload);
        }
    }
}
