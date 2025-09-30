package com.example.appmitiendita;
import android.provider.BaseColumns;
public class Estructura {

    public Estructura(){
    }
    public static abstract class EstructuraDeDatos
            implements BaseColumns{
        public static final String NOMBRE_TABLA = "MiTienda";
        public static final String COLUMNA_PRODUCTO = "producto";
        public static final String COLUMNA_CANTIDAD = "cantidad";
        public static final String COLUMNA_CATEGORIA = "categoria";
    }
}
