package com.exashare.Exashare.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exashare.Exashare.model.Herramientas;
import com.exashare.Exashare.model.ResenaHerramienta;
import com.exashare.Exashare.repository.HerramientasRepository;
import com.exashare.Exashare.repository.ResenaHerramientaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ResenaHerramientaService {


    @Autowired
    private ResenaHerramientaRepository resenaHerramientaRepository;

    @Autowired
    private HerramientasRepository herramientasRepository;

    public List<ResenaHerramienta> obtenerResenas() {
        return resenaHerramientaRepository.findAll();
    }

    public ResenaHerramienta obtenerPorId(long id) {
        return resenaHerramientaRepository.findById(id).orElse(null);
    }

    public ResenaHerramienta guardarResena(ResenaHerramienta resena) {
        return resenaHerramientaRepository.save(resena);
    }

    public void eliminarResena(Long id) {
        Herramientas herramientas = herramientasRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Relación Herramientas no encontrada"));

    List<ResenaHerramienta> resenas = resenaHerramientaRepository.findByHerramientas(herramientas);
    for (ResenaHerramienta resena : resenas) {
        resenaHerramientaRepository.delete(resena);
    }

    herramientasRepository.delete(herramientas);
    }

    public ResenaHerramienta updateResenaHerramienta(Long id, ResenaHerramienta resenaHerramienta) {
        ResenaHerramienta resenaherramientaToUpdate = resenaHerramientaRepository.findById(id).orElse(null);
        if (resenaherramientaToUpdate != null) {
            resenaherramientaToUpdate.setPuntuacion(resenaHerramienta.getPuntuacion());
            resenaherramientaToUpdate.setComentario(resenaHerramienta.getComentario());
            resenaherramientaToUpdate.setFecha(resenaHerramienta.getFecha());
            return resenaHerramientaRepository.save(resenaherramientaToUpdate);
        } else {
            return null;
        }
    }
    

    public ResenaHerramienta actualizarResenaParcial(Long id, ResenaHerramienta resenaParcial) {
        ResenaHerramienta resenaExistente = resenaHerramientaRepository.findById(id).orElse(null);
        if (resenaExistente != null) {
            if (resenaParcial.getPuntuacion() != null) {
                resenaExistente.setPuntuacion(resenaParcial.getPuntuacion());
            }
            if (resenaParcial.getComentario() != null) {
                resenaExistente.setComentario(resenaParcial.getComentario());
            }
            if (resenaParcial.getFecha() != null) {
                resenaExistente.setFecha(resenaParcial.getFecha());
            }
            return resenaHerramientaRepository.save(resenaExistente);
        } else {
            return null;
        }
    }

}
