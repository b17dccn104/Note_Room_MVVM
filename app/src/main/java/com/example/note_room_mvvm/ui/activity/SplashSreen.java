package com.example.note_room_mvvm.ui.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.note_room_mvvm.R;

import java.util.Objects;

public class SplashSreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide();
        setContentView(R.layout.activity_splash_sreen);
        handleProgressBarOpenApp();
    }

    private void handleProgressBarOpenApp() {
        new Handler().postDelayed(() -> {
            startActivity(new Intent(SplashSreen.this, MainActivity.class));
            finish();
        }, 2000);
    }
}