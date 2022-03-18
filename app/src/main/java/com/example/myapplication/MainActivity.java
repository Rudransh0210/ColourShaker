package com.example.myapplication;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.LinearLayout;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    public SensorManager SM;
    public float a;
    public float b;
    public float c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SM = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Objects.requireNonNull(SM).registerListener(mSensorListener, SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),SensorManager.SENSOR_DELAY_NORMAL);
        a = 10f;
        b = SensorManager.GRAVITY_EARTH;
        c = SensorManager.GRAVITY_EARTH;
    }
    public  SensorEventListener mSensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            c = b;
            b = (float) Math.sqrt((double) (x * x + y * y + z * z));
            float delta = b - c;
            a = a * 0.9f + delta;
            if (a > 10) {
                LinearLayout l1 = (LinearLayout) findViewById(R.id.l1);
                LinearLayout l2 = (LinearLayout) findViewById(R.id.l2);
                int l1c = Color.TRANSPARENT;
                Drawable bl1 = l1.getBackground();
                l1c = ((ColorDrawable) bl1).getColor();
                int l2c = Color.TRANSPARENT;
                Drawable bl2 = l2.getBackground();
                l2c = ((ColorDrawable) bl2).getColor();
                l1.setBackgroundColor(l2c);
                l2.setBackgroundColor(l1c);
            }
        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };
    @Override
    protected void onResume() {
        SM.registerListener(mSensorListener, SM.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        super.onResume();
    }
    @Override
    protected void onPause() {
        SM.unregisterListener(mSensorListener);
        super.onPause();
    }
}