package com.example.appradiobotones;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText num1, num2;
    RadioButton rbSuma, rbResta, rbMult, rbDiv;
    TextView resultado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        num1 = (EditText)findViewById(R.id.num1);
        num2 = (EditText)findViewById(R.id.num2);

        rbSuma = (RadioButton) findViewById(R.id.rbSuma);
        rbResta = (RadioButton) findViewById(R.id.rbResta);
        rbMult = (RadioButton) findViewById(R.id.rbMult);
        rbDiv = (RadioButton) findViewById(R.id.rbDiv);

        resultado = (TextView) findViewById(R.id.resultado);




    }

    public void realizaOperaciones(View w){
        int n1 = Integer.parseInt(num1.getText().toString());
        int n2 = Integer.parseInt(num2.getText().toString());

        double res = 0;

        if(rbSuma.isChecked()){
            res = n1 + n2;
        } else if (rbResta.isChecked()) {
            res = n1-n2;
        } else if (rbMult.isChecked()) {
            res = n1 * n2;
        } else if (rbDiv.isChecked()) {
            res = (double) n1 / n2;
        }

        resultado.setText((String.valueOf(res)));

    }



}