package com.exashare.Exashare.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exashare.Exashare.model.ResenaUsuario;
import com.exashare.Exashare.model.Resenas;
import com.exashare.Exashare.repository.ResenaUsuarioRepository;
import com.exashare.Exashare.repository.ResenasRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ResenaUsuarioService {


    @Autowired
    private ResenaUsuarioRepository resenaUsuarioRepository;

    @Autowired
    private ResenasRepository resenasRepository;



    public List<ResenaUsuario> obtenerResenas() {
        return resenaUsuarioRepository.findAll();
    }

    public ResenaUsuario obtenerPorId(long id) {
        return resenaUsuarioRepository.findById(id).orElse(null);
    }

    public ResenaUsuario guardarResena(ResenaUsuario resena) {
        return resenaUsuarioRepository.save(resena);
    }

public void eliminarResena(Long id) {
    ResenaUsuario resenaUsuario = resenaUsuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("ResenaUsuario no encontrada"));

        List<Resenas> resenas = resenasRepository.findByResenaUsuario(resenaUsuario);
        for (Resenas resena : resenas) {
            resenasRepository.delete(resena);
        }

        resenaUsuarioRepository.delete(resenaUsuario);
    }


    public ResenaUsuario actualizarResena(Long id, ResenaUsuario resena) {
        ResenaUsuario resenaToUpdate = resenaUsuarioRepository.findById(id).orElse(null);
        if (resenaToUpdate != null) {
            resenaToUpdate.setPuntuacion(resena.getPuntuacion());
            resenaToUpdate.setComentario(resena.getComentario());
            resenaToUpdate.setFecha(resena.getFecha());
            return resenaUsuarioRepository.save(resenaToUpdate);
        } else {
            return null;
        }
    }

    public ResenaUsuario actualizarResenaParcial(Long id, ResenaUsuario resenaParcial) {
        ResenaUsuario resenaExistente = resenaUsuarioRepository.findById(id).orElse(null);
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
            return resenaUsuarioRepository.save(resenaExistente);
        } else {
            return null;
        }
    }
   
}
