package com.example.appimc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    EditText Numero1, Numero2;
    Button Boton;
    TextView Resultado;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Numero1 = (EditText) findViewById(R.id.number1);
        Numero2 = (EditText) findViewById(R.id.number2);
        Boton = (Button) findViewById(R.id.Boton);
        Resultado = (TextView) findViewById(R.id.Resultado);
        imageView = (ImageView) findViewById(R.id.imageView);

        Boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double n1 = Double.parseDouble(Numero1.getText().toString());
                double n2 = Double.parseDouble(Numero2.getText().toString());

                double IMC = n1 / Math.pow(n2, 2);
                String resul;

                if (IMC < 18.5) {
                    resul = "Bajo peso";
                    imageView.setImageResource(R.drawable.delgado);
                } else if (IMC >= 18.5 && IMC <= 24.9) {
                    resul = "Peso normal";
                    imageView.setImageResource(R.drawable.estable);
                } else if (IMC >= 25 && IMC <= 29.9) {
                    resul = "Sobrepeso";
                    imageView.setImageResource(R.drawable.big);
                } else {
                    resul = "Obesidad";
                    imageView.setImageResource(R.drawable.obeso2);
                }

                Resultado.setText("IMC = " + String.valueOf(IMC) + " " + resul);
            }
        });

    }

}
