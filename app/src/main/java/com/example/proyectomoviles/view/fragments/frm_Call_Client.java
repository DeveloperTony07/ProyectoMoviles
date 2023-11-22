package com.example.proyectomoviles.view.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.proyectomoviles.R;
import com.example.proyectomoviles.controller.Database;
import com.example.proyectomoviles.model.Client;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.List;

public class frm_Call_Client extends Fragment {
    Button btnBack;
    ListView contactList;
    ArrayAdapter<Client> adapter;
    ArrayList<Client> clients;
    public frm_Call_Client() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.frm_call_client, container, false);
        btnBack = view.findViewById(R.id.btnBackClientContact);
        contactList = view.findViewById(R.id.listVwClientContact);

        Bundle arg = getArguments();
        String lawyerID = arg.getString("lawyerID");
        String lawyerUsername = arg.getString("lawyerUsername");


        // Lista para almacenar los clientes
        clients = new ArrayList<>();

        // Adaptador para el ListView
        adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, clients);
        contactList.setAdapter(adapter);

        Database db = new Database();
        db.getAllClients(lawyerID,
                new OnSuccessListener<List<Client>>() {
                    @Override
                    public void onSuccess(List<Client> clientList) {
                        // Limpiar la lista de clientes
                        clients.clear();

                        // Agregar los clientes obtenidos a la lista
                        clients.addAll(clientList);

                        // Notificar al adaptador que los datos han cambiado
                        adapter.notifyDataSetChanged();
                    }
                },
                new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Manejar el error
                        // Puedes mostrar un mensaje de error o realizar otra acción aquí
                    }
                });


        // Agregar un listener de clics al ListView
        contactList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Obtener el cliente seleccionado
                Client selectedClient = clients.get(position);

                // Verificar si el número de teléfono no está vacío
                if (selectedClient != null && selectedClient.getPhone() != 0) {
                    // Formatear el número de teléfono
                    String phoneNumber = "tel:" + selectedClient.getPhone();

                    // Crear un Intent para realizar la llamada
                    Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse(phoneNumber));

                    // Iniciar la actividad de llamada
                    startActivity(dialIntent);
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("lawyerID", lawyerID);
                bundle.putString("lawyerUsername", lawyerUsername);

                frm_Home nextFragment = new frm_Home();
                nextFragment.setArguments(bundle);

                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.frmLayoutHome, nextFragment).addToBackStack(null).commit();
            }
        });

        return  view;
    }
}