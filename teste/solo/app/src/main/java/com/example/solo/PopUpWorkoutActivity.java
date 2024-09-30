package com.example.solo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class PopUpWorkoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_popup_workout);

        Button btnCardioWorkout = findViewById(R.id.btnCardioWorkout);

        btnCardioWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PopUpWorkoutActivity.this, CardioActivity.class);
                startActivity(intent);
                finish();
            }
        });


        Button btnWorkoutHome = findViewById(R.id.btnWorkoutHome);
        btnWorkoutHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
