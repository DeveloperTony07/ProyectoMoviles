package com.example.proyectomoviles.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.proyectomoviles.R;
import com.example.proyectomoviles.controller.Database;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentSnapshot;

public class act_Login extends AppCompatActivity {

    private Button btnBack, btnLogin;
    private TextInputLayout id, password;
    Database db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_login);
        db = new Database();

        id = findViewById(R.id.txtIDLogin);
        password = findViewById(R.id.txtPasswordLogin);

        btnBack = findViewById(R.id.btnBackToStart2);
        btnLogin = findViewById(R.id.btnEnterToLogin);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(act_Login.this, act_Start.class);
                startActivity(intent);
                finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userID = id.getEditText().getText().toString();
                String userPassword = password.getEditText().getText().toString();

                db.loginUser(userID, userPassword,
                        new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                // Usuario autenticado correctamente
                                String userName = documentSnapshot.getString("username");
                                String userId = documentSnapshot.getString("id");

                                Intent intent = new Intent(act_Login.this, act_Home.class);
                                intent.putExtra("userId", userId);
                                intent.putExtra("userName", userName);

                                startActivity(intent);
                                finish();
                            }
                        },
                        new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Error de autenticación
                                Toast.makeText(act_Login.this, "Error de inicio de sesión: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

    }
}