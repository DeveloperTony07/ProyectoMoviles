package com.example.proyectomoviles.view.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.proyectomoviles.R;
public class frm_Home extends Fragment {

    TextView txtUsername;
    Button btnRegisterAppointment, btnAppointmentsList, btnCallClient;

    public frm_Home() {
        // Required empty public constructor
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.frm_home, container, false);

        // Inicializar el TextView correctamente
        txtUsername = view.findViewById(R.id.txtViewUsername);
        btnRegisterAppointment = view.findViewById(R.id.btnRegisterAppointment);
        btnAppointmentsList = view.findViewById(R.id.btnAppointmentsList);
        btnCallClient = view.findViewById(R.id.btnCallClient);

        // Recupera los datos del fragmento
        Bundle arg = getArguments();
        /*
        if (arg != null) {
        */

            String lawyerID = arg.getString("lawyerID");
            String lawyerUsername = arg.getString("lawyerUsername");

            Log.d("frm_Home", "Valor de lawyerUsername: " + lawyerUsername);
        txtUsername.setText(lawyerUsername);
            /*
            if (lawyerUsername != null) {
                txtUsername.setText(lawyerUsername);
            }
            */

            btnRegisterAppointment.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();

                    bundle.putString("lawyerID", lawyerID);
                    bundle.putString("lawyerUsername", lawyerUsername);

                    frm_RegisterAppointment nextFragment = new frm_RegisterAppointment();
                    nextFragment.setArguments(bundle);

                    FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                    transaction.replace(R.id.frmLayoutHome, nextFragment).addToBackStack(null).commit();
                }
            });

            // Botón para ver la lista de citas
            btnAppointmentsList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putString("lawyerID", lawyerID);
                    bundle.putString("lawyerUsername", lawyerUsername);

                    frm_Appointment_List nextFragment = new frm_Appointment_List();
                    nextFragment.setArguments(bundle);

                    FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                    transaction.replace(R.id.frmLayoutHome, nextFragment).addToBackStack(null).commit();
                }
            });

            // Button for see the contact list
        btnCallClient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("lawyerID", lawyerID);
                bundle.putString("lawyerUsername", lawyerUsername);

                frm_Call_Client nextFragment = new frm_Call_Client();
                nextFragment.setArguments(bundle);

                FragmentTransaction transaction = getParentFragmentManager().beginTransaction();
                transaction.replace(R.id.frmLayoutHome, nextFragment).addToBackStack(null).commit();
            }
        });

       // }//End the If
        // Return the inflated view
        return view;
    }//End onCreate
}
