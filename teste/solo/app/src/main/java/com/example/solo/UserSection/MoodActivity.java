package com.example.solo.UserSection;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.solo.R;
import com.example.solo.Util.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MoodActivity extends AppCompatActivity {

    private ImageView imgBack;
    private TextView veryhappy, happy, normal, unhappy, sad;
    private RequestQueue requestQueue;
    private int idUser;
    private static final String BASE_URL = new URL().getURL();

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

        // Configuração do requestQueue
        requestQueue = Volley.newRequestQueue(this);

        // Recuperando o ID do usuário
        SharedPreferences sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE);
        idUser = sharedPreferences.getInt("idUser", -1);

        // Configuração do botão de voltar
        imgBack = findViewById(R.id.imgBack);
        imgBack.setOnClickListener(v -> finish());

        // Exibindo informações de humor
        exibirInfoNaTela();
    }

    public void exibirInfoNaTela() {
        // Verifica se o ID do usuário é válido
        if (idUser != -1) {
            // Construindo a URL da requisição
            String url = BASE_URL + "/user/mood/" + idUser;

            // Log da URL para ver no Logcat
            Log.d("MoodActivity", "URL da requisição: " + url);

            // Requisição para buscar dados de humor
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                    response -> {
                        try {
                            // Se a resposta não for um array, deve ser um objeto único
                            if (response.length() > 0) {
                                JSONObject item = response.getJSONObject(0); // Caso seja um array com um objeto
                                String moodValue = item.getString("mood").toLowerCase();

                                // Atualizando os contadores de humor
                                int veryhappyCount = 0, happyCount = 0, normalCount = 0, unhappyCount = 0, sadCount = 0;

                                // Contando as ocorrências de cada humor
                                switch (moodValue) {
                                    case "veryhappy": veryhappyCount++; break;
                                    case "happy": happyCount++; break;
                                    case "normal": normalCount++; break;
                                    case "unhappy": unhappyCount++; break;
                                    case "sad": sadCount++; break;
                                }

                                // Atualizando os TextViews com as contagens
                                veryhappy.setText(String.valueOf(veryhappyCount));
                                happy.setText(String.valueOf(happyCount));
                                normal.setText(String.valueOf(normalCount));
                                unhappy.setText(String.valueOf(unhappyCount));
                                sad.setText(String.valueOf(sadCount));

                            } else {
                                Toast.makeText(MoodActivity.this, "Sem dados de humor para exibir.", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            Log.e("MoodActivity", "Erro ao processar a resposta JSON", e);
                            Toast.makeText(MoodActivity.this, "Erro ao processar a resposta do servidor.", Toast.LENGTH_SHORT).show();
                        }
                    }, error -> {
                // Log de erro na requisição
                Log.e("MoodActivity", "Erro na requisição: " + error.toString());
                Toast.makeText(MoodActivity.this, "Erro ao obter as informações.", Toast.LENGTH_SHORT).show();
            });

            // Adicionando a requisição à fila de requisições
            requestQueue.add(jsonArrayRequest);
        } else {
            Toast.makeText(this, "ID do usuário inválido.", Toast.LENGTH_SHORT).show();
        }
    }



}
