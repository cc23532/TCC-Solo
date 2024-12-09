package com.example.solo.Login_Register;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class RegisterUserActivity extends AppCompatActivity {

    private static final String BASE_URL = new URL().getURL();

    private EditText editTextApelido, editTextPhone, editTextEmail, editTextDateBorn, editTextHeight, editTextWeight, editTextPwd;
    private RadioGroup radioGroupGender;
    private Button btnCadastro;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        // Inicializando os campos
        editTextApelido = findViewById(R.id.editTextNickname);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextDateBorn = findViewById(R.id.editTextDateBorn);
        editTextHeight = findViewById(R.id.editTextHeight);
        editTextWeight = findViewById(R.id.editTextWeight);
        editTextPwd = findViewById(R.id.editTextPassword);
        radioGroupGender = findViewById(R.id.radioGroupGender);
        btnCadastro = findViewById(R.id.buttonRegister);

        // Inicializando a fila de requisições
        requestQueue = Volley.newRequestQueue(this);

        // Configurando o botão de cadastro
        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        // Configurando o campo de data de nascimento
        editTextDateBorn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
    }

    private void showDatePickerDialog() {
        // Obter a data atual
        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // Criar um DatePickerDialog
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Ajustar o mês (DatePicker fornece o mês de 0 a 11)
                        monthOfYear = monthOfYear + 1;
                        // Formatar a data no formato YYYY-MM-DD (ISO)
                        String date = String.format("%d-%02d-%02d", year, monthOfYear, dayOfMonth);
                        // Definir a data no EditText
                        editTextDateBorn.setText(date);
                    }
                }, year, month, day);

        // Mostrar o DatePickerDialog
        datePickerDialog.show();
    }

    private void registerUser() {
        // Obtendo os dados dos campos
        String apelido = editTextApelido.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String dateBorn = editTextDateBorn.getText().toString().trim();
        String height = editTextHeight.getText().toString().trim();
        String weight = editTextWeight.getText().toString().trim();
        String password = editTextPwd.getText().toString().trim();

        // Obtendo o gênero selecionado
        int selectedGenderId = radioGroupGender.getCheckedRadioButtonId();
        RadioButton selectedGenderButton = findViewById(selectedGenderId);
        String gender = selectedGenderButton != null ? selectedGenderButton.getText().toString() : "";

        // Verificando se os campos estão preenchidos
        if (TextUtils.isEmpty(apelido) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(email) || TextUtils.isEmpty(dateBorn)
                || TextUtils.isEmpty(gender) || TextUtils.isEmpty(height) || TextUtils.isEmpty(weight) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Todos os campos devem ser preenchidos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Criando o objeto JSON com os dados do usuário
        JSONObject userData = new JSONObject();
        try {
            userData.put("nickname", apelido);
            userData.put("phone", phone);
            userData.put("email", email);
            userData.put("birthday", dateBorn);
            userData.put("gender", gender);
            userData.put("height", height);
            userData.put("weight", weight);
            userData.put("pwd", password);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // URL do endpoint correto
        String url = BASE_URL + "/register/register"; // Substitua pela URL correta

        // Criando a requisição POST
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, userData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Obtendo o idUser da resposta
                            int registeredUser = response.getInt("idUser");

                            // Salvando o idUser em SharedPreferences
                            SharedPreferences sharedPreferences = getSharedPreferences("register-session", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putInt("idUser", registeredUser);
                            editor.apply();

                            // Redirecionar para a RegisterHabitsActivity
                            Intent intent = new Intent(RegisterUserActivity.this, RegisterHabitsActivity.class);
                            intent.putExtra("idUser", registeredUser); // Passando o idUser como parâmetro
                            startActivity(intent);

                            // Limpar os campos após o sucesso
                            clearFields();
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(RegisterUserActivity.this, "Erro ao processar a resposta", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String errorMessage = "Erro ao cadastrar o usuário: " + error.toString();
                        Toast.makeText(RegisterUserActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                        Log.e("RegisterUserActivity", "Error details: ", error);
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json; charset=utf-8");
                return headers;
            }
        };

        // Adicionando a requisição à fila
        requestQueue.add(jsonObjectRequest);
    }


    private void clearFields() {
        editTextApelido.setText("");
        editTextPhone.setText("");
        editTextEmail.setText("");
        editTextDateBorn.setText("");
        editTextHeight.setText("");
        editTextWeight.setText("");
        editTextPwd.setText("");
        radioGroupGender.clearCheck(); // Limpar a seleção do RadioGroup
    }
}
