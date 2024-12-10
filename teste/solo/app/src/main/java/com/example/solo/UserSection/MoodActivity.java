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

        veryhappy = findViewById(R.id.veryhappyText);
        happy = findViewById(R.id.happyText);
        normal = findViewById(R.id.normalText);
        unhappy = findViewById(R.id.unhappyText);
        sad = findViewById(R.id.sadText);

        // Configurando o requestQueue
        requestQueue = Volley.newRequestQueue(this);

        SharedPreferences sharedPreferences = getSharedPreferences("mood_session", MODE_PRIVATE);
        idUser = sharedPreferences.getInt("idUser", -1); // Recupera o id do usuário

        imgBack =findViewById(R.id.imgBack);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void exibirInfoNaTela() {
        // Referências para os TextViews que exibirão as informações
        veryhappy = findViewById(R.id.veryhappyText);
        happy = findViewById(R.id.happyText);
        normal = findViewById(R.id.normalText);
        unhappy = findViewById(R.id.unhappyText);
        sad = findViewById(R.id.sadText);

        // Recuperando dados da sessão
        SharedPreferences mood_session = getSharedPreferences("mood_session", MODE_PRIVATE);
        int idUser = mood_session.getInt("idUser", -1);

        // Verificando se o id da atividade é válido
        if (idUser != -1) {
            String url = BASE_URL + "/user/mood/" + idUser;

            // Criando a requisição para buscar as informações da atividade
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            if (response.length() > 0) {
                                // Variáveis para contar as ocorrências de cada humor
                                int veryhappyCount = 0;
                                int happyCount = 0;
                                int normalCount = 0;
                                int unhappyCount = 0;
                                int sadCount = 0;

                                try {
                                    // Percorrendo a resposta JSON
                                    for (int i = 0; i < response.length(); i++) {
                                        JSONObject item = response.getJSONObject(i);
                                        String moodValue = item.getString("mood");  // Supondo que o campo seja "mood"

                                        // Contando as ocorrências de cada string de humor
                                        switch (moodValue.toLowerCase()) {
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
                                        }
                                    }

                                    // Atualizando os TextViews com as contagens
                                    veryhappy.setText("Very Happy: " + veryhappyCount);
                                    happy.setText("Happy: " + happyCount);
                                    normal.setText("Normal: " + normalCount);
                                    unhappy.setText("Unhappy: " + unhappyCount);
                                    sad.setText("Sad: " + sadCount);
                                } catch (JSONException e) {
                                    Log.e("MoodActivity", "Erro ao processar a resposta JSON", e);
                                    Toast.makeText(MoodActivity.this, "Erro ao processar a resposta do servidor.", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(MoodActivity.this, "Nenhum item encontrado.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("MoodActivity", "Erro na requisição: " + error.toString());
                    Toast.makeText(MoodActivity.this, "Erro ao obter as informações.", Toast.LENGTH_SHORT).show();
                }
            });

            // Adicionando a requisição à fila de requisições
            requestQueue.add(jsonArrayRequest);
        } else {
            Toast.makeText(this, "Atividade não encontrada.", Toast.LENGTH_SHORT).show();
        }
    };
}