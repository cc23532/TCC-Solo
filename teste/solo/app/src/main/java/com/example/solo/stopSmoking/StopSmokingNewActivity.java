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

import org.json.JSONException;
import org.json.JSONObject;

public class StopSmokingNewActivity extends AppCompatActivity {

    private static final String TAG = "StopSmokingNewActivity";
    private static final String BASE_URL = new URL().getURL();

    private EditText cigarroPorDia, cigarroPreco, cigarroPrecoMaco;
    private Button btnConfirmar;

    private RequestQueue requestQueue;
    private int idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_smoking_new);

        cigarroPorDia = findViewById(R.id.cigarroPorDia);
        cigarroPreco = findViewById(R.id.cigarroPreco);
        cigarroPrecoMaco = findViewById(R.id.cigarroPrecoMaco);
        btnConfirmar = findViewById(R.id.btnConfirmar);

        requestQueue = Volley.newRequestQueue(this);

        SharedPreferences preferences = getSharedPreferences("user_session", MODE_PRIVATE);
        idUser = preferences.getInt("idUser", -1);

        if (idUser == -1) {
            Toast.makeText(this, "Usuário não autenticado!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        btnConfirmar.setOnClickListener(v -> enviarDados());
    }

    private void enviarDados() {
        // Criando o objeto JSON com os dados do formulário
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("cigsPerDay", Integer.parseInt(cigarroPorDia.getText().toString()));
            jsonBody.put("cigsPerPack", Integer.parseInt(cigarroPreco.getText().toString()));
            jsonBody.put("packPrice", Double.parseDouble(cigarroPrecoMaco.getText().toString()));

            // Imprimindo os dados no Log antes de enviar
            Log.d(TAG, "Dados a serem enviados: " + jsonBody.toString());

        } catch (JSONException | NumberFormatException e) {
            e.printStackTrace();
            Toast.makeText(this, "Erro ao criar os dados!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Definindo a URL para onde o POST será enviado
        String url = BASE_URL + "/stop-smoking/" + idUser ;

        // Criando a requisição POST com o objeto JSON
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
                response -> {
                    Toast.makeText(this, "Dados enviados com sucesso!", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Resposta: " + response.toString());
                    finish();
                },
                error -> {
                    Log.e(TAG, "Erro na requisição: ", error);
                });

        // Adicionando a requisição na fila para execução
        requestQueue.add(jsonObjectRequest);
    }
}
