package com.example.proyectovioleta;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "alerta_app.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUsuarioTable = "CREATE TABLE usuario (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT," +
                "telefono TEXT," +
                "domicilio TEXT," +
                "foto BLOB," +
                "contacto1Nombre TEXT," +
                "contacto1Telefono TEXT," +
                "contacto1Relacion TEXT," +
                "contacto2Nombre TEXT," +
                "contacto2Telefono TEXT," +
                "contacto2Relacion TEXT)";
        db.execSQL(createUsuarioTable);

        String createEventosTable = "CREATE TABLE eventos (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "tipo TEXT," +
                "fecha TEXT," +
                "fotoRuta TEXT)";
        db.execSQL(createEventosTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS usuario");
        db.execSQL("DROP TABLE IF EXISTS eventos");
        onCreate(db);
    }

    public boolean insertarUsuario(String nombre, String telefono, String domicilio, byte[] fotoBytes,
                                   String contacto1Nombre, String contacto1Telefono, String contacto1Relacion,
                                   String contacto2Nombre, String contacto2Telefono, String contacto2Relacion) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("nombre", nombre);
        values.put("telefono", telefono);
        values.put("domicilio", domicilio);
        values.put("foto", fotoBytes);
        values.put("contacto1Nombre", contacto1Nombre);
        values.put("contacto1Telefono", contacto1Telefono);
        values.put("contacto1Relacion", contacto1Relacion);
        values.put("contacto2Nombre", contacto2Nombre);
        values.put("contacto2Telefono", contacto2Telefono);
        values.put("contacto2Relacion", contacto2Relacion);

        long resultado = db.insert("usuario", null, values);
        db.close();
        return resultado != -1;
    }

    public boolean existeUsuario() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM usuario LIMIT 1", null);
        boolean existe = cursor.moveToFirst();
        cursor.close();
        db.close();
        return existe;
    }

    public void insertarEvento(String tipo, String fotoRuta) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tipo", tipo);
        values.put("fecha", String.valueOf(System.currentTimeMillis()));
        values.put("fotoRuta", fotoRuta);
        db.insert("eventos", null, values);
        db.close();
    }

    public Cursor obtenerEventos() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM eventos ORDER BY id DESC", null);
    }
}
