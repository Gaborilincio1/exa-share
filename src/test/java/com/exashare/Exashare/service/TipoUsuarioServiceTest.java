package com.exashare.Exashare.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.exashare.Exashare.repository.TipoUsuarioRepository;
import com.exashare.Exashare.model.TipoUsuario;


@SpringBootTest
public class TipoUsuarioServiceTest {

    @Autowired
    private TipoUsuarioService tipoUsuarioService;

    @MockBean
    private TipoUsuarioRepository tipoUsuarioRepository;

    private TipoUsuario createTipoUsuario() {
        return new TipoUsuario(1, "Admin", "Administrador");
    }

    @Test
    public void testObtenerTipos() {
        when(tipoUsuarioRepository.findAll()).thenReturn(List.of(createTipoUsuario()));
        List<TipoUsuario> tiposUsuario = tipoUsuarioService.obtenerTiposUsuario();
        assertNotNull(tiposUsuario);
        assertFalse(tiposUsuario.isEmpty());
        assertEquals(1, tiposUsuario.size());
    }

    @Test
    public void testObtenerPorId() {
        when(tipoUsuarioRepository.findById(1L)).thenReturn(java.util.Optional.of(createTipoUsuario()));
        TipoUsuario tipoUsuario = tipoUsuarioService.obtenerPorId(1);
        assertNotNull(tipoUsuario);
        assertEquals(1, tipoUsuario.getIdTipoUsuario());
    }

    @Test
    public void testGuardarTipo() {
        TipoUsuario tipoUsuario = createTipoUsuario();
        when(tipoUsuarioRepository.save(any(TipoUsuario.class))).thenReturn(tipoUsuario);
        TipoUsuario savedTipo = tipoUsuarioService.guardarTipoUsuario(tipoUsuario);
        assertNotNull(savedTipo);
        assertEquals(tipoUsuario.getIdTipoUsuario(), savedTipo.getIdTipoUsuario());
    }

    @Test
    public void testActualizarTipoParcial() {
        TipoUsuario tipoUsuario = createTipoUsuario();
        TipoUsuario tipoUsuarioPatch = new TipoUsuario();
        tipoUsuarioPatch.setDescripcionTipoUsuario("Nuevo Administrador");

        when(tipoUsuarioRepository.findById(1L)).thenReturn(java.util.Optional.of(tipoUsuario));
        when(tipoUsuarioRepository.save(any(TipoUsuario.class))).thenReturn(tipoUsuario);

        TipoUsuario patchedTipo = tipoUsuarioService.actualizarTipoUsuarioParcial(1L, tipoUsuarioPatch);
        assertNotNull(patchedTipo);
        assertEquals(tipoUsuario.getIdTipoUsuario(), patchedTipo.getIdTipoUsuario());
    }

    @Test
    public void testEliminarTipo() {
        doNothing().when(tipoUsuarioRepository).deleteById(1L);
        tipoUsuarioService.eliminarTipoUsuario(1L);
        verify(tipoUsuarioRepository, times(1)).deleteById(1L);
    }

}
