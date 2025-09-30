package com.example.appimageassets;

//Clase para crear y manejar la base de datos

import static android.provider.BaseColumns._ID;
import static com.example.appimageassets.Estructura.EstructuraTabla.*;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "Contactos.sqlite";
    public static final int DATABASE_VERSION = 1;

    public Database(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
    String query = "CREATE TABLE " + TABLE_NAME + " (" + _ID +
            " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME + " TEXT, " +
            COLUMN_PHONE + " TEXT)";

    db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String query = "DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(query);
        onCreate(db);

    }

    //Metodo para agregar un contacto
    public long agregarContacto (String nombre, String telefono){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contenedor = new ContentValues();
        contenedor.put(COLUMN_NAME,nombre);
        contenedor.put(COLUMN_PHONE,telefono);

        long id = db.insert(TABLE_NAME, null, contenedor);
        db.close();
        return id;
    }

    //Metodo para obtener todos los contactos

    public Cursor obtenerContactos(){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    //Metodo para actualizar un contacto
    public int actualizarContacto (long id, String nombre, String telefono){
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contenedor =  new ContentValues();
        contenedor.put(COLUMN_NAME,nombre);
        contenedor.put(COLUMN_PHONE,telefono);
        int result =  db.update(TABLE_NAME, contenedor, _ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return result;
    }


    //Metodo para eliminar un contacto
    public int eliminarContacto(long id){
        SQLiteDatabase db = this.getWritableDatabase();
        int result =  db.delete(TABLE_NAME, _ID + "=?", new String[]{String.valueOf(id)});
        db.close();
        return result;
    }










}
