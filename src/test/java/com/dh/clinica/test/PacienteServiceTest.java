package com.dh.clinica.test;

import com.dh.clinica.main.dao.impl.PacienteDAOH2;
import com.dh.clinica.main.model.Domicilio;
import com.dh.clinica.main.model.Paciente;
import com.dh.clinica.main.service.PacienteService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;


public class PacienteServiceTest {
    @BeforeAll //Nos conectamos a la base y ejecutamos el script
    public  static void cargarBaseDeDatos(){
        Connection connection = null;
        try{
            //Nos conectamos al driver y creamos la tabla desde acá con el comando init, la tabla esta seteada en el archivo create.sql
            Class.forName("org.h2.Driver").newInstance();
            connection = DriverManager.getConnection("jdbc:h2:~/db_pacientes;INIT=RUNSCRIPT FROM 'create.sql'", "sa", "");

        }catch (Exception e){
            e.printStackTrace();

        }finally {
            try{
                connection.close();
            }catch (SQLException ex){
                ex.printStackTrace();
            }
        }
    }
    @Test
    public void guardarPaciente(){
        PacienteService pacienteService = new PacienteService(new PacienteDAOH2());
        Domicilio domicilio = new Domicilio("Calle", 1234, "Localidad", "Provincia");
        Paciente paciente = new Paciente("Laura", "Anderson", 39185319, LocalDate.of(2022, 06, 1), domicilio);
        Paciente pacienteResultado;
        int resultadoEsperado = 3;

        pacienteService.guardarPaciente(paciente);
        pacienteResultado = pacienteService.buscarPaciente(3);//Estoy esperando que me devuelva el id=3 porque cargue 3 pacientes

        Assert.assertEquals(pacienteResultado.getId(), resultadoEsperado);
    }
}
