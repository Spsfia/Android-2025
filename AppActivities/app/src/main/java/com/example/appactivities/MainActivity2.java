package com.example.appactivities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

public class MainActivity2 extends AppCompatActivity {

    RadioButton suma, resta, mult, div;
    Button calcular;
    TextView res2;
    String mensaje = "";
    double num1, num2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        suma = findViewById(R.id.suma);
        resta = findViewById(R.id.resta);
        mult = findViewById(R.id.mult);
        div = findViewById(R.id.div);
        calcular = findViewById(R.id.btnCalcular);
        res2 = findViewById(R.id.resultado2);

        Intent i = getIntent();
        num1 = i.getDoubleExtra("numero1", 0.0);
        num2 = i.getDoubleExtra("numero2", 0.0);

        calcular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                calcularOperacion();

                Intent returnIntent = new Intent();
                returnIntent.putExtra("resultado", mensaje);
                setResult(RESULT_OK, returnIntent);
                finish(); // Cierra MainActivity2 y regresa a MainActivity
            }
        });
    }

    private void calcularOperacion() {
        if (suma.isChecked()) {
            mensaje = "Suma: " + num1 + " + " + num2 + " = " + (num1 + num2);
        } else if (resta.isChecked()) {
            mensaje = "Resta: " + num1 + " - " + num2 + " = " + (num1 - num2);
        } else if (div.isChecked()) {
            if (num2 != 0) {
                mensaje = "División: " + num1 + " / " + num2 + " = " + (num1 / num2);
            } else {
                mensaje = "Error: División por cero";
            }
        } else if (mult.isChecked()) {
            mensaje = "Multiplicación: " + num1 + " * " + num2 + " = " + (num1 * num2);
        } else {
            mensaje = "Selecciona una operación";
        }
        res2.setText(mensaje);
    }
}
