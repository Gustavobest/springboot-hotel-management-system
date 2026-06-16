package com.gustavo.hotel_management.dto;

public class RoomRequest {

    private String nombre;
    private double precio;

    public RoomRequest(String nombre, double precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    public RoomRequest() {
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
}
