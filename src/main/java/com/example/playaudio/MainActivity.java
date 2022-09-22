package com.example.playaudio;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.playaudio.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.app_container, new AudioFragment())
                .commit();


    }

}