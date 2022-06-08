package com.dh.clinica.main.dao.impl;
import com.dh.clinica.main.dao.IDAO;
import com.dh.clinica.main.model.Domicilio;

import java.sql.*;

public class DomicilioDAOH2 implements IDAO<Domicilio> {
    //Conexión. Lo dejamos estático porque no vamos a modificarlo.
    private static Connection getConnection() throws Exception{
    Class.forName("org.h2.Driver").newInstance();
    return DriverManager.getConnection("jdbc:h2:~/db_pacientes", "sa", "");
    }


    @Override
    public Domicilio guardar(Domicilio domicilio) {
        //Pasos para guardar en una base de datos

        //1. Creamos una conexión
        Connection connection = null;

        try {
            //2. Nos conectamos a la base de datos;
            connection = getConnection();

            //3. Creamos una sentencia. Le agregamos un parámetro más para que me devuelva las claves generadas.
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO domicilios(calle,numero, localidad, provincia) VALUES (?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, domicilio.getCalle());
            preparedStatement.setInt(2, domicilio.getNumero());
            preparedStatement.setString(3, domicilio.getLocalidad());
            preparedStatement.setString(4, domicilio.getProvincia());

            //4. Ejecutamos sentencia y cerramos
            preparedStatement.executeUpdate();


            //5.Para obtener las claves generadas.
            //Hacemos esto, porque después la va utilizar la clase paciente.
            ResultSet clavesGeneradas = preparedStatement.getGeneratedKeys();
            if (clavesGeneradas.next()) { //El next funciona moviendose un lugar y diciendome si tengo más elementos
                domicilio.setId(clavesGeneradas.getInt(1)); //Seteo en mi domicilio la clave generada
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return domicilio;

        }
    }

        @Override
        public Domicilio buscar(int id){
            //Pasos para buscar en una base de datos

            //1. Creamos una conexión, y un objeto domicilio
            Connection connection = null;
            Domicilio domicilio = null;

            try {
                //2. Levantar el driver y nos conectamos
                connection = getConnection();

                //3.Preparamos la consulta
                PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM domicilios WHERE ID=?");
                preparedStatement.setInt(1, id);

                //Ejecutamos la sentencia
                ResultSet resultSet = preparedStatement.executeQuery();
                while(resultSet.next()){ //Me devuelve un resultado
                    //Utilizamos la información buscada
                    domicilio = new Domicilio(resultSet.getInt(1), resultSet.getString(2), resultSet.getInt(3), resultSet.getString(4), resultSet.getString(5));

                }

            } catch (Exception e) {
                e.printStackTrace();

            } finally {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }

            return domicilio;
        }
    }
