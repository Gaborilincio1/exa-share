package com.exashare.Exashare.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exashare.Exashare.model.TipoUsuario;
import com.exashare.Exashare.model.Usuario;
import com.exashare.Exashare.repository.TipoUsuarioRepository;
import com.exashare.Exashare.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class TipoUsuarioService {

    @Autowired
    private TipoUsuarioRepository tipoUsuarioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<TipoUsuario> obtenerTiposUsuario() {
        return tipoUsuarioRepository.findAll();
    }

    public TipoUsuario obtenerPorId(long id) {
        return tipoUsuarioRepository.findById(id).orElse(null);
    }

    public TipoUsuario guardarTipoUsuario(TipoUsuario tipoUsuario) {
        return tipoUsuarioRepository.save(tipoUsuario);
    }

    public void eliminarTipoUsuario(Long id) {
        TipoUsuario tipoUsuario = tipoUsuarioRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("TipoUsuario no encontrado"));

    List<Usuario> usuarios = usuarioRepository.findByTipoUsuario(tipoUsuario);
    for (Usuario usuario : usuarios) {
        usuarioRepository.delete(usuario);
    }

    tipoUsuarioRepository.delete(tipoUsuario);
}

    public TipoUsuario actualizarTipoUsuario(Long id, TipoUsuario tipoUsuario) {
        TipoUsuario tipoToUpdate = tipoUsuarioRepository.findById(id).orElse(null);
        if (tipoToUpdate != null) {
            tipoToUpdate.setNombreTipoUsuario(tipoUsuario.getNombreTipoUsuario());
            tipoToUpdate.setDescripcionTipoUsuario(tipoUsuario.getDescripcionTipoUsuario());
            return tipoUsuarioRepository.save(tipoToUpdate);
        } else {
            return null;
        }
    }

    public TipoUsuario actualizarTipoUsuarioParcial(Long id, TipoUsuario tipoUsuarioParcial) {
        TipoUsuario tipoExistente = tipoUsuarioRepository.findById(id).orElse(null);
        if (tipoExistente != null) {
            if (tipoUsuarioParcial.getNombreTipoUsuario() != null) {
                tipoExistente.setNombreTipoUsuario(tipoUsuarioParcial.getNombreTipoUsuario());
            }
            if (tipoUsuarioParcial.getDescripcionTipoUsuario() != null) {
                tipoExistente.setDescripcionTipoUsuario(tipoUsuarioParcial.getDescripcionTipoUsuario());
            }
            return tipoUsuarioRepository.save(tipoExistente);
        } else {
            return null;
        }
    }

}
