package com.example.proyectomoviles.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

import com.example.proyectomoviles.R;
import com.example.proyectomoviles.view.fragments.frm_Home;

public class act_Home extends AppCompatActivity {

    String lawyerID, lawyerUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lyt_home);

        //Recupera el id y el nombre de usuario.
        Intent intent = getIntent();
        lawyerID = intent.getStringExtra("userId");
        lawyerUsername = intent.getStringExtra("userName");


        // Crea una instancia del fragmento que deseas cargar
        Fragment frmStart = new frm_Home();

        // Agrega los datos al fragmento usando setArguments
        Bundle bundle = new Bundle();
        bundle.putString("lawyerID", lawyerID);
        bundle.putString("lawyerUsername", lawyerUsername);
        frmStart.setArguments(bundle);

        // Utiliza el FragmentManager para realizar transacciones de fragmentos
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Reemplaza el contenedor con el fragmento
        fragmentTransaction.replace(R.id.frmLayoutHome, frmStart);

        // Confirma la transacción
        fragmentTransaction.commit();
    }

}