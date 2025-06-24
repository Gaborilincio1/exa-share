package com.exashare.Exashare.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.exashare.Exashare.repository.ResenaUsuarioRepository;
import com.exashare.Exashare.model.ResenaUsuario;

@SpringBootTest
public class ResenaUsuarioServiceTest {

    @Autowired
    private ResenaUsuarioService resenaUsuarioService;

    @MockBean
    private ResenaUsuarioRepository resenaUsuarioRepository;

    private ResenaUsuario createResenaUsuario() {
        return new ResenaUsuario(
            1,
            1, 
            "muy bueno", 
            new Date(), 
            null);
    }

    @Test
    public void testObtenerResenas() {
        when(resenaUsuarioRepository.findAll()).thenReturn(List.of(createResenaUsuario()));
        List<ResenaUsuario> resenas = resenaUsuarioService.obtenerResenas();
        assertNotNull(resenas);
        assertFalse(resenas.isEmpty());
        assertEquals(1, resenas.size());
    }

    @Test
    public void testObtenerPorId() {
        when(resenaUsuarioRepository.findById(1L)).thenReturn(java.util.Optional.of(createResenaUsuario()));
        ResenaUsuario resena = resenaUsuarioService.obtenerPorId(1);
        assertNotNull(resena);
        assertEquals(1, resena.getPuntuacion());
    }

    @Test
    public void testGuardarResena() {
        ResenaUsuario resena = createResenaUsuario();
        when(resenaUsuarioRepository.save(any(ResenaUsuario.class))).thenReturn(resena);
        ResenaUsuario savedResena = resenaUsuarioService.guardarResena(resena);
        assertNotNull(savedResena);
        assertEquals(1, savedResena.getPuntuacion());
    }

    @Test
    public void testActualizarResenaParcial() {
        ResenaUsuario resena = createResenaUsuario();
        ResenaUsuario resenaPatch = new ResenaUsuario();
        resenaPatch.setPuntuacion(2);

        when(resenaUsuarioRepository.findById(1L)).thenReturn(java.util.Optional.of(resena));
        when(resenaUsuarioRepository.save(any(ResenaUsuario.class))).thenReturn(resena);

        ResenaUsuario patchedResena = resenaUsuarioService.actualizarResenaParcial(1L, resenaPatch);
        assertNotNull(patchedResena);
        assertEquals(2, patchedResena.getPuntuacion());
    }

    @Test
    public void testEliminarResena() {
        doNothing().when(resenaUsuarioRepository).deleteById(1L);
        resenaUsuarioService.eliminarResena(1L);
        verify(resenaUsuarioRepository, times(1)).deleteById(1L);
    }
}
