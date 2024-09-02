package com.example.solo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.solo.models.SoloUser;
import com.example.solo.network.ApiClient;
import com.example.solo.network.ApiService;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Button btnLogin;
    private EditText nickname_input, pwd_input;
    private static final String TAG = "MainActivity";
    // Defina a URL base conforme o ambiente (emulador ou dispositivo real)
    private static final String BASE_URL = "http://10.0.2.2:3000"; // Para dispositivo real (substitua pelo IP correto)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnLogin = findViewById(R.id.btnlogin);
        nickname_input = findViewById(R.id.nickname_input);
        pwd_input = findViewById(R.id.pwd_input);

        btnLogin.setOnClickListener(view -> autenticarUsuario());
    }

    private void autenticarUsuario() {
        if (!validarNickname() || !validarSenha()) {
            return;
        }

        String nickname = nickname_input.getText().toString().trim();
        String pwd = pwd_input.getText().toString().trim();

        // Cria o mapa com os parâmetros de login
        Map<String, String> loginData = new HashMap<>();
        loginData.put("nickname", nickname);
        loginData.put("pwd", pwd);

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<SoloUser> call = apiService.login(loginData);

        call.enqueue(new Callback<SoloUser>() {
            @Override
            public void onResponse(Call<SoloUser> call, Response<SoloUser> response) {
                if (response.isSuccessful() && response.body() != null) {
                    SoloUser user = response.body();
                    Log.d(TAG, "Login bem-sucedido: " + user.toString());

                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    intent.putExtra("nickname", user.getNickname());
                    intent.putExtra("pwd", user.getPwd());
                    startActivity(intent);
                    finish();
                } else {
                    Log.e(TAG, "Erro na autenticação: " + response.message());
                    Toast.makeText(MainActivity.this, "Erro na autenticação. Verifique suas credenciais.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SoloUser> call, Throwable t) {
                Log.e(TAG, "Erro na requisição: " + t.getMessage());
                Toast.makeText(MainActivity.this, "Erro na conexão com o servidor. Verifique sua conexão e tente novamente.", Toast.LENGTH_SHORT).show();
            }
        });
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
