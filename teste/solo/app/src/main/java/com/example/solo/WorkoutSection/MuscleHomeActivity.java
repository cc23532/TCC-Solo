package com.example.solo.WorkoutSection;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.solo.R;
import com.example.solo.Util.URL;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class MuscleHomeActivity extends AppCompatActivity {

    private ImageView btnVoltar;
    private Button btnAddWorkout;
    private static final String BASE_URL = new URL().getURL() + "/workout/muscle/newActivity";
    private static final String TAG = "MuscleHomeActivity";
    private int idUser;
    private RequestQueue requestQueue;

    private EditText nameExerciseField, cargaField, seriesField, repeticoesField;
    private Button btnSaveExercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muscle_home);

        // Recuperar idUser da sessão
        SharedPreferences user_session = getSharedPreferences("user_session", MODE_PRIVATE);
        idUser = user_session.getInt("idUser", -1);

        requestQueue = Volley.newRequestQueue(this);

        btnAddWorkout = findViewById(R.id.btnAddWorkout);
        btnAddWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createActivity();  // Cria uma nova atividade e, ao sucesso, chama showDialog()
            }
        });

        btnVoltar = findViewById(R.id.imgBack);
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void createActivity() {
        // Cria o objeto JSON para enviar a atividade
        JSONObject newActivity = new JSONObject();
        try {
            newActivity.put("idUser", idUser);
            Log.d(TAG, "Objeto JSON criado: " + newActivity);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Erro ao criar os dados de exercício", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Erro ao criar o objeto JSON", e);
            return;
        }

        String url = BASE_URL + "/" + idUser;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, url, newActivity,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int idActivity = response.getInt("idActivity");
                            Log.d(TAG, "idActivity salvo: " + idActivity);

                            // Armazena o idActivity em SharedPreferences
                            SharedPreferences muscleActivity_session = getSharedPreferences("muscleActivity_session", MODE_PRIVATE);
                            SharedPreferences.Editor editor = muscleActivity_session.edit();
                            editor.putInt("idUser", idUser);
                            editor.putInt("idActivity", idActivity);

                            if (editor.commit()) {
                                Toast.makeText(MuscleHomeActivity.this, "Atividade criada com sucesso", Toast.LENGTH_SHORT).show();
                                showDialog();  // Exibe o diálogo após criar a atividade
                            }
                        } catch (Exception e) {
                            Log.e(TAG, "Erro ao processar a resposta JSON", e);
                            Toast.makeText(MuscleHomeActivity.this, "Erro ao processar a resposta do servidor.", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Erro na requisição: " + error.toString());
                        String errorMessage = "Ocorreu um erro desconhecido.";

                        if (error.networkResponse != null) {
                            String responseBody;
                            try {
                                responseBody = new String(error.networkResponse.data, HttpHeaderParser.parseCharset(error.networkResponse.headers, "utf-8"));
                                Log.e(TAG, "Resposta de erro do servidor: " + responseBody);
                                errorMessage = responseBody;
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        } else if (error.getCause() instanceof java.net.ConnectException) {
                            errorMessage = "Não foi possível conectar ao servidor. Verifique sua conexão e tente novamente.";
                        }
                        Toast.makeText(MuscleHomeActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // Adiciona a requisição à fila
        requestQueue.add(jsonObjectRequest);
    }

    public void showDialog(){
        Dialog dialog = new Dialog(this, R.style.DialogStyle);
        dialog.setContentView(R.layout.activity_your_workout);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_window);

        ImageView btnSair = dialog.findViewById(R.id.btnSair);
        Button btnAdd = dialog.findViewById(R.id.btnAddWork);

        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog2();  // Exibe o próximo diálogo ao clicar em "Adicionar"
            }
        });
        dialog.show();
    }

    public void showDialog2(){
        Dialog dialog = new Dialog(this, androidx.appcompat.R.style.Base_Theme_AppCompat_DialogWhenLarge);
        dialog.setContentView(R.layout.activity_add_exercise);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_window);

        // Inicializa os campos de entrada do exercício
        nameExerciseField = dialog.findViewById(R.id.nomeExercicio);
        cargaField = dialog.findViewById(R.id.carga);
        seriesField = dialog.findViewById(R.id.series);
        repeticoesField = dialog.findViewById(R.id.repeticoes);
        btnSaveExercise = dialog.findViewById(R.id.salvar_button);

        // Recupera o idActivity da sessão
        SharedPreferences muscleActivity_session = getSharedPreferences("muscleActivity_session", MODE_PRIVATE);
        int idActivity = muscleActivity_session.getInt("idActivity", -1);

        btnSaveExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameExercise = nameExerciseField.getText().toString().trim();
                String carga = cargaField.getText().toString().trim();
                String series = seriesField.getText().toString().trim();
                String repeticoes = repeticoesField.getText().toString().trim();

                if (nameExercise.isEmpty() || carga.isEmpty() || series.isEmpty() || repeticoes.isEmpty()) {
                    Toast.makeText(MuscleHomeActivity.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Cria o objeto JSON para salvar o exercício
                JSONObject exerciseData = new JSONObject();
                try {
                    exerciseData.put("idActivity", idActivity);
                    exerciseData.put("name", nameExercise);
                    exerciseData.put("carga", carga);
                    exerciseData.put("series", series);
                    exerciseData.put("repeticoes", repeticoes);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MuscleHomeActivity.this, "Erro ao criar os dados", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Faz a requisição POST para salvar o exercício
                String url = BASE_URL + "/muscle-exercise/save";
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, exerciseData,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Toast.makeText(MuscleHomeActivity.this, "Exercício salvo com sucesso!", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "Resposta da API: " + response.toString());
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MuscleHomeActivity.this, "Erro ao salvar exercício: " + error.toString(), Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                });

                // Adiciona a requisição à fila
                requestQueue.add(jsonObjectRequest);
            }
        });

        dialog.show();
    }
}
