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

import com.exashare.Exashare.repository.ResenaHerramientaRepository;
import com.exashare.Exashare.model.Herramienta;
import com.exashare.Exashare.model.ResenaHerramienta;

@SpringBootTest
public class ResenaHerramientaServiceTest {

    @Autowired
    private ResenaHerramientaService resenaHerramientaService;

    @MockBean
    private ResenaHerramientaRepository resenaHerramientaRepository;

    private ResenaHerramienta createResenaHerramienta() {
        Herramienta herramienta = new Herramienta();
        herramienta.setIdHerramienta(1);

        return new ResenaHerramienta(
            1, 
            5, 
            "Excelente herramienta", 
            new Date(), 
            herramienta 
        );
    }

    @Test
    public void testObtenerResenas() {
        when(resenaHerramientaRepository.findAll()).thenReturn(List.of(createResenaHerramienta()));
        List<ResenaHerramienta> resenas = resenaHerramientaService.obtenerResenas();
        assertNotNull(resenas);
        assertEquals(1, resenas.size());
    }

    @Test
    public void testObtenerPorId() {
        when(resenaHerramientaRepository.findById(1L)).thenReturn(java.util.Optional.of(createResenaHerramienta()));
        ResenaHerramienta resena = resenaHerramientaService.obtenerPorId(1L);
        assertNotNull(resena);
        assertEquals(5, resena.getPuntuacion());
    }

    @Test
    public void testGuardarResenaHerramienta() {
        ResenaHerramienta resena = createResenaHerramienta();
        when(resenaHerramientaRepository.save(resena)).thenReturn(resena);
        ResenaHerramienta savedResena = resenaHerramientaService.guardarResena(resena);
        assertNotNull(savedResena);
        assertEquals(5, savedResena.getPuntuacion());
    }


    @Test
    public void testActualizarResenaParcial() {
        ResenaHerramienta existingResena = createResenaHerramienta();
        ResenaHerramienta patchResena = new ResenaHerramienta();
        patchResena.setComentario("Muy buena herramienta");

        when(resenaHerramientaRepository.findById(1L)).thenReturn(java.util.Optional.of(existingResena));
        when(resenaHerramientaRepository.save(any(ResenaHerramienta.class))).thenReturn(existingResena);

        ResenaHerramienta patchedResena = resenaHerramientaService.actualizarResenaParcial(1L, patchResena);
        assertNotNull(patchedResena);
        assertEquals("Muy buena herramienta", patchedResena.getComentario());
    }

    @Test
    public void testEliminarResena() {
        doNothing().when(resenaHerramientaRepository).deleteById(1L);
        resenaHerramientaService.eliminarResena(1L);
        verify(resenaHerramientaRepository, times(1)).deleteById(1L);
    }


}
