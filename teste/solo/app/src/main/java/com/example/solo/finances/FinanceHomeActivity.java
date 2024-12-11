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
    private static final String TAG = "FinanceHomeActivity";
    private static final String BASE_URL = new URL().getURL() + "/finances";

    private ImageView btnVoltar;
    private TextView totalSpentInfo, totalEconomizeInfo;
    private TextView expensesList;
    private RequestQueue requestQueue;
    private double totalSpent = 0;
    private double totalEconomize = 0;

    private Button btnEnviar, btnAdicionar;
    private EditText valorMovimentacao, dataMovimentacao, descricaoEditText, rotuloEditText;
    private RadioButton radioEntrada, radioSaida, radioFixo, radioVariavel;
    private int idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finance_home);

        // Obtendo ID do usuário armazenado nas preferências
        SharedPreferences preferences = getSharedPreferences("user_session", MODE_PRIVATE);
        idUser = preferences.getInt("idUser", -1);

        // Configurando RequestQueue
        requestQueue = Volley.newRequestQueue(this);

        // Inicializando componentes da interface
        totalSpentInfo = findViewById(R.id.totalSpentInfo);
        totalEconomizeInfo = findViewById(R.id.totalEconomizeInfo);
        btnVoltar = findViewById(R.id.imgBack);
        expensesList = findViewById(R.id.expensesList);
        btnAdicionar = findViewById(R.id.btnAdicionar);

        // Ação para botão de voltar
        btnVoltar.setOnClickListener(v -> finish());

        // Carregar lista de despesas ao iniciar
        carregarDespesas();

        // Ação para botão de adicionar
        btnAdicionar.setOnClickListener(view -> popUpAddRegister());
    }

    private void carregarDespesas() {
        if (idUser != -1) {
            String url = BASE_URL + "/extract/" + idUser;

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                    response -> {
                        Log.d(TAG, "Resposta recebida: " + response.toString());

                        // Verificando e processando a resposta JSON
                        // Dentro do método carregarDespesas(), onde o cálculo é feito
                        if (response.length() > 0) {
                            totalSpent = 0;
                            totalEconomize = 0;
                            StringBuilder expensesBuilder = new StringBuilder();

                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject expense = response.getJSONObject(i);
                                    double moneyValue = expense.getDouble("moneyValue");
                                    String description = expense.optString("description", "Sem descrição");
                                    String transactionType = expense.optString("transactionType", "expense");

                                    if ("income".equals(transactionType)) {
                                        totalEconomize += moneyValue;
                                    } else {
                                        totalSpent += moneyValue;
                                    }

                                    expensesBuilder.append(description)
                                            .append(", Valor: R$ ").append(moneyValue)
                                            .append("\n");
                                } catch (JSONException e) {
                                    Log.e(TAG, "Erro ao processar a resposta JSON", e);
                                    Toast.makeText(FinanceHomeActivity.this, "Erro ao processar a resposta do servidor.", Toast.LENGTH_SHORT).show();
                                }
                            }

                            // Ajuste para garantir que totalEconomize não seja negativo
                            totalEconomize = totalEconomize - totalSpent; // Economias totais = entradas - saídas
                            if (totalEconomize < 0) {
                                totalEconomize = 0; // Se o valor for negativo, definimos como zero
                            }

                            totalSpentInfo.setText("R$" + totalSpent);
                            totalEconomizeInfo.setText("R$" + String.format("%.2f", totalEconomize)); // Formatar com 2 casas decimais
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

        // Inicializando componentes da interface do pop-up
        valorMovimentacao = dialog.findViewById(R.id.valorMovimentacao);
        dataMovimentacao = dialog.findViewById(R.id.dataMovimentacao);
        descricaoEditText = dialog.findViewById(R.id.descricaoEditText);
        rotuloEditText = dialog.findViewById(R.id.rotuloEditText);
        descricaoEditText = dialog.findViewById(R.id.descricaoEditText);
        radioEntrada = dialog.findViewById(R.id.radioEntrada);
        radioSaida = dialog.findViewById(R.id.radioSaida);
        radioFixo = dialog.findViewById(R.id.radioFixo);
        radioVariavel = dialog.findViewById(R.id.radioVariavel);
        btnEnviar = dialog.findViewById(R.id.btnEnviar);

        // Ação do botão enviar
        btnEnviar.setOnClickListener(v -> {
            String valor = valorMovimentacao.getText().toString().trim();
            String data = dataMovimentacao.getText().toString().trim();
            String rotulo = rotuloEditText.getText().toString().trim();
            String descricao = descricaoEditText.getText().toString().trim();

            // Validar campos obrigatórios
            if (valor.isEmpty() || data.isEmpty() || rotulo.isEmpty()) {
                Toast.makeText(FinanceHomeActivity.this, "Preencha os campos obrigatórios", Toast.LENGTH_SHORT).show();
                return;
            }

            // Determinar tipo de movimentação e gasto
            String transactionType = radioEntrada.isChecked() ? "income" : "expense";
            String movementType = radioFixo.isChecked() ? "fixed" : "variable";

            // Criar objeto JSON
            JSONObject movimentacaoData = new JSONObject();
            try {
                movimentacaoData.put("transactionType", transactionType);
                movimentacaoData.put("movementType", movementType);
                movimentacaoData.put("moneyValue", Double.parseDouble(valor));
                movimentacaoData.put("activityDate", data);
                movimentacaoData.put("label", rotulo);
                movimentacaoData.put("description", descricao.isEmpty() ? "Sem descrição" : descricao);

                Log.d(TAG, "JSON Enviado: " + movimentacaoData.toString());
            } catch (JSONException e) {
                Log.e(TAG, "Erro ao criar JSON", e);
                Toast.makeText(FinanceHomeActivity.this, "Erro ao criar o JSON", Toast.LENGTH_SHORT).show();
                return;
            }

            // Enviar requisição ao servidor
            String url = BASE_URL + "/add/" + idUser;
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, movimentacaoData,
                    response -> {
                        Toast.makeText(FinanceHomeActivity.this, "Movimentação salva com sucesso!", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                        carregarDespesas(); // Atualizar despesas após adicionar
                    },
                    error -> {
                        Log.e(TAG, "Erro ao salvar movimentação", error);
                        Toast.makeText(FinanceHomeActivity.this, "Erro ao salvar movimentação.", Toast.LENGTH_SHORT).show();
                    });

            requestQueue.add(jsonObjectRequest);
        });

        dialog.show();
    }
}
