package com.example.health;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class ContactoActivity extends AppCompatActivity {

    Bundle bundle;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacto);

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
    }

    public void saveContacto(View v){
        ContactoActivity createPackageContext;
        Intent i = new Intent(createPackageContext = ContactoActivity.this, MisContactosActivity.class);
        i.putExtra("uid",uid);
        startActivity(i);
    }
}