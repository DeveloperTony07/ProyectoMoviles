package com.example.proyectomoviles.controller;

import com.example.proyectomoviles.model.Client;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database {

    private FirebaseFirestore mFirestore;

    public Database() {
        mFirestore = FirebaseFirestore.getInstance();
    }


    //Add a lawyer
    public void postLawyer(String id, String username, String phone, String email, String password,
                           OnSuccessListener<DocumentReference> onSuccessListener,
                           OnFailureListener onFailureListener) {
        // Realiza una consulta para verificar si el ID ya existe
        mFirestore.collection("lawyer")
                .whereEqualTo("id", id)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (queryDocumentSnapshots.isEmpty()) {
                        // No hay documentos con el mismo ID, procede con la inserción
                        Map<String, Object> map = new HashMap<>();
                        map.put("id", id);
                        map.put("username", username);
                        map.put("phone", phone);
                        map.put("email", email);
                        map.put("password", password);

                        mFirestore.collection("lawyer").add(map)
                                .addOnSuccessListener(onSuccessListener)
                                .addOnFailureListener(onFailureListener);
                    } else {
                        // Ya existe un documento con el mismo ID
                        onFailureListener.onFailure(new Exception("El ID ya existe"));
                    }
                })
                .addOnFailureListener(onFailureListener);
    }//End postLawyer

    // Agregar una cita (appointment)
    public void postAppointment(String lawyerId, String clientName, String clientID, int clientPhone, String appointmentName,
                                String appointmentType, String time, int pay,
                                OnSuccessListener<DocumentReference> onSuccessListener,
                                OnFailureListener onFailureListener) {

        // Crear un mapa con los datos de la cita
        Map<String, Object> appointmentData = new HashMap<>();
        appointmentData.put("lawyerId", lawyerId); // Clave foránea para relacionar con el abogado
        appointmentData.put("clientName", clientName);
        appointmentData.put("clientID", clientID);
        appointmentData.put("clientPhone", clientPhone);
        appointmentData.put("appointmentName", appointmentName);
        appointmentData.put("appointmentType", appointmentType);
        appointmentData.put("time", time);
        appointmentData.put("pay", pay);

        // Agregar la cita a la colección "appointments"
        mFirestore.collection("appointments").add(appointmentData)
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
    }//End RegisterAppointment

    //user login
    public void loginUser(String id, String password,
                          OnSuccessListener<DocumentSnapshot> onSuccessListener,
                          OnFailureListener onFailureListener) {
        mFirestore.collection("lawyer")
                .whereEqualTo("id", id)
                .whereEqualTo("password", password)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        // Usuario autenticado correctamente
                        onSuccessListener.onSuccess(queryDocumentSnapshots.getDocuments().get(0));
                    } else {
                        // No se encontró un usuario con las credenciales proporcionadas
                        onFailureListener.onFailure(new Exception("Credenciales inválidas"));
                    }
                })
                .addOnFailureListener(onFailureListener);
    }

    public void getAllLawyers(OnSuccessListener<QuerySnapshot> onSuccessListener,
                              OnFailureListener onFailureListener) {
        mFirestore.collection("lawyer")
                .get()
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
    }

    public void getAppointmentsByLawyer(String lawyerId,
                                        OnSuccessListener<QuerySnapshot> onSuccessListener,
                                        OnFailureListener onFailureListener) {
        mFirestore.collection("appointments")
                .whereEqualTo("lawyerId", lawyerId)
                .get()
                .addOnSuccessListener(onSuccessListener)
                .addOnFailureListener(onFailureListener);
    }


    public void getAllClients(String lawyerId,
                              OnSuccessListener<List<Client>> onSuccessListener,
                              OnFailureListener onFailureListener) {
        mFirestore.collection("appointments")
                .whereEqualTo("lawyerId", lawyerId)  // Filtrar por el ID del abogado
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    List<Client> clients = new ArrayList<>();
                    for (DocumentSnapshot document : queryDocumentSnapshots) {
                        String name = document.getString("clientName");
                        long phone = document.getLong("clientPhone");

                        if (name != null && phone != 0) {
                            clients.add(new Client(name, (int) phone));
                        }
                    }
                    onSuccessListener.onSuccess(clients);
                })
                .addOnFailureListener(onFailureListener);
    }


}


