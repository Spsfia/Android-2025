package com.example.appspaceinvaders;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.View;

public class MyView extends View {

    // pantalla
    private int w; //ancho
    private int h; //alto
    //nave defensora
    private float xNave, yNave, anchoNave, altoNave;
    //nave invasora
    private float xNI, yNI, anchoNI, altoNI;
    private float separacion;
    private int numNaves;
    boolean [] dibuja; //arreglo de banderas par dibujar la nave invasora
    // proyectil (bala)
    private float xBala, yBala;
    private boolean dispara;
    // para el acelerometro:
    private float newX; // nueva posición de la nave defensora
    private int sensibilidad; // para el desplazamiento por acelerometro

    //Archivos para graficos
    private Bitmap bmpBackground;
    private Bitmap bmpNave;
    private Bitmap bmpNI;
    private Bitmap bmpBala;
    // Archivos para sonidos
    private MediaPlayer shot;
    private MediaPlayer boom;

    public MyView(Context context) {
        super(context);
        sensibilidad = 200;
        dispara      = false;
        numNaves     = 5;
        separacion   = 30;

        // obtener imagenes
        bmpBackground = BitmapFactory.decodeResource(getResources(),R.drawable.universe2);
        bmpNave = BitmapFactory.decodeResource(getResources(),R.drawable.spaceship2);
        bmpNI = BitmapFactory.decodeResource(getResources(),R.drawable.badship2);
        bmpBala = BitmapFactory.decodeResource(getResources(),R.drawable.shot);
        // recursos de sonidos
        shot = MediaPlayer.create(context,R.raw.disparo);
        boom = MediaPlayer.create(context,R.raw.explosion);
        // nave defensora (dimensiones)
        anchoNave = bmpNave.getWidth();
        altoNave = bmpNave.getHeight();
        // nave invasota
        anchoNI = bmpNI.getWidth();
        altoNI = bmpNI.getHeight();

        //valores iniciales para las posiciones de:
        // Nave invasora
        xNI = 0;
        yNI = 50;
        // proyectil
        xBala = 0;
        yBala = 0;
        // arreglo de banderas para dibujar naves invasoras
        dibuja = new boolean[30];
        for(int i = 0; i < dibuja.length;i++)
            dibuja[i] = true;
    }  // termina constructor

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //obtener alto y ancho del lienzo (pantalla del celular)
        w = getWidth();
        h = getHeight();

        // posición de la nave defensora
        xNave = w/2 - anchoNave/2 -sensibilidad*newX;
        yNave = h - altoNave;

        // se establece el fondo
        canvas.drawBitmap(bmpBackground,0.0f,0.0f,null);

        // pintar la nave defensora
        canvas.drawBitmap(bmpNave,xNave,yNave,null);

        //pintar las naves invasoras
        numNaves = (int)(w/anchoNI);
        xNI = 0;
        for(int i = 0; i < numNaves; i++){
            if(dibuja[i])
                canvas.drawBitmap(bmpNI,xNI,yNI,null);
            xNI = xNI + separacion + anchoNI;
        }
        // Bala
        // Calcular la posición inicial de la bala/proyectil
        if(!dispara) {
            xBala = xNave + anchoNave / 2;
            yBala = yNave;
        }

        // si ya se disparó, realizae la animación del proyectil
        if(dispara){
            yBala -=10; // yBala = yBala - 10
            canvas.drawBitmap(bmpBala,xBala,yBala,null);
        }

        //revisar si hay colisión en el eje y de las naves invasoras
        // faltaría revisar si hay colisión con una nave invasora....eso es tarea
        if(yBala < yNI + altoNI){
            boom.start();
            dispara=false;
        }

        //repintar el escenario
        invalidate();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        dispara = true;
        shot.start();
        return true;
    }

    public void actualizaX(float x){
        newX = x;
    }
}
