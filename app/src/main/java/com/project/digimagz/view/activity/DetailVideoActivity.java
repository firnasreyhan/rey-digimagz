package com.project.digimagz.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.InputDevice;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.project.digimagz.R;

public class DetailVideoActivity extends AppCompatActivity {

    private WebView webViewVideo;
    private ProgressBar progressBarVideo;
    private String youtubeId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_video);

        Intent intent = getIntent();
        youtubeId = intent.getStringExtra("youtubeId");

        String url = "<iframe width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/" + youtubeId + "?autoplay=1\" frameborder=\"0\" allow=\"accelerometer;  autoplay; encrypted-media; gyroscope; picture-in-picture\" allowfullscreen></iframe>";
        webViewVideo = findViewById(R.id.webViewVideo);
        progressBarVideo = findViewById(R.id.progressBarVideo);

        webViewVideo.getSettings().setJavaScriptEnabled(true);
        webViewVideo.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webViewVideo.getSettings().setPluginState(WebSettings.PluginState.ON);
        //webViewVideo.setWebChromeClient(new WebChromeClient());
        webViewVideo.loadData(url, "text/html" , "utf-8" );
        webViewVideo.setWebViewClient(new AutoPlayVideoWebViewClient());
        //webViewVideo.loadUrl("javascript:(function() { document.getElementsByTagName('video')[0].play(); })()");
    }

    private class AutoPlayVideoWebViewClient extends WebViewClient {

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            // mimic onClick() event on the center of the WebView

            long delta = 100;
            long downTime = SystemClock.uptimeMillis();
            float x = view.getLeft() + (view.getWidth()/2);
            float y = view.getTop() + (view.getHeight()/2);

            MotionEvent tapDownEvent = MotionEvent.obtain(downTime, downTime + delta, MotionEvent.ACTION_DOWN, x, y, 0);
            tapDownEvent.setSource(InputDevice.SOURCE_CLASS_POINTER);
            MotionEvent tapUpEvent = MotionEvent.obtain(downTime, downTime + delta + 2, MotionEvent.ACTION_UP, x, y, 0);
            tapUpEvent.setSource(InputDevice.SOURCE_CLASS_POINTER);

            view.dispatchTouchEvent(tapDownEvent);
            view.dispatchTouchEvent(tapUpEvent);

            //ShowHide
            progressBarVideo.setVisibility(View.GONE);
        }
    }
}
