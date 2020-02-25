package com.project.digimagz.adapter;

import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.URLUtil;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.android.material.button.MaterialButton;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.project.digimagz.Constant;
import com.project.digimagz.R;
import com.project.digimagz.model.EmagzModel;
import com.project.digimagz.view.activity.DetailVideoActivity;

import java.io.File;
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

        Log.e("position", String.valueOf(position));
        final String urlDownload = Constant.URL_DOWNLOAD_EMAGZ + dataModel.getIdEmagz();
        Log.e("idEmagz", dataModel.getIdEmagz());

        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = simpleDateFormat.parse(dataModel.getDateUploaded().substring(0, 10));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Log.e("date", dataModel.getDateUploaded());

        if (dataModel.getThumbnail() != null) {
            if (URLUtil.isValidUrl(dataModel.getThumbnail())) {
                Glide.with(context)
                        .load(dataModel.getThumbnail())
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .placeholder(R.drawable.mqdefault)
                        .into(holder.imageViewThumbnailEmagz);
            } else {
                String imageUrl = Constant.URL_IMAGE_EMAGZ + dataModel.getThumbnail();
                Glide.with(context)
                        .load(imageUrl)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .skipMemoryCache(true)
                        .placeholder(R.drawable.mqdefault)
                        .into(holder.imageViewThumbnailEmagz);
            }
        }

        holder.materialButtonDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Toast.makeText(context, "Downloading...", Toast.LENGTH_SHORT).show();
                Log.e("urlDownload", urlDownload);
                file_download(urlDownload, dataModel);
//                DownloadManager downloadManager = (DownloadManager) v.getContext().getSystemService(v.getContext().DOWNLOAD_SERVICE);
//                Uri uri = Uri.parse(urlDownload);
//                DownloadManager.Request request = new DownloadManager.Request(uri);
//                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//                Long aLong = downloadManager.enqueue(request);
//                DownloadManager.Request request = new DownloadManager.Request(uri);
//                request.setTitle(dataModel.getName());
//                request.setDescription("Downloading");
//                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
//                request.setVisibleInDownloadsUi(false);
//                request.setDestinationUri(Uri.parse("file://Emagz/" + dataModel.getFile()));
//                downloadManager.enqueue(request);
            }
        });
    }

    public void file_download(String uRl, EmagzModel model) {
        File direct = new File(Environment.getExternalStorageDirectory()
                + "/Emagz");

        if (!direct.exists()) {
            direct.mkdirs();
        }

        DownloadManager mgr = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

        Uri downloadUri = Uri.parse(uRl);
        DownloadManager.Request request = new DownloadManager.Request(
                downloadUri);

        request.setAllowedNetworkTypes(
                DownloadManager.Request.NETWORK_WIFI
                        | DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false)
                .setTitle(model.getName())
                .setDescription("Downloading")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setVisibleInDownloadsUi(false)
                .setDestinationInExternalPublicDir("/Emagz", model.getFile());

        mgr.enqueue(request);

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
