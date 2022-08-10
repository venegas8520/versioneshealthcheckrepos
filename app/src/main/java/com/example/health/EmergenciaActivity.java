package com.example.health;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class EmergenciaActivity extends AppCompatActivity {

    Button btnSelec;
    String name;
    String number;

    Bundle bundle;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergencia);

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

        btnSelec = findViewById(R.id.btnSelec);


        btnSelec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK);

                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);

                startActivityForResult(intent, 1);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK){

            Uri uri = data.getData();
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);

            if (cursor != null && cursor.moveToFirst()){

                int indiceName = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                int indiceNumber = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

                String nombre = cursor.getString(indiceName);
                String numero = cursor.getString(indiceNumber);

                numero = numero.replace("(", "").replace(")", "").replace(" ", "").replace("-", "");

                name = nombre;
                number = (numero);

                Intent i = new Intent(EmergenciaActivity.this, MisContactosActivity.class);
                i.putExtra("name",name);
                i.putExtra("number",number);
                i.putExtra("uid",uid);
                startActivity(i);
            }

        }

    }

    public void contacto(View view){
        EmergenciaActivity createPackageContext;
        Intent j = new Intent(createPackageContext = EmergenciaActivity.this, ContactoActivity.class);
        j.putExtra("uid",uid);
        startActivity(j);
    }

}