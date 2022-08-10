package com.example.health;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class DispositivosActivity extends AppCompatActivity {

    Bundle bundle;
    String uid;

    ListView listaContactos;
    ArrayList<String> ar = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dispositivos);

        Intent intent = getIntent();
        bundle = intent.getExtras();

        if(bundle != null){
            uid = bundle.getString("uid");
            try {
                Toast.makeText(this, uid, Toast.LENGTH_SHORT).show();

            } catch (Throwable t) {
                Toast.makeText(this, t.toString(), Toast.LENGTH_SHORT).show();
            }
        }

        listaContactos = (ListView) findViewById(R.id.lista);

        for (int i = 0; i < 1; i++) {
            ar.add("Google Wear OS");
            ar.add("Watch OS");
            ar.add("Mi Band");
        }

        ArrayAdapter<String> adapter =new ArrayAdapter<String>(DispositivosActivity.this,
                android.R.layout.simple_expandable_list_item_1,ar);
        listaContactos.setAdapter(adapter);
    }

    public void dispositivo(View view){
        DispositivosActivity createPackageContext;
        Intent i = new Intent(createPackageContext = DispositivosActivity.this, BuscandoActivity.class);
        i.putExtra("uid",uid);
        startActivity(i);
    }
}