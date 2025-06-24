package com.exashare.Exashare.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.exashare.Exashare.repository.HerramientasRepository;
import com.exashare.Exashare.model.Herramientas;
import com.exashare.Exashare.model.Arriendo;
import com.exashare.Exashare.model.Herramienta;

@SpringBootTest
public class HerramientasServiceTest {

    @Autowired
    private HerramientasService herramientasService;

    @MockBean
    private HerramientasRepository herramientasRepository;

    private Herramientas createHerramientas() {
        Herramienta herramienta = new Herramienta();
        herramienta.setIdHerramienta(1);

        Arriendo arriendo = new Arriendo();
        arriendo.setIdArriendo(1);
        arriendo.setPrecioArriendo(1000);

        return new Herramientas(
            1, 
            arriendo, 
            herramienta);
        }

   @Test
    public void testObtenerHerramientas() {
        when(herramientasRepository.findAll()).thenReturn(List.of(createHerramientas()));
        List<Herramientas> herramientas = herramientasService.obtenerHerramientas(); 
        assertNotNull(herramientas);
        assertEquals(1, herramientas.size());
    }

    @Test
    public void testObtenerPorId() {
        when(herramientasRepository.findById(1L)).thenReturn(java.util.Optional.of(createHerramientas()));
        Herramientas herramientas = herramientasService.obtenerPorId(1L);
        assertNotNull(herramientas);
        assertEquals(1000, herramientas.getArriendo().getPrecioArriendo());
    }

    @Test
    public void testGuardarHerramientas() {
        Herramientas herramientas = createHerramientas();
        when(herramientasRepository.save(any(Herramientas.class))).thenReturn(herramientas);
        Herramientas saved = herramientasService.guardarHerramientas(herramientas); 
        assertNotNull(saved);
        assertEquals(1000, saved.getArriendo().getPrecioArriendo());
    }

    @Test
    public void testActualizarHerramientasParcial() {
        Herramientas existingHerramientas = createHerramientas();
        Herramientas patchHerramientas = new Herramientas();
        Arriendo nuevoArriendo = new Arriendo();
        nuevoArriendo.setIdArriendo(2);
        patchHerramientas.setArriendo(nuevoArriendo);

        when(herramientasRepository.findById(1L)).thenReturn(java.util.Optional.of(existingHerramientas));
        when(herramientasRepository.save(any(Herramientas.class))).thenReturn(existingHerramientas);

        Herramientas patchedHerramientas = herramientasService.actualizarHerramientasParcial(1L, patchHerramientas);
        assertNotNull(patchedHerramientas);
        assertEquals(2, patchedHerramientas.getArriendo().getIdArriendo());
    }

    @Test
    public void testUpdateHerramientas() {
        Herramientas existingHerramientas = createHerramientas();
        Herramientas updateHerramientas = new Herramientas();
        
        Arriendo nuevoArriendo = new Arriendo();
        nuevoArriendo.setIdArriendo(2);
        updateHerramientas.setArriendo(nuevoArriendo);
        Herramienta nuevaHerramienta = new Herramienta();
        nuevaHerramienta.setIdHerramienta(2);
        updateHerramientas.setHerramienta(nuevaHerramienta);

        when(herramientasRepository.findById(1L)).thenReturn(java.util.Optional.of(existingHerramientas));
        when(herramientasRepository.save(any(Herramientas.class))).thenReturn(existingHerramientas);

        Herramientas updatedHerramientas = herramientasService.updateHerramientas(1L, updateHerramientas);

        assertNotNull(updatedHerramientas);
        assertEquals(2, updatedHerramientas.getArriendo().getIdArriendo());
        assertEquals(2, updatedHerramientas.getHerramienta().getIdHerramienta());
    }

    @Test
    public void testEliminarHerramientas() {
        doNothing().when(herramientasRepository).deleteById(1L);
        herramientasService.eliminarHerramientas(1L);
        verify(herramientasRepository, times(1)).deleteById(1L);
    }


}
