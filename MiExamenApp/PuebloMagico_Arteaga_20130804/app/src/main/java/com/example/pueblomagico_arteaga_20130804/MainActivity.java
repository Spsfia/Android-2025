package com.example.pueblomagico_arteaga_20130804;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    Button buttonG, buttonT, buttonE, buttonU;
    public ImageView imageView;
    public int currentImageIndex = 0;
    public int[] imageResources = {
            R.drawable.arteaga1,
            R.drawable.arteaga2,
            R.drawable.arteaga3,
            R.drawable.arteaga4
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.imageView1);

        //Gastronomia
        Button buttonGastronomia = findViewById(R.id.buttonG);
        // Configurar el listener para abrir la nueva Activity
        buttonGastronomia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Crear el Intent para abrir GastronomiaActivity
                Intent intent = new Intent(MainActivity.this, Gastronomia.class);
                startActivity(intent); // Iniciar la nueva actividad
            }
        });

        //Entretenimiento
        Button buttonEntretenimiento = findViewById(R.id.buttonE);
        buttonEntretenimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Entretenimiento.class);
                startActivity(intent);
            }
        });

        //Lugares turisticos
        Button buttonTuristico = findViewById(R.id.buttonT);
        buttonTuristico.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LugaresTuristicos.class);
                startActivity(intent);
            }
        });

        //Ubicación
        Button buttonUbicacion = findViewById(R.id.buttonU);
        buttonUbicacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Ubicacion.class);
                startActivity(intent);
            }
        });


        final Handler handler = new Handler();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                // Cambia la imagen en base al índice actual
                imageView.setImageResource(imageResources[currentImageIndex]);

                // Incrementa el índice para la próxima imagen
                currentImageIndex = (currentImageIndex + 1) % imageResources.length;

                // Repite el cambio de imagen después de 3 segundos
                handler.postDelayed(this, 3000);
            }
        };

        // Llama al Runnable para que empiece a cambiar la imagen
        handler.post(runnable);


    }
}