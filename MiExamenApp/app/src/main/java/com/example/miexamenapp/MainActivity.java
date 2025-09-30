package com.example.miexamenapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.QuickContactBadge;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText editTextEdad = findViewById(R.id.edad);
        Button btnVerificar = findViewById(R.id.btnVerificar);

        btnVerificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String edadTexto = editTextEdad.getText().toString();


                    int edad = Integer.parseInt(edadTexto);

                    if (edad >= 18) {
                        Toast.makeText(MainActivity.this, "Eres mayor de edad", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(MainActivity.this, ResultadoActivity.class);
                        i.putExtra("edadTexto", edadTexto);
                        startActivity(i);
                    } else {
                        Toast.makeText(MainActivity.this, "Eres menor de edad", Toast.LENGTH_SHORT).show();

                        Intent i = new Intent(MainActivity.this, ResultadoActivity.class);
                        i.putExtra("edadTexto", edadTexto);
                        startActivity(i);
                }
            }



        });


    }
}