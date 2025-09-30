package com.example.miexamenapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ResultadoActivity extends AppCompatActivity {

    Button btnRegresar;
    TextView tuedad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultado);

        btnRegresar = findViewById(R.id.btnRegresar);
        tuedad = findViewById(R.id.edadTexto);

        Intent i = getIntent();
        String edadTexto = i.getStringExtra("edadTexto");


            tuedad.setText("Tu edad es de " + edadTexto + " años");


        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultadoActivity.this, MainActivity.class);
                startActivity(intent);
                finish(); // Cierra la actividad actual
            }
        });
    }
}
