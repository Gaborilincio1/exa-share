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

import com.exashare.Exashare.repository.ArriendoRepository;
import com.exashare.Exashare.model.Arriendo;

import com.exashare.Exashare.model.Usuario;


@SpringBootTest
public class ArriendoServiceTest {

    @Autowired
    private ArriendoService arriendoService;

    @MockBean
    private ArriendoRepository arriendoRepository;

    private Arriendo createArriendo() {

        Usuario usuario = new Usuario();
        usuario.setIdUsuario(1);

        return new Arriendo(
            1, 
            new Date(), 
            new Date(), 
            "Activo", 
            1000, 
            null);
    }

    @Test
    public void testObtenerArriendos() {
        when(arriendoRepository.findAll()).thenReturn(List.of(createArriendo()));
        List<Arriendo> arriendos = arriendoService.obtenerArriendos();
        assertNotNull(arriendos);
        assertEquals(1, arriendos.size());
    }

    @Test
    public void testObtenerPorId() {
        when(arriendoRepository.findById(1L)).thenReturn(java.util.Optional.of(createArriendo()));
        Arriendo arriendo = arriendoService.obtenerPorId(1L);
        assertNotNull(arriendo);
        assertEquals(1000, arriendo.getPrecioArriendo());
    }

    @Test
    public void testGuardarArriendo() {
        Arriendo arriendo = createArriendo();
        when(arriendoRepository.save(arriendo)).thenReturn(arriendo);
        Arriendo savedArriendo = arriendoService.guardarArriendo(arriendo);
        assertNotNull(savedArriendo);
        assertEquals(1000, savedArriendo.getPrecioArriendo());
    }

    @Test
    public void testActualizarArriendoParcial(){
        Arriendo existingArriendo = createArriendo();
        Arriendo patchArriendo = new Arriendo();
        patchArriendo.setPrecioArriendo(99999);

        when(arriendoRepository.findById(1L)).thenReturn(java.util.Optional.of(existingArriendo));
        when(arriendoRepository.save(any(Arriendo.class))).thenReturn(existingArriendo);

        Arriendo patchedArriendo = arriendoService.actualizarArriendoParcial(1L, patchArriendo);
        assertNotNull(patchedArriendo);
        assertEquals(99999,patchedArriendo.getPrecioArriendo());
    }

    @Test
    public void testEliminarArriendo(){
        doNothing().when(arriendoRepository).deleteById(1L);
        arriendoService.eliminarArriendo(1L);
        verify(arriendoRepository, times(1)).deleteById(1L);
    }
}
