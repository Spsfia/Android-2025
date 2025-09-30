package com.example.appspaceinvaders;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements
        SensorEventListener {

    private MyView vista;
    private SensorManager sensorMgr;
    private Sensor acelerometro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vista = new MyView(this);
        setContentView(vista);

        //instanciar los objetos para sensor manager
        sensorMgr = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        acelerometro = sensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorMgr.registerListener(this,acelerometro,
                SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor miSensor = sensorEvent.sensor;
        if(miSensor.getType()==Sensor.TYPE_ACCELEROMETER){
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];
            // actualizar el nuevo valor de x (newX en MyView)
            vista.actualizaX(x);
        }
    }
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }
}