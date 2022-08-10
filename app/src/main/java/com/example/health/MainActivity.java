package com.example.health;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void singIn(View v){
        MainActivity createPackageContext;
        Intent i = new Intent(createPackageContext = MainActivity.this, LoginActivity.class);
        startActivity(i);
    }

    public void singUp(View v){
        MainActivity createPackageContext;
        Intent j = new Intent(createPackageContext = MainActivity.this, RegistroActivity.class);
        startActivity(j);
    }
}