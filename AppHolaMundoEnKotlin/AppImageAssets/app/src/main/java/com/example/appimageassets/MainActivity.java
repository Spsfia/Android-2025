package com.example.appimageassets;

import androidx.appcompat.app.AppCompatActivity;

import static android.provider.BaseColumns._ID;
import static com.example.appimageassets.Estructura.EstructuraTabla.*;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

import com.example.appimageassets.*;

import com.example.appimageassets.R;

public class MainActivity extends AppCompatActivity {

    private EditText txtNombre, txtTelefono, txtID;
    private ImageButton btnCreate, btnUpdate, btnDelete, btnRead;
    private ListView lvContactos;
    private Database db;
    private SimpleCursorAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Vincular las variables con la interfaz gráfica
        txtNombre = (EditText) findViewById(R.id.txtNombre);
        txtTelefono = (EditText) findViewById(R.id.txtTelefono);
        txtID = (EditText) findViewById(R.id.txtID);
        btnCreate = (ImageButton) findViewById(R.id.btnCreate);
        btnDelete = (ImageButton) findViewById(R.id.btnDelete);
        btnUpdate = (ImageButton) findViewById(R.id.btnUpdate);
        btnRead = (ImageButton) findViewById(R.id.btnRead);
        lvContactos = (ListView) findViewById(R.id.lvContactos);


        //crear la nueva db y la tabla
        db = new Database(this);

        //actualizar el ListView
        actualizarLV();

        //Manejar selecci[on en la ListaView
        lvContactos.setOnItemClickListener((parent, view, position, id) -> {
            Cursor cursor = (Cursor) parent.getItemAtPosition(position);
            txtID.setText(cursor.getString(
                    cursor.getColumnIndexOrThrow(_ID)));
            txtNombre.setText(cursor.getString(
                    cursor.getColumnIndexOrThrow(COLUMN_NAME)));
            txtTelefono.setText(cursor.getString(
                    cursor.getColumnIndexOrThrow(COLUMN_PHONE)));
        });

        //Boton crear
        btnCreate.setOnClickListener(view -> {
                String nombre = txtNombre.getText().toString();
                String telefono = txtTelefono.getText().toString();
            if(!nombre.isEmpty()&&!telefono.isEmpty()){
                long id = db.agregarContacto(nombre,telefono);
                if(id>0){
                    Toast.makeText(this, "Contacto guardado", Toast.LENGTH_SHORT).show();
                    actualizarLV();
                    limpiarCampos();
                }else{
                    Toast.makeText(this, "No se guardo el contacto", Toast.LENGTH_SHORT).show();
                }
            } else{
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            }

        });

        //Boton update
        btnUpdate.setOnClickListener(view -> {
            long id = Long.parseLong(txtID.getText().toString());
            String nombre = txtNombre.getText().toString();
            String telefono = txtTelefono.getText().toString();
            if(!nombre.isEmpty()&&!telefono.isEmpty()){
                if(id>0){
                    int resul = db.actualizarContacto(id,nombre,telefono);
                    if(resul>0){
                        Toast.makeText(this, "Contacto actualizado", Toast.LENGTH_SHORT).show();
                        actualizarLV();
                        limpiarCampos();
                    }else{
                        Toast.makeText(this, "Contacto NO actualizado", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(this, "Selecciona un contacto", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            }

        });

        //Boton eliminar
        btnDelete.setOnClickListener(view -> {
            long id = Long.parseLong(txtID.getText().toString());
            if(id>0){
                int result = db.eliminarContacto(id);
                if(result > 0){
                    Toast.makeText(this, "Contacto eliminado", Toast.LENGTH_SHORT).show();
                    actualizarLV();
                    limpiarCampos();
                }else{
                    Toast.makeText(this, "Error al eliminar", Toast.LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this, "Selecciona un contacto", Toast.LENGTH_SHORT).show();
                
            }
        });


    }

    //Obtener ID
//    public int obtenerIDSeleccionado(){
//        Cursor cursor = (Cursor) lvContactos.getItemAtPosition(
//                lvContactos.getCheckedItemPosition());
//                return cursor.getInt(cursor.getColumnIndexOrThrow(
//                        Database.COLUMN_ID));
//
//    }

    public void onClickBtnRead(View v){
        actualizarLV();
    }

    //Metodo para actualizar el listView
    public void actualizarLV(){
        Cursor cursor = db.obtenerContactos();
        String[]from = {COLUMN_NAME, COLUMN_PHONE};
        int[] to = {android.R.id.text1,android.R.id.text2};
        adapter = new SimpleCursorAdapter(this, android.R.layout.simple_list_item_2,cursor,from,to,0);
        lvContactos.setAdapter(adapter);


    }

    public void limpiarCampos(){
        txtID.setText("");
        txtTelefono.setText("");
        txtNombre.setText("");
    }
}