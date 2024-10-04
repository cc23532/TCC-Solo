package com.example.solo.WorkoutSection;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.solo.R;
import com.example.solo.Util.URL;

public class MuscleHomeActivity extends AppCompatActivity {
    private static final String BASE_URL = new URL().getURL();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muscle_home);

    }
}