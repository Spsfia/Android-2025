package com.example.proyectovioleta;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.proyectovioleta.databinding.ActivityMain1Binding;
import com.google.android.material.navigation.NavigationView;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Main1Activity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMain1Binding binding;

    private static final int CODIGO_PERMISOS = 100;
    private static final int CODIGO_CAPTURA_IMAGEN = 200;
    private String rutaFotoActual;

    private String[] permisos = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.CAMERA,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.SEND_SMS
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMain1Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain1.toolbar);

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,
                R.id.nav_historial,
                R.id.nav_configuracion,
                R.id.nav_registro)
                .setOpenableLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main1);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // 🚨 Pedir permisos
        ActivityCompat.requestPermissions(this, permisos, CODIGO_PERMISOS);

        // 📸 FAB para tomar foto
        binding.appBarMain1.fab.setOnClickListener(view -> abrirCamara());

        // 🚨 Si NO existe usuario, ir al registro
        DBHelper dbHelper = new DBHelper(this);
        if (!dbHelper.existeUsuario()) {
            navController.navigate(R.id.nav_registro);
        }
    }

    private void abrirCamara() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

            if (intent.resolveActivity(getPackageManager()) != null) {
                File fotoArchivo = null;
                try {
                    fotoArchivo = crearArchivoImagen();
                } catch (IOException ex) {
                    Toast.makeText(this, "Error al crear archivo de imagen", Toast.LENGTH_SHORT).show();
                }

                if (fotoArchivo != null) {
                    Uri fotoURI = FileProvider.getUriForFile(this,
                            "com.example.ProyectoVioleta.fileprovider", // Asegúrate que coincida con tu package
                            fotoArchivo);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, fotoURI);
                    startActivityForResult(intent, CODIGO_CAPTURA_IMAGEN);
                }
            }
        } else {
            Toast.makeText(this, "Permiso de cámara no concedido", Toast.LENGTH_SHORT).show();
        }
    }

    private File crearArchivoImagen() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String nombreArchivo = "FOTO_" + timeStamp + "_";
        File storageDir = getFilesDir(); // Guarda en archivos internos
        File imagen = File.createTempFile(
                nombreArchivo,
                ".jpg",
                storageDir
        );

        rutaFotoActual = imagen.getAbsolutePath();
        return imagen;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CODIGO_CAPTURA_IMAGEN && resultCode == RESULT_OK) {
            if (rutaFotoActual != null) {
                File imgFile = new File(rutaFotoActual);
                if (imgFile.exists()) {
                    // Guardar evento de foto capturada
                    DBHelper dbHelper = new DBHelper(this);
                    dbHelper.insertarEvento("Foto capturada", rutaFotoActual);

                    Toast.makeText(this, "Foto guardada exitosamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Error: No se encontró la imagen", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_terminos) {
            mostrarTerminos();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void mostrarTerminos() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Términos y Condiciones");

        builder.setMessage(
                "Proyecto Violeta - Plataforma de Alerta de Violencia\n\n" +

                        "1. Aceptación de los Términos\n" +
                        "Al utilizar la aplicación, el usuario acepta en su totalidad estos Términos y Condiciones.\n\n" +

                        "2. Descripción del Servicio\n" +
                        "La aplicación ayuda a prevenir y atender situaciones de violencia, enviando alertas a autoridades y contactos.\n\n" +

                        "3. Registro de Usuario\n" +
                        "Se solicitará nombre, teléfono, domicilio en Torreón, fotografía y dos contactos de emergencia.\n\n" +

                        "4. Uso de la Aplicación\n" +
                        "El usuario debe utilizar la app de manera responsable y solo en casos reales de emergencia.\n\n" +

                        "5. Acceso a Datos\n" +
                        "Se requiere acceso a ubicación, cámara y almacenamiento para funcionar correctamente.\n\n" +

                        "6. Privacidad\n" +
                        "La información será utilizada únicamente para emergencias y estadísticas, protegiendo la identidad del usuario.\n\n" +

                        "7. Base de Datos\n" +
                        "Los datos podrán ser usados de manera anónima para fines estadísticos.\n\n" +

                        "8. Responsabilidades\n" +
                        "La app no se responsabiliza por fallas técnicas o de conexión fuera de su control.\n\n" +

                        "9. Modificaciones\n" +
                        "Los términos pueden cambiar, y los cambios serán notificados en la app.\n\n" +

                        "10. Contacto\n" +
                        "Para dudas o comentarios, comunicarse a las autoridades correspondientes."
        );

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();
    }



    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main1);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}
