package com.dh.clinica.main.dao.impl;
import com.dh.clinica.main.dao.IDAO;
import com.dh.clinica.main.model.Domicilio;
import com.dh.clinica.main.model.Paciente;


import java.sql.*;

public class PacienteDAOH2 implements IDAO<Paciente> {
    //Conexión. Lo dejamos estático porque no vamos a modificarlo.
    private static Connection getConnection() throws Exception{
        Class.forName("org.h2.Driver").newInstance();
        return DriverManager.getConnection("jdbc:h2:~/db_pacientes", "sa", "");
    }

    @Override
    public Paciente guardar(Paciente paciente)  {
        //Pasos para guardar en una base de datos

        //1. Creamos una conexión y un objeto preparedStatement
        Connection connection = null;


        try {
            //Esto lo hacemos para después setear el domicilio en la sentencia en el parámetro de domicilioId.
            DomicilioDAOH2 domicilioDAOH2 = new DomicilioDAOH2();
            domicilioDAOH2.guardar(paciente.getDomicilio());


            //2. Levantar el driver y conectarnos
           connection = getConnection();

            //3. Creamos una sentencia
           PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO pacientes(nombre, apellido, dni, fecha_ingreso, domicilio_id) VALUES (?,?,?,?,?)");
            preparedStatement.setString(1, paciente.getNombre());
            preparedStatement.setString(2, paciente.getApellido());
            preparedStatement.setInt(3, paciente.getDni());
            preparedStatement.setDate(4, Date.valueOf(paciente.getFechaIngreso()) );
            preparedStatement.setInt(5, paciente.getDomicilio().getId());


            //4. Ejecutamos sentencia y cerramos
            preparedStatement.executeUpdate();
            preparedStatement.close();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                connection.close();
            }catch (SQLException ex){
                ex.printStackTrace();
            }
        }
        return paciente;

    }

    @Override
    public Paciente buscar(int id) {
        //1. Creamos una conexión, al paciente y domicilio
        Connection connection = null;
        Paciente paciente = null;
        Domicilio domicilio = null;

        try {
            //2. Vamos a buscar el domicilio
            DomicilioDAOH2 domicilioDAOH2 = new DomicilioDAOH2();

            //3. Levantar el driver y conectarnos
            connection = getConnection();

            //3. Creamos una sentencia
           PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM pacientes WHERE id = ?");
            preparedStatement.setInt(1, id);

            //4. Ejecutamos sentencia
            ResultSet result = preparedStatement.executeQuery(); //Me devuelve un resultado
            //5. Recorro los registros de ese resultado
            while (result.next()){
               int id_domicilio = result.getInt(6); //5 porque esta cargado en ese lugar
                domicilio = domicilioDAOH2.buscar(id_domicilio); //Le cargamos el id que acabamos de buscar

                //Cargar datos de paciente en el objeto final
                paciente = new Paciente(result.getInt(1), result.getString(2), result.getString(3), result.getInt(4), result.getDate(5).toLocalDate(),domicilio); //La fecha la transforma de Date a LocalDate


            }
            preparedStatement.close();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                connection.close();
            }catch (SQLException ex){
                ex.printStackTrace();
            }
        }
        return paciente;
    }
}
