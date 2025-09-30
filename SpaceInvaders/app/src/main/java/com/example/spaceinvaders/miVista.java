package com.example.spaceinvaders;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.NinePatchDrawable;
import android.media.MediaPlayer;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;

public class miVista extends View {

    //Variables

    //Pantalla
    private int w;
    private int h;

    //Nave defensora
    private float anchoNave;
    private float altoNave;
    private float xNave;
    private float yNave;

    //Nave Invasora
    private float anchoNI;
    private float altoNI;
    private float xNI;
    private float yNI;

    //Separacion entre naves
    private float separacion;
    private boolean [] dibuja;

    //Proyectil
    private  float xBala;
    private  float yBala;
    private boolean dispara;

    //Acelerometro
    private float newX;
    private int sensibilidad;

    //Recursos: imagenes
    private Bitmap bmpBackground;
    private Bitmap bmpNave;
    private Bitmap bmpNI;
    private Bitmap bmpBala;

    //Recursos: sonidos
    private MediaPlayer disparo;
    private MediaPlayer explosion;

    public miVista(Context context) {
        super(context);
        sensibilidad = 200;
        dispara = false;
        separacion = 30;

        //Obtener recursos
        //Imagenes
        bmpBackground = BitmapFactory.decodeResource(getResources(), R.drawable.universe2);
        bmpNave = BitmapFactory.decodeResource(getResources(), R.drawable.spaceship2);
        bmpNI = BitmapFactory.decodeResource(getResources(), R.drawable.badship2);
        bmpBala = BitmapFactory.decodeResource(getResources(), R.drawable.shot);

        //Sonido
        disparo = MediaPlayer.create(context,R.raw.disparo);
        explosion = MediaPlayer.create(context,R.raw.explosion);

        //Dimensiones de naves
        //Defensora
        anchoNave = bmpNave.getWidth();
        altoNave = bmpNave.getHeight();

        //Invasora
        anchoNI = bmpNI.getWidth();
        altoNI = bmpNI.getHeight();

        //Valores iniciales posicionales
        //Invasora
        xNI = 0;
        yNI = 50;

        //Bala
        xBala = 0;
        yBala = 0;

        //Arreglo de banderas para dibujar las naves invasoras
        dibuja = new boolean[30];
        for (int i = 0; i < dibuja.length; i++){
            dibuja[i] = true;

        }

    };

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        //Obtencion de las dimensiones del dispositivo
        w = getWidth();
        h = getHeight();

        //NINEPATCH para el fondo


        //Localizacion de la nave defensora
        xNave = w / 2 - anchoNave / 2 - sensibilidad * newX;
        yNI = h - altoNave;

        //Establecer el fondo
        canvas.drawBitmap(bmpBackground, 0.0f, 0.0f, null);

        //Establecer la nave
        canvas.drawBitmap(bmpNave, xNave, yNave, null);

        //Establecer la NI
        xNI = 0;
        int numNI = (int) (w / anchoNI);
        for (int i = 0; i < numNI; i++){
            if (dibuja[i])
                canvas.drawBitmap(bmpNI, xNI, yNI, null);
            xNI = xNI + anchoNI + separacion;
        }
        //Revisar si se hizo un disparo
        if (dispara){
            yBala -= 10;
            canvas.drawBitmap(bmpBala, xBala, yBala, null);
        }
        //revisar colision de la bala con una nave invasora

        //agregar la validacion de colision en x

        if(yBala < yNI + altoNI){
            explosion.start();
            dispara = false;
        }
        //repintar escenario
        invalidate();

    }

    //
    public void actualizaX(float x){
        newX = x;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        dispara = true;
        disparo.start();
        return true;
    }
}
