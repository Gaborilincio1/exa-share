package com.exashare.Exashare.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exashare.Exashare.model.Resenas;
import com.exashare.Exashare.repository.ResenasRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ResenasService {

    @Autowired
    private ResenasRepository resenasRepository;

    public List<Resenas> obtenerResenas() {
        return resenasRepository.findAll();
    }

    public Resenas obtenerPorId(Long id) {
        return resenasRepository.findById(id).orElse(null);
    }

    public Resenas guardarResenas(Resenas resenas) {
        return resenasRepository.save(resenas);
    }

    public void eliminarResenas(Long id) {
        resenasRepository.deleteById(id);
    }

    public Resenas updateResenas(Long id, Resenas resenas) {
        Resenas existente = obtenerPorId(id);
        existente.setResenaUsuario(resenas.getResenaUsuario());
        existente.setUsuario(resenas.getUsuario());
        return resenasRepository.save(existente);
    }


    public Resenas actualizarResenasParcial(Long id, Resenas resenas) {
        Resenas existente = resenasRepository.findById(id).orElse(null);
        if (existente == null) {
            return null;
        }
        if (resenas.getResenaUsuario() != null) {
            existente.setResenaUsuario(resenas.getResenaUsuario());
        }
        if (resenas.getUsuario() != null) {
            existente.setUsuario(resenas.getUsuario());
        }
        return resenasRepository.save(existente);
    }
}


