package com.example.solo;

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

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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

public class UpdateUserActivity extends AppCompatActivity {

    private static final String TAG = "UpdateUserActivity";
    private RequestQueue requestQueue;
    private static final String BASE_URL = "http://10.0.2.2:8080";
    private EditText editTextNickname, editTextPhone, editTextEmail, editTextBirthday, editTextHeight, editTextWeight;
    private RadioGroup radioGroupGender;
    private Button btnUpdateUser, btnCancel;
    private int idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_user);

        editTextNickname = findViewById(R.id.editTextNickname);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextBirthday = findViewById(R.id.editTextBirthday);
        editTextHeight = findViewById(R.id.editTextHeight);
        editTextWeight = findViewById(R.id.editTextWeight);
        radioGroupGender = findViewById(R.id.radioGroupGender);
        btnUpdateUser = findViewById(R.id.btnUpdateUser);
        btnCancel = findViewById(R.id.btnCancel);

        requestQueue = Volley.newRequestQueue(this);

        SharedPreferences sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE);
        idUser = sharedPreferences.getInt("idUser", -1); // -1 é um valor padrão caso o id não seja encontrado

        if (idUser != -1) {
            fetchUserData(idUser);
            btnUpdateUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updateUser();
                }
            });

            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            editTextBirthday.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDatePickerDialog();
                }
            });
        } else {
            Toast.makeText(this, "ID de usuário não encontrado.", Toast.LENGTH_SHORT).show();
        }
    }

    private void fetchUserData(int idUser){
        String url = BASE_URL + "/user/update/" + idUser;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String nickname = response.getString("nickname");
                            String email = response.getString("email");
                            String phone = response.getString("phone");
                            String birthday = response.getString("birthday");
                            String height = response.getString("height");
                            String weight = response.getString("weight");
                            String gender = response.getString("gender");

                            // Popula os campos de texto
                            editTextNickname.setText(nickname);
                            editTextEmail.setText(email);
                            editTextPhone.setText(phone);
                            editTextBirthday.setText(birthday);
                            editTextHeight.setText(height);
                            editTextWeight.setText(weight);

                            // Seleciona o RadioButton de acordo com o valor do gênero
                            if (gender.equalsIgnoreCase("masculino")) {
                                radioGroupGender.check(R.id.rbMale);
                            } else if (gender.equalsIgnoreCase("feminino")) {
                                radioGroupGender.check(R.id.rbFemale);
                            } else {
                                radioGroupGender.check(R.id.rbAnotherGender);
                            }

                        } catch (Exception e) {
                            Log.e(TAG, "Erro ao processar a resposta JSON", e);
                            Toast.makeText(UpdateUserActivity.this, "Erro ao processar os dados do usuário.", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "Erro na requisição: " + error.toString());
                        Toast.makeText(UpdateUserActivity.this, "Erro ao obter os dados do usuário.", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
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
                        editTextBirthday.setText(date);
                    }
                }, year, month, day);

        // Mostrar o DatePickerDialog
        datePickerDialog.show();
    }

    private void updateUser() {
        // Obtendo os dados dos campos
        String nickname = editTextNickname.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String birthday = editTextBirthday.getText().toString().trim();
        String height = editTextHeight.getText().toString().trim();
        String weight = editTextWeight.getText().toString().trim();

        // Obtendo o gênero selecionado
        int selectedGenderId = radioGroupGender.getCheckedRadioButtonId();
        RadioButton selectedGenderButton = findViewById(selectedGenderId);
        String gender = selectedGenderButton != null ? selectedGenderButton.getText().toString() : "";

        // Verificando se os campos estão preenchidos
        if (TextUtils.isEmpty(nickname) || TextUtils.isEmpty(phone) || TextUtils.isEmpty(email) || TextUtils.isEmpty(birthday)
                || TextUtils.isEmpty(gender) || TextUtils.isEmpty(height) || TextUtils.isEmpty(weight)) {
            Toast.makeText(this, "Todos os campos devem ser preenchidos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Criando o objeto JSON com os dados do usuário
        JSONObject userData = new JSONObject();
        try {
            userData.put("nickname", nickname);
            userData.put("phone", phone);
            userData.put("email", email);
            userData.put("birthday", birthday);
            userData.put("gender", gender);
            userData.put("height", height);
            userData.put("weight", weight);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // URL do endpoint correto
        String url = BASE_URL + "/user/update/" + idUser; // Substitua pela URL correta

        // Criando a requisição POST
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, userData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Redirecionar para a RegisterHabitsActivity
                            // Limpar os campos após o sucesso
                            clearFields();
                            Intent intent = new Intent(UpdateUserActivity.this, ProfileActivity.class);
                            startActivity(intent);
                        } catch (Exception e) {
                            Log.e(TAG, "Erro ao processar a resposta JSON", e);
                            Toast.makeText(UpdateUserActivity.this, "Erro ao processar a resposta", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String errorMessage = "Erro ao cadastrar o usuário: " + error.toString();
                        Toast.makeText(UpdateUserActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                        Log.e("UpdateUserActivity", "Error details: ", error);
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
        editTextNickname.setText("");
        editTextPhone.setText("");
        editTextEmail.setText("");
        editTextBirthday.setText("");
        editTextHeight.setText("");
        editTextWeight.setText("");
        radioGroupGender.clearCheck(); // Limpar a seleção do RadioGroup
    }
}