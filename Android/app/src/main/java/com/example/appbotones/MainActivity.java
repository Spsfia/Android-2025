package com.example.appbotones;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //Variables globales
    TextView resultado;
    EditText num1, num2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resultado = (TextView) findViewById(R.id.resul);
        num1 = (EditText) findViewById(R.id.num1);
        num2 = (EditText) findViewById(R.id.num2);
    }
    //Metodos
    public void suma (View v){
        int n1 = Integer.parseInt(num1.getText().toString()); //Convirtiendo string to int
        int n2 = Integer.parseInt(num2.getText().toString()); //Convirtiendo string to int

        int r = n1 + n2;

        resultado.setText(String.valueOf(r));

    }

    public void resta (View v){
        int n1 = Integer.parseInt(num1.getText().toString()); //Convirtiendo string to int
        int n2 = Integer.parseInt(num2.getText().toString()); //Convirtiendo string to int

        int r = n1 - n2;

        resultado.setText(String.valueOf(r));

    }

    public void mult (View v){
        int n1 = Integer.parseInt(num1.getText().toString()); //Convirtiendo string to int
        int n2 = Integer.parseInt(num2.getText().toString()); //Convirtiendo string to int

        int r = n1 * n2;

        resultado.setText(String.valueOf(r));

    }

    public void div (View v){
        int n1 = Integer.parseInt(num1.getText().toString()); //Convirtiendo string to int
        int n2 = Integer.parseInt(num2.getText().toString()); //Convirtiendo string to int

        int r = n1 / n2;

        resultado.setText(String.valueOf(r));

    }


}