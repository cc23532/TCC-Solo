package com.example.solo.WorkoutSection;

import android.content.SharedPreferences;
import android.location.Location;
import android.os.Bundle;
import android.Manifest;
import android.content.pm.PackageManager;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.solo.R;
import com.example.solo.Util.URL;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CardioNewActivity extends FragmentActivity {

    private static final String BASE_URL = new URL().getURL();
    private static final String DIRECTIONS_API_URL = "https://maps.googleapis.com/maps/api/directions/json";
    private static final String ELEVATION_API_URL = "https://maps.googleapis.com/maps/api/elevation/json";

    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;

    private List<Location> routePoints = new ArrayList<>();
    private long startTime;
    private long endTime;

    private EditText etDurationTime, etDistance, etAverageSpeed, etElevationGain;
    private Button btnFinishActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardio_new);

        SharedPreferences sharedPreferences = getSharedPreferences("user_session", MODE_PRIVATE);
        int idUser = sharedPreferences.getInt("idUser", -1);
        Log.d("CardioNewActivity", "Nova atividade --> idUser: " + idUser);

        if (idUser != -1) {
            etDurationTime = findViewById(R.id.etDurationTime);
            etDistance = findViewById(R.id.etDistance);
            etAverageSpeed = findViewById(R.id.etAverageSpeed);
            etElevationGain = findViewById(R.id.etElevationGain);
            btnFinishActivity = findViewById(R.id.btnFinishActivity);

            // Inicializar o cliente de localização
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

            // Definir o callback para coletar localizações em tempo real
            locationCallback = new LocationCallback() {
                @Override
                public void onLocationResult(@NonNull LocationResult locationResult) {
                    Location newLocation = locationResult.getLastLocation();

                    if (newLocation != null) {
                        // Adicionar a nova localização à rota
                        routePoints.add(newLocation);
                        Log.d("CardioNewActivity", "Novo ponto de localização: " + newLocation.getLatitude() + ", " + newLocation.getLongitude());
                    }
                }
            };

            // Iniciar o monitoramento de localização ao começar o exercício
            startTrackingExercise();
            btnFinishActivity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finishActivity();
                }
            });
        }
    }

    private void startTrackingExercise() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            return;
        }

        // Iniciar o tempo do exercício
        startTime = System.currentTimeMillis();

        // Configurar solicitações de localização
        LocationRequest locationRequest = LocationRequest.create()
                .setInterval(5000) // Atualização a cada 5 segundos
                .setFastestInterval(2000) // Mais rápido a cada 2 segundos
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, getMainLooper());
    }

    private void stopTrackingExercise() {
        // Parar de coletar dados de localização
        fusedLocationClient.removeLocationUpdates(locationCallback);
        endTime = System.currentTimeMillis();

        // Aqui, você pode traçar a rota usando a Directions API
        traceRoute();
    }

    private void traceRoute() {
        if (routePoints.size() < 2) return; // Precisamos de pelo menos dois pontos

        // Prepare as coordenadas para a chamada da API
        StringBuilder waypoints = new StringBuilder();
        for (Location location : routePoints) {
            waypoints.append(location.getLatitude()).append(",").append(location.getLongitude()).append("|");
        }
        String waypointsStr = waypoints.toString();

        String url = DIRECTIONS_API_URL + "?origin=" + routePoints.get(0).getLatitude() + "," + routePoints.get(0).getLongitude() +
                "&destination=" + routePoints.get(routePoints.size() - 1).getLatitude() + "," + routePoints.get(routePoints.size() - 1).getLongitude() +
                "&waypoints=" + waypointsStr;

        // Fazer a chamada HTTP
        new Thread(() -> {
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder().url(url).build();
            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    String responseData = response.body().string();
                    Log.d("DirectionsAPI", responseData);
                    // Aqui você pode processar a resposta para mostrar a rota no mapa
                } else {
                    Log.e("DirectionsAPI", "Erro: " + response.code());
                }
            } catch (IOException e) {
                Log.e("DirectionsAPI", "Erro na chamada da API", e);
            }
        }).start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Parar o monitoramento da rota quando a atividade for pausada ou parada
        stopTrackingExercise();
    }

    private void finishActivity() {
        // Aqui você pode implementar a lógica para finalizar a atividade
    }
}
