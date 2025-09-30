package com.example.appproyecto1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    // Componentes de la interfaz
    EditText mEditTextNumberPhone;
    Button mButtonSend;
    TextView mTextViewResponse;

    // Instancia de autenticación de Firebase
    FirebaseAuth mAuth;

    // Callbacks para manejar la verificación del número
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicialización de elementos del layout
        mEditTextNumberPhone = findViewById(R.id.editTextNumberT);
        mButtonSend = findViewById(R.id.btnSend);
        mTextViewResponse = findViewById(R.id.textViewResponse);

        // Obtener instancia de FirebaseAuth
        mAuth = FirebaseAuth.getInstance();

        // Establecer idioma de Firebase a español
        mAuth.setLanguageCode("es");

        // Acción al presionar el botón "Enviar"
        mButtonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener número ingresado
                String phoneNumber = "+" + mEditTextNumberPhone.getText().toString();

                if (!phoneNumber.isEmpty()) {
                    // Configuración de opciones para verificación del número
                    PhoneAuthOptions options =
                            PhoneAuthOptions.newBuilder(mAuth)
                                    .setPhoneNumber(phoneNumber)       // Número a verificar
                                    .setTimeout(60L, TimeUnit.SECONDS) // Tiempo de espera
                                    .setActivity(MainActivity.this)    // Actividad actual
                                    .setCallbacks(mCallbacks)         // Callbacks definidos abajo
                                    .build();

                    // Iniciar proceso de verificación
                    PhoneAuthProvider.verifyPhoneNumber(options);
                } else {
                    // Mostrar mensaje de error si el campo está vacío
                    mTextViewResponse.setText("Ingrese el numero de telefono con su respectivo codigo de Pais");
                    mTextViewResponse.setTextColor(Color.RED);
                }
            }
        });

        // Definición de los callbacks para manejar eventos de verificación
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            // Si se verifica automáticamente el número (verificación instantánea o recuperación automática del código)
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                // Iniciar sesión automáticamente con las credenciales recibidas
                iniciarSesion(phoneAuthCredential);
            }

            // Si la verificación falla (número inválido, sin conexión, etc.)
            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                mTextViewResponse.setText(e.getMessage());
                mTextViewResponse.setTextColor(Color.RED);
            }

            // Si el código fue enviado con éxito al número ingresado
            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);

                mTextViewResponse.setText("El codigo de verificacion fue enviado");
                mTextViewResponse.setTextColor(Color.BLACK);

                // Esperar 1 segundo antes de pasar a la actividad de verificación
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // Lanzar VerificationActivity y pasarle el ID de verificación (s)
                        Intent intent = new Intent(MainActivity.this, VerificationActivity.class);
                        intent.putExtra("auth", s); // Enviar ID a VerificationActivity
                        startActivity(intent);
                    }
                }, 1000); // 1 segundo
            }
        };
    }

    // Verificar si ya hay un usuario autenticado al iniciar la actividad
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            // Si ya está autenticado, ir directamente a HomeActivity
            inicioActivityHome();
        }
    }

    // Iniciar sesión con las credenciales obtenidas del código de verificación
    private void iniciarSesion(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Inicio de sesión exitoso
                    inicioActivityHome();
                } else {
                    // Mostrar error si falló el inicio de sesión
                    mTextViewResponse.setText(task.getException().getMessage());
                    mTextViewResponse.setTextColor(Color.RED);
                }
            }
        });
    }

    // Redirigir a la pantalla principal de la aplicación
    private void inicioActivityHome() {
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);
        finish(); // Finalizar MainActivity para que no pueda volver con "atrás"
    }
}
