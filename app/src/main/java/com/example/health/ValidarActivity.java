package com.example.health;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ValidarActivity extends AppCompatActivity {

    Bundle bundle;
    String extra, uid;
    int codigo;
    EditText code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_validar);

        code = (EditText)findViewById(R.id.editTextTextPersonNumber);

        Intent intent = getIntent();
        bundle = intent.getExtras();

        if(bundle != null){
            extra = bundle.getString("codigo");
            uid = bundle.getString("uid");

            Toast.makeText(this, extra+uid, Toast.LENGTH_SHORT).show();
            codigo = Integer.parseInt(extra);
        }

    }

    public void verificar(View view){
        if(code.getText().toString().isEmpty()){
            Toast.makeText(this, "Ingresa el código", Toast.LENGTH_SHORT).show();
        }else{
            try {
                int cod = Integer.parseInt(code.getText().toString());
                if(cod==codigo){
                    Toast.makeText(this, "Verficado", Toast.LENGTH_SHORT).show();
                    ValidarActivity createPackageContext;
                    Intent i = new Intent(createPackageContext = ValidarActivity.this, FelicitacionesActivity.class);
                    i.putExtra("uid",uid);
                    startActivity(i);
                }else{
                    Toast.makeText(this, "No coinciden", Toast.LENGTH_SHORT).show();
                }
            }catch (Exception e){
                Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void msj(View view){
        ValidarActivity createPackageContext;
        Intent j = new Intent(createPackageContext = ValidarActivity.this, SmsActivity.class);
        startActivity(j);
    }
}