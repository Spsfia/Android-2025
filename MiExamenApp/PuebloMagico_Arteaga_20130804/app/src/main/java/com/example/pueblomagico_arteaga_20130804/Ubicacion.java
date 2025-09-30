package com.example.pueblomagico_arteaga_20130804;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Ubicacion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ubicacion);


        Button buttonRegresar = findViewById(R.id.buttonRU);
        buttonRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Ubicacion.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    public void abrirMapas(View view){
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:25.443189, -100.856114")); //Latitud y longitud de Arteaga
        startActivity(i);
    }
}


