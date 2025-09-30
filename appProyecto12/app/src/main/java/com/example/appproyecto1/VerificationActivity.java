package com.example.appproyecto1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class VerificationActivity extends AppCompatActivity {

    // Elementos del layout
    EditText mEditTextCode;
    TextView mTextViewResponse;
    Button mButtonVerificar;

    // Autenticación con Firebase
    FirebaseAuth mAuth;

    // ID de verificación recibido desde MainActivity
    String intenAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification); // Cargar diseño XML de verificación

        // Vincular los elementos del layout
        mEditTextCode = findViewById(R.id.textViewCode); // Campo para ingresar el código
        mTextViewResponse = findViewById(R.id.textViewResponse); // Texto para mostrar mensajes (no se usa aquí realmente)
        mButtonVerificar = findViewById(R.id.btnVerificar); // Botón para verificar el código

        // Obtener instancia de autenticación de Firebase
        mAuth = FirebaseAuth.getInstance();

        // Obtener el ID de verificación desde el intent (pasado desde MainActivity)
        intenAuth = getIntent().getStringExtra("auth");

        // Acción al hacer clic en el botón "Verificar"
        mButtonVerificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codigoVerificacio = mEditTextCode.getText().toString(); // Obtener código ingresado

                // Verificar si el campo no está vacío
                if (!codigoVerificacio.isEmpty()) {
                    // Crear credencial con el ID y el código
                    PhoneAuthCredential credential = PhoneAuthProvider.getCredential(intenAuth, codigoVerificacio);

                    // Iniciar sesión con esa credencial
                    iniciarSesion(credential);
                } else {
                    // Mostrar mensaje si el campo está vacío
                    Toast.makeText(VerificationActivity.this, "Ingrese el código de verificación", Toast.LENGTH_SHORT).show();
                }
            }

            // Método para iniciar sesión con la credencial creada
            private void iniciarSesion(PhoneAuthCredential credential) {
                mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // Si fue exitoso, ir a la pantalla principal
                        if (task.isSuccessful()) {
                            inicioActivityHome();
                        } else {
                            // Si falla, mostrar mensaje de error
                            Toast.makeText(VerificationActivity.this, "Error de verificación", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    // Verificar si ya hay un usuario autenticado al iniciar la actividad
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();

        // Si el usuario ya está autenticado, ir directamente a HomeActivity
        if (user != null) {
            inicioActivityHome();
        }
    }

    // Método para abrir la pantalla principal (HomeActivity)
    private void inicioActivityHome() {
        Intent intent = new Intent(VerificationActivity.this, HomeActivity.class);
        startActivity(intent);
        finish(); // Cierra esta actividad para evitar volver con el botón "Atrás"
    }
}
