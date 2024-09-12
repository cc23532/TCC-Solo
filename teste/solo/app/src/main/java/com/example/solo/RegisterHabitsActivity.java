package com.example.solo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class RegisterHabitsActivity extends AppCompatActivity {

    private EditText input_workBegin, input_workEnd, input_studyBegin, input_studyEnd, input_workoutBegin, input_workoutEnd, input_sleepBegin, input_sleepEnd;
    private Switch switchWork, switchStudy, switchWorkout;
    private RadioButton rbSmokerTrue, rbSmokerFalse;
    private Button btnRegisterHabits;
    private RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_habits);

        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        int registeredUser = sharedPreferences.getInt("registeredUser", -1); // -1 é um valor padrão caso o id não seja encontrado

        // Inicializando os campos
        switchWork = findViewById(R.id.switchWork);
        input_workBegin = findViewById(R.id.input_workBegin);
        input_studyEnd = findViewById(R.id.input_workEnd);
        switchStudy = findViewById(R.id.switchStudy);
        input_studyBegin = findViewById(R.id.input_studyBegin);
        input_studyEnd = findViewById(R.id.input_studyEnd);
        switchWorkout = findViewById(R.id.switchWorkout);
        input_workoutBegin = findViewById(R.id.input_workoutBegin);
        input_workoutEnd = findViewById(R.id.input_workoutEnd);
        input_sleepBegin = findViewById(R.id.input_sleepBegin);
        input_sleepEnd = findViewById(R.id.input_sleepEnd);
        rbSmokerTrue = findViewById(R.id.rbSmokerTrue);
        rbSmokerFalse = findViewById(R.id.rbSmokerFalse);
        btnRegisterHabits = findViewById(R.id.btnRegisterHabits);

        // Inicializando a fila de requisições
        requestQueue = Volley.newRequestQueue(this);

        btnRegisterHabits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUserHabits();
                Intent intent =  new Intent(RegisterHabitsActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    };

    private void registerUserHabits(){

    }

}
