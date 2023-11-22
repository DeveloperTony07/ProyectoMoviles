package com.example.proyectomoviles.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyectomoviles.R;
import com.example.proyectomoviles.controller.Database;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;

public class act_Register extends AppCompatActivity {

    private EditText txtID, txtUsername, txtPhone, txtEmail, txtPassword, txtConfirmPassword;
    private Button btnSingUp, btnBack;
    Database db;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_register);
        db = new Database();

        txtID = findViewById(R.id.txtID);
        txtUsername = findViewById(R.id.txtUsername);
        txtPhone = findViewById(R.id.txtPhone);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtPassword);
        txtConfirmPassword = findViewById(R.id.txtConfirmPassword);

        btnSingUp = findViewById(R.id.btnSingUpLawyer);
        btnBack = findViewById(R.id.btnBackToStart);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(act_Register.this, act_Start.class);
                startActivity(intent);
                finish();
            }
        });

        btnSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = txtID.getText().toString().trim();
                String username = txtUsername.getText().toString().trim();
                String phone = txtPhone.getText().toString().trim();
                String email = txtEmail.getText().toString().trim();
                String password = txtPassword.getText().toString().trim();

                if (id.isEmpty() || username.isEmpty() || phone.isEmpty() || email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Ingrese los datos", Toast.LENGTH_SHORT).show();
                } else {
                    // Se ejecuta solo si todos los campos tienen datos
                    if (verifyPassword()) {
                        db.postLawyer(id, username, phone, email, password,
                                new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Toast.makeText(getApplicationContext(), "Lawyer added correctly", Toast.LENGTH_SHORT).show();
                                        clean();
                                        // Espera 1 segundo antes de cambiar de actividad
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                // Cambia a la siguiente actividad (reemplaza "NextActivity.class" con tu actividad deseada)
                                                Intent intent = new Intent(act_Register.this, act_Start.class);
                                                startActivity(intent);

                                                // Finaliza la actividad actual si es necesario
                                                finish();
                                            }
                                        }, 1000); // 1000 milisegundos (1 segundo)
                                    }
                                },
                                new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getApplicationContext(), "Error to add Lawyer", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        Toast.makeText(getApplicationContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });//End btnSingUp

    }//End onCreate

    private boolean verifyPassword(){
        String password = txtPassword.getText().toString();
        String confirmPassword = txtConfirmPassword.getText().toString();

        if(password.equals(confirmPassword)){
            return true;
        }else{
            return false;
        }
    }

    private void clean(){
        txtID.setText("");
        txtUsername.setText("");
        txtPhone.setText("");
        txtEmail.setText("");
        txtPassword.setText("");
        txtConfirmPassword.setText("");
    }

}