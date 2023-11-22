package com.example.proyectomoviles.model;

public class Appointment {

    String lawyerId;
    String clientName, clientID;
    int  clientPhone;
    String appointmentName, appointmentType, time;
    int pay;

    public Appointment() {
        this.lawyerId = "";
        this.clientName = "";
        this.clientID = "";
        this.clientPhone = 0;
        this.appointmentName = "";
        this.appointmentType = "";
        this.time = "";
        this.pay = 0;
    }

    public Appointment(String lawyerId, String clientName, String clientID, int clientPhone, String appointmentName, String appointmentType, String time, int pay) {
        this.lawyerId = lawyerId;
        this.clientName = clientName;
        this.clientID = clientID;
        this.clientPhone = clientPhone;
        this.appointmentName = appointmentName;
        this.appointmentType = appointmentType;
        this.time = time;
        this.pay = pay;
    }

    public String getLawyerId() {
        return lawyerId;
    }

    public void setLawyerId(String lawyerId) {
        this.lawyerId = lawyerId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getClientID() {
        return clientID;
    }

    public void setClientID(String clientID) {
        this.clientID = clientID;
    }

    public int getClientPhone() {
        return clientPhone;
    }

    public void setClientPhone(int clientPhone) {
        this.clientPhone = clientPhone;
    }

    public String getAppointmentName() {
        return appointmentName;
    }

    public void setAppointmentName(String appointmentName) {
        this.appointmentName = appointmentName;
    }

    public String getAppointmentType() {
        return appointmentType;
    }

    public void setAppointmentType(String appointmentType) {
        this.appointmentType = appointmentType;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getPay() {
        return pay;
    }

    public void setPay(int pay) {
        this.pay = pay;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "lawyerId='" + lawyerId + '\'' +
                ", clientName='" + clientName + '\'' +
                ", clientID='" + clientID + '\'' +
                ", clientPhone=" + clientPhone +
                ", appointmentName='" + appointmentName + '\'' +
                ", appointmentType='" + appointmentType + '\'' +
                ", time='" + time + '\'' +
                ", pay=" + pay +
                '}';
    }

}
