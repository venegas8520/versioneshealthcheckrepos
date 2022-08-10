package com.example.health;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;


import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    TextView username,edad,altura,peso,pasosDados,kmRecorrido,kcalQuemadas,cKmRecorrido,cKcalQuemadas,pasosGrafica,ritmoCardiaco,ritmoNivel,estres,oxigeno;

    Bundle bundle;
    String uid;

    int i = 0;

    DatabaseReference mDatabase;
    ArrayList<String> ar = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        username = (TextView)findViewById(R.id.username);
        edad = (TextView)findViewById(R.id.edad);
        altura = (TextView)findViewById(R.id.altura);
        peso = (TextView)findViewById(R.id.peso);
        pasosDados = (TextView)findViewById(R.id.pasos_dados);
        kmRecorrido = (TextView)findViewById(R.id.distancia_pasos);
        kcalQuemadas = (TextView)findViewById(R.id.kcal);
        pasosGrafica = (TextView)findViewById(R.id.pasos_grafica);
        ritmoCardiaco = (TextView)findViewById(R.id.ritmo_cardiaco);
        ritmoNivel = (TextView)findViewById(R.id.ritmo_nivel);
        estres = (TextView)findViewById(R.id.estres);
        oxigeno = (TextView)findViewById(R.id.oxigeno);
        cKmRecorrido = (TextView)findViewById(R.id.km_recorrido);
        cKcalQuemadas = (TextView)findViewById(R.id.kcal_quemadas);

        Intent intent = getIntent();
        bundle = intent.getExtras();

        if(bundle != null){
            uid = bundle.getString("uid");
            Intent inten = new Intent(this, SmartwatchService.class);
            inten.putExtra("uid",uid);
            startService(inten);
            try {
                mDatabase = FirebaseDatabase.getInstance().getReference();
                mDatabase.child("users").child(uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(HomeActivity.this, "Error al obtener los datos", Toast.LENGTH_SHORT).show();
                        } else {
                            i = 0;
                            for (DataSnapshot ds : task.getResult().getChildren()) {
                                String dato = ds.getValue(String.class);
                                ar.add(dato);
                                i++;
                            }
                            username.setText(ar.get(1)+" "+ar.get(2));
                            if(i>=6){
                                int year = Integer.parseInt(ar.get(9));
                                int nac = 2022-year;
                                edad.setText(Integer.toString(nac));
                                altura.setText(ar.get(5) + " cm");
                                peso.setText(ar.get(6) + " kg");
                                Toast.makeText(HomeActivity.this, "Registro completo", Toast.LENGTH_SHORT).show();

                                pasosDados.setText(ar.get(10));
                                kmRecorrido.setText(ar.get(11));
                                kcalQuemadas.setText(ar.get(12));
                                cKmRecorrido.setText(ar.get(11));
                                cKcalQuemadas.setText(ar.get(12));
                                pasosGrafica.setText(ar.get(13));
                                ritmoCardiaco.setText(ar.get(14)+" LPM");
                                ritmoNivel.setText(ar.get(15));
                                estres.setText(ar.get(16)+"%");
                                oxigeno.setText(ar.get(17)+"% / 100%");
                                ejecutar();
                            }else{
                                sms();
                            }
                        }
                    }
                });
            } catch (Throwable t) {
                Toast.makeText(this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        }
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
                    Toast.makeText(HomeActivity.this, "Error al obtener los datos", Toast.LENGTH_SHORT).show();
                } else {
                    i = 0;
                    for (DataSnapshot ds : task.getResult().getChildren()) {
                        String dato = ds.getValue(String.class);
                        ar.add(dato);
                        i++;
                    }
                    username.setText(ar.get(1)+" "+ar.get(2));
                    if(i>=6){
                        int year = Integer.parseInt(ar.get(9));
                        int nac = 2022-year;
                        edad.setText(Integer.toString(nac));
                        altura.setText(ar.get(5) + " cm");
                        peso.setText(ar.get(6) + " kg");
                        Toast.makeText(HomeActivity.this, "OK", Toast.LENGTH_SHORT).show();
                        pasosDados.setText(ar.get(10));
                        kmRecorrido.setText(ar.get(11));
                        kcalQuemadas.setText(ar.get(12));
                        cKmRecorrido.setText(ar.get(11));
                        cKcalQuemadas.setText(ar.get(12));
                        pasosGrafica.setText(ar.get(13));
                        ritmoCardiaco.setText(ar.get(14) + " LPM");
                        ritmoNivel.setText(ar.get(15));
                        estres.setText(ar.get(16) + "%");
                        oxigeno.setText(ar.get(17) + "% / 100%");
                    }else{
                        sms();
                    }
                }
            };
        });

        }


    public void sms(){
        HomeActivity createPackageContext;
        Intent i = new Intent(createPackageContext = HomeActivity.this, SmsActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("uid",uid);
        startActivity(i);
    }
}