package com.example.solo.Login_Register;

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
import com.example.solo.R;
import com.example.solo.Util.URL;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterHabitsActivity extends AppCompatActivity {

    private static final String TAG = "RegisterHabitsActivity"; // Tag para logs
    private static final String BASE_URL = new URL().getURL();

    private EditText input_workBegin, input_workEnd, input_studyBegin, input_studyEnd, input_workoutBegin, input_workoutEnd, input_sleepBegin, input_sleepEnd;
    private Switch switchWork, switchStudy, switchWorkout;
    private RadioButton rbSmokerTrue, rbSmokerFalse;
    private Button btnRegisterHabits;
    private RequestQueue requestQueue;
    private int idUser;

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

        // Inicializando a fila de requisições
        requestQueue = Volley.newRequestQueue(this);

        // Obtendo o idUser salvo em SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("register-session", MODE_PRIVATE);
        idUser = sharedPreferences.getInt("idUser", -1); // Recupera o idUser salvo
        Log.d(TAG, "idUser recuperado: " + idUser);

        btnRegisterHabits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (idUser != -1) {
                    Log.d(TAG, "Iniciando o registro dos habitos para o idUser: " + idUser);
                    registerUserHabits(idUser);
                } else {
                    Toast.makeText(RegisterHabitsActivity.this, "Erro: usuário não registrado.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void registerUserHabits(int idUser) {
        // Obtendo os dados dos campos
        String workBegin = input_workBegin.getText().toString().trim();
        String workEnd = input_workEnd.getText().toString().trim();
        String studyBegin = input_studyBegin.getText().toString().trim();
        String studyEnd = input_studyEnd.getText().toString().trim();
        String workoutBegin = input_workoutBegin.getText().toString().trim();
        String workoutEnd = input_workoutEnd.getText().toString().trim();
        String sleepBegin = input_sleepBegin.getText().toString().trim();
        String sleepEnd = input_sleepEnd.getText().toString().trim();
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
            habitsData.put("idUser", idUser);
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
            Log.d(TAG, "Objeto JSON criado: " + habitsData);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Erro ao criar os dados de habitos", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Erro ao criar o objeto JSON", e);
            return;
        }

        // Salvando o idUser em "register-session"
        SharedPreferences sharedPreferences = getSharedPreferences("register-session", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("idUser", idUser);
        editor.apply();

        // Construindo a URL com o idUser
        String url = BASE_URL + "/register/habits/" + idUser;
        Log.d(TAG, "URL da requisição: " + url);

        // Criando a requisição POST
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, habitsData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(RegisterHabitsActivity.this, "Habitos registrados com sucesso!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Resposta da requisição: " + response.toString());
                        // Redirecionar para a LoginActivity após o registro
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String errorMessage = "Erro ao registrar habitos: " + error.toString();
                        error.printStackTrace();
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
        Intent intent = new Intent(RegisterHabitsActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();

    }

}
