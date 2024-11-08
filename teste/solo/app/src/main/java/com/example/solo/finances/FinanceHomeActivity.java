package com.example.solo.finances;

import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.solo.R;
import com.example.solo.Util.URL;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FinanceHomeActivity extends AppCompatActivity {
    private ImageView btnVoltar;
    private TextView totalSpentInfo;
    private TextView expensesList;
    private static final String BASE_URL = new URL().getURL() + "/finances";
    private static final String TAG = "FinanceHomeActivity";
    private RequestQueue requestQueue;
    private double totalSpent = 0;
    private Button btnEnviar, btnAdicionar;
    private EditText valorMovimentacao, dataMovimentacao, finalidadeEditText;
    private RadioButton radioEntrada, radioSaida;
    private int idUser = getSharedPreferences("user_session", MODE_PRIVATE).getInt("idUser", -1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance_home);

        requestQueue = Volley.newRequestQueue(this);


        totalSpentInfo = findViewById(R.id.totalSpentInfo);
        btnVoltar = findViewById(R.id.imgBack);
        expensesList = findViewById(R.id.expensesList);

        btnVoltar.setOnClickListener(v -> finish());

        carregarDespesas();

        btnAdicionar = findViewById(R.id.btnAdicionar);

        btnAdicionar.setOnClickListener(view -> {
            popUpAddRegister();
        });


    }

    private void carregarDespesas() {


        if (idUser != -1) {
            String url = BASE_URL + "/extract/" + idUser;


            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                    response -> {
                        Log.d(TAG, "Resposta recebida: " + response.toString());

                        if (response.length() > 0) {
                            totalSpent = 0;
                            StringBuilder expensesBuilder = new StringBuilder();

                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject expense = response.getJSONObject(i);
                                    double moneyValue = expense.getDouble("moneyValue");
                                    String description = expense.getString("description");

                                    totalSpent += moneyValue;

                                    expensesBuilder.append(description)
                                            .append(", Valor: R$ ").append(moneyValue)
                                            .append("\n");

                                } catch (JSONException e) {
                                    Log.e(TAG, "Erro ao processar a resposta JSON", e);
                                    Toast.makeText(FinanceHomeActivity.this, "Erro ao processar a resposta do servidor.", Toast.LENGTH_SHORT).show();
                                }
                            }

                            totalSpentInfo.setText("Total Gasto: R$ " + totalSpent);
                            expensesList.setText(expensesBuilder.toString());

                        } else {
                            Toast.makeText(FinanceHomeActivity.this, "Nenhuma despesa registrada.", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "Nenhuma despesa registrada.");
                        }
                    }, error -> {
                Log.e(TAG, "Erro na requisição: " + error.toString());

                if (error.networkResponse != null) {
                    Log.e(TAG, "Código de erro HTTP: " + error.networkResponse.statusCode);
                    Log.e(TAG, "Resposta do servidor: " + new String(error.networkResponse.data));
                }

                Toast.makeText(FinanceHomeActivity.this, "Erro ao obter as informações.", Toast.LENGTH_SHORT).show();
            });

            requestQueue.add(jsonArrayRequest);
        } else {
            Toast.makeText(this, "ID do usuário não encontrado.", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "ID do usuário não encontrado.");
        }
    }

    private void popUpAddRegister() {
        Dialog dialog = new Dialog(this, R.style.DialogStyle);
        dialog.setContentView(R.layout.activity_popup_finances);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_window);

        valorMovimentacao = dialog.findViewById(R.id.valorMovimentacao);
        dataMovimentacao = dialog.findViewById(R.id.dataMovimentacao);
        finalidadeEditText = dialog.findViewById(R.id.finalidadeEditText);
        radioEntrada = dialog.findViewById(R.id.radioEntrada);
        radioSaida = dialog.findViewById(R.id.radioSaida);
        btnEnviar = dialog.findViewById(R.id.btnEnviar);
        
        btnEnviar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Obtendo os valores dos campos
                String valor = valorMovimentacao.getText().toString().trim();
                String data = dataMovimentacao.getText().toString().trim();
                String finalidade = finalidadeEditText.getText().toString().trim();

                // Verificando se algum campo obrigatório está vazio
                if (valor.isEmpty() || data.isEmpty() || finalidade.isEmpty()) {
                    Toast.makeText(FinanceHomeActivity.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Determinando o tipo da movimentação (Entrada ou Saída)
                String tipoMovimentacao = "";
                if (radioEntrada.isChecked()) {
                    tipoMovimentacao = "income";  // Entrada = income
                } else if (radioSaida.isChecked()) {
                    tipoMovimentacao = "expense"; // Saída = expense
                } else {
                    Toast.makeText(FinanceHomeActivity.this, "Selecione o tipo de movimentação (Entrada ou Saída)", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Criando o objeto JSON com os dados da movimentação
                JSONObject movimentacaoData = new JSONObject();
                try {
                    movimentacaoData.put("value", valor);
                    movimentacaoData.put("date", data);
                    movimentacaoData.put("purpose", finalidade);
                    movimentacaoData.put("type", tipoMovimentacao);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(FinanceHomeActivity.this, "Erro ao criar os dados", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Definindo a URL para o POST da movimentação financeira
                String url = BASE_URL + "/add/" + idUser;

                // Criando a requisição para enviar os dados
                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, movimentacaoData,
                        new Response.Listener<JSONObject>() {
                            public void onResponse(JSONObject response) {
                                Toast.makeText(FinanceHomeActivity.this, "Movimentação salva com sucesso!", Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "Resposta da API: " + response.toString());
                                dialog.dismiss();
                            }
                        }, new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(FinanceHomeActivity.this, "Erro ao salvar movimentação: " + error.toString(), Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                    }
                });

                // Adicionando a requisição na fila de requisições
                requestQueue.add(jsonObjectRequest);
            }
        });

        dialog.show();


    }

}
