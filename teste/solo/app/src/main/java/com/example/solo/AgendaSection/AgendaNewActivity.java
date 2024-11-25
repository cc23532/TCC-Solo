package com.example.solo.AgendaSection;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.solo.R;
import com.example.solo.Util.URL;

import org.json.JSONException;
import org.json.JSONObject;

public class AgendaNewActivity extends AppCompatActivity {

    private static final String TAG = "AgendaNewActivity";
    private static final String BASE_URL = new URL().getURL();

    private EditText nomeCompromisso, categoriaCompromisso, dataCompromisso, horarioCompromisso, localizacaoCompromisso;
    private Button btnConfirmar;

    private RequestQueue requestQueue;
    private int idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda_new);

        nomeCompromisso = findViewById(R.id.nomeCompromisso);
        categoriaCompromisso = findViewById(R.id.categoriaCompromisso);
        dataCompromisso = findViewById(R.id.dataCompromisso);
        horarioCompromisso = findViewById(R.id.horarioCompromisso);
        localizacaoCompromisso = findViewById(R.id.localizacaoCompromisso);
        btnConfirmar = findViewById(R.id.btnConfirmar);

        requestQueue = Volley.newRequestQueue(this);

        SharedPreferences preferences = getSharedPreferences("user_session", MODE_PRIVATE);
        idUser = preferences.getInt("idUser", -1);

        if (idUser == -1) {
            Toast.makeText(this, "Usuário não autenticado!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        btnConfirmar.setOnClickListener(v -> enviarDados());
    }

    private void enviarDados() {
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("title", nomeCompromisso.getText().toString());
            jsonBody.put("category", categoriaCompromisso.getText().toString());
            jsonBody.put("eventDate", dataCompromisso.getText().toString());
            jsonBody.put("startTime", horarioCompromisso.getText().toString());
            jsonBody.put("description", "");
            jsonBody.put("location", localizacaoCompromisso.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Erro ao criar os dados!", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = BASE_URL + "/schedule/" + idUser + "/add";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
                response -> {
                    Toast.makeText(this, "Compromisso adicionado com sucesso!", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Resposta: " + response.toString());
                    finish();
                },
                error -> {
                    Log.e(TAG, "Erro na requisição: ", error);
                    Toast.makeText(this, "Erro ao enviar os dados!", Toast.LENGTH_SHORT).show();
                });

        requestQueue.add(jsonObjectRequest);
    }
}
