package com.example.solo.UserSection;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.solo.R;
import com.example.solo.WorkoutSection.CardioHomeActivity;
import com.example.solo.WorkoutSection.MuscleHomeActivity;

public class HomeActivity extends AppCompatActivity {
<<<<<<< HEAD:teste/solo/app/src/main/java/com/example/solo/HomeActivity.java
    private ImageView btnPerfil;
    private ImageView btnPopUpWorkout;
=======
    private Button btnPerfil, btnPopUpWorkout;
>>>>>>> 0562a13faf09441a381e1c7bc96690ae3d593b25:teste/solo/app/src/main/java/com/example/solo/UserSection/HomeActivity.java

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        SharedPreferences sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE);
        int idUser = sharedPreferences.getInt("idUser", -1); // -1 é o valor padrão caso o id não seja encontrado
        String nickname = sharedPreferences.getString("nickname", null);
        Log.d("HomeActivity", sharedPreferences.toString());
        Log.d("HomeActivity", "idUser: " + idUser + ", nickname: " + nickname);

        if (idUser != -1) {
            btnPerfil = findViewById(R.id.btnProfileIcon);
            btnPerfil.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(HomeActivity.this, ProfileActivity.class);
                    startActivity(intent);
                }
            });
<<<<<<< HEAD:teste/solo/app/src/main/java/com/example/solo/HomeActivity.java
=======
            // O id não foi encontrado, talvez o usuário não esteja autenticado
>>>>>>> 0562a13faf09441a381e1c7bc96690ae3d593b25:teste/solo/app/src/main/java/com/example/solo/UserSection/HomeActivity.java
        } else {
            Log.e("HomeActivity", "ID nulo, tente novamente");
        }

<<<<<<< HEAD:teste/solo/app/src/main/java/com/example/solo/HomeActivity.java
        btnPopUpWorkout = findViewById(R.id.btnWorkoutIcon);
=======
        btnPopUpWorkout = findViewById(R.id.btnPopUpWorkout);

>>>>>>> 0562a13faf09441a381e1c7bc96690ae3d593b25:teste/solo/app/src/main/java/com/example/solo/UserSection/HomeActivity.java
        btnPopUpWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();  // Mostra o pop-up de treino
            }
        });
    }

    private void showDialog() {
<<<<<<< HEAD:teste/solo/app/src/main/java/com/example/solo/HomeActivity.java
        Dialog dialog = new Dialog(this);
=======
        Dialog dialog = new Dialog(this, R.style.DialogStyle);
>>>>>>> 0562a13faf09441a381e1c7bc96690ae3d593b25:teste/solo/app/src/main/java/com/example/solo/UserSection/HomeActivity.java
        dialog.setContentView(R.layout.activity_popup_workout);
        dialog.getWindow().setBackgroundDrawableResource(android.R.color.transparent);

<<<<<<< HEAD:teste/solo/app/src/main/java/com/example/solo/HomeActivity.java
=======
        // Recuperando os botões dentro do Dialog
        Button btnCardioHome = dialog.findViewById(R.id.btnCardioHome);
        Button btnMuscleHome = dialog.findViewById(R.id.btnMuscleHome);

        // Manipulando o clique do botão de fechar
>>>>>>> 0562a13faf09441a381e1c7bc96690ae3d593b25:teste/solo/app/src/main/java/com/example/solo/UserSection/HomeActivity.java
        ImageView btnClose = dialog.findViewById(R.id.imageView);
        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

<<<<<<< HEAD:teste/solo/app/src/main/java/com/example/solo/HomeActivity.java
        

        dialog.show();
    }
}
=======
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
>>>>>>> 0562a13faf09441a381e1c7bc96690ae3d593b25:teste/solo/app/src/main/java/com/example/solo/UserSection/HomeActivity.java
