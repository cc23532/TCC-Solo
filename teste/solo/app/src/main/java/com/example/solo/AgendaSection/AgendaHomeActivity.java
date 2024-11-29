package com.example.solo.AgendaSection;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.solo.R;
import com.example.solo.Util.URL;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;

import android.graphics.Color;
import android.text.style.ForegroundColorSpan;


public class AgendaHomeActivity extends AppCompatActivity {

    private static final String TAG = "AgendaHomeActivity";
    private static final String BASE_URL = new URL().getURL();
    private CalendarView calendarView;
    private RequestQueue requestQueue;
    private ImageView imgVoltar;
    private LinearLayout containerTarefas;
    private TextView txtStatus;
    private ProgressBar progressBar;
    private Button btnAddCompromisso, btnVerMais, btnAddTarefa;
    private int idUser;
    private HashSet<String> datasComEventos; // Armazena as datas com compromissos no formato "yyyy-MM-dd"

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda_home);

        containerTarefas = findViewById(R.id.containerTarefas);
        imgVoltar = findViewById(R.id.imgVoltar);
        calendarView = findViewById(R.id.calendarView);
        txtStatus = findViewById(R.id.selectDate);
        progressBar = findViewById(R.id.progressBar);
        btnAddCompromisso = findViewById(R.id.btnAddCompromisso);
        btnVerMais = findViewById(R.id.btnVerMais);
        btnAddTarefa = findViewById(R.id.btnAddTarefa);

        btnVerMais.setVisibility(View.GONE);
        btnAddTarefa.setVisibility(View.GONE);
        btnAddCompromisso.setVisibility(View.GONE);

        requestQueue = Volley.newRequestQueue(this);
        datasComEventos = new HashSet<>();

        imgVoltar.setOnClickListener(v -> finish());

        SharedPreferences preferences = getSharedPreferences("user_session", MODE_PRIVATE);
        idUser = preferences.getInt("idUser", -1);

        if (idUser == -1) {
            Toast.makeText(this, "Usuário não autenticado!", Toast.LENGTH_SHORT).show();
            finish();
        }

        requestQueue = Volley.newRequestQueue(this);


        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            String selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
            fetchCompromissos(selectedDate);
        });

        btnAddTarefa.setOnClickListener(view -> {
            Intent intent = new Intent(AgendaHomeActivity.this, AgendaNewActivity.class);
            startActivity(intent);
        });

        btnAddCompromisso.setOnClickListener(view -> {
            Intent intent = new Intent(AgendaHomeActivity.this, AgendaNewActivity.class);
            startActivity(intent);
        });
    }

    private void fetchCompromissos(String date) {
        // Implementação anterior sem alterações
        if (idUser == -1) {
            Toast.makeText(this, "Usuário não autenticado!", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = BASE_URL + "/schedule/" + idUser + "/events-by-date?eventDate=" + date;
        Log.d(TAG, "Fetching compromissos from URL: " + url);


        txtStatus.setVisibility(View.VISIBLE);
        containerTarefas.removeAllViews();
        progressBar.setVisibility(View.VISIBLE);
        btnVerMais.setVisibility(View.GONE);
        btnAddTarefa.setVisibility(View.GONE);
        btnAddCompromisso.setVisibility(View.VISIBLE);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    progressBar.setVisibility(View.GONE);

                    try {
                        if (response.length() == 0) {
                            txtStatus.setVisibility(View.VISIBLE);
                            btnAddCompromisso.setVisibility(View.VISIBLE);
                        } else {
                            txtStatus.setVisibility(View.GONE);
                            btnAddCompromisso.setVisibility(View.GONE);
                            btnVerMais.setVisibility(View.VISIBLE);
                            btnAddTarefa.setVisibility(View.VISIBLE);

                            int maxTarefas = Math.min(response.length(), 5);
                            for (int i = 0; i < maxTarefas; i++) {
                                JSONObject obj = response.getJSONObject(i);

                                String titulo = obj.getString("title");
                                String horario = obj.getString("startTime");

                                if (horario != null && horario.length() >= 5) {
                                    horario = horario.substring(0, 5);
                                }

                                View taskView = LayoutInflater.from(this).inflate(R.layout.task_item, containerTarefas, false);

                                TextView taskTitle = taskView.findViewById(R.id.textView9);
                                TextView taskTime = taskView.findViewById(R.id.textView16);

                                taskTitle.setText(titulo);
                                taskTime.setText(horario);

                                containerTarefas.addView(taskView);
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Erro ao processar dados.", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> {
                    progressBar.setVisibility(View.GONE);
                    Log.e(TAG, "Erro ao buscar compromissos", error);
                });

        requestQueue.add(request);
    }
}
