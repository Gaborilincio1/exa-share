package com.exashare.Exashare.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.exashare.Exashare.repository.ResenasRepository;
import com.exashare.Exashare.model.Resenas;
import com.exashare.Exashare.model.Usuario;
import com.exashare.Exashare.model.ResenaUsuario;

@SpringBootTest
public class ResenasServiceTest {

    @Autowired
    private ResenasService resenasService;

    @MockBean
    private ResenasRepository resenasRepository;

    private Resenas createResena() {
        ResenaUsuario resenaUsuario = new ResenaUsuario();
        resenaUsuario.setIdResenaUsuario(2);

        Usuario usuario = new Usuario();
        usuario.setIdUsuario(3);

        return new Resenas(1, resenaUsuario, usuario);
    }

    @Test
    public void testObtenerResenas() {
        when(resenasRepository.findAll()).thenReturn(List.of(createResena()));
        List<Resenas> resenas = resenasService.obtenerResenas();
        assertNotNull(resenas);
        assertEquals(1, resenas.size());
    }

    @Test
    public void testObtenerPorId() {
        when(resenasRepository.findById(1L)).thenReturn(java.util.Optional.of(createResena()));
        Resenas resena = resenasService.obtenerPorId(1L);
        assertNotNull(resena);
        assertEquals(2, resena.getResenaUsuario().getIdResenaUsuario());
    }

    @Test
    public void testGuardarResena() {
        Resenas resena = createResena();
        when(resenasRepository.save(resena)).thenReturn(resena);
        Resenas savedResena = resenasService.guardarResenas(resena);
        assertNotNull(savedResena);
        assertEquals(2, savedResena.getResenaUsuario().getIdResenaUsuario());
    }

    @Test
    public void testActualizarResenaParcial() {
        Resenas existingResena = createResena();
        Resenas patchResenas = new Resenas();
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(2);
        patchResenas.setUsuario(usuario);

        when(resenasRepository.findById(1L)).thenReturn(java.util.Optional.of(existingResena));
        when(resenasRepository.save(existingResena)).thenReturn(existingResena);

        Resenas patchedResena = resenasService.actualizarResenasParcial(1L, patchResenas);
        assertNotNull(patchedResena);
        assertEquals(2, patchedResena.getUsuario().getIdUsuario());
    }

    @Test
    public void testEliminarResena() {
        doNothing().when(resenasRepository).deleteById(1L);
        resenasService.eliminarResenas(1L);
        verify(resenasRepository, times(1)).deleteById(1L);
    }

}
