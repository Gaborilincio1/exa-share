package com.exashare.Exashare.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exashare.Exashare.model.Arriendo;
import com.exashare.Exashare.repository.ArriendoRepository;
import com.exashare.Exashare.repository.ResenasRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ArriendoService {

    @Autowired
    private ArriendoRepository arriendoRepository;

    @Autowired
    private ResenasRepository resenasRepository;



    public List<Arriendo> obtenerArriendos() {
        return arriendoRepository.findAll();
    }

    public Arriendo obtenerPorId(Long id) {
        return arriendoRepository.findById(id).orElse(null);
    }

    public Arriendo guardarArriendo(Arriendo arriendo) {
        return arriendoRepository.save(arriendo);
    }
    
    public void eliminarArriendo(Long id) {
        
        arriendoRepository.deleteById(id);
    }


    public Arriendo updateArriendo(Long id, Arriendo arriendo) {
        Arriendo arriendoToUpdate = arriendoRepository.findById(id).orElse(null);
        if (arriendoToUpdate != null) {
            arriendoToUpdate.setFechaInicio(arriendo.getFechaInicio());
            arriendoToUpdate.setFechaFin(arriendo.getFechaFin());
            arriendoToUpdate.setEstado(arriendo.getEstado());
            arriendoToUpdate.setPrecioArriendo(arriendo.getPrecioArriendo());
            return arriendoRepository.save(arriendoToUpdate);
        } else {
            return null;
        }
    }

    public Arriendo actualizarArriendoParcial(Long id, Arriendo arriendoParcial) {
        Arriendo arriendoExistente = arriendoRepository.findById(id).orElse(null);
        if (arriendoExistente != null) {
            if (arriendoParcial.getFechaInicio() != null) {
                arriendoExistente.setFechaInicio(arriendoParcial.getFechaInicio());
            }
            if (arriendoParcial.getFechaFin() != null) {
                arriendoExistente.setFechaFin(arriendoParcial.getFechaFin());
            }
            if (arriendoParcial.getEstado() != null) {
                arriendoExistente.setEstado(arriendoParcial.getEstado());
            }
            if (arriendoParcial.getPrecioArriendo() != null) {
                arriendoExistente.setPrecioArriendo(arriendoParcial.getPrecioArriendo());
            }
            return arriendoRepository.save(arriendoExistente);
        } else {
            return null;
        }
    }

    public List<Map<String, Object>> getArriendoConUsuarioYHerramienta() {
    return arriendoRepository.getArriendoConUsuarioYHerramienta();
    }

    public List<Arriendo> findByFechaInicioBetween(Date fechaInicio, Date fechaFin) {
        return arriendoRepository.findByFechaInicioBetween(fechaInicio, fechaFin);
    }

    public List<Arriendo> findByEstadoAndFechaInicioBetween(String estado, Date fechaInicio, Date fechaFin) {
        return arriendoRepository.findByEstadoAndFechaInicioBetween(estado, fechaInicio, fechaFin);
    }

    

}
