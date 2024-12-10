package com.example.solo.UserSection;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.solo.R;
import com.example.solo.Util.URL;

import org.json.JSONObject;

public class RecoverPwdActivity extends AppCompatActivity {

    private static final String TAG = "RecoverPwdActivity";
    private RequestQueue requestQueue;

    private EditText email, novaSenha, confirmNovaSenha;
    private Button btnConfirmar;

    private int idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recover_pwd);

        // Inicializa a fila de requisições
        requestQueue = Volley.newRequestQueue(this);

        // Inicializa os componentes da interface
        email = findViewById(R.id.email);
        novaSenha = findViewById(R.id.novaSenha);
        confirmNovaSenha = findViewById(R.id.confirmNovaSenha);
        btnConfirmar = findViewById(R.id.btnConfirmar);

        // Recupera o idUser da sessão
        SharedPreferences sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE);
        idUser = sharedPreferences.getInt("idUser", -1);

        if (idUser == -1) {
            Toast.makeText(this, "Erro: Usuário não autenticado.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Configura o clique no botão
        btnConfirmar.setOnClickListener(view -> {
            if (validarCampos()) {
                recuperarSenha();
            }
        });
    }

    private void recuperarSenha() {
        String urlBase = new URL().getURL();
        String url = urlBase + "/recoverPwd/" + idUser;

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("email", email.getText().toString());
            jsonBody.put("pwd", novaSenha.getText().toString());
        } catch (Exception e) {
            Log.e(TAG, "Erro ao criar o JSON", e);
            Toast.makeText(this, "Erro ao criar os dados de requisição.", Toast.LENGTH_SHORT).show();
            return;
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.PUT,
                url,
                jsonBody,
                response -> {
                    Log.d(TAG, "Resposta da API: " + response.toString());
                    Toast.makeText(this, "Senha atualizada com sucesso!", Toast.LENGTH_SHORT).show();
                    finish();
                },
                error -> {
                    Log.e(TAG, "Erro na requisição: " + error.toString());
                    Toast.makeText(this, "Erro ao atualizar a senha.", Toast.LENGTH_SHORT).show();
                }
        );

        requestQueue.add(jsonObjectRequest);
    }

    private boolean validarCampos() {
        String emailInput = email.getText().toString();
        String senhaInput = novaSenha.getText().toString();
        String confirmSenhaInput = confirmNovaSenha.getText().toString();

        if (emailInput.isEmpty()) {
            email.setError("Campo obrigatório.");
            return false;
        }
        if (senhaInput.isEmpty()) {
            novaSenha.setError("Campo obrigatório.");
            return false;
        }
        if (confirmSenhaInput.isEmpty()) {
            confirmNovaSenha.setError("Campo obrigatório.");
            return false;
        }
        if (!senhaInput.equals(confirmSenhaInput)) {
            confirmNovaSenha.setError("Senhas não coincidem.");
            return false;
        }
        return true;
    }
}
