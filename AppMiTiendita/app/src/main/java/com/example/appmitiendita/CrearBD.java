package com.example.appmitiendita;
import static android.provider.BaseColumns._ID;
import static com.example.appmitiendita.Estructura.EstructuraDeDatos.*;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;
public class CrearBD extends SQLiteOpenHelper {
    String crearTabla;
    public CrearBD(@Nullable Context context) {
        super(context, "Tienda.sqlite", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        crearTabla = "CREATE TABLE " + NOMBRE_TABLA + " (" +
                _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMNA_PRODUCTO + " TEXT," +
                COLUMNA_CANTIDAD + " TEXT," +
                COLUMNA_CATEGORIA + " TEXT )";
        db.execSQL(crearTabla);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String borrarTabla = "DROP TABLE IF EXISTS " + NOMBRE_TABLA;
        db.execSQL(borrarTabla);
        onCreate(db);
    }
}