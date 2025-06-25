package com.exashare.Exashare.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.exashare.Exashare.model.Herramienta;
import com.exashare.Exashare.model.Herramientas;
import com.exashare.Exashare.repository.HerramientasRepository;
import com.exashare.Exashare.repository.ResenaHerramientaRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class HerramientasService {

    @Autowired
    private HerramientasRepository herramientasRepository;

    @Autowired
    private ResenaHerramientaRepository resenaHerramientaRepository;

    public List<Herramientas> obtenerHerramientas() {
        return herramientasRepository.findAll();
    }

    public Herramientas obtenerPorId(Long id) {
        return herramientasRepository.findById(id).orElse(null);
    }

    public Herramientas guardarHerramientas(Herramientas herramientas) {
        return herramientasRepository.save(herramientas);
    }

    public void eliminarHerramientas(Long id) {
        herramientasRepository.deleteById(id);
}

    public Herramientas updateHerramientas(Long id, Herramientas herramientas) {
        Herramientas existente = obtenerPorId(id);
        existente.setArriendo(herramientas.getArriendo());
        existente.setHerramienta(herramientas.getHerramienta());
        return herramientasRepository.save(existente);
    }

    public Herramientas actualizarHerramientasParcial(Long id, Herramientas herramientas) {
        Herramientas existente = herramientasRepository.findById(id).orElse(null);
        if (existente == null) {
            return null;
        }
        if (herramientas.getArriendo() != null) {
            existente.setArriendo(herramientas.getArriendo());
        }
        if (herramientas.getHerramienta() != null) {
            existente.setHerramienta(herramientas.getHerramienta());
        }
        return herramientasRepository.save(existente);
    }


    
    public interface HerramientaRepository extends JpaRepository<Herramienta, Long> {
    List<Herramienta> buscarPorRangoPrecio(Integer precioMinimo, Integer precioMaximo);
    }

}