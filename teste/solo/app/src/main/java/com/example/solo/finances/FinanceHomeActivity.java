package com.example.solo.finances;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
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
    private static final String BASE_URL = new URL().getURL() + "/finances/extract";
    private static final String TAG = "FinanceHomeActivity";
    private RequestQueue requestQueue;
    private double totalSpent = 0;

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
    }

    private void carregarDespesas() {
        int idUser = getSharedPreferences("user_session", MODE_PRIVATE).getInt("idUser", -1);

        if (idUser != -1) {
            String url = BASE_URL + "/" + idUser;


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
}
