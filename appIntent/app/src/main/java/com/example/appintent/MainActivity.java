package com.example.appintent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void abrirMapas(View view){
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:25.53544, -103.43483")); //Latitud y longitud del tec laguna
        startActivity(i);
    }
    public void tomarFoto(View view){
        Intent i = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivity(i);
    }

    public void abrirPagWeb(View view){
        Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com.mx")); //Latitud y longitud del tec laguna
        startActivity(i);
    }

    public void hacerllamada(View view){
        Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:8711718979")); //Numero
//        if(ContextCompat.checkSelfPermission(MainActivity.this, MainActivity.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED){
//            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest});
//        }
        startActivity(i);
    }
}