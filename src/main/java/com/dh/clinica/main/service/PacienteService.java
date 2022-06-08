package com.dh.clinica.main.service;

import com.dh.clinica.main.dao.IDAO;
import com.dh.clinica.main.dao.impl.PacienteDAOH2;
import com.dh.clinica.main.model.Paciente;



public class PacienteService {
    private IDAO<Paciente> pacienteIDAO;

    public PacienteService(PacienteDAOH2 pacienteIDAO) {
        this.pacienteIDAO = pacienteIDAO;
    }

    //Métodos que delega la responsabilidad de las acciones al Dao
    public void guardarPaciente(Paciente paciente){
        pacienteIDAO.guardar(paciente);
    }


    public Paciente buscarPaciente(int id){
        return pacienteIDAO.buscar(id);
    }
}