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
import com.example.solo.DietSection.DietHomeActivity;
import com.example.solo.DietSection.DietNewActivity;
import com.example.solo.R;
import com.example.solo.Util.URL;

import org.json.JSONException;

import java.text.DecimalFormat;

public class stopSmokingHomeActivity extends AppCompatActivity {
    private static final String TAG = "StopSmokingHomeActivity";
    private static final String BASE_URL = new URL().getURL() + "/stop-smoking";
    private RequestQueue requestQueue;

    private TextView daysWithoutSmokingInfo, moneySavedInfo, lifeMinutesSavedInfo, avoidedCigarettesInfo, daysWithoutSmokingInfo2;
    private Button btnBack, btnAtualizar;

    private ImageView imgVoltar;
    private int idUser;
    private DecimalFormat decimalFormat = new DecimalFormat("0");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_smoking_home);

        idUser = getSharedPreferences("user_session", MODE_PRIVATE).getInt("idUser", -1);

        requestQueue = Volley.newRequestQueue(this);

        daysWithoutSmokingInfo = findViewById(R.id.daysWithoutSmokingInfo);
        daysWithoutSmokingInfo2 = findViewById(R.id.daysWithoutSmokingInfo2);
        moneySavedInfo = findViewById(R.id.moneySavedInfo);
        lifeMinutesSavedInfo = findViewById(R.id.lifeMinutesSavedInfo);
        avoidedCigarettesInfo = findViewById(R.id.avoidedCigarettesInfo);
        imgVoltar = findViewById(R.id.imgVoltar);
        btnAtualizar = findViewById(R.id.btnAtualizar);


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
                            int daysWithoutSmoking = response.getInt("daysWithoutSmoking");
                            int daysWithoutSmoking2 = response.getInt("daysWithoutSmoking");
                            double moneySaved = response.getDouble("moneySaved");
                            double lifeMinutesSaved = response.getDouble("lifeMinutesSaved");
                            double avoidedCigarettes = response.getDouble("avoidedCigarettes");

                            daysWithoutSmokingInfo.setText(String.valueOf(daysWithoutSmoking) + "d");
                            daysWithoutSmokingInfo2.setText(String.valueOf(daysWithoutSmoking));
                            moneySavedInfo.setText(decimalFormat.format(moneySaved) + "R$");
                            lifeMinutesSavedInfo.setText(decimalFormat.format(lifeMinutesSaved));
                            avoidedCigarettesInfo.setText(decimalFormat.format(avoidedCigarettes));

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

