package com.example.solo.stopSmoking;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.solo.R;
import com.example.solo.Util.URL;

import org.json.JSONObject;

public class StopSmokingNewActivity extends AppCompatActivity {

    private static final String TAG = "StopSmokingNewActivity";
    private RequestQueue requestQueue;

    private int idUser;
    private int idCount;

    private EditText edtCigsPerDay, edtCigsPerPack, edtPackPrice;
    private Button btnSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_smoking_new);

        // Inicializa a fila de requisições
        requestQueue = Volley.newRequestQueue(this);

        // Inicializando componentes da interface
        edtCigsPerDay = findViewById(R.id.cigarroPorDia);
        edtCigsPerPack = findViewById(R.id.cigarroPreco);
        edtPackPrice = findViewById(R.id.cigarroPrecoMaco);
        btnSave = findViewById(R.id.btnConfirmar);

        // Recupera o idUser da sessão
        SharedPreferences sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE);
        idUser = sharedPreferences.getInt("idUser", -1);

        if (idUser == -1) {
            Toast.makeText(this, "Erro: Usuário não autenticado.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Clique no botão de salvar
        btnSave.setOnClickListener(view -> {
            // Recupera o idCount da API e depois atualiza os dados
            obterIdCount();
        });
    }

    private void obterIdCount() {
        String urlBase = new URL().getURL();
        String url = urlBase + "/stop-smoking/" + idUser;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    try {
                        // Pega o idCount da resposta
                        idCount = response.getInt("idCount");
                        Log.d(TAG, "idCount recuperado: " + idCount);
                        // Agora que temos o idCount, podemos atualizar os dados
                        atualizarStopSmoking();
                    } catch (Exception e) {
                        Log.e(TAG, "Erro ao processar a resposta", e);
                        Toast.makeText(StopSmokingNewActivity.this, "Erro ao recuperar os dados.", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    Log.e(TAG, "Erro na requisição: " + error.toString());
                    Toast.makeText(StopSmokingNewActivity.this, "Erro ao recuperar idCount.", Toast.LENGTH_SHORT).show();
                }
        );

        requestQueue.add(jsonObjectRequest);
    }

    private void atualizarStopSmoking() {
        if (!validarCampos()) {
            return;
        }

        int cigsPerDay = Integer.parseInt(edtCigsPerDay.getText().toString());
        int cigsPerPack = Integer.parseInt(edtCigsPerPack.getText().toString());
        double packPrice = Double.parseDouble(edtPackPrice.getText().toString());

        ZonedDateTime now = ZonedDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
        String formattedDate = now.format(formatter);

        String urlBase = new URL().getURL();
        String url = urlBase + "/stop-smoking/" + idUser + "/" + idCount;

        // Cria o JSON com os dados a serem enviados
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("cigsPerDay", cigsPerDay);
            jsonBody.put("cigsPerPack", cigsPerPack);
            jsonBody.put("packPrice", packPrice);
            jsonBody.put("baseDate", formattedDate);
        } catch (Exception e) {
            Log.e(TAG, "Erro ao criar o JSON", e);
            Toast.makeText(this, "Erro ao criar o JSON", Toast.LENGTH_SHORT).show();
            return;
        }

        // Faz a requisição PUT
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.PUT,
                url,
                jsonBody,
                response -> {
                    Log.d(TAG, "Resposta da API: " + response.toString());
                    Toast.makeText(StopSmokingNewActivity.this, "Dados atualizados com sucesso!", Toast.LENGTH_SHORT).show();
                    finish();
                },
                error -> {
                    Log.e(TAG, "Erro na requisição: " + error.toString());
                    Toast.makeText(StopSmokingNewActivity.this, "Erro ao atualizar dados.", Toast.LENGTH_SHORT).show();
                }
        );

        requestQueue.add(jsonObjectRequest);
    }

    private boolean validarCampos() {
        String cigsPerDay = edtCigsPerDay.getText().toString();
        String cigsPerPack = edtCigsPerPack.getText().toString();
        String packPrice = edtPackPrice.getText().toString();

        if (cigsPerDay.isEmpty()) {
            edtCigsPerDay.setError("Campo não pode ser vazio.");
            return false;
        }
        if (cigsPerPack.isEmpty()) {
            edtCigsPerPack.setError("Campo não pode ser vazio.");
            return false;
        }
        if (packPrice.isEmpty()) {
            edtPackPrice.setError("Campo não pode ser vazio.");
            return false;
        }

        return true;
    }
}
