package com.example.solo.AgendaSection;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

public class AgendaHomeActivity extends AppCompatActivity {

    private static final String TAG = "AgendaHomeActivity";
    private static final String BASE_URL = new URL().getURL(); // URL do backend

    private CalendarView calendarView;
    private TextView txtCompromissos;
    private RequestQueue requestQueue;
    private ImageView imgVoltar;

    private int idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda_home);

        // Recupera o idUser do SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE);
        idUser = sharedPreferences.getInt("idUser", -1); // Recupera o idUser salvo
        Log.d(TAG, "idUser recuperado: " + idUser);

        // Inicializando os elementos
        calendarView = findViewById(R.id.calendarView);
        txtCompromissos = findViewById(R.id.txtCompromissos);
        imgVoltar = findViewById(R.id.imgVoltar);
        requestQueue = Volley.newRequestQueue(this);

        // Evento de voltar
        imgVoltar.setOnClickListener(v -> finish());

        // Capturar clique em data no CalendarView
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            String selectedDate = year + "-" + (month + 1) + "-" + dayOfMonth;
            fetchCompromissos(selectedDate);
        });
    }

    private void fetchCompromissos(String date) {
        if (idUser == -1) {
            Toast.makeText(this, "Usuário não autenticado!", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = BASE_URL + "/schedule/" + idUser + "/events-by-date?eventDate=" + date;
        Log.d(TAG, "Fetching compromissos from URL: " + url);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d(TAG, "Response received: " + response);
                        StringBuilder compromissos = new StringBuilder();
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject obj = response.getJSONObject(i);

                                // Obtém os valores do JSON
                                String titulo = obj.getString("title"); // Corrigido para "title"
                                String horario = obj.getString("startTime"); // Corrigido para "startTime"
                                String local = obj.getString("location"); // Adicionado o local
                                String categoria = obj.getString("category"); // Categoria do evento

                                // Concatena as informações
                                compromissos.append("- ").append(titulo)
                                        .append(" (").append(categoria).append(")\n")
                                        .append("  Horário: ").append(horario)
                                        .append("\n  Local: ").append(local)
                                        .append("\n\n");
                            }

                            // Exibe os compromissos ou mensagem padrão
                            txtCompromissos.setText(compromissos.toString().isEmpty() ?
                                    "Nenhum compromisso para esta data." : compromissos.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(AgendaHomeActivity.this, "Erro ao processar dados", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Error fetching compromissos", error);
                        Toast.makeText(AgendaHomeActivity.this, "Erro ao buscar compromissos", Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(request);
    }
}
