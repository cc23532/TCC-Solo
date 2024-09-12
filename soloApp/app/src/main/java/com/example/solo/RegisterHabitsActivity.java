package com.example.solo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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

        // Inicializando os campos
        switchWork = findViewById(R.id.switchWork);
        input_workBegin = findViewById(R.id.input_workBegin);
        input_workEnd = findViewById(R.id.input_workEnd);
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
        
        requestQueue = Volley.newRequestQueue(this);

        // idUser salvo em SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String idUser = sharedPreferences.getString("idUser", null);

        btnRegisterHabits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (idUser != null) {
                    registerUserHabits(idUser);
                } else {
                    Toast.makeText(RegisterHabitsActivity.this, "Erro: usuário não registrado.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void registerUserHabits(String idUser) {
        String workBegin = input_workBegin.getText().toString().trim();
        String workEnd = input_workEnd.getText().toString().trim();
        String studyBegin = input_studyBegin.getText().toString().trim();
        String studyEnd = input_studyEnd.getText().toString().trim();
        String workoutBegin = input_workoutBegin.getText().toString().trim();
        String workoutEnd = input_workoutEnd.getText().toString().trim();
        String sleepBegin = input_sleepBegin.getText().toString().trim();
        String sleepEnd = input_sleepEnd.getText().toString().trim();
        boolean isSmoker = rbSmokerTrue.isChecked();

        if (TextUtils.isEmpty(sleepBegin) || TextUtils.isEmpty(sleepEnd)) {
            Toast.makeText(this, "Os horários de sono devem ser preenchidos", Toast.LENGTH_SHORT).show();
            return;
        }

        JSONObject habitsData = new JSONObject();
        try {
            habitsData.put("idUser", idUser);
            habitsData.put("workEnabled", switchWork.isChecked());
            habitsData.put("workBegin", workBegin);
            habitsData.put("workEnd", workEnd);
            habitsData.put("studyEnabled", switchStudy.isChecked());
            habitsData.put("studyBegin", studyBegin);
            habitsData.put("studyEnd", studyEnd);
            habitsData.put("workoutEnabled", switchWorkout.isChecked());
            habitsData.put("workoutBegin", workoutBegin);
            habitsData.put("workoutEnd", workoutEnd);
            habitsData.put("sleepBegin", sleepBegin);
            habitsData.put("sleepEnd", sleepEnd);
            habitsData.put("smoker", isSmoker);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Erro ao criar os dados de hábitos", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = "http://2804:868:d040:7790:9565:a1dc:bf04:9c31:3000/habits/register"; // Substitua pela URL correta

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, habitsData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(RegisterHabitsActivity.this, "Hábitos registrados com sucesso!", Toast.LENGTH_SHORT).show();
                        // Redirecionar para a LoginActivity após o registro
                        Intent intent = new Intent(RegisterHabitsActivity.this, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String errorMessage = "Erro ao registrar hábitos: " + error.toString();
                        Toast.makeText(RegisterHabitsActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                        Log.e("RegisterHabitsActivity", "Error details: ", error);
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        requestQueue.add(jsonObjectRequest);
    }
}
