package com.example.solo.Login_Register;

import android.content.Intent;
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
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.solo.UserSection.HomeActivity;
import com.example.solo.R;
import com.example.solo.Util.URL;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class LoginActivity extends AppCompatActivity {

    private Button btnCreateAccount;
    private Button btnLogin;
    private EditText nickname_input, pwd_input;
    private RequestQueue requestQueue;
    private static final String TAG = "LoginActivity";
<<<<<<< HEAD:teste/solo/app/src/main/java/com/example/solo/LoginActivity.java

    // Defina a URL base conforme o ambiente (emulador ou dispositivo real)
    // private static final String BASE_URL = "http://10.0.2.2:8080"; // Para emulador

    // Conectar pelo celular no pc, URL gerada pelo ngrok
     private static final String BASE_URL = "https://2930-143-106-200-95.ngrok-free.app";
=======
    private static final String BASE_URL = new URL().getURL();
>>>>>>> 0562a13faf09441a381e1c7bc96690ae3d593b25:teste/solo/app/src/main/java/com/example/solo/Login_Register/LoginActivity.java

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializa a fila de requisições
        requestQueue = Volley.newRequestQueue(this);

        // Inicializando componentes da interface
        btnLogin = findViewById(R.id.btnlogin);
        nickname_input = findViewById(R.id.nickname_input);
        pwd_input = findViewById(R.id.pwd_input);

        // Clique para autenticar o usuário
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                autenticarUsuario();
            }
        });

        // Clique para ir à tela de criação de conta
        btnCreateAccount = findViewById(R.id.btnCreateAccount);
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterUserActivity.class);
                startActivity(intent);
            }
        });
    }


    private void autenticarUsuario() {
        if (!validarNickname() || !validarSenha()) {
            return;
        }

        String url = BASE_URL + "/login";

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("nickname", nickname_input.getText().toString().trim());
            jsonBody.put("pwd", pwd_input.getText().toString().trim());
        } catch (Exception e) {
            Log.e(TAG, "Erro ao criar o JSON", e);
            Toast.makeText(LoginActivity.this, "Erro ao criar o JSON.", Toast.LENGTH_SHORT).show();
            return;
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonBody,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d(TAG, "Resposta da API: " + response.toString());

                            int idUser = response.getInt("idUser");
                            String nickname = response.getString("nickname");
                            Log.d(TAG, "idUser salvo: " + idUser);


                            SharedPreferences sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putInt("idUser", idUser);
                            editor.putString("nickname", nickname);

                            if (editor.commit()){
                                Toast.makeText(LoginActivity.this, "Usuário salvo com sucesso", Toast.LENGTH_SHORT).show();

                                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                                startActivity(intent);
                                finish();
                            }


                        } catch (Exception e) {
                            Log.e(TAG, "Erro ao processar a resposta JSON", e);
                            Toast.makeText(LoginActivity.this, "Erro ao processar a resposta do servidor.", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Erro na requisição: " + error.toString());
                        String errorMessage = "Ocorreu um erro desconhecido.";

                        if (error.networkResponse != null) {
                            String responseBody;
                            try {
                                responseBody = new String(error.networkResponse.data, HttpHeaderParser.parseCharset(error.networkResponse.headers, "utf-8"));
                                Log.e(TAG, "Resposta de erro do servidor: " + responseBody);
                                errorMessage = responseBody;
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                        } else if (error.getCause() instanceof java.net.ConnectException) {
                            errorMessage = "Não foi possível conectar ao servidor. Verifique sua conexão e tente novamente.";
                        }

                        Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                    }
                }
        );

        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(
                5000, // Timeout em milissegundos
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        ));

        requestQueue.add(jsonObjectRequest);
    }

    private boolean validarNickname() {
        String nickname = nickname_input.getText().toString().trim();
        if (nickname.isEmpty()) {
            nickname_input.setError("Nickname não pode ser vazio");
            return false;
        }

        nickname_input.setError(null);
        return true;
    }

    private boolean validarSenha() {
        String senha = pwd_input.getText().toString().trim();
        if (senha.isEmpty()) {
            pwd_input.setError("Senha não pode ser vazia");
            return false;
        }
        if (senha.length() < 4) {
            pwd_input.setError("A senha deve ter pelo menos 4 caracteres");
            return false;
        }
        pwd_input.setError(null);
        return true;
    }
}
