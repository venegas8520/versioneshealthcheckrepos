package com.example.health;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class SaludActivity extends AppCompatActivity {

    ImageView mas,fem,nobi;
    Bundle bundle;
    String uid,genero;

    String correo,password,nombre,apellidos,id,date,nacimiento;

    EditText altura,peso;

    DatabaseReference mDatabase;

    private DatePickerDialog datePickerDialog;
    private Button dateButton;

    ArrayList<String> ar = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salud);

        altura = (EditText)findViewById(R.id.altura);
        peso = (EditText)findViewById(R.id.peso);

        Intent intent = getIntent();
        bundle = intent.getExtras();

        if(bundle != null){
            uid = bundle.getString("uid");
            try {
                mDatabase = FirebaseDatabase.getInstance().getReference();
                mDatabase.child("users").child(uid).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(SaludActivity.this, "Error al obtener los datos", Toast.LENGTH_SHORT).show();
                        } else {
                            for (DataSnapshot ds : task.getResult().getChildren()) {
                                String dato = ds.getValue(String.class);
                                ar.add(dato);
                            }
                            correo = ar.get(0);
                            nombre = ar.get(1);
                            apellidos = ar.get(2);
                            password = ar.get(3);
                            id = ar.get(4);
                        }
                    }
                });
                Toast.makeText(this, uid, Toast.LENGTH_SHORT).show();
            } catch (Throwable t) {
                Toast.makeText(this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        }

        mas = (ImageView) findViewById(R.id.imagemas);
        fem = (ImageView) findViewById(R.id.imagefem);
        nobi = (ImageView) findViewById(R.id.imagenobi);

        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());
    }

    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                date = makeDateString(day, month, year);
                nacimiento = Integer.toString(year);
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    private String makeDateString(int day, int month, int year)
    {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "ENERO";
        if(month == 2)
            return "FEBRERO";
        if(month == 3)
            return "MARZO";
        if(month == 4)
            return "ABRIL";
        if(month == 5)
            return "MAYO";
        if(month == 6)
            return "JUNIO";
        if(month == 7)
            return "JULIO";
        if(month == 8)
            return "AGOSTO";
        if(month == 9)
            return "SEPTIEMBRE";
        if(month == 10)
            return "OCTUBRE";
        if(month == 11)
            return "NOVIEMBRE";
        if(month == 12)
            return "DICIEMBRE";

        //default should never happen
        return "ENERO";
    }

    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }

    public void finalizar(View view){

        String alt = altura.getText().toString();
        String pes = peso.getText().toString();
        Map<String, Object> map = new HashMap<>();
        map.put("a", correo);
        map.put("b", nombre);
        map.put("c", apellidos);
        map.put("d", password);
        map.put("e", id);
        map.put("f",alt);
        map.put("g",pes);
        map.put("h", genero);
        map.put("i",date);
        map.put("j",nacimiento);
        map.put("k","500");
        map.put("l","1");
        map.put("m","150");
        map.put("n","2000");
        map.put("o","150");
        map.put("p","Alterado");
        map.put("q","90");
        map.put("r","40");
        map.put("s","50");
        map.put("t","60");
        map.put("u","70");
        map.put("v","80");
        map.put("w","90");
        map.put("x","100");
        map.put("y","80");
        Toast.makeText(SaludActivity.this, map.toString(), Toast.LENGTH_SHORT).show();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabase.child("users").child(uid).setValue(map);
        SaludActivity createPackageContext;
        Intent j = new Intent(createPackageContext = SaludActivity.this, EmergenciaActivity.class);
        j.putExtra("uid",uid);
        startActivity(j);
    }

    public void hombre(View view){
        mas.setBackgroundColor(Color.rgb(0,102,255));
        fem.setBackgroundColor(Color.rgb(255,255,255));
        nobi.setBackgroundColor(Color.rgb(255,255,255));
        genero = "hombre";
    }

    public void femenino(View view){
        fem.setBackgroundColor(Color.rgb(0,102,255));
        mas.setBackgroundColor(Color.rgb(255,255,255));
        nobi.setBackgroundColor(Color.rgb(255,255,255));
        genero = "femenino";
    }

    public void nobinario(View view){
        nobi.setBackgroundColor(Color.rgb(0,102,255));
        fem.setBackgroundColor(Color.rgb(255,255,255));
        mas.setBackgroundColor(Color.rgb(255,255,255));
        genero = "nobinario";
    }
}