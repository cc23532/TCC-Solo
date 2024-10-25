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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.solo.DietSection.DietHomeActivity;
import com.example.solo.R;
import com.example.solo.UserSection.HomeActivity;
import com.example.solo.Util.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class MuscleHomeActivity extends AppCompatActivity {

    private ImageView btnVoltar;
    private Button btnAddWorkout;
    private static final String BASE_URL = new URL().getURL() + "/workout/muscle/newActivity";
    private static final String TAG = "MuscleHomeActivity";
    private int idUser;
    private RequestQueue requestQueue;
    private EditText nameExerciseField, cargaField, seriesField, repeticoesField;
    private TextView name, weight, repetition;
    private Button btnSaveExercise;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_muscle_home);

        SharedPreferences user_session = getSharedPreferences("user_session", MODE_PRIVATE);
        idUser = user_session.getInt("idUser", -1);

        requestQueue = Volley.newRequestQueue(this);

        exibirInfoNaTela();
        btnAddWorkout = findViewById(R.id.btnAddWorkout);
        btnAddWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MuscleHomeActivity.this, MuscleNewActivity.class);
                startActivity(intent);
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



    public void exibirInfoNaTela() {
        name = findViewById(R.id.name);
        weight = findViewById(R.id.weight);
        repetition = findViewById(R.id.repetition);

        SharedPreferences muscleActivity_session = getSharedPreferences("muscleActivity_session", MODE_PRIVATE);
        int idActivity = muscleActivity_session.getInt("idActivity", -1);

        if (idActivity != -1) {
            String url = BASE_URL + "/" + idUser + "/exercises/activityItems/" + idActivity;

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            if (response.length() > 0) { 
                                try {
                                    JSONObject item = response.getJSONObject(0);
                                    String nameValue = item.getString("name");
                                    String weightValue = item.getDouble("weight") + "kg";
                                    String repetitionValue = item.getInt("repetition") + " rep";

                                    name.setText(nameValue);
                                    weight.setText(weightValue);
                                    repetition.setText(repetitionValue);
                                } catch (JSONException e) {
                                    Log.e(TAG, "Erro ao processar a resposta JSON", e);
                                    Toast.makeText(MuscleHomeActivity.this, "Erro ao processar a resposta do servidor.", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(MuscleHomeActivity.this, "Nenhum item encontrado.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e(TAG, "Erro na requisição: " + error.toString());
                    Toast.makeText(MuscleHomeActivity.this, "Erro ao obter as informações.", Toast.LENGTH_SHORT).show();
                }
            });

            requestQueue.add(jsonArrayRequest);
        } else {
            Toast.makeText(this, "Atividade não encontrada.", Toast.LENGTH_SHORT).show();
        }
    }
}
