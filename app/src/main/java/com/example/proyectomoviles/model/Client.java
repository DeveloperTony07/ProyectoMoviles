package com.example.proyectomoviles.model;

public class Client {

    private String name;
    private int phone;

    public Client() {
        this.name = "";
        this.phone = 0;
    }

    public Client(String name, int phone) {
        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Client{" +
                "name='" + name + '\'' +
                ", phone=" + phone +
                '}';
    }
}
