package com.example.solo.WorkoutSection;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.solo.R;

public class PopUpWorkoutActivity extends AppCompatActivity {
    private Button btnCardioHome, btnMuscleHome;
    private static final String TAG = "PopUpWorkoutActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate called");
        setContentView(R.layout.activity_popup_workout);

        btnCardioHome = findViewById(R.id.btnCardioHome);
        btnMuscleHome = findViewById(R.id.btnMuscleHome);

        btnCardioHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PopUpWorkoutActivity.this, "Cardio Home Clicked", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "CardioHomeClicked");
                Intent intent = new Intent(PopUpWorkoutActivity.this, CardioHomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btnMuscleHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(PopUpWorkoutActivity.this, "Muscle Home Clicked", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "MuscleHomeClicked");
                Intent intent =  new Intent(PopUpWorkoutActivity.this, MuscleHomeActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}