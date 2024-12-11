package com.example.solo.UserSection;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.solo.R;
import com.example.solo.Util.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MoodActivity extends AppCompatActivity {

    private ImageView imgBack;
    private TextView veryhappy, happy, normal, unhappy, sad;
    private int idUser;
    private RequestQueue requestQueue;
    private static final String BASE_URL = new URL().getURL();
    private static final String TAG = "MoodActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mood);

        // Inicialização dos TextViews
        veryhappy = findViewById(R.id.veryhappyText);
        happy = findViewById(R.id.happyText);
        normal = findViewById(R.id.normalText);
        unhappy = findViewById(R.id.unhappyText);
        sad = findViewById(R.id.sadText);

        // Inicialização do requestQueue
        requestQueue = Volley.newRequestQueue(this);

        // Recuperando o ID do usuário
        idUser = getSharedPreferences("user_session", MODE_PRIVATE).getInt("idUser", -1);

        // Configuração do botão de voltar
        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(v -> finish());

        // Exibindo informações de humor
        exibirInfoNaTela();
    }

    private void exibirInfoNaTela() {
        if (idUser != -1) {
            // URL da API
            String url = BASE_URL + "/user/mood/" + idUser;

            // Criando uma requisição GET para JSONArray
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                    response -> {
                        Log.d(TAG, "Resposta recebida: " + response.toString());

                        // Contadores de humor
                        int veryhappyCount = 0;
                        int happyCount = 0;
                        int normalCount = 0;
                        int unhappyCount = 0;
                        int sadCount = 0;

                        // Processando cada objeto na resposta
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject moodObject = response.getJSONObject(i);
                                String mood = moodObject.getString("mood");

                                switch (mood.toLowerCase()) {
                                    case "veryhappy":
                                        veryhappyCount++;
                                        break;
                                    case "happy":
                                        happyCount++;
                                        break;
                                    case "normal":
                                        normalCount++;
                                        break;
                                    case "unhappy":
                                        unhappyCount++;
                                        break;
                                    case "sad":
                                        sadCount++;
                                        break;
                                    default:
                                        Log.w(TAG, "Humor desconhecido: " + mood);
                                }
                            } catch (JSONException e) {
                                Log.e(TAG, "Erro ao processar o objeto JSON", e);
                            }
                        }

                        // Atualizando os TextViews com os valores
                        veryhappy.setText(String.valueOf(veryhappyCount));
                        happy.setText(String.valueOf(happyCount));
                        normal.setText(String.valueOf(normalCount));
                        unhappy.setText(String.valueOf(unhappyCount));
                        sad.setText(String.valueOf(sadCount));
                    },
                    error -> {
                        Log.e(TAG, "Erro na requisição: " + error.toString());

                        if (error.networkResponse != null) {
                            Log.e(TAG, "Código de erro HTTP: " + error.networkResponse.statusCode);
                            Log.e(TAG, "Resposta do servidor: " + new String(error.networkResponse.data));
                        }

                        Toast.makeText(MoodActivity.this, "Erro ao obter as informações.", Toast.LENGTH_SHORT).show();
                    });

            requestQueue.add(jsonArrayRequest);

        } else {
            Toast.makeText(this, "ID do usuário inválido.", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "ID do usuário inválido.");
        }
    }
}