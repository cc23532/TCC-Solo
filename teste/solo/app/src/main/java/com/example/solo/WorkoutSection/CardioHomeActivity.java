package com.example.solo.WorkoutSection;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.solo.Login_Register.LoginActivity;
import com.example.solo.R;
import com.example.solo.Util.URL;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class CardioHomeActivity extends AppCompatActivity {
    private Button btnStartNewActivity;
    private ImageView btnVoltar;
    private static final String BASE_URL = new URL().getURL() + "/workout/cardio/newActivity";
    private static final String TAG = "CardioHomeActivity";
    private int idUser;
    private RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_cardio_home);

        SharedPreferences user_session = getSharedPreferences("user_session", MODE_PRIVATE);
        idUser = user_session.getInt("idUser", -1);

        requestQueue = Volley.newRequestQueue(this);

        btnStartNewActivity = findViewById(R.id.btnStartNewActivity);
        btnStartNewActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { createActivity(); }
        });

        btnVoltar = findViewById(R.id.imgBack);
        btnVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void createActivity(){
        //cria o JSON
        JSONObject newActivity = new JSONObject();
        try {
            newActivity.put("idUser", idUser);
            Log.d(TAG, "Objeto JSON criado: " + newActivity.toString());
        } catch (JSONException e){
            e.printStackTrace();
            Toast.makeText(this, "Erro ao criar os dados de habitos", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Erro ao criar o objeto JSON", e);
            return;
        }

        String url = BASE_URL + "/" + idUser;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST, url, newActivity,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            int idActivity = response.getInt("idActivity");
                            Log.d(TAG, "idActivity salvo: " + idActivity);

                            SharedPreferences cardioActivity_session = getSharedPreferences("cardioActivity_session", MODE_PRIVATE);
                            SharedPreferences.Editor editor = cardioActivity_session.edit();
                            editor.putInt("idUser", idUser);
                            editor.putInt("idActivity", idActivity);

                            if (editor.commit()) {
                                Toast.makeText(CardioHomeActivity.this, "Atividade salva com sucesso", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(CardioHomeActivity.this, CardioNewActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        } catch (Exception e) {
                            Log.e(TAG, "Erro ao processar a resposta JSON", e);
                            Toast.makeText(CardioHomeActivity.this, "Erro ao processar a resposta do servidor.", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Erro na requisição: " + error.toString());
                        String errorMessage = "Ocorreu um erro desconhecido.";

                        // Trata erros específicos
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
                        Toast.makeText(CardioHomeActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                    }
                }
        );
        // Adiciona a requisição à fila
        requestQueue.add(jsonObjectRequest);
    }
}