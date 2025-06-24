package com.exashare.Exashare.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exashare.Exashare.model.Membresia;
import com.exashare.Exashare.model.Usuario;
import com.exashare.Exashare.repository.MembresiaRepository;
import com.exashare.Exashare.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class MembresiaService {


    @Autowired
    private MembresiaRepository membresiaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<Membresia> obtenerMembresias(){
        return membresiaRepository.findAll();
    }

    public Membresia obtenerPorId(long id) {
        return membresiaRepository.findById(id).orElse(null);
    }

    public Membresia guardarMembresia(Membresia membresia) {
        return membresiaRepository.save(membresia);
    }

    public void eliminarMembresia(Long id) {
        Membresia membresia = membresiaRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Membresía no encontrada"));


    List<Usuario> usuarios = usuarioRepository.findByMembresia(membresia);
    for (Usuario usuario : usuarios) {
        usuarioRepository.delete(usuario);
    }

    membresiaRepository.delete(membresia);
    }

    public Membresia updateMembresia(Long id, Membresia membresia) {
        Membresia membresiaToUpdate = membresiaRepository.findById(id).orElse(null);
        if (membresiaToUpdate != null) {
            membresiaToUpdate.setTipo(membresia.getTipo());
            membresiaToUpdate.setFechaInicio(membresia.getFechaInicio());
            membresiaToUpdate.setFechaFin(membresia.getFechaFin());
            membresiaToUpdate.setBeneficios(membresia.getBeneficios());
            return membresiaRepository.save(membresiaToUpdate);
        } else {
            return null;
        }
    }

    public Membresia actualizarMembresiaParcial(Long id, Membresia membresiaParcial) {
        Membresia membresiaExistente = membresiaRepository.findById(id).orElse(null);
        if (membresiaExistente != null) {
            if (membresiaParcial.getTipo() != null) {
                membresiaExistente.setTipo(membresiaParcial.getTipo());
            }
            if (membresiaParcial.getFechaInicio() != null) {
                membresiaExistente.setFechaInicio(membresiaParcial.getFechaInicio());
            }
            if (membresiaParcial.getFechaFin() != null) {
                membresiaExistente.setFechaFin(membresiaParcial.getFechaFin());
            }
            if (membresiaParcial.getBeneficios() != null) {
                membresiaExistente.setBeneficios(membresiaParcial.getBeneficios());
            }
            return membresiaRepository.save(membresiaExistente);
        } else {
            return null;
        }
    }

}
