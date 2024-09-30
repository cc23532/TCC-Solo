package com.example.solo.UserSection;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.example.solo.R;
import com.example.solo.Util.URL;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class UpdateHabitsActivity extends AppCompatActivity {

    private static final String TAG = "UpdateHabitsActivity"; // Tag para logs

    private EditText input_workBegin, input_workEnd, input_studyBegin, input_studyEnd, input_workoutBegin, input_workoutEnd, input_sleepBegin, input_sleepEnd;
    private Switch switchWork, switchStudy, switchWorkout;
    private RadioGroup radioGroupSmoker;
    private Button btnUpdateHabits, btnCancel;
    private RequestQueue requestQueue;
    private int idUser;

<<<<<<< HEAD:teste/solo/app/src/main/java/com/example/solo/UpdateHabitsActivity.java
    // private String BASE_URL = "http://10.0.2.2:8080";

    // ngrok
    private String BASE_URL = "https://2930-143-106-200-95.ngrok-free.app";
=======
    private static final String BASE_URL = new URL().getURL();
>>>>>>> 0562a13faf09441a381e1c7bc96690ae3d593b25:teste/solo/app/src/main/java/com/example/solo/UserSection/UpdateHabitsActivity.java

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_habits);

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
        radioGroupSmoker = findViewById(R.id.radioGroupSmoker);
        btnUpdateHabits = findViewById(R.id.btnUpdateHabits);
        btnCancel = findViewById(R.id.btnCancel);

        // Inicializando a fila de requisições
        requestQueue = Volley.newRequestQueue(this);

        // Obtendo o idUser salvo em SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE);
        idUser = sharedPreferences.getInt("idUser", -1); // Recupera o idUser salvo
        Log.d(TAG, "idUser recuperado: " + idUser);

        if(idUser != -1){
            fetchUserHabits(idUser);

            btnUpdateHabits.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateHabits(idUser);
                }
            });
            btnCancel.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    finish();
                }
            });
        }
    }

    private void fetchUserHabits(int idUser){
        String url = BASE_URL + "/habits/" + idUser;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String isWork = response.getString("work");
                            String workBegin = response.getString("workBegin");
                            String workEnd = response.getString("workEnd");
                            String isStudy = response.getString("study");
                            String studyBegin = response.getString("studyBegin");
                            String studyEnd = response.getString("studyEnd");
                            String isWorkout = response.getString("workout");
                            String workoutBegin = response.getString("workoutBegin");
                            String workoutEnd = response.getString("workoutEnd");
                            String sleepBegin = response.getString("sleepBegin");
                            String sleepEnd = response.getString("sleepEnd");
                            String isSmoker = response.getString("smoke");

                            // Set data to TextViews
                            input_workBegin.setText(workBegin);
                            input_workEnd.setText(workEnd);
                            input_studyBegin.setText(studyBegin);
                            input_studyEnd.setText(studyEnd);
                            input_workoutBegin.setText(workoutBegin);
                            input_workoutEnd.setText(workoutEnd);
                            input_sleepBegin.setText(sleepBegin);
                            input_sleepEnd.setText(sleepEnd);

                            if (isWork.equalsIgnoreCase("true")) {
                                switchWork.setChecked(true);
                            } else {
                                switchWork.setChecked(false);

                            }

                            if (isStudy.equalsIgnoreCase("true")) {
                                switchStudy.setChecked(true);
                            } else {
                                switchStudy.setChecked(false);
                            }

                            if (isWorkout.equalsIgnoreCase("true")) {
                                switchWorkout.setChecked(true);
                            } else {
                                switchWorkout.setChecked(false);
                            }

                            if (isSmoker.equalsIgnoreCase("true")) {
                                radioGroupSmoker.check(R.id.rbSmokerTrue);
                            } else {
                                radioGroupSmoker.check(R.id.rbSmokerFalse);
                            }

                        } catch (Exception e) {
                            Log.e(TAG, "Erro ao processar a resposta JSON", e);
                            Toast.makeText(UpdateHabitsActivity.this, "Erro ao processar os dados do usuário.", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Erro na requisição: " + error.toString());
                        Toast.makeText(UpdateHabitsActivity.this, "Erro ao obter os dados do usuário.", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }

    private void updateHabits(int idUser){
        // Obtendo os dados dos campos
        String workBegin = input_workBegin.getText().toString().trim();
        String workEnd = input_workEnd.getText().toString().trim();
        String studyBegin = input_studyBegin.getText().toString().trim();
        String studyEnd = input_studyEnd.getText().toString().trim();
        String workoutBegin = input_workoutBegin.getText().toString().trim();
        String workoutEnd = input_workoutEnd.getText().toString().trim();
        String sleepBegin = input_sleepBegin.getText().toString().trim();
        String sleepEnd = input_sleepEnd.getText().toString().trim();
        RadioButton rbSmokerTrue = findViewById(R.id.rbSmokerTrue);
        boolean isSmoker = rbSmokerTrue.isChecked();

        Log.d(TAG, "Dados obtidos: workBegin=" + workBegin + ", workEnd=" + workEnd + ", studyBegin=" + studyBegin + ", studyEnd=" + studyEnd + ", workoutBegin=" + workoutBegin + ", workoutEnd=" + workoutEnd + ", sleepBegin=" + sleepBegin + ", sleepEnd=" + sleepEnd + ", isSmoker=" + isSmoker);

        // Verificar se os campos obrigatórios estão preenchidos
        if (TextUtils.isEmpty(sleepBegin) || TextUtils.isEmpty(sleepEnd)) {
            Toast.makeText(this, "Os horários de sono devem ser preenchidos", Toast.LENGTH_SHORT).show();
            Log.w(TAG, "Campos obrigatórios não preenchidos: sleepBegin ou sleepEnd");
            return;
        }

        JSONObject habitsData = new JSONObject();
        try {
            habitsData.put("work", switchWork.isChecked());
            habitsData.put("workBegin", workBegin);
            habitsData.put("workEnd", workEnd);
            habitsData.put("study", switchStudy.isChecked());
            habitsData.put("studyBegin", studyBegin);
            habitsData.put("studyEnd", studyEnd);
            habitsData.put("workout", switchWorkout.isChecked());
            habitsData.put("workoutBegin", workoutBegin);
            habitsData.put("workoutEnd", workoutEnd);
            habitsData.put("sleepBegin", sleepBegin);
            habitsData.put("sleepEnd", sleepEnd);
            habitsData.put("smoke", isSmoker);
            Log.d(TAG, "Objeto JSON criado: " + habitsData.toString());
        } catch (JSONException e) {
            Toast.makeText(this, "Erro ao criar os dados de habitos", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Erro ao criar o objeto JSON", e);
            return;
        }

        // Construindo a URL com o idUser
        String url = BASE_URL + "/habits/" + idUser;
        Log.d(TAG, "URL da requisição: " + url);

        // Criando a requisição POST
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, habitsData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(UpdateHabitsActivity.this, "Habitos registrados com sucesso!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Resposta da requisição: " + response.toString());
                        Intent intent = new Intent(UpdateHabitsActivity.this, ProfileActivity.class);
                        startActivity(intent);
                        finish();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String errorMessage = "Erro ao registrar habitos: " + error.toString();
                        Toast.makeText(UpdateHabitsActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                        Log.e(TAG, "Detalhes do erro: ", error);
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        // Adicionando a requisição à fila
        requestQueue.add(jsonObjectRequest);
    }
}