package com.example.proyectomoviles.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.proyectomoviles.R;
import com.example.proyectomoviles.controller.Database;
import com.example.proyectomoviles.model.Lawyer;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class act_UserList extends AppCompatActivity {

    Button btnBack;
    ListView userList;
    ArrayAdapter<String> adapter;
    Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_user_list);
        db = new Database();


        userList = findViewById(R.id.lawyersList);
        btnBack = findViewById(R.id.btnBackToStart3);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(act_UserList.this, act_Start.class);
                startActivity(intent);
                finish();
            }
        });

        // Obtener la lista de abogados y mostrarla en el ListView
        db.getAllLawyers(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                List<String> lawyerList = new ArrayList<>();
                for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                    // Convierte cada documento en un objeto Lawyer y obtén la información necesaria
                    Lawyer lawyer = document.toObject(Lawyer.class);

                    // Agregar logs para depuración
                    Log.d("ID_DEBUG", "ID: " + lawyer.getId());

                    String lawyerInfo = "ID: " + lawyer.getId() + "\n"
                            + "Username: " + lawyer.getUsername() + "\n"
                            + "Phone: " + lawyer.getPhone() + "\n"
                            + "Email: " + lawyer.getEmail() + "\n"
                            + "Password: " + lawyer.getPassword();
                    lawyerList.add(lawyerInfo);
                }

                // Configura el ArrayAdapter y establece los datos en el ListView
                adapter = new ArrayAdapter<>(act_UserList.this, android.R.layout.simple_list_item_1, lawyerList);
                userList.setAdapter(adapter);
            }
        }, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(act_UserList.this, "Error al obtener la lista de abogados: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
      }
}


