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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.solo.AgendaSection.AgendaHomeActivity;
import com.example.solo.DietSection.DietHomeActivity;
import com.example.solo.R;
import com.example.solo.Util.URL;
import com.example.solo.WorkoutSection.CardioHomeActivity;
import com.example.solo.WorkoutSection.MuscleHomeActivity;
import com.example.solo.finances.FinanceHomeActivity;
import com.example.solo.stopSmoking.stopSmokingHomeActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity {
    private Button btnPopUpWorkout;
    private ImageView btnProfileIcon, personIcon, dietIcon, financeIcon, calendarIcon;
    private static final String BASE_URL = new URL().getURL();
    private FrameLayout frameWorkout, frameSmoking;
    private LinearLayout containerCompromissos;
    private RequestQueue requestQueue;
    private int idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Inicializando as views
        btnProfileIcon = findViewById(R.id.btnProfileIcon);
        personIcon = findViewById(R.id.personIcon);
        dietIcon = findViewById(R.id.dietIcon);
        financeIcon = findViewById(R.id.financeIcon);
        calendarIcon = findViewById(R.id.calendarIcon);
        frameSmoking = findViewById(R.id.frameSmoking);
        frameWorkout = findViewById(R.id.frameWorkout);
        containerCompromissos = findViewById(R.id.containerTarefas);  // Inicialização corrigida

        // Configurando o requestQueue
        requestQueue = Volley.newRequestQueue(this);

        SharedPreferences sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE);
        idUser = sharedPreferences.getInt("idUser", -1); // Recupera o id do usuário

        if (idUser != -1) {
            // Configura os ícones para abrir as respectivas telas
            btnProfileIcon.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, ProfileActivity.class)));
            personIcon.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, ProfileActivity.class)));
            dietIcon.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, DietHomeActivity.class)));
            financeIcon.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, FinanceHomeActivity.class)));
            frameSmoking.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, stopSmokingHomeActivity.class)));
            calendarIcon.setOnClickListener(v -> startActivity(new Intent(HomeActivity.this, AgendaHomeActivity.class)));

            // Carregar compromissos do dia
            fetchCompromissos();
        }

        frameWorkout.setOnClickListener(v -> showDialog());

        // Ajuste de layout com padding para system bars
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    

    private void fetchCompromissos() {
        // Limpar os compromissos antes de adicionar novos
        containerCompromissos.removeAllViews();

        // Obtém a data e hora atuais
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String currentDate = dateFormat.format(calendar.getTime());

        // Formata a hora atual (exemplo: 12:00)
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String currentTime = timeFormat.format(calendar.getTime());

        // API para pegar compromissos do dia com a data atual
        String url = BASE_URL + "/schedule/" + idUser + "/today-events?eventDate=" + currentDate + "&startTime=" + currentTime;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        // Limitar a quantidade de compromissos a 3
                        int limit = 3;
                        if (response.length() == 0) {
                            Toast.makeText(HomeActivity.this, "Nenhum compromisso para hoje.", Toast.LENGTH_SHORT).show();
                        } else {
                            for (int i = 0; i < response.length() && i < limit; i++) {
                                JSONObject event = response.getJSONObject(i);
                                String title = event.getString("title");
                                String startTime = event.getString("startTime").substring(0, 5); // Formato HH:MM

                                // Inflar a view de cada compromisso
                                View taskView = getLayoutInflater().inflate(R.layout.task_item, containerCompromissos, false);

                                // Encontrando os TextViews e ImageView no layout inflado
                                TextView taskTitle = taskView.findViewById(R.id.textView9);
                                TextView taskTime = taskView.findViewById(R.id.textView16);
                                ImageView taskImage = taskView.findViewById(R.id.imageView13);

                                // Definindo os dados do compromisso
                                taskTitle.setText(title);
                                taskTime.setText(startTime);

                                // Definindo a imagem do compromisso (exemplo: imagem fixa para todos)
                                taskImage.setImageResource(R.drawable.task); // Troque para qualquer imagem desejada

                                // Adicionando o item ao layout
                                containerCompromissos.addView(taskView);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(HomeActivity.this, "Erro ao carregar compromissos", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    Toast.makeText(HomeActivity.this, "Erro de conexão", Toast.LENGTH_SHORT).show();
                });

        requestQueue.add(request);
    }




    private void showDialog() {
        Dialog dialog = new Dialog(this, R.style.DialogStyle);
        dialog.setContentView(R.layout.activity_popup_workout);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_window);

        Button btnCardioHome = dialog.findViewById(R.id.btnCardioHome);
        Button btnMuscleHome = dialog.findViewById(R.id.btnMuscleHome);

        ImageView btnClose = dialog.findViewById(R.id.imageView);
        btnClose.setOnClickListener(v -> dialog.dismiss());

        btnCardioHome.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, CardioHomeActivity.class);
            startActivity(intent);
            dialog.dismiss();
        });

        btnMuscleHome.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, MuscleHomeActivity.class);
            startActivity(intent);
            dialog.dismiss();
        });

        dialog.show();
    }
}
