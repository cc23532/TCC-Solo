package com.example.solo.DietSection;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.solo.R;
import com.example.solo.UserSection.HomeActivity;
import com.example.solo.UserSection.ProfileActivity;
import com.example.solo.Util.URL;
import com.example.solo.WorkoutSection.MuscleHomeActivity;
import com.example.solo.WorkoutSection.MuscleNewActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DietHomeActivity extends AppCompatActivity {
    private Button btnAddMeal;
    private ImageView btnVoltar;
    private FrameLayout frameBreakfast, frameLunch, frameAfternoon, frameDinner;
    private TextView mealsInfo, totalCaloriesInfo;
    private static final String BASE_URL = new URL().getURL() + "/diet/my-meals";
    private static final String TAG = "DietHomeActivity";
    private int idUser;
    private RequestQueue requestQueue;
    private double totalCalories = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_diet_home);

        btnAddMeal = findViewById(R.id.btnAddMeal);
        btnAddMeal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DietHomeActivity.this, DietNewActivity.class);
                startActivity(intent);
            }
        });
        frameBreakfast = findViewById(R.id.frameBreakfast);
        frameBreakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DietHomeActivity.this, DietBreakfastActivity.class);
                startActivity(intent);
            }
        });
        frameLunch = findViewById(R.id.frameLunch);
        frameLunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DietHomeActivity.this, DietLunchActivity.class);
                startActivity(intent);
            }
        });
        frameAfternoon = findViewById(R.id.frameAfternoon);
        frameAfternoon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DietHomeActivity.this, DietafternoonActivity.class);
                startActivity(intent);
            }
        });
        frameDinner = findViewById(R.id.frameDinner);
        frameDinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DietHomeActivity.this, DietDinnerActivity.class);
                startActivity(intent);
            }
        });
        btnVoltar = findViewById(R.id.imgVoltar);
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void exibirRefeicoes() {
        if (idUser != -1) {
            String url = BASE_URL + "/" + idUser;

            Log.d(TAG, "URL: " + url);

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            Log.d(TAG, "Resposta recebida: " + response.toString());
                            if (response.length() > 0) {
                                StringBuilder mealDetails = new StringBuilder();
                                for (int i = 0; i < response.length(); i++) {
                                    try {
                                        JSONObject meal = response.getJSONObject(i);
                                        if (meal.has("idMeal") && !meal.isNull("idMeal")) {
                                            int mealId = meal.getInt("idMeal");
                                            obterItensDaRefeicao(mealId, mealDetails);
                                        }
                                    } catch (JSONException e) {
                                        Log.e(TAG, "Erro ao processar a resposta JSON", e);
                                        Toast.makeText(DietHomeActivity.this, "Erro ao processar a resposta do servidor.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            } else {
                                mealsInfo.setText("Nenhuma refeição registrada.");
                                Log.d(TAG, "Nenhuma refeição encontrada.");
                            }
                        }
                    }, error -> {
                Log.e(TAG, "Erro na requisição: " + error.toString());
                Toast.makeText(DietHomeActivity.this, "Erro ao obter as informações.", Toast.LENGTH_SHORT).show();
            });

            requestQueue.add(jsonArrayRequest);
        } else {
            Toast.makeText(this, "ID do usuário não encontrado.", Toast.LENGTH_SHORT).show();
            Log.d(TAG, "ID do usuário é -1");
        }
    }

    private void obterItensDaRefeicao(int idMeal, StringBuilder mealDetails) {
        String url = BASE_URL + "/" + idUser + "/items/" + idMeal;

        Log.d(TAG, "Requisição para itens da refeição com idMeal: " + idMeal + ", URL: " + url);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    Log.d(TAG, "Resposta recebida dos itens: " + response.toString());
                    if (response.length() > 0) {
                        for (int j = 0; j < response.length(); j++) {
                            try {
                                JSONObject item = response.getJSONObject(j);
                                String foodName = item.getString("foodName");
                                double energyKCal = item.getDouble("energy_KCal");
                                Log.d(TAG, "Alimento encontrado: " + foodName + " - Calorias: " + energyKCal);

                                mealDetails.append(" - ").append(foodName).append(" " + energyKCal + "Kcal").append(" \n");

                                totalCalories += energyKCal;

                            } catch (JSONException e) {
                                Log.e(TAG, "Erro ao processar os itens da refeição", e);
                            }
                        }
                    } else {
                        mealDetails.append(" - Nenhum alimento registrado.\n");
                        Log.d(TAG, "Nenhum alimento encontrado para idMeal: " + idMeal);
                    }

                    mealsInfo.setText(mealDetails.toString());
                    String totalCaloriesFormat = String.format("%.2f", totalCalories);

                    totalCaloriesInfo.setText("" + totalCaloriesFormat);
                }, error -> Log.e(TAG, "Erro ao obter itens da refeição: " + error.toString()));

        requestQueue.add(jsonArrayRequest);
    }
}

