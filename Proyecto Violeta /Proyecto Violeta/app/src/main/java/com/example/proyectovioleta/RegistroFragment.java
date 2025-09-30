package com.example.proyectovioleta;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
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
import androidx.navigation.Navigation;

import com.example.proyectovioleta.DBHelper;
import com.example.proyectovioleta.R;

import java.io.ByteArrayOutputStream;

public class RegistroFragment extends Fragment {

    private static final int CODIGO_SELECCION_IMAGEN = 101;
    private ImageView imgFotoPerfil;
    private byte[] fotoBytes;

    private EditText edtNombre, edtTelefono, edtDomicilio,
            edtContacto1Nombre, edtContacto1Telefono, edtContacto1Relacion,
            edtContacto2Nombre, edtContacto2Telefono, edtContacto2Relacion;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registro, container, false);

        imgFotoPerfil = view.findViewById(R.id.imgFotoPerfil);
        edtNombre = view.findViewById(R.id.edtNombre);
        edtTelefono = view.findViewById(R.id.edtTelefono);
        edtDomicilio = view.findViewById(R.id.edtDomicilio);
        edtContacto1Nombre = view.findViewById(R.id.edtContacto1Nombre);
        edtContacto1Telefono = view.findViewById(R.id.edtContacto1Telefono);
        edtContacto1Relacion = view.findViewById(R.id.edtContacto1Relacion);
        edtContacto2Nombre = view.findViewById(R.id.edtContacto2Nombre);
        edtContacto2Telefono = view.findViewById(R.id.edtContacto2Telefono);
        edtContacto2Relacion = view.findViewById(R.id.edtContacto2Relacion);
        Button btnGuardar = view.findViewById(R.id.btnGuardar);

        imgFotoPerfil.setOnClickListener(v -> abrirGaleria());

        btnGuardar.setOnClickListener(v -> guardarRegistro());

        return view;
    }

    private void abrirGaleria() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, CODIGO_SELECCION_IMAGEN);
    }

    private void guardarRegistro() {
        String nombre = edtNombre.getText().toString().trim();
        String telefono = edtTelefono.getText().toString().trim();
        String domicilio = edtDomicilio.getText().toString().trim();
        String contacto1Nombre = edtContacto1Nombre.getText().toString().trim();
        String contacto1Telefono = edtContacto1Telefono.getText().toString().trim();
        String contacto1Relacion = edtContacto1Relacion.getText().toString().trim();
        String contacto2Nombre = edtContacto2Nombre.getText().toString().trim();
        String contacto2Telefono = edtContacto2Telefono.getText().toString().trim();
        String contacto2Relacion = edtContacto2Relacion.getText().toString().trim();

        if (nombre.isEmpty() || telefono.isEmpty() || domicilio.isEmpty()
                || contacto1Nombre.isEmpty() || contacto1Telefono.isEmpty()
                || contacto2Nombre.isEmpty() || contacto2Telefono.isEmpty()) {
            Toast.makeText(getContext(), "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        if (fotoBytes == null) {
            Toast.makeText(getContext(), "Debes seleccionar una foto", Toast.LENGTH_SHORT).show();
            return;
        }

        DBHelper dbHelper = new DBHelper(requireContext());
        boolean registrado = dbHelper.insertarUsuario(nombre, telefono, domicilio, fotoBytes,
                contacto1Nombre, contacto1Telefono, contacto1Relacion,
                contacto2Nombre, contacto2Telefono, contacto2Relacion);

        if (registrado) {
            Toast.makeText(getContext(), "Registro exitoso", Toast.LENGTH_SHORT).show();
            Navigation.findNavController(requireView()).navigate(R.id.nav_home);
        } else {
            Toast.makeText(getContext(), "Error al registrar", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CODIGO_SELECCION_IMAGEN && resultCode == Activity.RESULT_OK && data != null) {
            try {
                Uri imageUri = data.getData();
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(requireActivity().getContentResolver(), imageUri);
                imgFotoPerfil.setImageBitmap(bitmap);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                fotoBytes = stream.toByteArray();
            } catch (Exception e) {
                Toast.makeText(getContext(), "Error al seleccionar imagen", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
