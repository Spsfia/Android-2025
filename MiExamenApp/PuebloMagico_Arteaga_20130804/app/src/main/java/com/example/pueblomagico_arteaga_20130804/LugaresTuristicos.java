package com.example.pueblomagico_arteaga_20130804;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LugaresTuristicos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lugares_turisticos);

        Button buttonRegresar = findViewById(R.id.buttonRL);
        buttonRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LugaresTuristicos.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });



    }
}