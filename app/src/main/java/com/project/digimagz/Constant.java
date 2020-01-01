package com.project.digimagz;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

public class Constant {
//    public static final String URL_IMAGE_NEWS = "http://digimon.kristomoyo.com/images/news/";
//    public static final String URL_IMAGE_STORY = "http://digimon.kristomoyo.com/images/coverstory/";
//    public static final String URL_IMAGE_GALLERY = "http://digimon.kristomoyo.com/images/gallery/";
//    public static final String URL_IMAGE_EMAGZ = "http://digimon.kristomoyo.com/emagazine/thumbnail/";
//    public static final String URL_DOWNLOAD_EMAGZ = "http://digimon.kristomoyo.com/api/emagz/download/";
//    public static final String URL = "http://digimon.kristomoyo.com/";

    public static final String URL_IMAGE_NEWS = "http://pn10mobprd.ptpn10.co.id:8598/images/news/";
    public static final String URL_IMAGE_STORY = "http://pn10mobprd.ptpn10.co.id:8598/images/coverstory/";
    public static final String URL_IMAGE_GALLERY = "http://pn10mobprd.ptpn10.co.id:8598/images/gallery/";
    public static final String URL_IMAGE_EMAGZ = "http://pn10mobprd.ptpn10.co.id:8598/emagazine/thumbnail/";
    public static final String URL_DOWNLOAD_EMAGZ = "http://pn10mobprd.ptpn10.co.id:8598/api/emagz/download/";
    public static final String URL = "http://pn10mobprd.ptpn10.co.id:8598/";

    public static int getOrientationFromURI(Context context, Uri contentUri) {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            return getOrientationForV19AndUp(context, contentUri);
        } else {
            return getOrientationForPreV19(context, contentUri);
        }
    }

    public static int getOrientationForPreV19(Context context, Uri contentUri) {
        int orient = 0;

        String[] proj = { MediaStore.Images.Media.ORIENTATION };
        String[] uri = contentUri.toString().split("/");
        String id = uri[uri.length-1];
        String sel = MediaStore.Images.Media._ID + "=?";
        Cursor cursor = context.getContentResolver().query(contentUri, proj, sel, new String[] {id}, null);
        if(cursor != null && cursor.moveToFirst()){
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.ORIENTATION);
            orient = cursor.getInt(column_index);
        }
        if(cursor != null)
            cursor.close();

        return orient;
    }

    public static int getOrientationForV19AndUp(Context context, Uri contentUri) {
        int orient = 0;

        String[] proj = { MediaStore.Images.Media.ORIENTATION };
        String[] uri = contentUri.toString().split("/");
        String id = uri[uri.length-1];
        String sel = MediaStore.Images.Media._ID + "=?";
        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, proj, sel, new String[] {id}, null);
        if(cursor != null && cursor.moveToFirst()){;
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.ORIENTATION);
            orient = cursor.getInt(column_index);
        }
        if(cursor != null)
            cursor.close();

        return orient;
    }
}

