package com.example.solo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
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
    private RequestQueue reqUserData;



    // ngrok
    //private static final String BASE_URL = "https://cc92-143-106-203-198.ngrok-free.app";

    // emulador
    private static final String BASE_URL = "http://10.0.2.2:8080";

    private TextView tvNickname, tvEmail, tvHeight, tvWeight, tvBirthday, tvWork, tvStudy, tvWorkout, tvSleeptime;
    private Button btnGotoUpdateUser, btnGotoUpdateHabits;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        tvNickname = findViewById(R.id.tvNickname);
        tvEmail = findViewById(R.id.tvEmail);
        tvHeight = findViewById(R.id.tvHeight);
        tvWeight = findViewById(R.id.tvWeight);
        tvBirthday = findViewById(R.id.tvBirthday);
        tvWork = findViewById(R.id.tvWork);
        tvStudy = findViewById(R.id.tvStudy);
        tvWorkout = findViewById(R.id.tvWorkout);
        tvSleeptime = findViewById(R.id.tvSleeptime);
        btnGotoUpdateUser = findViewById(R.id.btnGotoUpdateUser);
        btnGotoUpdateHabits = findViewById(R.id.btnGotoUpdateHabits);

        reqUserData = Volley.newRequestQueue(this);


        SharedPreferences sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE);
        int idUser = sharedPreferences.getInt("idUser", -1); // -1 é um valor padrão caso o id não seja encontrado

        if (idUser != -1) {
            fetchUserData(idUser);
            btnGotoUpdateUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ProfileActivity.this, UpdateUserActivity.class);
                    startActivity(intent);
                }
            });

            btnGotoUpdateHabits.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ProfileActivity.this, UpdateHabitsActivity.class);
                    startActivity(intent);
                }
            });
        } else {
            Toast.makeText(this, "ID de usuário não encontrado.", Toast.LENGTH_SHORT).show();
        }


    }

    private void fetchUserData(int idUser) {
        String url = BASE_URL + "/user/" + idUser;

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
                            String work = response.getString("workTime");
                            String study = response.getString("studyTime");
                            String workout = response.getString("workoutTime");
                            String sleepTime = response.getString("sleepTime");

                            // Set data to TextViews
                            tvNickname.setText("Apelido: " + nickname);
                            tvEmail.setText("Email: " + email);
                            tvHeight.setText("Altura: " + height + " m");
                            tvWeight.setText("Peso: " + weight + " kg");
                            tvBirthday.setText("Aniversário: " + birthday);
                            tvWork.setText("Trabalho: " + work);
                            tvStudy.setText("Estudos: " + study);
                            tvWorkout.setText("Treinamentos: " + workout);
                            tvSleeptime.setText("Tempo de Sono: " + sleepTime);


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

        reqUserData.add(jsonObjectRequest);
    }
}