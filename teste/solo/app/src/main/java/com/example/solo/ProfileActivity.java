package com.example.solo;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = "ProfileActivity";
    private RequestQueue requestQueue;

    // ngrok
    private static final String BASE_URL = "https://cc92-143-106-203-198.ngrok-free.app";

    // emulador
    //private static final String BASE_URL = "https://10.2.2:3000/";

    private TextView tvApelido, tvEmail, tvAltura, tvPeso, tvAniversario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tvApelido = findViewById(R.id.tvApelido);
        tvEmail = findViewById(R.id.tvEmail);
        tvAltura = findViewById(R.id.tvAltura);
        tvPeso = findViewById(R.id.tvPeso);
        tvAniversario = findViewById(R.id.tvAniversario);

        requestQueue = Volley.newRequestQueue(this);


        SharedPreferences sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE);
        int userId = sharedPreferences.getInt("idUser", -1); // -1 é um valor padrão caso o id não seja encontrado

        if (userId != -1) {
            fetchUserData(userId);
        } else {
            Toast.makeText(this, "ID de usuário não encontrado.", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchUserData(int userId) {
        String url = BASE_URL + "/user/user/" + userId;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String nickname = response.getString("nickname");
                            String email = response.getString("email");
                            String height = response.getString("height");
                            String weight = response.getString("weight");
                            String birthday = response.getString("birthday");

                            // Set data to TextViews
                            tvApelido.setText("Apelido: " + nickname);
                            tvEmail.setText("Email: " + email);
                            tvAltura.setText("Altura: " + height + " m");
                            tvPeso.setText("Peso: " + weight + " kg");
                            tvAniversario.setText("Aniversário: " + birthday);

                        } catch (Exception e) {
                            Log.e(TAG, "Erro ao processar a resposta JSON", e);
                            Toast.makeText(ProfileActivity.this, "Erro ao processar os dados do usuário.", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Erro na requisição: " + error.toString());
                        Toast.makeText(ProfileActivity.this, "Erro ao obter os dados do usuário.", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }
}