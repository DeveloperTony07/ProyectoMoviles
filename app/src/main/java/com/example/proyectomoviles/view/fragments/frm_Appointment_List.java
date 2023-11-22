package com.example.proyectomoviles.view.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectomoviles.R;
import com.example.proyectomoviles.controller.Database;
import com.example.proyectomoviles.model.Appointment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class frm_Appointment_List extends Fragment {

    ListView appointmentList;
    ArrayAdapter<Appointment> adapter;
    ArrayList<Appointment> appointments;
    Button btnBack;

    public frm_Appointment_List() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frm_appointment_list, container, false);

        appointmentList = view.findViewById(R.id.listVwAppointmentList);
        btnBack =view.findViewById(R.id.btnBackAppointmentList);

        Bundle arg = getArguments();
        String lawyerID = arg.getString("lawyerID");
        String lawyerUsername = arg.getString("lawyerUsername");


        // Lista para almacenar las citas
        appointments = new ArrayList<>();

        // Adaptador personalizado para el ListView
        adapter = new ArrayAdapter<Appointment>(requireContext(), android.R.layout.simple_list_item_1, appointments) {
            @NonNull
            @Override
            public View getView(int position, View convertView, @NonNull ViewGroup parent) {
                // Verificar si la vista actual es nula, de lo contrario, inflarla
                if (convertView == null) {
                    convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_1, parent, false);
                }

                // Obtener el objeto Appointment en la posición actual
                Appointment currentAppointment = getItem(position);

                // Configurar el texto en la vista con los detalles del Appointment
                if (currentAppointment != null) {
                    String appointmentInfo = currentAppointment.getAppointmentName() +
                            " LawyerID: " + currentAppointment.getLawyerId() +
                            " \nClient name: " + currentAppointment.getClientName() +
                            " \nClient ID: " + currentAppointment.getClientID()+
                            " \nClient Phone: " + currentAppointment.getClientPhone() +
                            " \nAppointment Name: " + currentAppointment.getAppointmentName() +
                            " \nAppointment Type: " + currentAppointment.getAppointmentType() +
                            " \nTime: " + currentAppointment.getTime() +
                            " \nPay: " + currentAppointment.getPay();

                    ((TextView) convertView.findViewById(android.R.id.text1)).setText(appointmentInfo);
                }

                return convertView;
            }
        };

        appointmentList.setAdapter(adapter);

        Database db = new Database();
        db.getAppointmentsByLawyer(lawyerID,
                new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        // Limpiar la lista de citas
                        appointments.clear();

                        // Iterar sobre los documentos y agregar las citas a la lista
                        for (DocumentSnapshot document : queryDocumentSnapshots.getDocuments()) {
                            // Obtener los datos del documento
                            String lawyerId = document.getString("lawyerId");
                            String clientName = document.getString("clientName");
                            String clientId = document.getString("clientID");
                            int clientPhone = document.getLong("clientPhone").intValue();
                            String appointmentName = document.getString("appointmentName");
                            String appointmentType = document.getString("appointmentType");
                            String time = document.getString("time");
                            int pay = document.getLong("pay").intValue();

                            // Crear un objeto Appointment con los datos
                            Appointment appointment = new Appointment(
                                    lawyerId, clientName, clientId, clientPhone,
                                    appointmentName, appointmentType, time, pay
                            );

                            // Agregar el objeto Appointment a la lista
                            appointments.add(appointment);
                        }

                        // Notificar al adaptador que los datos han cambiado
                        adapter.notifyDataSetChanged();
                    }
                },
                new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Manejar el error
                        if (isAdded()) {
                            String errorMessage = "Error al obtener las citas: " + e.getMessage();
                            Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("lawyerID",lawyerID);
                bundle.putString("lawyerUsername",lawyerUsername);

                frm_Home nextFragment = new frm_Home();
                nextFragment.setArguments(bundle);

                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.frmLayoutHome, nextFragment).addToBackStack(null).commit();
            }
        });

        return view;
    }
}
