package com.example.solo.stopSmoking;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.solo.R;
import com.example.solo.Util.URL;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class stopSmokingHomeActivity extends AppCompatActivity {
    private static final String TAG = "StopSmokingHomeActivity";
    private static final String BASE_URL = new URL().getURL() + "/stop-smoking";
    private RequestQueue requestQueue;

    private TextView daysWithoutSmokingInfoDay, daysWithoutSmokingHora, daysWithoutSmokingMinutos;
    private TextView avoidedCigarettesInfo, moneySavedInfo, lifeMinutesSavedInfo, daysWithoutSmokingInfo;
    private Button btnAtualizar;
    private ImageView imgVoltar;
    private int idUser;
    private DecimalFormat decimalFormat = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_smoking_home);

        // Recupera o ID do usuário salvo na sessão
        idUser = getSharedPreferences("user_session", MODE_PRIVATE).getInt("idUser", -1);

        requestQueue = Volley.newRequestQueue(this);

        // Referências aos elementos do layout
        daysWithoutSmokingInfoDay = findViewById(R.id.daysWithoutSmokingInfoDay);
        daysWithoutSmokingHora = findViewById(R.id.daysWithoutSmokingInfoHora);
        daysWithoutSmokingMinutos = findViewById(R.id.daysWithoutSmokingInfoMinuto);
        avoidedCigarettesInfo = findViewById(R.id.avoidedCigarettesInfo);
        moneySavedInfo = findViewById(R.id.moneySavedInfo);
        lifeMinutesSavedInfo = findViewById(R.id.lifeMinutesSavedInfo);
        imgVoltar = findViewById(R.id.imgVoltar);
        btnAtualizar = findViewById(R.id.btnAtualizar);
        daysWithoutSmokingInfo = findViewById(R.id.daysWithoutSmokingInfo);


        // Ações dos botões
        btnAtualizar.setOnClickListener(view -> {
            Intent intent = new Intent(stopSmokingHomeActivity.this, StopSmokingNewActivity.class);
            startActivity(intent);
            finish();
        });

        imgVoltar.setOnClickListener(v -> finish());

        carregarStopSmokingData();
    }

    private void carregarStopSmokingData() {
        if (idUser != -1) {
            String url = BASE_URL + "/" + idUser;

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    response -> {
                        Log.d(TAG, "Resposta recebida: " + response.toString());
                        try {
                            // Lê os valores retornados pela API
                            JSONObject daysWithoutSmoking = response.getJSONObject("daysWithoutSmoking");


                            JSONObject lifeMinutesSaved = response.getJSONObject("lifeMinutesSaved");

                            int days = daysWithoutSmoking.getInt("days");
                            int months = daysWithoutSmoking.getInt("months");
                            int years = daysWithoutSmoking.getInt("years");
                            int hours = daysWithoutSmoking.getInt("hours");

                            int lifeDays = lifeMinutesSaved.getInt("days");
                            int lifeHours = lifeMinutesSaved.getInt("hours");
                            int lifeMinutes = lifeMinutesSaved.getInt("minutes");

                            String avoidedCigarettes = response.getString("avoidedCigarettes");
                            String moneySaved = response.getString("moneySaved");

                            // Atualiza as TextViews com os dados
                            daysWithoutSmokingInfoDay.setText(String.format("%d", days));
                            daysWithoutSmokingInfo.setText(String.format("%d", days));
                            daysWithoutSmokingHora.setText(String.format("%d", hours));
                            daysWithoutSmokingMinutos.setText(String.format("%d", lifeMinutes));
                            avoidedCigarettesInfo.setText(avoidedCigarettes);
                            moneySavedInfo.setText(moneySaved + " R$");
                            lifeMinutesSavedInfo.setText(
                                    String.format("%d",lifeMinutes)
                            );
                        } catch (JSONException e) {
                            Log.e(TAG, "Erro ao processar a resposta JSON", e);
                            Toast.makeText(stopSmokingHomeActivity.this, "Erro ao processar a resposta do servidor.", Toast.LENGTH_SHORT).show();
                        }
                    }, error -> {
                Log.e(TAG, "Erro na requisição: " + error.toString());

                if (error.networkResponse != null) {
                    Log.e(TAG, "Código de erro HTTP: " + error.networkResponse.statusCode);
                    Log.e(TAG, "Resposta do servidor: " + new String(error.networkResponse.data));
                }

                Toast.makeText(stopSmokingHomeActivity.this, "Erro ao obter as informações.", Toast.LENGTH_SHORT).show();
            });

            requestQueue.add(jsonObjectRequest);
        } else {
            Toast.makeText(this, "ID do usuário não encontrado.", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "ID do usuário não encontrado.");
        }
    }
}
