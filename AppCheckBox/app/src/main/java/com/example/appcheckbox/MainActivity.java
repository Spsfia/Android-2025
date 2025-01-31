package com.example.appcheckbox;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView resultado;
    CheckBox suma, resta, multiplica, divide;

    EditText num1, num2;

    Button boton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultado = (TextView) findViewById(R.id.resultado);
        num1 = (EditText) findViewById(R.id.num1);
        num2 = (EditText) findViewById(R.id.num2);
        suma = (CheckBox) findViewById(R.id.suma);
        resta = (CheckBox) findViewById(R.id.resta);
        multiplica = (CheckBox) findViewById(R.id.multiplica);
        divide = (CheckBox) findViewById(R.id.divide);
        boton = (Button) findViewById(R.id.button);

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int n1 = Integer.parseInt(num1.getText().toString());
                int n2 = Integer.parseInt(num2.getText().toString());
                String res = "";
                if(suma.isChecked()){
                    res = res + (n1 + n2);
                }
                if(resta.isChecked()){
                    res = res + (n1 - n2);
                }
                if(multiplica.isChecked()){
                    res = res + (n1 * n2);
                }
                if(divide.isChecked()){
                    res = res + ((double)n1 / n2);
                }

                resultado.setText(res);
            }
        });



    }
}