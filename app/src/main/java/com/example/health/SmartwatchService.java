package com.example.health;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class SmartwatchService extends Service {

    private static final String CHANNEL_ID = "Canal";

    DatabaseReference mDatabase;
    ArrayList<String> ar = new ArrayList<String>();

    Bundle bundle;
    String uid;
    int i = 0;

    @Override
    public void onCreate() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //return super.onStartCommand(intent, flags, startId);

        String data = (String) intent.getExtras().get("uid");
        bundle = intent.getExtras();

        if(bundle != null){
            uid = bundle.getString("uid");
            ejecutar();
        }


        return START_STICKY;
    }

    private void ejecutar(){
        final Handler handler= new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                smart();//llamamos nuestro metodo
                handler.postDelayed(this,15000);//se ejecutara cada 15 segundos
            }
        },5000);//empezara a ejecutarse después de 5 milisegundos
    }

    public void smart(){
        ar.clear();

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child(uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(SmartwatchService.this, "Error al obtener los datos", Toast.LENGTH_SHORT).show();
                } else {
                    i = 0;
                    for (DataSnapshot ds : task.getResult().getChildren()) {
                        String dato = ds.getValue(String.class);
                        ar.add(dato);
                        i++;
                    }
                    if(i>=6){
                        int ritmo = Integer.parseInt(ar.get(14));
                        int estres = Integer.parseInt(ar.get(16));
                        int oxigeno = Integer.parseInt(ar.get(17));

                        if(ritmo <= 60){
                            notificacion("Ritmo cardíaco bajo", 1);
                        }
                        if(ritmo >= 100){
                            notificacion("Ritmo cardíaco alto", 2);
                        }

                        if(estres >= 80){
                            notificacion("Nivel de estrés alto", 3);
                        }

                        if(oxigeno <= 75){
                            notificacion("Nivel de oxígeno bajo", 4);
                        }

                    }
                }
            };
        });

    }

    public void notificacion(String token, int id){
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "nofify", importance);

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.logo_healthcheck)
                .setContentTitle("HealtCheck")
                .setContentText(token)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManager.notify(id,builder.build());
    }

    @Override
    public void onDestroy(){

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
