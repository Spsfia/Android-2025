package com.example.apprelativelayout2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    ImageView imagen;
    Switch switch1;
    SeekBar seekBar;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imagen = (ImageView) findViewById(R.id.imagen);
        switch1 = (Switch) findViewById(R.id.switch1);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        seekBar = (SeekBar) findViewById(R.id.seekBar);

        //Evento del Switch

        switch1.setOnCheckedChangeListener((compoundButton, b) -> );
        String estado;
        estado = isChecked?"Activado":"Desactivado";
        Toast.makeText(this, "Switch " + estado, Toast.LENGTH_SHORT).show();

    }

    //evento de Seekbar
    seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener){
        
    }
}