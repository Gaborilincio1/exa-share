package com.exashare.Exashare.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exashare.Exashare.model.Arriendo;
import com.exashare.Exashare.model.Herramienta;
import com.exashare.Exashare.model.Membresia;
import com.exashare.Exashare.model.Resenas;
import com.exashare.Exashare.model.TipoUsuario;
import com.exashare.Exashare.model.Usuario;
import com.exashare.Exashare.repository.ArriendoRepository;
import com.exashare.Exashare.repository.HerramientaRepository;
import com.exashare.Exashare.repository.ResenasRepository;
import com.exashare.Exashare.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class UsuarioService {


    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private HerramientaService herramientaService;

    @Autowired
    private HerramientaRepository herramientaRepository;

    @Autowired
    private ArriendoService arriendoService;

    @Autowired
    private ArriendoRepository arriendoRepository;

    @Autowired
    private ResenasRepository resenasRepository;

    public List<Usuario> obtenerUsuarios() {
        return usuarioRepository.findAll();
    }

    public Usuario obtenerPorId(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public Usuario guardarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

     public void eliminarUsuario(Long id) {

        Usuario usuario = usuarioRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));


        List<Herramienta> herramientas = herramientaRepository.findByUsuario(usuario);
        for (Herramienta herramienta : herramientas) {
            herramientaService.eliminarHerramienta(herramienta.getIdHerramienta().longValue());
        }

        List<Arriendo> arriendos = arriendoRepository.findByUsuario(usuario);
        for (Arriendo arriendo : arriendos) {
            arriendoService.eliminarArriendo(arriendo.getIdArriendo().longValue());
        }

        List<Resenas> resenas = resenasRepository.findByUsuario(usuario);
        for (Resenas resena : resenas) {
            resenasRepository.delete(resena);
        }


        usuarioRepository.delete(usuario);
    }

    public Usuario actualizarUsuario(Long id, Usuario usuario) {
        Usuario usuarioToUpdate = usuarioRepository.findById(id).orElse(null);
        if (usuarioToUpdate != null) {
            usuarioToUpdate.setNombreUsuario(usuario.getNombreUsuario());
            usuarioToUpdate.setEmail(usuario.getEmail());
            usuarioToUpdate.setContraseña(usuario.getContraseña());
            usuarioToUpdate.setDireccion(usuario.getDireccion());
            usuarioToUpdate.setTelefono(usuario.getTelefono());
            return usuarioRepository.save(usuarioToUpdate);
        } else {
            return null;
        }
    }

    public Usuario actualizarUsuarioParcial(Long id, Usuario usuarioParcial) {
        Usuario usuarioExistente = usuarioRepository.findById(id).orElse(null);
        if (usuarioExistente != null) {
            if (usuarioParcial.getNombreUsuario() != null) {
                usuarioExistente.setNombreUsuario(usuarioParcial.getNombreUsuario());
            }
            if (usuarioParcial.getEmail() != null) {
                usuarioExistente.setEmail(usuarioParcial.getEmail());
            }
            if (usuarioParcial.getContraseña() != null) {
                usuarioExistente.setContraseña(usuarioParcial.getContraseña());
            }
            if (usuarioParcial.getDireccion() != null) {
                usuarioExistente.setDireccion(usuarioParcial.getDireccion());
            }
            if (usuarioParcial.getTelefono() != null) {
                usuarioExistente.setTelefono(usuarioParcial.getTelefono());
            }
            return usuarioRepository.save(usuarioExistente);
        } else {
            return null;
        }
    }

    public List<Map<String, Object>> getResenasPorUsuario (Integer id_usuario) {
        return usuarioRepository.getResenasPorUsuario (id_usuario);
    }

    public List<Usuario> findByIdTipoUsuarioAndIdMembresia(TipoUsuario idTipoUsuario, Membresia idMembresia) {
        return usuarioRepository.findByIdTipoUsuarioAndIdMembresia(idTipoUsuario, idMembresia);
    }

    public List<Object[]> getInfoCompletaPorUsuario(Integer id_usuario) {
        return usuarioRepository.getInfoCompletaPorUsuario(id_usuario);
    }
}

