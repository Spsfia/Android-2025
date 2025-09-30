package com.example.proyectovioleta.ui.home;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.proyectovioleta.DBHelper;
import com.example.proyectovioleta.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private static final int PERMISO_SMS_CODE = 1234;

    private Button btnLlamar911, btnLlamarSeguridad;
    private ImageButton alertButton;
    private Handler handler = new Handler();
    private FusedLocationProviderClient fusedLocationClient;
    private double latitudActual = 0.0;
    private double longitudActual = 0.0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        alertButton = view.findViewById(R.id.btnAlerta);
        btnLlamar911 = view.findViewById(R.id.btnLlamar911);
        btnLlamarSeguridad = view.findViewById(R.id.btnLlamarSeguridad);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity());

        alertButton.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    handler.postDelayed(this::activarAlerta, 3000);
                    break;
                case MotionEvent.ACTION_UP:
                    handler.removeCallbacksAndMessages(null);
                    break;
            }
            return true;
        });

        btnLlamar911.setOnClickListener(v -> llamarEmergencia911());
        btnLlamarSeguridad.setOnClickListener(v -> llamarEmergenciaSP());

        return view;
    }

    private void activarAlerta() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.SEND_SMS}, PERMISO_SMS_CODE);
            return;
        }

        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(getContext(), "Permiso de ubicación no concedido", Toast.LENGTH_SHORT).show();
            return;
        }

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(location -> {
                    if (location != null) {
                        latitudActual = location.getLatitude();
                        longitudActual = location.getLongitude();
                    }

                    try {
                        DBHelper dbHelper = new DBHelper(requireContext());
                        Cursor cursor = dbHelper.getReadableDatabase().rawQuery(
                                "SELECT nombre, contacto1Telefono, contacto2Telefono FROM usuario LIMIT 1", null);

                        if (cursor.moveToFirst()) {
                            String nombreUsuario = cursor.getString(cursor.getColumnIndexOrThrow("nombre"));
                            String telefono1 = cursor.getString(cursor.getColumnIndexOrThrow("contacto1Telefono"));
                            String telefono2 = cursor.getString(cursor.getColumnIndexOrThrow("contacto2Telefono"));

                            String mensaje = "¡Emergencia! Se ha activado una alerta de " + nombreUsuario +
                                    ". Ubicación: " + latitudActual + ", " + longitudActual + ". Verifica su bienestar y llama a " + nombreUsuario;

                            sendSMS(telefono1, mensaje);
                            sendSMS(telefono2, mensaje);

                            Toast.makeText(getContext(), "Mensajes enviados automáticamente", Toast.LENGTH_SHORT).show();

                            dbHelper.insertarEvento("Alerta activada", null);
                        }

                        cursor.close();
                    } catch (Exception e) {
                        Toast.makeText(getContext(), "Error al enviar mensaje: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Error al obtener ubicación", Toast.LENGTH_SHORT).show();
                });
    }

    private void sendSMS(String phoneNumber, String message) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            ArrayList<String> parts = smsManager.divideMessage(message);
            smsManager.sendMultipartTextMessage(phoneNumber, null, parts, null, null);
        } catch (Exception e) {
            Toast.makeText(getContext(), "Error al enviar mensaje: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void llamarEmergencia911() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CALL_PHONE)
                == PackageManager.PERMISSION_GRANTED) {
            DBHelper dbHelper = new DBHelper(requireContext());
            dbHelper.insertarEvento("Llamada al 911", null);

            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:8711718979"));
            startActivity(intent);
        } else {
            Toast.makeText(getContext(), "Permiso de llamada no concedido", Toast.LENGTH_SHORT).show();
        }
    }

    private void llamarEmergenciaSP() {
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CALL_PHONE)
                == PackageManager.PERMISSION_GRANTED) {
            DBHelper dbHelper = new DBHelper(requireContext());
            dbHelper.insertarEvento("Llamada a Seguridad Pública", null);

            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:8711718979"));
            startActivity(intent);
        } else {
            Toast.makeText(getContext(), "Permiso de llamada no concedido", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISO_SMS_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                activarAlerta(); // Reintentar si ya se concedió
            } else {
                Toast.makeText(getContext(), "Permiso de SMS no concedido", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
