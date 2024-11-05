package com.example.solo.DietSection;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DietNewActivity extends AppCompatActivity {

    private static final String TAG = "DietNewActivity"; // Tag para logs
    private static final String BASE_URL = new URL().getURL(); // Base URL do servidor

    private ImageView imgVoltar;
    private EditText data, quantidade, horario;
    private AutoCompleteTextView alimentos;
    private Button btnAdicionarRefeicao;
    private RequestQueue requestQueue;
    private int idUser;  // ID do usuário

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diet_new);

        // Inicializando os campos
        imgVoltar = findViewById(R.id.imgVoltar);
        data = findViewById(R.id.data);
        alimentos = findViewById(R.id.alimentos);
        quantidade = findViewById(R.id.quantidade);
        horario = findViewById(R.id.horario);
        btnAdicionarRefeicao = findViewById(R.id.addMeal);

        // Inicializando a fila de requisições
        requestQueue = Volley.newRequestQueue(this);

        // Obtendo o idUser salvo em SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("register-session", MODE_PRIVATE);
        idUser = sharedPreferences.getInt("idUser", -1); // Recupera o idUser salvo
        Log.d(TAG, "idUser recuperado: " + idUser);

        // Ação para o botão "Voltar"
        imgVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Ação para o botão "Adicionar Refeição"
        btnAdicionarRefeicao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (idUser != -1) {
                    Log.d(TAG, "Iniciando o registro da refeição para o idUser: " + idUser);
                    registerMeal(idUser);
                } else {
                    Toast.makeText(DietNewActivity.this, "Erro: usuário não registrado.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Carregar a lista de alimentos da API
        loadFoodData();
    }

    private void loadFoodData() {
        String url = BASE_URL + "/diet/food-data-ibge/select-food";
        Log.d(TAG, "URL para buscar alimentos: " + url);

        // Criando a requisição GET para buscar os alimentos
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        List<String> alimentosList = new ArrayList<>();
                        try {
                            // Iterando pela resposta da API e adicionando alimentos à lista
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject food = response.getJSONObject(i);
                                String foodName = food.getString("foodName");  // Assumindo que a chave do nome é "foodName"
                                alimentosList.add(foodName);
                            }

                            // Criando o ArrayAdapter com os alimentos
                            ArrayAdapter<String> adapter = new ArrayAdapter<>(DietNewActivity.this, android.R.layout.simple_dropdown_item_1line, alimentosList);
                            alimentos.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(DietNewActivity.this, "Erro ao processar os alimentos.", Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "Erro ao processar a resposta JSON", e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String errorMessage = "Erro ao buscar alimentos: " + error.toString();
                        Toast.makeText(DietNewActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                        Log.e(TAG, "Erro ao buscar alimentos", error);
                    }
                });

        // Adicionando a requisição à fila
        requestQueue.add(request);
    }

    private void registerMeal(int idUser) {
        // Obtendo os dados dos campos
        String dataRefeicao = data.getText().toString().trim();
        String alimentosRefeicao = alimentos.getText().toString().trim();
        String quantidadeRefeicao = quantidade.getText().toString().trim();
        String horarioRefeicao = horario.getText().toString().trim();

        Log.d(TAG, "Dados obtidos: dataRefeicao=" + dataRefeicao + ", alimentosRefeicao=" + alimentosRefeicao +
                ", quantidadeRefeicao=" + quantidadeRefeicao + ", horarioRefeicao=" + horarioRefeicao);

        // Verificar se os campos obrigatórios estão preenchidos
        if (TextUtils.isEmpty(dataRefeicao) || TextUtils.isEmpty(alimentosRefeicao) || TextUtils.isEmpty(quantidadeRefeicao) || TextUtils.isEmpty(horarioRefeicao)) {
            Toast.makeText(this, "Por favor, preencha todos os campos.", Toast.LENGTH_SHORT).show();
            Log.w(TAG, "Campos obrigatórios não preenchidos");
            return;
        }

        // Criando o objeto JSON com os dados da refeição
        JSONObject mealData = new JSONObject();
        try {
            mealData.put("mealDate", dataRefeicao );
            mealData.put("mealTime", horarioRefeicao);
            Log.d(TAG, "Objeto JSON criado: " + mealData);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Erro ao criar os dados da refeição", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Erro ao criar o objeto JSON", e);
            return;
        }

        // Salvando o idUser em "meal-session" novamente, caso necessário
        SharedPreferences sharedPreferences = getSharedPreferences("meal-session", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("idUser", idUser);
        editor.apply();

        // Construindo a URL com o idUser
        String urlMeal = BASE_URL + "/diet/addMeal/" + idUser;
        Log.d(TAG, "URL para adicionar refeição: " + urlMeal);

        // Criando a requisição POST para adicionar refeição
        JsonObjectRequest mealRequest = new JsonObjectRequest(Request.Method.POST, urlMeal, mealData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(DietNewActivity.this, "Refeição adicionada com sucesso!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Resposta da requisição para adicionar refeição: " + response.toString());

                        try {
                            // Obter o idMeal da resposta para adicionar os itens
                            int idMeal = response.getInt("idMeal");
                            addMealItems(idUser, idMeal);  // Chama a função para adicionar os itens à refeição
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(DietNewActivity.this, "Erro ao obter ID da refeição.", Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "Erro ao obter idMeal da resposta", e);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String errorMessage = "Erro ao adicionar refeição: " + error.toString();
                        Toast.makeText(DietNewActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                        Log.e(TAG, "Detalhes do erro: ", error);
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
        requestQueue.add(mealRequest);
    }

    private void addMealItems(int idUser, int idMeal) {
        // Criando a URL para adicionar os itens à refeição
        String urlItems = BASE_URL + "/diet/addMeal/" + idUser + "/items/" + idMeal;
        Log.d(TAG, "URL para adicionar itens à refeição: " + urlItems);

        // Criando o objeto JSON para os itens (exemplo, você pode coletar os dados dos campos aqui)
        JSONObject itemsData = new JSONObject();
        try {
            // Adicione os itens à refeição aqui
            itemsData.put("foodName", "Exemplo de Alimento");
            itemsData.put("weight", 1);
            Log.d(TAG, "Objeto JSON para itens criado: " + itemsData);
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Erro ao criar os itens da refeição", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "Erro ao criar o objeto JSON para itens", e);
            return;
        }

        // Criando a requisição POST para adicionar itens à refeição
        JsonObjectRequest itemsRequest = new JsonObjectRequest(Request.Method.POST, urlItems, itemsData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(DietNewActivity.this, "Itens adicionados à refeição com sucesso!", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "Resposta da requisição para adicionar itens: " + response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String errorMessage = "Erro ao adicionar itens: " + error.toString();
                        Toast.makeText(DietNewActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                        error.printStackTrace();
                        Log.e(TAG, "Detalhes do erro ao adicionar itens: ", error);
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
        requestQueue.add(itemsRequest);
    }
}
