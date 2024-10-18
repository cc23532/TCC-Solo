package com.example.solo.UserSection;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.solo.DietSection.DietHomeActivity;
import com.example.solo.R;
import com.example.solo.WorkoutSection.CardioHomeActivity;
import com.example.solo.WorkoutSection.MuscleHomeActivity;

public class HomeActivity extends AppCompatActivity {
    private Button btnPopUpWorkout;

    private ImageView btnProfileIcon, btnWorkoutIcon, personIcon, dietIcon;

    private FrameLayout frameWorkout;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        SharedPreferences sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE);
        int idUser = sharedPreferences.getInt("idUser", -1); // -1 é um valor padrão caso o id não seja encontrado
        String nickname = sharedPreferences.getString("nickname", null);
        Log.d("HomeActivity", sharedPreferences.toString());
        Log.d("HomeActivity", "idUser: " + idUser + ", nickname: " + nickname);

        if (idUser != -1) {
            // O id foi recuperado com sucesso, prossiga com a lógica
            btnProfileIcon = findViewById(R.id.btnProfileIcon);
            btnProfileIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                    startActivity(intent);
                }
            });
            personIcon = findViewById(R.id.personIcon);
            personIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                    startActivity(intent);
                }
            });

            dietIcon = findViewById(R.id.dietIcon);
            dietIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(HomeActivity.this, DietHomeActivity.class);
                    startActivity(intent);
                }
            });
            // O id não foi encontrado, talvez o usuário não esteja autenticado
        } else {
            Log.e("HomeActivity", "ID nulo, tente novamente");
        }

        frameWorkout = findViewById(R.id.frameWorkout);

        frameWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void showDialog() {
        Dialog dialog = new Dialog(this, R.style.DialogStyle);
        dialog.setContentView(R.layout.activity_popup_workout);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_window);

        // Recuperando os botões dentro do Dialog
        Button btnCardioHome = dialog.findViewById(R.id.btnCardioHome);
        Button btnMuscleHome = dialog.findViewById(R.id.btnMuscleHome);

        // Manipulando o clique do botão de fechar
        ImageView btnClose = dialog.findViewById(R.id.imageView);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        // Manipulando os cliques dos botões no Dialog
        btnCardioHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "Cardio Home Clicked", Toast.LENGTH_SHORT).show();
                Log.d("HomeActivity", "CardioHomeClicked");
                Intent intent = new Intent(HomeActivity.this, CardioHomeActivity.class);
                startActivity(intent);
                dialog.dismiss(); // Fecha o Dialog após o clique
            }
        });

        btnMuscleHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "Cardio Home Clicked", Toast.LENGTH_SHORT).show();
                Log.d("HomeActivity", "MuscleHomeClicked");
                Intent intent = new Intent(HomeActivity.this, MuscleHomeActivity.class);
                startActivity(intent);
                dialog.dismiss(); // Fecha o Dialog após o clique
            }
        });

        btnMuscleHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this, "Muscle Home Clicked", Toast.LENGTH_SHORT).show();
                Log.d("HomeActivity", "MuscleHomeClicked");
                Intent intent = new Intent(HomeActivity.this, MuscleHomeActivity.class);
                startActivity(intent);
                dialog.dismiss(); // Fecha o Dialog após o clique
            }
        });

        dialog.show();
    }

}