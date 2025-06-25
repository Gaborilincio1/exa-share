package com.exashare.Exashare.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.exashare.Exashare.repository.UsuarioRepository;
import com.exashare.Exashare.model.Membresia;
import com.exashare.Exashare.model.TipoUsuario;
import com.exashare.Exashare.model.Usuario;

@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
    private UsuarioService usuarioService;

    @MockBean
    private UsuarioRepository usuarioRepository;

    private Usuario createUsuario() {
        return new Usuario(
            1, 
            "test", 
            "test@test.com", 
            "test123", 
            "Test Address", 
            "123456789", 
            new Membresia(1, "test", new Date(), new Date(), "null"),
            new TipoUsuario(1, "Usuario", "Descripción Usuario"), 
            null);
    }

    @Test
    public void testObtenerUsuarios() {
        when(usuarioRepository.findAll()).thenReturn(List.of(createUsuario()));
        List<Usuario> usuarios = usuarioService.obtenerUsuarios();
        assertNotNull(usuarios);
        assertEquals(1, usuarios.size());
    }

    @Test
    public void testObtenerPorId() {
        when(usuarioRepository.findById(1L)).thenReturn(java.util.Optional.of(createUsuario()));
        Usuario usuario = usuarioService.obtenerPorId(1L);
        assertNotNull(usuario);
        assertEquals(1, usuario.getIdUsuario());
    }

    @Test
    public void testGuardarUsuario() {
        Usuario usuario = createUsuario();
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);
        Usuario savedUsuario = usuarioService.guardarUsuario(usuario);
        assertNotNull(savedUsuario);
        assertEquals(usuario.getIdUsuario(), savedUsuario.getIdUsuario());
    }

    @Test
    public void testActualizarUsuarioParcial() {
        Usuario existingUsuario = createUsuario();
        Usuario patchUsuario = new Usuario();
        patchUsuario.setEmail("nuevo@test.com");

        when(usuarioRepository.findById(1L)).thenReturn(java.util.Optional.of(existingUsuario));
        when(usuarioRepository.save(any(Usuario.class))).thenReturn(existingUsuario);

        Usuario patchedUsuario = usuarioService.actualizarUsuarioParcial(1L, patchUsuario);
        assertNotNull(patchedUsuario);
        assertEquals("nuevo@test.com", patchedUsuario.getEmail());
    }

    @Test
    public void testEliminarUsuario() {
        doNothing().when(usuarioRepository).deleteById(1L);
        usuarioService.eliminarUsuario(1L);
        verify(usuarioRepository, times(1)).deleteById(1L);
    }

}
