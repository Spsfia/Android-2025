package com.example.proyectovioleta;

public class Evento {
    private String tipo;
    private String fecha;
    private String fotoRuta;

    public Evento(String tipo, String fecha, String fotoRuta) {
        this.tipo = tipo;
        this.fecha = fecha;
        this.fotoRuta = fotoRuta;
    }

    public String getTipo() {
        return tipo;
    }

    public String getFecha() {
        return fecha;
    }

    public String getFotoRuta() {
        return fotoRuta;
    }
}
