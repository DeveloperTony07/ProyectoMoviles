package com.example.proyectomoviles.view.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.proyectomoviles.R;
import com.example.proyectomoviles.controller.Database;
import com.example.proyectomoviles.model.Appointment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.firestore.DocumentReference;

public class frm_Register_Appointment2 extends Fragment {

    private TextInputLayout etTime, etPay;
    private Button btnRegisterAppointment;
    Database db;

    public frm_Register_Appointment2() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frm_register_appointment2, container, false);

        btnRegisterAppointment = view.findViewById(R.id.btnRegisterAppointment);

        etTime = view.findViewById(R.id.txtRegisterAppointmentTime);
        etPay = view.findViewById(R.id.txtRegisterAppointmentPay);

        btnRegisterAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle arguments = getArguments();
                if (arguments != null) {
                    String lawyerID = arguments.getString("lawyerID");
                    String clientName = arguments.getString("clientName");
                    String clientID = arguments.getString("clientID");
                    String clientPhone = arguments.getString("clientPhone");
                    String appointmentName = arguments.getString("appointmentName");
                    String appointmentType = arguments.getString("appointmentType");
                    String time = etTime.getEditText().getText().toString();
                    String stringPay = etPay.getEditText().getText().toString();
                    int pay = Integer.parseInt(stringPay);

                    String lawyerUsername = arguments.getString("lawyerUsername");

                    db = new Database();

                    // Crear una instancia de Appointment con los datos obtenidos
                    Appointment appointment = new Appointment(
                            lawyerID,
                            clientName,
                            clientID,
                            Integer.parseInt(clientPhone), // Suponiendo que clientPhone es un número
                            appointmentName,
                            appointmentType,
                            time,
                            pay
                    );

                    // Llamar al método para agregar la cita
                    db.postAppointment(
                            appointment.getLawyerId(),
                            appointment.getClientName(),
                            appointment.getClientID(),
                            appointment.getClientPhone(),
                            appointment.getAppointmentName(),
                            appointment.getAppointmentType(),
                            appointment.getTime(),
                            appointment.getPay(),
                            new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {


                                    Bundle bundle = new Bundle();
                                    bundle.putString("lawyerID", lawyerID);
                                    bundle.putString("lawyerUsername", lawyerUsername);


                                    frm_Home nextFragment = new frm_Home();
                                    nextFragment.setArguments(bundle);

                                    FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                                    transaction.replace(R.id.frmLayoutHome, nextFragment).addToBackStack(null).commit();
                                }
                            },
                            new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getActivity(), "Error to add appointment", Toast.LENGTH_SHORT).show();
                                }
                            }
                    );
                } else {
                    Toast.makeText(getActivity(),"Se fue a la mierda", Toast.LENGTH_SHORT).show();
                }

            }
        });

        return view;
    }
}
