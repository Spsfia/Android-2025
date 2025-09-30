package com.example.appactivities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText numero1, numero2;
    ImageButton btnEnviar;
    TextView resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        numero1 = findViewById(R.id.numero1);
        numero2 = findViewById(R.id.numero2);
        btnEnviar = findViewById(R.id.btnEnviar);
        resultado = findViewById(R.id.resultado);

        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (numero1.getText().toString().isEmpty() || numero2.getText().toString().isEmpty()) {
                    resultado.setText("Ingresa ambos números");
                    return;
                }

                double n1 = Double.parseDouble(numero1.getText().toString());
                double n2 = Double.parseDouble(numero2.getText().toString());

                Intent i = new Intent(MainActivity.this, MainActivity2.class);
                i.putExtra("numero1", n1);
                i.putExtra("numero2", n2);
                startActivityForResult(i, 1); // Se espera un resultado de MainActivity2
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK) {
            String resultadoTexto = data.getStringExtra("resultado");
            resultado.setText(resultadoTexto); // Muestra el resultado en el TextView
        }
    }
}
