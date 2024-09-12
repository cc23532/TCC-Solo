package com.example.solo;

import android.app.DatePickerDialog;
import android.content.Intent;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class RegisterUserActivity extends AppCompatActivity {

    private EditText editTextApelido, editTextPhone, editTextEmail, editTextDateBorn, editTextHeight, editTextWeight, editTextPwd;
    private RadioGroup radioGroupGender;
    private Button btnCadastro;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        editTextApelido = findViewById(R.id.editTextName);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextDateBorn = findViewById(R.id.editTextDateBorn);
        editTextHeight = findViewById(R.id.editTextHeight);
        editTextWeight = findViewById(R.id.editTextWeight);
        editTextPwd = findViewById(R.id.editTextPassword);
        radioGroupGender = findViewById(R.id.radioGroupGender);
        btnCadastro = findViewById(R.id.buttonRegister);

        requestQueue = Volley.newRequestQueue(this);

        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        editTextDateBorn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
    }

    private void showDatePickerDialog() {

        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        // datePickerDialog - quando o usuario clica la em dataDeNasc aparece um calendario.
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        monthOfYear = monthOfYear + 1;
                        //  data no formato YYYY-MM-DD
                        String date = String.format("%d-%02d-%02d", year, monthOfYear, dayOfMonth);
                        editTextDateBorn.setText(date);
                    }
                }, year, month, day);

        datePickerDialog.show();
    }

    private void registerUser() {
        String apelido = editTextApelido.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String birthday = editTextDateBorn.getText().toString().trim();
        String height = editTextHeight.getText().toString().trim();
        String weight = editTextWeight.getText().toString().trim();
        String password = editTextPwd.getText().toString().trim();

        // genero com o radiuo button
        int selectedGenderId = radioGroupGender.getCheckedRadioButtonId();
        RadioButton selectedGenderButton = findViewById(selectedGenderId);
        String gender = selectedGenderButton != null ? selectedGenderButton.getText().toString() : "";

        if (TextUtils.isEmpty(apelido) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(email) || TextUtils.isEmpty(birthday)
                || TextUtils.isEmpty(gender) || TextUtils.isEmpty(height) || TextUtils.isEmpty(weight) || TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Todos os campos devem ser preenchidos", Toast.LENGTH_SHORT).show();
            return;
        }

        // converte para enviar no JSON o genero como M ou F
        gender = gender.equalsIgnoreCase("Masculino") ? "M" : "F";

        JSONObject userData = new JSONObject();
        try {
            // json a ser enviado
            userData.put("nickname", apelido);
            userData.put("phone", phone);
            userData.put("email", email);
            userData.put("birthday", birthday);
            userData.put("gender", gender);
            userData.put("height", Double.parseDouble(height));
            userData.put("weight", Double.parseDouble(weight));
            userData.put("pwd", password);

            Log.d("RegisterUserActivity", "JSON enviado: " + userData.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // URL do ngrok para o celular via usb
        String url = "https://cc92-143-106-203-198.ngrok-free.app/register/register";

        // URL do emulador
        // String url = "http://10.2.2:3000/register/register";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, userData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(RegisterUserActivity.this, "Usuário cadastrado com sucesso!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterUserActivity.this, MainActivity.class));
                        limparCampos();
                        finish();
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

        requestQueue.add(jsonObjectRequest);
    }

    private void limparCampos() {
        editTextApelido.setText("");
        editTextPhone.setText("");
        editTextEmail.setText("");
        editTextDateBorn.setText("");
        editTextHeight.setText("");
        editTextWeight.setText("");
        editTextPwd.setText("");
        radioGroupGender.clearCheck();
    }
}
