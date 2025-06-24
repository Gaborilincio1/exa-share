package com.exashare.Exashare.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.exashare.Exashare.repository.MembresiaRepository;
import com.exashare.Exashare.model.Membresia;

@SpringBootTest
public class MembresiaServiceTest {

    @Autowired
    private MembresiaService membresiaService;

    @MockBean
    private MembresiaRepository membresiaRepository;

    private Membresia createMembresia() {
        return new Membresia(
            1, 
            "Premium", 
            new Date(), 
            new Date(), 
            "Acceso a todas las herramientas y arriendos");
    }

    @Test
    public void testObtenerMembresias() {
        when(membresiaRepository.findAll()).thenReturn(List.of(createMembresia()));
        List<Membresia> membresias = membresiaService.obtenerMembresias();
        assertNotNull(membresias);
        assertEquals(1, membresias.size());
    }

    @Test
    public void testObtenerPorId() {
        when(membresiaRepository.findById(1L)).thenReturn(java.util.Optional.of(createMembresia()));
        Membresia membresia = membresiaService.obtenerPorId(1);
        assertNotNull(membresia);
        assertEquals("Premium", membresia.getTipo());
    }

    @Test
    public void testGuardarMembresia() {
        Membresia membresia = createMembresia();
        when(membresiaRepository.save(membresia)).thenReturn(membresia);
        Membresia savedMembresia = membresiaService.guardarMembresia(membresia);
        assertNotNull(savedMembresia);
        assertEquals("Premium", savedMembresia.getTipo());
    }

    @Test
    public void testActualizarMembresiaParcial(){
        Membresia existingMembresia = createMembresia();
        Membresia patchMembresia = new Membresia();
        patchMembresia.setTipo("Basic");
        
        when(membresiaRepository.findById(1L)).thenReturn(java.util.Optional.of(existingMembresia));
        when(membresiaRepository.save(any(Membresia.class))).thenReturn(existingMembresia);

        Membresia patchedMembresia = membresiaService.actualizarMembresiaParcial(1L, patchMembresia);
        assertNotNull(patchedMembresia);
        assertEquals("Basic", patchedMembresia.getTipo());
    }

    @Test
    public void testEliminarMembresia(){
        doNothing().when(membresiaRepository).deleteById(1L);
        membresiaService.eliminarMembresia(1L);
        verify(membresiaRepository, times(1)).deleteById(1L);
    }

}
