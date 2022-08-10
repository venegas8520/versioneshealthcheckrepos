package com.example.health;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {

    DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    EditText correo,password;
    String cor,pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        correo=(EditText)findViewById(R.id.correo);
        password=(EditText)findViewById(R.id.password);

        mAuth = FirebaseAuth.getInstance();

    }

    public void singIn(View v){

        cor = correo.getText().toString();
        pass = password.getText().toString();

        if(cor.isEmpty() && pass.isEmpty()){
            Toast.makeText(this, "Rellena los campos", Toast.LENGTH_SHORT).show();
        }else {

            mAuth.signInWithEmailAndPassword(cor, pass)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                if (user != null) {
                                    Toast.makeText(LoginActivity.this, user.getUid(), Toast.LENGTH_SHORT).show();
                                    LoginActivity createPackageContext;
                                    //Intent i = new Intent(createPackageContext = LoginActivity.this, SmsActivity.class);
                                    Intent i = new Intent(createPackageContext = LoginActivity.this, HomeActivity.class);
                                    i.putExtra("uid", user.getUid());
                                    startActivity(i);
                                }
                            } else {
                                Toast.makeText(LoginActivity.this, "Usuario o contraseña incorrecta", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

    public void singUp(View v){
        LoginActivity createPackageContext;
        Intent j = new Intent(createPackageContext = LoginActivity.this, RegistroActivity.class);
        startActivity(j);
    }
}