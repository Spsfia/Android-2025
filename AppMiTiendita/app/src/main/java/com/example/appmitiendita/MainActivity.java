package com.example.appmitiendita;
import static android.provider.BaseColumns._ID;
import static com.example.appmitiendita.Estructura.EstructuraDeDatos.*;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
public class MainActivity extends AppCompatActivity {
    private EditText txtID;
    private EditText txtDesc;
    private EditText txtCantidad;
    private Spinner spCategoria;
    // declaramos la clase encargada de crear y actualizar la BD
    CrearBD BD;
    // Creamos un arreglo de Strings con las categorias de la tienda
    String []categorias = {"Frutas y Verduras", "Hogar", "Juguetes", "Blancos"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // vinculamos lo controles definidos (en java) con sus
        // recursos definidos en el layout
        txtID = (EditText) findViewById(R.id.clave);
        txtDesc = (EditText) findViewById(R.id.desc);
        txtCantidad = (EditText) findViewById(R.id.cantidad);
        spCategoria = (Spinner) findViewById(R.id.categoria);
        spCategoria.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item,categorias));
    }
    // método para guardar un producto en la tabla
    public void guardarProducto(View v){
        // se inicializa la BD
        BD = new CrearBD(this);
        // SQLiteDatabase: clase que permite llamar a los métodos para crear,
        // eliminar, leer y actualizar registros.
        // En este caso, se establecen permisos de escritura:
        SQLiteDatabase sqlite = BD.getWritableDatabase();
        // Leer los campos de la UI
        String producto = txtDesc.getText().toString();
        String cantidad = txtCantidad.getText().toString();
        String categoria = spCategoria.getSelectedItem().toString();
        ContentValues contenedor = new ContentValues();
        if(producto.equals("")||cantidad.equals("")){
            Toast.makeText(this,
                    "Todos los campos son obligatorios",Toast.LENGTH_LONG).show();
        } else {
 /*
 Se añaden los valores introducidos, con el par clave, valor
 */
            contenedor.put(COLUMNA_PRODUCTO, producto);
            contenedor.put(COLUMNA_CANTIDAD, cantidad);
            contenedor.put(COLUMNA_CATEGORIA, categoria);
            sqlite.insert(NOMBRE_TABLA,null,contenedor);
        }
    }

    public void buscarProducto(View v){
        BD = new CrearBD(this);
        SQLiteDatabase sqlite = BD.getReadableDatabase();
        // query
        //lista de las columnas
        String[]columnas ={_ID, COLUMNA_PRODUCTO,
                COLUMNA_CANTIDAD, COLUMNA_CATEGORIA};
        // la consulta:
        String queryProducto = COLUMNA_PRODUCTO + " LIKE '"+
                txtDesc.getText().toString() + "'";
        // orden
        String orden = COLUMNA_PRODUCTO + " DESC";
        // realizar consulta
        Cursor result = sqlite.query(NOMBRE_TABLA, columnas,
                queryProducto, null,null,null, orden);
        if(result.getCount()!=0){
            result.moveToFirst();
            long idProducto = result.getLong(result.getColumnIndexOrThrow(_ID));
            txtID.setText(String.valueOf(idProducto));
            String producto = result.getString(result.getColumnIndexOrThrow(COLUMNA_PRODUCTO));
            txtDesc.setText(producto);
            String cantidad = result.getString(result.getColumnIndexOrThrow(COLUMNA_CANTIDAD));
            txtCantidad.setText(cantidad);
            String seccion = result.getString(result.getColumnIndexOrThrow(COLUMNA_CATEGORIA));
            for(int i=0;i<categorias.length; i++){
                if((seccion.equals(categorias[i])))
                    spCategoria.setSelection(i);
            }
        } else Toast.makeText(this, "Producto no encontrado",
                Toast.LENGTH_SHORT).show();
    }
    //Evento on click que modifica un elemento de la tabla
    public void modificarProducto(View v){
        // inicializamos el objeto para la base de datos
        BD = new CrearBD(this);
        // establecemos permiso de escritura
        SQLiteDatabase sqlite= BD.getWritableDatabase();
        Long idModificar = Long.valueOf(txtID.getText().toString());
        String producto = txtDesc.getText().toString();
        String cantidad = txtCantidad.getText().toString();
        String categoria = spCategoria.getSelectedItem().toString();

        ContentValues content = new ContentValues();
 /* Añadir los valores introducidos de cada campo mediante el par
 nombre de columna, valor
 */
        content.put(COLUMNA_PRODUCTO,producto);
        content.put(COLUMNA_CANTIDAD,cantidad);
        content.put(COLUMNA_CATEGORIA,categoria);
        String seleccion = _ID + " LIKE " + idModificar;
        // llamar al método update() pasandole los parámetros para modificar el producto
        int count = sqlite.update(NOMBRE_TABLA, content,seleccion,null);
        Toast.makeText(this,"Se acualizó el producto" + producto,
                Toast.LENGTH_LONG).show();
        // después de modificar, limpiamos los campos de captura
        txtID.setText("");
        txtDesc.setText("");
        txtCantidad.setText("");
        spCategoria.setSelection(0);
        // cerramos la conexión a la base de datos
        sqlite.close();
    }
    public void eliminarProducto(View v){
        String producto = txtDesc.getText().toString();
        // iniciamos la conexión con la bd
        BD = new CrearBD(this);
        SQLiteDatabase sqlite = BD.getWritableDatabase();
        // creamos el string para buscar el producto
        String query = COLUMNA_PRODUCTO + " LIKE '" + producto+ "'";
        // se realiza la búsqueda y eliminación del producto
        sqlite.delete(NOMBRE_TABLA,query,null);
        // limpiar los campos
        txtID.setText("");
        txtDesc.setText("");
        txtCantidad.setText("");
        spCategoria.setSelection(0);
        Toast.makeText(this,"Se eliminó el producto" + producto,
                Toast.LENGTH_LONG).show();
        // cerramos la conexión a la base de datos
        sqlite.close();
    }
}