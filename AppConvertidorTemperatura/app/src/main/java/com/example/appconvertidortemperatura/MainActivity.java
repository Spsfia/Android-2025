package com.example.appconvertidortemperatura;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {


    Button CaF,FaC;
    EditText pantalla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        CaF = (Button) findViewById(R.id.CaF);
        FaC = (Button) findViewById(R.id.FaC);

        pantalla = (EditText) findViewById(R.id.pantalla);

        CaF.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                float celsius = Float.parseFloat(pantalla.getText().toString());
                float fahrenheit = celsius *9/5 + 32;
                pantalla .setText(String.valueOf(fahrenheit));
            }
        });

        FaC.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                float fahrenheit = Float.parseFloat(pantalla.getText().toString());
                float celsius = (fahrenheit - 32)*5/9;
                pantalla.setText(String.valueOf(celsius));
            }
        });
    }
}