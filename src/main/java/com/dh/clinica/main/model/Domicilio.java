package com.dh.clinica.main.model;

public class Domicilio {
    //Atributos
    private int id;
    private String calle;
    private int numero;
    private String localidad;
    private String provincia;

    //Constructor con todos los atributos
    public Domicilio(int id, String calle, int numero, String localidad, String provincia) {
        this.id = id;
        this.calle = calle;
        this.numero = numero;
        this.localidad = localidad;
        this.provincia = provincia;
    }

    //Constructor sin el id
    public Domicilio(String calle, int numero, String localidad, String provincia) {
        this.calle = calle;
        this.numero = numero;
        this.localidad = localidad;
        this.provincia = provincia;
    }

    //Métodos accesores
    public int getId() {
        return id;
    }

    public String getCalle() {
        return calle;
    }

    public int getNumero() {
        return numero;
    }

    public String getLocalidad() {
        return localidad;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setId(int id) {
        this.id = id;
    }

    //ToString que lo va a usar el ToString de paciente
    @Override
    public String toString() {
        return "Domicilio{" +
                "id=" + id +
                ", calle='" + calle + '\'' +
                ", numero=" + numero +
                ", localidad='" + localidad + '\'' +
                ", provincia='" + provincia + '\'' +
                '}';
    }
}
