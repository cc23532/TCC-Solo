package com.example.solo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class HomeActivity extends AppCompatActivity {
    private Button btnPerfil, btnHabitos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        // Inicialize os botões
        btnPerfil = findViewById(R.id.btnProfile);
        btnHabitos = findViewById(R.id.btnHabitos); // Certifique-se de que o ID está correto

        SharedPreferences sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE);
        int idUser = sharedPreferences.getInt("idUser", -1); // -1 é um valor padrão caso o id não seja encontrado
        String nickname = sharedPreferences.getString("nickname", null);

        if (idUser != -1) {
            // O id foi recuperado com sucesso, prossiga com a lógica
            btnPerfil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                    startActivity(intent);
                }
            });

            btnHabitos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(HomeActivity.this, RegisterHabitsActivity.class);
                    startActivity(intent);
                }
            });
            // O id não foi encontrado, talvez o usuário não esteja autenticado
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
}
