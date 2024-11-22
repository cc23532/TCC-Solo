package com.example.solo.AgendaSection;

import android.app.Dialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.solo.R;

public class AgendaHomeActivity extends AppCompatActivity {

    private ImageView imgVoltar;

    private Button btnAddTarefa, btnConfirmar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda_home);

        imgVoltar = findViewById(R.id.imgVoltar);
        imgVoltar.setOnClickListener(v -> finish());

        btnAddTarefa = findViewById(R.id.btnAddTarefa);

        btnAddTarefa.setOnClickListener(view -> {
            addTarefa();
        });
    }

    public void addTarefa(){
        Dialog dialog = new Dialog(this, androidx.appcompat.R.style.Base_Theme_AppCompat_DialogWhenLarge);
        dialog.setContentView(R.layout.activity_add_tarefa);
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_window);

        btnConfirmar = findViewById(R.id.btnConfirmar);

        btnConfirmar.setOnClickListener(view ->{
            dialog.dismiss();
        });

        dialog.show();
    }
}