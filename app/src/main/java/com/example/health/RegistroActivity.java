package com.example.health;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class RegistroActivity extends AppCompatActivity {

    DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    EditText correo,password,nombre,apellidos;
    String cor,pass,nom,app;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        correo=(EditText)findViewById(R.id.correo);
        password=(EditText)findViewById(R.id.password);
        nombre=(EditText)findViewById(R.id.nombre);
        apellidos=(EditText)findViewById(R.id.apellidos);

        mAuth = FirebaseAuth.getInstance();
    }

    public void singUp(View view){
        cor = correo.getText().toString();
        pass = password.getText().toString();
        nom = nombre.getText().toString();
        app = apellidos.getText().toString();

        mAuth.createUserWithEmailAndPassword(cor,pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    mAuth = FirebaseAuth.getInstance();
                    FirebaseUser user = mAuth.getCurrentUser();
                    if (user != null) {
                        mAuth.signInWithEmailAndPassword(cor, pass)
                                .addOnCompleteListener(RegistroActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            // Sign in success, update UI with the signed-in user's information
                                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                            if (user != null) {
                                                // Name, email address, and profile photo Url
                                                String name = user.getDisplayName();
                                                String email = user.getEmail();

                                                String uid = user.getUid();

                                                Map<String, Object> map = new HashMap<>();
                                                map.put("a", email);
                                                map.put("b", nom);
                                                map.put("c",app);
                                                map.put("d",pass);
                                                map.put("e", uid);

                                                mDatabase = FirebaseDatabase.getInstance().getReference();
                                                mDatabase.child("users").child(uid).setValue(map);

                                                startActivity(new Intent(RegistroActivity.this, LoginActivity.class));
                                                finish();
                                            }
                                        }
                                    }
                                });
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(RegistroActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void singIn(View v){
        RegistroActivity createPackageContext;
        Intent i = new Intent(createPackageContext = RegistroActivity.this, LoginActivity.class);
        startActivity(i);
    }
}