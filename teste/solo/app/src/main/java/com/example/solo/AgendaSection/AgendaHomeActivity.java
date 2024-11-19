package com.example.solo.AgendaSection;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.solo.R;

public class AgendaHomeActivity extends AppCompatActivity {

    private ImageView imgVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda_home);

        imgVoltar = findViewById(R.id.imgVoltar);
        imgVoltar.setOnClickListener(v -> finish());

    }
}