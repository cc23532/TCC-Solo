package com.example.solo.WorkoutSection;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.solo.R;
import com.example.solo.Util.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.Locale;

public class MuscleNewActivity extends AppCompatActivity {

    private ImageView btnVoltar;
    private Button btnNovoTreino;
    private static final String BASE_URL = new URL().getURL() + "/workout/muscle/";
    private static final String TAG = "MuscleNewActivity";
    private int idUser;
    private RequestQueue requestQueue;
    private EditText nameExerciseField, cargaField, seriesField, repeticoesField;
    private TextView name, weight, repetition, typeExercise, tempo;
    private Button btnSaveExercise, btnStart, btnStop, btnPause;
    private long startTime;
    int segundos, minutos, horas, milisegundos;
    long milisegundo, time, updateTime = 0L;
    Handler handler;

    private int idActivity;

    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            milisegundo = SystemClock.uptimeMillis() - startTime;
            updateTime = time + milisegundo;
            segundos = (int) (updateTime / 1000);
            minutos = segundos / 60;
            horas = minutos / 60;
            minutos = minutos % 60;
            segundos = segundos % 60;
            milisegundos = (int) (updateTime % 1000);

            // Exibir horas:minutos:segundos
            tempo.setText(MessageFormat.format("{0}:{1}:{2}",
                    horas,
                    String.format(Locale.getDefault(), "%02d", minutos),
                    String.format(Locale.getDefault(), "%02d", segundos)
            ));

            handler.postDelayed(this, 0);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muscle_new);

        SharedPreferences user_session = getSharedPreferences("user_session", MODE_PRIVATE);
        idUser = user_session.getInt("idUser", -1);

        requestQueue = Volley.newRequestQueue(this);
        tempo = findViewById(R.id.timer);
        btnStart = findViewById(R.id.btnStart);
        btnPause = findViewById(R.id.btnPause);

        handler = new Handler(Looper.getMainLooper());

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTime = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, 0);
                btnPause.setEnabled(true);
                btnStart.setEnabled(false);
            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time += milisegundo;
                handler.removeCallbacks(runnable);
                btnStop.setEnabled(false);
                btnStart.setEnabled(true);

            }
        });

        tempo.setText("00:00:00");
        exibirInfoNaTela();
        btnNovoTreino = findViewById(R.id.btnNovoTreino);
        btnNovoTreino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createActivity();
            }
        });

        btnStop = findViewById(R.id.btnStop);
        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });




    }

    private void createActivity() {
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

        String url = BASE_URL + "newActivity/" + idUser;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, url, newActivity,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            idActivity = response.getInt("idActivity");
                            Log.d(TAG, "idActivity salvo: " + idActivity);

                            SharedPreferences muscleNewActivity_session = getSharedPreferences("muscleNewActivity_session", MODE_PRIVATE);
                            SharedPreferences.Editor editor = muscleNewActivity_session.edit();
                            editor.putInt("idActivity", idActivity);

                            if (editor.commit()) {
                                Toast.makeText(MuscleNewActivity.this, "Atividade criada com sucesso", Toast.LENGTH_SHORT).show();
                                showDialog();
                            }
                        } catch (Exception e) {
                            Log.e(TAG, "Erro ao processar a resposta JSON", e);
                            Toast.makeText(MuscleNewActivity.this, "Erro ao processar a resposta do servidor.", Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(MuscleNewActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }
    public void showDialog() {
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
                createExercisePopUp();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void createExercisePopUp() {
        Dialog dialog = new Dialog(this, R.style.DialogStyle);
        dialog.setContentView(R.layout.activity_muscle_type_exercises);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_window);

        ImageView btnSair = dialog.findViewById(R.id.btnSair);
        Button btnType = dialog.findViewById(R.id.btnType);
        EditText typeExerciseField = dialog.findViewById(R.id.typeExercise);

        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String exerciseType = typeExerciseField.getText().toString().trim();

                if (exerciseType.isEmpty()) {
                    Toast.makeText(MuscleNewActivity.this, "Por favor, insira o tipo de exercício", Toast.LENGTH_SHORT).show();
                    return;
                }

                JSONObject typeExerciseData = new JSONObject();
                try {
                    typeExerciseData.put("name", exerciseType);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MuscleNewActivity.this, "Erro ao criar os dados", Toast.LENGTH_SHORT).show();
                    return;
                }

                String url = BASE_URL + "newActivity/" + idUser + "/exercises";

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, typeExerciseData,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    int idExercise = response.getInt("idExercise");
                                    Log.d(TAG, "idExercise salvo: " + idExercise);

                                    SharedPreferences exerciseData = getSharedPreferences("exerciseData", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = exerciseData.edit();
                                    editor.putInt("idExercise", idExercise);

                                    if (editor.commit()) {
                                        Toast.makeText(MuscleNewActivity.this, "Exercício criado com sucesso", Toast.LENGTH_SHORT).show();
                                        createActivityItemsPopUp();
                                        exibirInfoNaTela();
                                        dialog.dismiss();
                                    }
                                } catch (Exception e) {
                                    Log.e(TAG, "Erro ao processar a resposta JSON", e);
                                    Toast.makeText(MuscleNewActivity.this, "Erro ao processar a resposta do servidor.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MuscleNewActivity.this, "Erro ao salvar tipo de exercício: " + error.toString(), Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                });

                requestQueue.add(jsonObjectRequest);
            }
        });

        dialog.show();
    }

    public void exibirInfoNaTela() {
        name = findViewById(R.id.name);
        weight = findViewById(R.id.weight);
        repetition = findViewById(R.id.repetition);
        typeExercise = findViewById(R.id.typeExercise);

        SharedPreferences muscleActivity_session = getSharedPreferences("muscleActivity_session", MODE_PRIVATE);
        int idActivity = muscleActivity_session.getInt("idActivity", -1);

        if (idActivity != -1) {
            String url = BASE_URL + "newActivity/" + idUser + "/exercises/activityItems/" + idActivity;

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            if (response.length() > 0) {
                                try {
                                    JSONObject item = response.getJSONObject(0);
                                    String categoryValue = item.getString("category");
                                    String nameValue = item.getString("name");
                                    String weightValue = item.getDouble("weight") + "kg";
                                    String repetitionValue = item.getInt("repetition") + " rep";

                                    name.setText(nameValue);
                                    typeExercise.setText(categoryValue);
                                    weight.setText(weightValue);
                                    repetition.setText(repetitionValue);
                                } catch (JSONException e) {
                                    Log.e(TAG, "Erro ao processar a resposta JSON", e);
                                    Toast.makeText(MuscleNewActivity.this, "Erro ao processar a resposta do servidor.", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(MuscleNewActivity.this, "Nenhum item encontrado.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "Erro na requisição: " + error.toString());
                    Toast.makeText(MuscleNewActivity.this, "Erro ao obter as informações.", Toast.LENGTH_SHORT).show();
                }
            });

            requestQueue.add(jsonArrayRequest);
        } else {
            Toast.makeText(this, "Atividade não encontrada.", Toast.LENGTH_SHORT).show();
        }
    }

    public void createActivityItemsPopUp(){
        Dialog dialog = new Dialog(this, androidx.appcompat.R.style.Base_Theme_AppCompat_DialogWhenLarge);
        dialog.setContentView(R.layout.activity_add_exercise);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_window);

        nameExerciseField = dialog.findViewById(R.id.nomeExercicio);
        cargaField = dialog.findViewById(R.id.carga);
        seriesField = dialog.findViewById(R.id.series);
        repeticoesField = dialog.findViewById(R.id.repeticoes);
        btnSaveExercise = dialog.findViewById(R.id.salvar_button);

        SharedPreferences muscleActivity_session = getSharedPreferences("muscleActivity_session", MODE_PRIVATE);
        SharedPreferences exerciseData = getSharedPreferences("exerciseData", MODE_PRIVATE);
        // int idActivity = muscleActivity_session.getInt("idActivity", -1);

        int idActivity = muscleActivity_session.getInt("idActivity", -1);
        int idExercise = exerciseData.getInt("idExercise", -1);


        btnSaveExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameExercise = nameExerciseField.getText().toString().trim();
                String carga = cargaField.getText().toString().trim();
                String series = seriesField.getText().toString().trim();
                String repeticoes = repeticoesField.getText().toString().trim();

                if (nameExercise.isEmpty() || carga.isEmpty() || series.isEmpty() || repeticoes.isEmpty()) {
                    Toast.makeText(MuscleNewActivity.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                JSONObject exerciseData = new JSONObject();
                try {
                    exerciseData.put("name", nameExercise);
                    exerciseData.put("weight", carga);
                    exerciseData.put("series", series);
                    exerciseData.put("repetition", repeticoes);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MuscleNewActivity.this, "Erro ao criar os dados", Toast.LENGTH_SHORT).show();
                    return;
                }

                String url = BASE_URL + "newActivity/" + idUser + "/exercises/activityItems?idActivity=" + idActivity + "&idExercise=" + idExercise;
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, exerciseData,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Toast.makeText(MuscleNewActivity.this, "Exercício salvo com sucesso!", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "Resposta da API: " + response.toString());
                                exibirInfoNaTela();
                                dialog.dismiss();
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MuscleNewActivity.this, "Erro ao salvar exercício: " + error.toString(), Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                });

                requestQueue.add(jsonObjectRequest);
            }
        });

        dialog.show();
    }
}