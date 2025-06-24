package com.exashare.Exashare.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.exashare.Exashare.repository.HerramientaRepository;
import com.exashare.Exashare.model.Herramienta;
import com.exashare.Exashare.model.Usuario;

@SpringBootTest
public class HerramientaServiceTest {

    @Autowired
    private HerramientaService herramientaService;

    @MockBean
    private HerramientaRepository herramientaRepository;

    private Herramienta createHerramienta() {
        return new Herramienta(
            1, 
            "Martillo", 
            "Martillo de carpintero", 
            "Herramientas manuales", 
            "Disponible", 
            "Bodega principal", 
            15000, 
            new Usuario(), 
            null
        );
    }

    @Test
    public void testObtenerHerramientas() {
        when(herramientaRepository.findAll()).thenReturn(List.of(createHerramienta()));
        List<Herramienta> herramientas = herramientaService.obtenerHerramientas();
        assertNotNull(herramientas);
        assertFalse(herramientas.isEmpty());
        assertEquals(1, herramientas.size());
    }

    @Test
    public void testObtenerPorId() {
        when(herramientaRepository.findById(1L)).thenReturn(java.util.Optional.of(createHerramienta())); 
        Herramienta herramienta = herramientaService.obtenerPorId(1L);
        assertNotNull(herramienta);
        assertEquals("Martillo", herramienta.getNombre());
    }

    @Test
    public void testGuardarHerramienta() {
        Herramienta herramienta = createHerramienta();
        when(herramientaRepository.save(any(Herramienta.class))).thenReturn(herramienta);
        Herramienta savedHerramienta = herramientaService.guardarHerramienta(herramienta);
        assertNotNull(savedHerramienta);
        assertEquals("Martillo", savedHerramienta.getNombre());
    }

    @Test
    public void testActualizarHerramientaParcial() {
        Herramienta existingHerramienta = createHerramienta();
        Herramienta patchHerramienta = new Herramienta();
        patchHerramienta.setNombre("Martillo actualizado");

        when(herramientaRepository.findById(1L)).thenReturn(java.util.Optional.of(existingHerramienta));
        when(herramientaRepository.save(any(Herramienta.class))).thenReturn(existingHerramienta);

        Herramienta patchedHerramienta = herramientaService.actualizarHerramientaParcial(1L, patchHerramienta);
        assertNotNull(patchedHerramienta);
        assertEquals("Martillo actualizado", patchedHerramienta.getNombre());
    }

    @Test
    public void testEliminarHerramienta() {
        doNothing().when(herramientaRepository).deleteById(1L);
        herramientaService.eliminarHerramienta(1L);
        verify(herramientaRepository, times(1)).deleteById(1L);
    }

}
