package com.example.proyectovioleta;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ConfiguracionFragment extends Fragment {

    private static final int CODIGO_SELECCION_IMAGEN = 2;

    private EditText etNombreCompletoConfig, etTelefonoConfig, etDomicilioConfig;
    private EditText etContacto1NombreConfig, etContacto1TelefonoConfig, etContacto1RelacionConfig;
    private EditText etContacto2NombreConfig, etContacto2TelefonoConfig, etContacto2RelacionConfig;
    private Button btnGuardarCambios, btnCambiarFoto;
    private ImageView imagenUsuarioConfig;

    private DBHelper dbHelper;
    private Bitmap imagenBitmap; // Para guardar la nueva imagen seleccionada

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_configuracion, container, false);

        // Inicializar vistas
        imagenUsuarioConfig = view.findViewById(R.id.imagenUsuarioConfig);
        btnCambiarFoto = view.findViewById(R.id.btnCambiarFoto);

        etNombreCompletoConfig = view.findViewById(R.id.etNombreCompletoConfig);
        etTelefonoConfig = view.findViewById(R.id.etTelefonoConfig);
        etDomicilioConfig = view.findViewById(R.id.etDomicilioConfig);
        etContacto1NombreConfig = view.findViewById(R.id.etContacto1NombreConfig);
        etContacto1TelefonoConfig = view.findViewById(R.id.etContacto1TelefonoConfig);
        etContacto1RelacionConfig = view.findViewById(R.id.etContacto1RelacionConfig);
        etContacto2NombreConfig = view.findViewById(R.id.etContacto2NombreConfig);
        etContacto2TelefonoConfig = view.findViewById(R.id.etContacto2TelefonoConfig);
        etContacto2RelacionConfig = view.findViewById(R.id.etContacto2RelacionConfig);
        btnGuardarCambios = view.findViewById(R.id.btnGuardarCambios);

        dbHelper = new DBHelper(requireContext());

        cargarDatosUsuario();

        btnCambiarFoto.setOnClickListener(v -> abrirGaleria());
        btnGuardarCambios.setOnClickListener(v -> guardarCambios());

        return view;
    }

    private void cargarDatosUsuario() {
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery("SELECT * FROM usuario LIMIT 1", null);
        if (cursor.moveToFirst()) {
            etNombreCompletoConfig.setText(cursor.getString(cursor.getColumnIndexOrThrow("nombre")));
            etTelefonoConfig.setText(cursor.getString(cursor.getColumnIndexOrThrow("telefono")));
            etDomicilioConfig.setText(cursor.getString(cursor.getColumnIndexOrThrow("domicilio")));
            etContacto1NombreConfig.setText(cursor.getString(cursor.getColumnIndexOrThrow("contacto1Nombre")));
            etContacto1TelefonoConfig.setText(cursor.getString(cursor.getColumnIndexOrThrow("contacto1Telefono")));
            etContacto1RelacionConfig.setText(cursor.getString(cursor.getColumnIndexOrThrow("contacto1Relacion")));
            etContacto2NombreConfig.setText(cursor.getString(cursor.getColumnIndexOrThrow("contacto2Nombre")));
            etContacto2TelefonoConfig.setText(cursor.getString(cursor.getColumnIndexOrThrow("contacto2Telefono")));
            etContacto2RelacionConfig.setText(cursor.getString(cursor.getColumnIndexOrThrow("contacto2Relacion")));

            // Cargar imagen guardada
            byte[] fotoBytes = cursor.getBlob(cursor.getColumnIndexOrThrow("foto"));
            if (fotoBytes != null) {
                imagenBitmap = BitmapFactory.decodeByteArray(fotoBytes, 0, fotoBytes.length);
                imagenUsuarioConfig.setImageBitmap(imagenBitmap);
            }
        }
        cursor.close();
    }

    private void abrirGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, CODIGO_SELECCION_IMAGEN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CODIGO_SELECCION_IMAGEN && resultCode == Activity.RESULT_OK && data != null) {
            Uri imagenUri = data.getData();
            try {
                imagenBitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), imagenUri);
                imagenUsuarioConfig.setImageBitmap(imagenBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void guardarCambios() {
        ContentValues values = new ContentValues();
        values.put("nombre", etNombreCompletoConfig.getText().toString());
        values.put("telefono", etTelefonoConfig.getText().toString());
        values.put("domicilio", etDomicilioConfig.getText().toString());
        values.put("contacto1Nombre", etContacto1NombreConfig.getText().toString());
        values.put("contacto1Telefono", etContacto1TelefonoConfig.getText().toString());
        values.put("contacto1Relacion", etContacto1RelacionConfig.getText().toString());
        values.put("contacto2Nombre", etContacto2NombreConfig.getText().toString());
        values.put("contacto2Telefono", etContacto2TelefonoConfig.getText().toString());
        values.put("contacto2Relacion", etContacto2RelacionConfig.getText().toString());

        if (imagenBitmap != null) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            imagenBitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            byte[] fotoBytes = outputStream.toByteArray();
            values.put("foto", fotoBytes);
        }

        int filasActualizadas = dbHelper.getWritableDatabase().update("usuario", values, null, null);

        if (filasActualizadas > 0) {
            Toast.makeText(getContext(), "Datos actualizados exitosamente", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Error al actualizar datos", Toast.LENGTH_SHORT).show();
        }
    }
}
