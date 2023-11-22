package com.example.proyectomoviles.view;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


import com.example.proyectomoviles.R;
import com.example.proyectomoviles.controller.Database;


public class act_Start extends AppCompatActivity {

    Database db;
    private Button btnSingUp, btnLogin, btnUserList;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_start);
        db = new Database();

        btnSingUp = findViewById(R.id.btnSingUp);
        btnLogin = findViewById(R.id.btnLogin);
        btnUserList = findViewById(R.id.btnSeeUserList);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(act_Start.this, act_Login.class);
                startActivity(intent);
            }
        });

        btnUserList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(act_Start.this, act_UserList.class);
                startActivity(intent);
            }
        });

        btnSingUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(act_Start.this, act_Register.class);
                startActivity(intent);
            }
        });


    }//End onCreate

}