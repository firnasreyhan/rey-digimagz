package com.project.digimagz.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

import com.google.android.material.button.MaterialButton;
import com.project.digimagz.R;

public class PrivacyPolicyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy_policy);

        WebView webView = findViewById(R.id.webViewPrivacyPolicy);
        MaterialButton materialButtonSetuju = findViewById(R.id.materialButtonSetuju);
        webView.loadUrl("file:///android_asset/privacy_policy.html");

        materialButtonSetuju.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
