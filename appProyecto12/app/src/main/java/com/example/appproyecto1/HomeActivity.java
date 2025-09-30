package com.example.appproyecto1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {

    // Botón para cerrar sesión
    Button mButtonSalir;

    // Instancia de FirebaseAuth
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home); // Cargar el layout de esta actividad

        // Inicializar el botón "Salir"
        mButtonSalir = findViewById(R.id.btnSalir);

        // Obtener la instancia de autenticación de Firebase
        mAuth = FirebaseAuth.getInstance();

        // Acción al presionar el botón "Salir"
        mButtonSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Cerrar sesión del usuario actual
                mAuth.signOut();

                // Volver a MainActivity (pantalla de inicio/sesión)
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);

                // Finalizar esta actividad para que no se pueda volver con el botón "Atrás"
                finish();
            }
        });
    }

    // Verificar si el usuario sigue autenticado cuando la actividad inicia
    @Override
    protected void onStart() {
        super.onStart();

        // Obtener el usuario actual
        FirebaseUser user = mAuth.getCurrentUser();

        // Si no hay usuario autenticado, volver a MainActivity
        if (user == null){
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Cerrar HomeActivity
        }
    }
}
