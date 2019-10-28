package com.project.digimagz.view.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.project.digimagz.R;

public class ErrorActivity extends AppCompatActivity {

    private MaterialButton materialButtonCobaLagi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_error);

        materialButtonCobaLagi = findViewById(R.id.materialButtonCobaLagi);

        materialButtonCobaLagi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), SplashActivity.class));
                finish();
            }
        });
    }
}
