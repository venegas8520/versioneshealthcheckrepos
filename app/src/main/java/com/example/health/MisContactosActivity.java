package com.example.health;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.ArrayList;

public class MisContactosActivity extends AppCompatActivity {

    Bundle bundle;
    String name, number,uid;

    ListView listaContactos;
    ArrayList<String> ar = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mis_contactos);

        Intent intent = getIntent();
        bundle = intent.getExtras();

        listaContactos = (ListView) findViewById(R.id.lista);

        if(bundle != null){
            name = bundle.getString("name");
            number = bundle.getString("number");
            uid = bundle.getString("uid");
            Toast.makeText(this, name+number, Toast.LENGTH_SHORT).show();
            for (int i = 0; i < 1; i++) {
                ar.add(name+"\n"+number);
            }
        }else {
            for (int i = 0; i < 1; i++) {
                ar.add("Contacto 1\nHermano\n9512548968\ncorreo@gmail.com");
            }
        }


        ArrayAdapter<String> adapter =new ArrayAdapter<String>(MisContactosActivity.this,
                android.R.layout.simple_expandable_list_item_1,ar);
        listaContactos.setAdapter(adapter);
    }

    public void dispositivo(View view){
        MisContactosActivity createPackageContext;
        Intent i = new Intent(createPackageContext = MisContactosActivity.this, DispositivosActivity.class);
        i.putExtra("uid",uid);
        startActivity(i);
    }

    public void add(View view){
        MisContactosActivity createPackageContext;
        Intent i = new Intent(createPackageContext = MisContactosActivity.this, EmergenciaActivity.class);
        i.putExtra("uid",uid);
        startActivity(i);
    }
}