package com.dh.clinica.main.dao;

public interface IDAO<T> { //Recibe el tipo cómo parámetro
    public T guardar(T t); //Métodos que van a sobreescribirse
    public T buscar(int id);


}
