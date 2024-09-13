package com.example.solo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

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

    private EditText editTextWorkBegin, editTextWorkEnd, editTextStudyBegin, editTextStudyEnd,
            editTextWorkoutBegin, editTextWorkoutEnd, editTextSleepBegin, editTextSleepEnd;
    private Switch switchWork, switchStudy, switchWorkout;
    private RadioGroup radioGroupSmoke;
    private RadioButton radioButtonSmokeYes, radioButtonSmokeNo;
    private Button btnRegisterHabits;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_habits);

        switchWork = findViewById(R.id.switchWork);
        editTextWorkBegin = findViewById(R.id.editTextWorkBegin);
        editTextWorkEnd = findViewById(R.id.editTextWorkEnd);
        switchStudy = findViewById(R.id.switchStudy);
        editTextStudyBegin = findViewById(R.id.editTextStudyBegin);
        editTextStudyEnd = findViewById(R.id.editTextStudyEnd);
        switchWorkout = findViewById(R.id.switchWorkout);
        editTextWorkoutBegin = findViewById(R.id.editTextWorkoutBegin);
        editTextWorkoutEnd = findViewById(R.id.editTextWorkoutEnd);
        editTextSleepBegin = findViewById(R.id.editTextSleepBegin);
        editTextSleepEnd = findViewById(R.id.editTextSleepEnd);
        radioGroupSmoke = findViewById(R.id.radioGroupSmoke);
        radioButtonSmokeYes = findViewById(R.id.radioButtonSmokeYes);
        radioButtonSmokeNo = findViewById(R.id.radioButtonSmokeNo);
        btnRegisterHabits = findViewById(R.id.btnRegisterHabits);

        requestQueue = Volley.newRequestQueue(this);
        
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String idUser = sharedPreferences.getString("idUser", null);
        
        Log.d("RegisterHabits", "idUser : " + idUser);
        
        if (idUser == null) {
            Toast.makeText(RegisterHabitsActivity.this, "Usuário não autenticado.", Toast.LENGTH_SHORT).show();
            return; 
        }
        
        switchVisibilidade();

        btnRegisterHabits.setOnClickListener(v -> registerHabits(idUser));
    }

    private void switchVisibilidade() {
        switchWork.setOnCheckedChangeListener((buttonView, isChecked) -> {
            editTextWorkBegin.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            editTextWorkEnd.setVisibility(isChecked ? View.VISIBLE : View.GONE);
        });

        switchStudy.setOnCheckedChangeListener((buttonView, isChecked) -> {
            editTextStudyBegin.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            editTextStudyEnd.setVisibility(isChecked ? View.VISIBLE : View.GONE);
        });

        switchWorkout.setOnCheckedChangeListener((buttonView, isChecked) -> {
            editTextWorkoutBegin.setVisibility(isChecked ? View.VISIBLE : View.GONE);
            editTextWorkoutEnd.setVisibility(isChecked ? View.VISIBLE : View.GONE);
        });
    }

    private void registerHabits(String idUser) {
        // Obter os horários inseridos
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("work", switchWork.isChecked() ? 1 : 0);
            jsonBody.put("workBegin", editTextWorkBegin.getText().toString().trim());
            jsonBody.put("workEnd", editTextWorkEnd.getText().toString().trim());
            
            jsonBody.put("study", switchStudy.isChecked() ? 1 : 0);
            jsonBody.put("studyBegin", editTextStudyBegin.getText().toString().trim());
            jsonBody.put("studyEnd", editTextStudyEnd.getText().toString().trim());
            
            jsonBody.put("workout", switchWorkout.isChecked() ? 1 : 0);
            jsonBody.put("workoutBegin", editTextWorkoutBegin.getText().toString().trim());
            jsonBody.put("workoutEnd", editTextWorkoutEnd.getText().toString().trim());
            
            jsonBody.put("sleepBegin", editTextSleepBegin.getText().toString().trim());
            jsonBody.put("sleepEnd", editTextSleepEnd.getText().toString().trim());
            
            jsonBody.put("smoke", radioGroupSmoke.getCheckedRadioButtonId() == R.id.radioButtonSmokeYes ? 1 : 0);
            
            Log.d("RegisterHabits", "JSON enviado: " + jsonBody.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        // emulador
        // String url = "10.2.2:3000/habits/" + idUser;
        String url = "https://cc92-143-106-203-198.ngrok-free.app/habits/" + idUser;
        
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.PUT, url, jsonBody, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(RegisterHabitsActivity.this, "Hábitos registrados com sucesso.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String errorMessage = "Erro ao registrar hábitos.";
                        if (error.networkResponse != null) {
                            errorMessage += " Código de erro: " + error.networkResponse.statusCode;
                        }
                        Toast.makeText(RegisterHabitsActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                        Log.e("RegisterHabits", "Erro na requisição: ", error);
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
