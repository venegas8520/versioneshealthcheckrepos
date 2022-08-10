package com.example.health;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class SmsActivity extends AppCompatActivity {

    EditText etCel;
    FloatingActionButton btnSelec;
    Button btnEnviar;
    String etMsj = "123456";
    Bundle bundle;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

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

        etCel = findViewById(R.id.editTextTextPersonNumber);

        btnEnviar = findViewById(R.id.button);

        btnSelec = findViewById(R.id.btnSelec);

        if (ActivityCompat.checkSelfPermission(SmsActivity.this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(SmsActivity.this, new String[]{Manifest.permission.SEND_SMS}, 1);
        }


        btnSelec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_PICK);

                intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);

                startActivityForResult(intent, 1);

            }
        });


        btnEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (etMsj.isEmpty() || etCel.getText().toString().isEmpty()){

                    Toast.makeText(SmsActivity.this, "Ingrese el Numero de Celular...", Toast.LENGTH_LONG).show();

                }else {

                    try {
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(etCel.getText().toString(), null, etMsj, null, null);;
                        Toast.makeText(SmsActivity.this, "MSJ Enviado", Toast.LENGTH_LONG).show();
                        SmsActivity createPackageContext;
                        Intent i = new Intent(createPackageContext = SmsActivity.this, ValidarActivity.class);
                        i.putExtra("codigo",etMsj);
                        i.putExtra("uid",uid);
                        startActivity(i);
                    }catch (Exception e){

                        Toast.makeText(SmsActivity.this, "Error SMS...\n Ingrese un Numero Valido", Toast.LENGTH_LONG).show();

                    }


                }

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

                int indiceNumber = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

                String numero = cursor.getString(indiceNumber);

                numero = numero.replace("(", "").replace(")", "").replace(" ", "").replace("-", "");

                etCel.setText(numero);

            }

        }

    }
}